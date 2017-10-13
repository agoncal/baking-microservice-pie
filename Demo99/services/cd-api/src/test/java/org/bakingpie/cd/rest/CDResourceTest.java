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
package org.bakingpie.cd.rest;

import org.bakingpie.cd.domain.CD;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URL;

import static javax.ws.rs.client.Entity.entity;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.*;
import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
@RunAsClient()
// @DefaultDeployment()
public class CDResourceTest {

    @Deployment(testable = false)
    public static Archive<?> createDeploymentPackage() {

        return ShrinkWrap.create(WebArchive.class)
            .addPackages(true, "org.bakingpie.cd")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
    }

    @ArquillianResource
    private URL base;

    private WebTarget webTarget;

    @Before
    public void setUp() throws Exception {
        final Client client = ClientBuilder.newClient();
        webTarget = client.target(URI.create(new URL(base, "api/cds").toExternalForm()));
    }

    @Test
    @InSequence(1)
    public void findById() throws Exception {
        final Response response = webTarget.path("{id}").resolveTemplate("id", 0).request().get();

        assertEquals(OK.getStatusCode(), response.getStatus());
    }

    @Test
    @InSequence(2)
    public void findAll() throws Exception {
        final Response response = webTarget.request().get();

        assertEquals(OK.getStatusCode(), response.getStatus());
    }

    @Test
    @InSequence(3)
    public void create() throws Exception {
        final CD cd = new CD("Animal", 12.5F, "Tech", "Best Pink Floyd album");
        final Response response = webTarget.request().post(entity(cd, APPLICATION_JSON_TYPE));

        assertEquals(CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    @InSequence(4)
    public void update() throws Exception {
        final CD cd = new CD("Animal", 12.5F, "Tech", "Best Pink Floyd album");
        final Response response = webTarget.path("{id}")
                                           .resolveTemplate("id", 1)
                                           .request()
                                           .put(entity(cd, APPLICATION_JSON_TYPE));

        assertEquals(OK.getStatusCode(), response.getStatus());
    }

    @Test
    @InSequence(5)
    public void delete() throws Exception {
        final Response response = webTarget.path("{id}")
                                           .resolveTemplate("id", 1)
                                           .request()
                                           .delete();

        assertEquals(NO_CONTENT.getStatusCode(), response.getStatus());
    }
}
