/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bakingpie.book.rest;


import com.netflix.client.ClientException;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.health.ServiceHealth;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.net.URI;
import java.util.List;
import java.util.UUID;

import static com.netflix.client.ClientFactory.registerNamedLoadBalancerFromclientConfig;
import static com.netflix.client.config.CommonClientConfigKey.NFLoadBalancerClassName;
import static com.netflix.client.config.CommonClientConfigKey.NFLoadBalancerRuleClassName;
import static com.netflix.client.config.CommonClientConfigKey.ServerListRefreshInterval;
import static com.netflix.client.config.DefaultClientConfigImpl.getClientConfigWithDefaultValues;
import static com.orbitz.consul.model.agent.Registration.RegCheck.http;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@Singleton
@Startup
public class ConsulManagementService {

    private static final Logger log = LoggerFactory.getLogger(ConsulManagementService.class);

    // Service
    private static final String BOOK_API_NAME = "CONSUL_BOOK_API";
    private String bookApiHost = "http://localhost";
    private Integer bookApiPort = 8081;
    // Consul
    private String consulHost = "http://localhost";
    private Integer consulPort = 8500;
    private AgentClient agentClient;

    @PostConstruct
    protected void registerService() {

        final Config config = ConfigProvider.getConfig();
        config.getOptionalValue("BOOK_API_HOST", String.class).ifPresent(host -> bookApiHost = host);
        config.getOptionalValue("BOOK_API_PORT", Integer.class).ifPresent(port -> bookApiPort = port);
        config.getOptionalValue("CONSUL_HOST", String.class).ifPresent(host -> consulHost = host);
        config.getOptionalValue("CONSUL_PORT", Integer.class).ifPresent(port -> consulPort = port);

        log.info("Consul host and port " + consulHost + " " + consulPort);
        final Consul consul = Consul.builder().withUrl(consulHost + ":" + consulPort).build();
        agentClient = consul.agentClient();

        final ImmutableRegistration registration =
            ImmutableRegistration.builder()
                                 .id(UUID.randomUUID().toString())
                                 .name(BOOK_API_NAME)
                                 .address(bookApiHost)
                                 .port(bookApiPort)
                                 .check(http(bookApiHost + ":" + bookApiPort + "/book-api/api/books/health", 5))
                                 .build();
        agentClient.register(registration);

        log.info(BOOK_API_NAME + " is registered in consul on " + bookApiHost + ":" + bookApiPort);

        registerLoadBalancer(consul);
    }
    @PreDestroy
    protected void unregisterService() {

        agentClient.deregister(BOOK_API_NAME);

        log.info(BOOK_API_NAME + " is un-registered from consul");
    }

    private void registerLoadBalancer(final Consul consul) {
        try {
            final DefaultClientConfigImpl clientConfig = getClientConfigWithDefaultValues("number-api");
            clientConfig.set(NFLoadBalancerClassName, "com.netflix.loadbalancer.DynamicServerListLoadBalancer");
            clientConfig.set(NFLoadBalancerRuleClassName, "com.netflix.loadbalancer.RoundRobinRule");
            clientConfig.set(ServerListRefreshInterval, 3000);

            final DynamicServerListLoadBalancer dynamicServerListLoadBalancer =
                    (DynamicServerListLoadBalancer) registerNamedLoadBalancerFromclientConfig("number-api", clientConfig);
            dynamicServerListLoadBalancer.setServerListImpl(new NumberApiServers(consul));
            dynamicServerListLoadBalancer.enableAndInitLearnNewServersFeature();
        } catch (final ClientException e) {
            e.printStackTrace();
        }
    }

    private static class NumberApiServers implements ServerList<Server> {
        final Consul consul;

        NumberApiServers(final Consul consul) {
            this.consul = consul;
        }

        @Override
        public List<Server> getInitialListOfServers() {
            final HealthClient healthClient = consul.healthClient();
            final List<ServiceHealth> nodes =
                healthClient.getHealthyServiceInstances("CONSUL_NUMBER_API").getResponse();
            final List<Server> servers = nodes.stream()
                                              .map(serviceHealth -> new Server(
                                                  URI.create(serviceHealth.getService().getAddress()).getHost(),
                                                  serviceHealth.getService().getPort()))
                                              .collect(toList());
            log.info("Updating Load Balancers servers: " + servers.stream().map(Server::getId).collect(joining(",")));
            return servers;
        }

        @Override
        public List<Server> getUpdatedListOfServers() {
            return getInitialListOfServers();
        }
    }
}
