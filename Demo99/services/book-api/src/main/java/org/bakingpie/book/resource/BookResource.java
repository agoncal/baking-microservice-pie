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
package org.bakingpie.book.resource;

import org.bakingpie.book.persistence.Book;
import org.bakingpie.book.persistence.BookBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.status;

@ApplicationScoped
@Path("books")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {
    @Inject
    private BookBean bookBean;

    @GET
    @Path("{id}")
    public Response findById(@PathParam("id") final Long id) {
        return Optional.ofNullable(bookBean.findById(id))
                       .map(Response::ok)
                       .orElse(Math.random() * 100 < 95 ? status(NOT_FOUND) : status(INTERNAL_SERVER_ERROR))
                       .build();
    }

    @GET
    public Response findAll() {
        return Response.ok(bookBean.findAll()).build();
    }

    @POST
    public Response create(final Book movie) {
        final Book created = bookBean.create(movie);
        return Response.created(URI.create("movies/" + created.getId())).build();
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") final Long id, final Book movie) {
        return Response.ok(bookBean.update(movie)).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") final Long id) {
        bookBean.delete(id);
        return Response.noContent().build();
    }
}
