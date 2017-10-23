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


import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.providers.MyDataCenterInstanceConfigProvider;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.guice.EurekaModule;
import com.netflix.governator.InjectorBuilder;
import com.netflix.governator.LifecycleInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class EurekaManagementService {


    private final Logger log = LoggerFactory.getLogger(EurekaManagementService.class);

    private EurekaClient eurekaClient;
    private DynamicPropertyFactory configInstance;

    @PostConstruct
    protected  void startService() {
        configInstance = com.netflix.config.DynamicPropertyFactory.getInstance();

        final LifecycleInjector injector = InjectorBuilder
            .fromModule(new EurekaModule())
            .overrideWith(new AbstractModule() {
                    @Override
                    protected void configure() {
                        // the default impl of EurekaInstanceConfig is CloudInstanceConfig, which we only want in an AWS
                        // environment. Here we override that by binding MyDataCenterInstanceConfig to EurekaInstanceConfig.
                        bind(EurekaInstanceConfig.class).toProvider(MyDataCenterInstanceConfigProvider.class).in(Scopes.SINGLETON);
                    }
                })
            .createInjector();

        final EurekaClient eurekaClient = injector.getInstance(EurekaClient.class);
        final ApplicationInfoManager applicationInfoManager = injector.getInstance(ApplicationInfoManager.class);

        // A good practice is to register as STARTING and only change status to UP
        // after the service is ready to receive traffic
        System.out.println("Registering service to eureka with STARTING status");
        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.STARTING);

        // If you need to do some slower startup stuff perform that work HERE.
        // Only once finished set the status to UP.

        // Now we change our status to UP
        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
        waitForRegistrationWithEureka(eurekaClient);
        log.info("Service started and ready to process requests..");


        log.info("--------------------------------------------------------------------------------------------");
        log.info("EurekaManagementService started.");
        log.info("--------------------------------------------------------------------------------------------");
    }

    @PreDestroy
    protected  void stopService() {

        if (eurekaClient != null) {
            eurekaClient.shutdown();
        }


        log.info("--------------------------------------------------------------------------------------------");
        log.info("EurekaManagementService stopped.");
        log.info("--------------------------------------------------------------------------------------------");
    }


    private void waitForRegistrationWithEureka(final EurekaClient eurekaClient) {
        // my vip address to listen on
        String vipAddress = configInstance.getStringProperty("eureka.vipAddress", "sampleservice.mydomain.net").get();
        InstanceInfo nextServerInfo = null;
        while (nextServerInfo == null) {
            try {
                nextServerInfo = eurekaClient.getNextServerFromEureka(vipAddress, false);
            } catch (Throwable e) {
                // Taken from the example code. I have no idea why you have this pattern.
                log.info("Waiting ... verifying service registration with eureka ...");

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }


}
