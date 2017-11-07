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
package org.bakingpie.number.rest;

import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.UUID;

import static com.orbitz.consul.model.agent.Registration.RegCheck.http;

@Singleton
@Startup
public class ConsulManagementService {

    private final Logger log = LoggerFactory.getLogger(ConsulManagementService.class);

    // Service
    private static final String NUMBER_API_NAME = "CONSUL_NUMBER_API";
    private String numberApiHost = "http://localhost";
    private Integer numberApiPort = 8084;
    // Consul
    private String consulHost = "http://localhost";
    private Integer consulPort = 8500;
    private AgentClient agentClient;

    @PostConstruct
    protected void registerService() {

        final Config config = ConfigProvider.getConfig();
        config.getOptionalValue("NUMBER_API_HOST", String.class).ifPresent(host -> numberApiHost = host);
        config.getOptionalValue("NUMBER_API_PORT", Integer.class).ifPresent(port -> numberApiPort = port);
        config.getOptionalValue("CONSUL_HOST", String.class).ifPresent(host -> consulHost = host);
        config.getOptionalValue("CONSUL_PORT", Integer.class).ifPresent(port -> consulPort = port);

        log.info("Consul host and port " + consulHost + ":" + consulPort);
        Consul consul = Consul.builder().withUrl(consulHost + ":" + consulPort).build();
        agentClient = consul.agentClient();

        final ImmutableRegistration registration =
            ImmutableRegistration.builder()
                                 .id(UUID.randomUUID().toString())
                                 .name(NUMBER_API_NAME)
                                 .address(numberApiHost)
                                 .port(numberApiPort)
                                 .check(http(numberApiHost + ":" + numberApiPort + "/number-api/api/numbers/health", 5))
                                 .build();
        agentClient.register(registration);

        log.info(NUMBER_API_NAME + " is registered in consul on " + numberApiHost + ":" + numberApiPort);
    }

    @PreDestroy
    protected void unregisterService() {

        agentClient.deregister(NUMBER_API_NAME);

        log.info(NUMBER_API_NAME + " is un-registered from consul");
    }
}
