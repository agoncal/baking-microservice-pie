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
package org.bakingpie.inventory.rest;

import org.bakingpie.inventory.domain.Item;
import org.bakingpie.inventory.repository.ItemRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.status;

@ApplicationScoped
@Path("inventory")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ItemResource {

    @Inject
    private ItemRepository itemRepository;

    @GET
    @Path("{number}")
    public Response findByNumber(@PathParam("number") final String number) {
        return Optional.ofNullable(itemRepository.findByNumber(number))
            .map(Response::ok)
            .orElse(Math.random() * 100 < 95 ? status(NOT_FOUND) : status(INTERNAL_SERVER_ERROR))
            .build();
    }

    @GET
    public Response findAll() {
        return Response.ok(itemRepository.findAll()).build();
    }

    @POST
    public Response create(final Item item) {
        final Item created = itemRepository.create(item);
        return Response.created(URI.create("inventory/" + created.getNumber())).build();
    }

    @PUT
    @Path("{number}")
    public Response update(@PathParam("number") final String number, final Item item) {
        return Response.ok(itemRepository.update(item)).build();
    }

    @DELETE
    @Path("{number}")
    public Response delete(@PathParam("number") final String number) {
        itemRepository.delete(number);
        return Response.noContent().build();
    }
}
