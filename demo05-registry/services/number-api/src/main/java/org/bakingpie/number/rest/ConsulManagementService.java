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


import me.magnet.consultant.Consultant;
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

    public static final String SERVICE_NAME = "CONSUL_NUMBER_API";
    public static final Integer SERVICE_PORT = 8084;

    private Consultant consultant = Consultant.builder().identifyAs(SERVICE_NAME).build();

    @PostConstruct
    protected void registerService() {

        consultant.registerService(SERVICE_PORT);

        log.info(SERVICE_NAME + " is registered in consul on port " + SERVICE_PORT);
    }

    @PreDestroy
    protected void unregisterService() {

        consultant.deregisterService();

        log.info(SERVICE_NAME + " is un-registered from consul");
    }
}
