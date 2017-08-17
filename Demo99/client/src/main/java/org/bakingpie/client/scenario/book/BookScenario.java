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
package org.bakingpie.client.scenario.book;

import com.github.javafaker.*;
import org.bakingpie.client.auth.BasicAuth;
import org.bakingpie.client.scenario.Endpoint;
import org.bakingpie.client.scenario.ScenarioInvoker;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static javax.ws.rs.client.Entity.json;
import static javax.ws.rs.client.Entity.text;
import static org.bakingpie.client.scenario.Endpoint.endpoint;
import static org.bakingpie.client.scenario.Endpoint.endpointWithEntity;
import static org.bakingpie.client.scenario.Endpoint.endpointWithTemplates;

@ApplicationScoped
@BasicAuth
public class BookScenario extends ScenarioInvoker {
    @Inject
    private Faker faker;

    @Override
    protected List<Endpoint> getEndpoints() {
        final String contextRoot = "book"; // TODO - Externalize
        return Stream.of(endpoint(contextRoot + "/rest/books", "GET"),
                         endpointWithEntity(contextRoot + "/rest/books", "POST", this::createBook),
                         endpointWithTemplates(contextRoot + "/rest/books/{id}", "DELETE", this::deleteBook),
                         endpointWithTemplates(contextRoot + "/rest/books/{id}", "GET", this::findBook))
                     .collect(collectingAndThen(toList(), Collections::unmodifiableList));
    }

    private Entity createBook() {
        if (Math.random() * 100 < 95) {
            final com.github.javafaker.Book book = faker.book();
            return json(new org.bakingpie.client.scenario.book.Book(book.author(),
                                                                    book.title(),
                                                                    faker.number().numberBetween(1900, 2017),
                                                                    book.genre(),
                                                                    faker.number().digits(13)));
        } else {
            return text("");
        }
    }

    private Map<String, Object> deleteBook() {
        final HashMap<String, Object> templates = new HashMap<>();
        templates.put("id", getRandomBook());
        return templates;
    }

    private Map<String, Object> findBook() {
        final HashMap<String, Object> templates = new HashMap<>();
        templates.put("id", getRandomBook());
        return templates;
    }

    @Inject
    private Client client;

    private Integer getRandomBook() {
        final String targetUrl = "http://localhost:8080/"; // TODO - Externalize
        final WebTarget webTarget = client.target(targetUrl).path("book/rest/books");
        final List<org.bakingpie.client.scenario.book.Book> books = webTarget.request().get(new GenericType<List<org.bakingpie.client.scenario.book.Book>>() {});
        return books.isEmpty() ? 1 : books.get((int) (Math.random() * books.size())).getId();
    }
}
