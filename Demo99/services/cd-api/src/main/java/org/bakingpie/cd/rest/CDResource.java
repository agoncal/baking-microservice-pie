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
import org.bakingpie.cd.repository.CDRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.status;

@ApplicationScoped
@Path("cds")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CDResource {

    // ======================================
    // =             Injection              =
    // ======================================

    @Inject
    private CDRepository cdRepository;

    // ======================================
    // =              Methods               =
    // ======================================

    @GET
    @Path("{id}")
    public Response findById(@PathParam("id") final Long id) {
        return Optional.ofNullable(cdRepository.findById(id))
            .map(Response::ok)
            .orElse(Math.random() * 100 < 95 ? status(NOT_FOUND) : status(INTERNAL_SERVER_ERROR))
            .build();
    }

    @GET
    public Response findAll() {
        return Response.ok(cdRepository.findAll()).build();
    }

    @POST
    public Response create(final CD cd) {
        final CD created = cdRepository.create(cd);
        return Response.created(UriBuilder.fromResource(CDResource.class).path(String.valueOf(created.getId())).build()).build();
    }

    @PUT
    public Response update(final CD cd) {
        return Response.ok(cdRepository.update(cd)).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") final Long id) {
        cdRepository.deleteById(id);
        return Response.noContent().build();
    }
}
