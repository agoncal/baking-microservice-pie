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
package org.bakingpie.dog.rest;

import org.bakingpie.dog.domain.Category;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.CREATED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Arquillian.class)
@RunAsClient()
// @DefaultDeployment()
public class CategoryResourceTest {

    @Deployment(testable = false)
    public static Archive<?> createDeploymentPackage() {

        return ShrinkWrap.create(WebArchive.class)
            .addClass(Category.class)
            .addClass(Category.class)
            .addClass(ApplicationConfig.class)
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
    }


    @Test
    public void shouldCreateACategory(@ArquillianResteasyResource("api/categories") WebTarget webTarget) {
        // Creates a book
        Category category = new Category("Dalmat", "Tall dog in black and white");
        Response response = webTarget.request(APPLICATION_JSON).post(Entity.entity(category, APPLICATION_JSON));
        assertEquals(CREATED.getStatusCode(), response.getStatus());
        // Checks the created book
        String location = response.getHeaderString("location");
        assertNotNull(location);
    }
}
