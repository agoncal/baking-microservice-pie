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

import static javax.ws.rs.client.Entity.entity;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;
import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
@RunAsClient()
// @DefaultDeployment()
public class CDResourceTest {

    @Deployment
    public static WebArchive webApp() {
        return ShrinkWrap.create(WebArchive.class)
            .addPackages(true, "org.bakingpie.cd")
            .addAsResource("META-INF/persistence-test.xml", "META-INF/persistence.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            ;
    }

    private WebTarget webTarget;

    @Before
    public void setUp() throws Exception {
        final Client client = ClientBuilder.newClient();
        webTarget = client.target("http://localhost:8080").path("api").path("cds");
    }

    @Test
    @InSequence(1)
    public void findEmpty() throws Exception {
        final Response response = webTarget.request().get();
        assertEquals(OK.getStatusCode(), response.getStatus());
    }

    @Test
    @InSequence(2)
    public void create() throws Exception {
        final CD cd = new CD("BK-1234-456", "Animal", 12.5F, "Tech", "Best Pink Floyd album");
        final Response response = webTarget.request(APPLICATION_JSON_TYPE).post(entity(cd, APPLICATION_JSON_TYPE));
        assertEquals(CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    @InSequence(3)
    public void findAll() throws Exception {
        final Response response = webTarget.request().get();

        assertEquals(OK.getStatusCode(), response.getStatus());
    }

    // @Test
    // @InSequence(4)
    // public void update() throws Exception {
    //     final CD cd = new CD("Animal", 12.5F, "Tech", "Best Pink Floyd album");
    //     final Response response = webTarget.path("{id}")
    //         .resolveTemplate("id", 1)
    //         .request()
    //         .put(entity(cd, APPLICATION_JSON_TYPE));
    //
    //     assertEquals(OK.getStatusCode(), response.getStatus());
    // }

    // @Test
    // @InSequence(5)
    // public void delete() throws Exception {
    //     final Response response = webTarget.path("{id}")
    //         .resolveTemplate("id", 1)
    //         .request()
    //         .delete();
    //
    //     assertEquals(NO_CONTENT.getStatusCode(), response.getStatus());
    // }
}
