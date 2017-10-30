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


import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.model.agent.Registration;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class ConsulManagementService {

    private final Logger log = LoggerFactory.getLogger(ConsulManagementService.class);

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

        log.info(" Consul host and port " + consulHost + " " + consulPort);
        Consul consul = Consul.builder().withUrl(consulHost + ":" + consulPort).build(); // connect to Consul on localhost
        agentClient = consul.agentClient();

        Registration.RegCheck check = Registration.RegCheck.http(bookApiHost + ":" + bookApiPort + "/book-api/api/books/health", 10);
        agentClient.register(bookApiPort, check, BOOK_API_NAME, "1");

        log.info(BOOK_API_NAME + " is registered in consul on " + bookApiHost + ":" + bookApiPort);
    }

    @PreDestroy
    protected void unregisterService() {

        agentClient.deregister(BOOK_API_NAME);

        log.info(BOOK_API_NAME + " is un-registered from consul");
    }
}
