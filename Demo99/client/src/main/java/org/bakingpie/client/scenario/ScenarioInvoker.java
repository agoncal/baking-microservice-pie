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
package org.bakingpie.client.scenario;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static java.lang.String.format;

public abstract class ScenarioInvoker implements Runnable {
    private static Logger logger = Logger.getLogger(ScenarioInvoker.class.getName());

    @Inject
    private Client client;

    private boolean execute;
    private WeightedRandomResult<Endpoint> endpointsToExecute;

    protected abstract List<Endpoint> getEndpoints();

    @PostConstruct
    private void init() {
        getEndpoints();
        this.execute = true;
        this.endpointsToExecute = distributeEndpoints();
    }

    protected WeightedRandomResult<Endpoint> distributeEndpoints() {
        return new WeightedRandomResult<>(getEndpoints());
    }

    @Override
    public void run() {
        while (execute) {
            try {
                final String targetUrl = "http://localhost:8080"; // TODO - Externalize
                final Endpoint endpoint = endpointsToExecute.get();
                final WebTarget webTarget = client.target(targetUrl).path(endpoint.getPath()).resolveTemplates(endpoint.getTemplates());
                final Response response = webTarget.request().method(endpoint.getMethod(), endpoint.getEntity());
                logger.info(format("%s - %s - %d", endpoint.getMethod(), webTarget.getUri(), response.getStatus()));
                sleep();
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sleep() {
        try {
            TimeUnit.MILLISECONDS.sleep(500); // TODO - Externalize
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    private void destroy() {
        this.execute = false;
    }
}
