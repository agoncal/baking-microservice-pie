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
package org.bakingpie.client;

import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import org.bakingpie.client.scenario.ScenarioInvoker;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static com.orbitz.consul.model.agent.Registration.RegCheck.http;

@Singleton
@Startup
@Lock(LockType.READ)
@Path("health")
public class Initializer {

    // Service
    private static final String SIMULATOR_NAME = "CONSUL_SIMULATOR";
    private String simulatorHost = "http://localhost";
    private Integer simulatorPort = 8080;
    // Consul
    private String consulHost = "http://localhost";
    private Integer consulPort = 8500;
    private AgentClient agentClient;

    @Resource
    private ManagedExecutorService mes;

    @Inject
    private Instance<ScenarioInvoker> scenarioInvokers;

    @PostConstruct
    void postConstruct() {
        registerService();
        scenarioInvokers.forEach(mes::execute);
    }

    protected void registerService() {

        final Config config = ConfigProvider.getConfig();
        config.getOptionalValue("SIMULATOR_HOST", String.class).ifPresent(host -> simulatorHost = host);
        config.getOptionalValue("SIMULATOR_PORT", Integer.class).ifPresent(port -> simulatorPort = port);
        config.getOptionalValue("CONSUL_HOST", String.class).ifPresent(host -> consulHost = host);
        config.getOptionalValue("CONSUL_PORT", Integer.class).ifPresent(port -> consulPort = port);

        final Consul consul = Consul.builder().withUrl(consulHost + ":" + consulPort).build();
        agentClient = consul.agentClient();

        final ImmutableRegistration registration =
                ImmutableRegistration.builder()
                        .id(UUID.randomUUID().toString())
                        .name(SIMULATOR_NAME)
                        .address(simulatorHost)
                        .port(simulatorPort)
                        .check(http(simulatorHost + ":" + simulatorPort + "/simulator/api/health", 5))
                        .build();
        agentClient.register(registration);
        }

    @GET
    public Response health() {
        return Response.ok().build();
    }
}
