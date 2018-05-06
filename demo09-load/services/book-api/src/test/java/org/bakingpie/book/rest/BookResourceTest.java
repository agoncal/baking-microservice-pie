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

import org.bakingpie.book.client.ApiClient;
import org.bakingpie.book.client.api.NumbersApi;
import org.bakingpie.book.client.api.NumbersApiAlternative;
import org.bakingpie.book.domain.Book;
import org.bakingpie.book.repository.BookRepository;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
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
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static javax.ws.rs.core.Response.Status.OK;
import static org.jboss.shrinkwrap.resolver.api.maven.Maven.resolver;
import static org.junit.Assert.assertEquals;

/**
 * Description.
 *
 * @author Roberto Cortez
 */
@RunWith(Arquillian.class)
public class BookResourceTest {
    @Deployment
    public static WebArchive webApp() {
        return ShrinkWrap.create(WebArchive.class)
                         .addClass(Book.class)
                         .addClass(BookRepository.class)
                         .addClass(ApplicationConfig.class)
                         .addClass(BookResource.class)
                         .addClass(ApiClient.Api.class)
                         .addClass(NumbersApi.class)
                         .addClass(NumbersApiAlternative.class)
                         .addAsWebInfResource("META-INF/beans.xml")
                         .addAsResource("META-INF/persistence.xml")
                         .addAsResource("sql/load.sql")
                ;
    }

    @ArquillianResource
    private URL base;

    private WebTarget webTarget;

    @Before
    public void setUp() throws Exception {
        final Client client = ClientBuilder.newClient();
        webTarget = client.target(URI.create(new URL(base, "api/books").toExternalForm()));
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
        final Book book = new Book("Joshua Bloch", "Effective Java (2nd Edition)", 2001, "Tech", " 978-0-3213-5668-0");
        final Response response = webTarget.request().post(entity(book, APPLICATION_JSON_TYPE));

        assertEquals(CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    @InSequence(4)
    public void update() throws Exception {
        final Book book = new Book("Joshua Bloch", "Effective Java (3rd Edition)", 2018, "Tech", " 978-0-1346-8599-1");
        final Response response = webTarget.path("{id}")
                                           .resolveTemplate("id", 1)
                                           .request()
                                           .put(entity(book, APPLICATION_JSON_TYPE));

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
