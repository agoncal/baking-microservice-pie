/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bakingpie.dvd.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.bakingpie.dvd.domain.DVD;
import org.bakingpie.dvd.repository.DVDRepository;

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

import static java.util.Optional.ofNullable;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_NO_CONTENT;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.created;
import static javax.ws.rs.core.Response.noContent;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.status;

@ApplicationScoped
@Path("dvds")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "dvds", description = "Operations for DVD's.")
public class DVDResource {
    @Inject
    private DVDRepository dvdRepository;

    @GET
    @Path("{id}")
    @ApiOperation(
        value = "Find a Book by the Id.",
        response = DVD.class)
    @ApiResponses(@ApiResponse(code = 404, message = "Not Found"))
    public Response findById(@PathParam("id") final Long id) {
        return ofNullable(dvdRepository.findById(id))
            .map(Response::ok)
            .orElse(status(NOT_FOUND))
            .build();
    }

    @GET
    @ApiOperation(
        value = "Find all Books",
        response = DVD.class, responseContainer = "List")
    public Response findAll() {
        return ok(dvdRepository.findAll()).build();
    }

    @POST
    @ApiOperation(
        value = "Create a Book",
        response = DVD.class, code = SC_CREATED)
    public Response create(final DVD dvd) {
        final DVD created = dvdRepository.create(dvd);
        return created(URI.create("dvds/" + created.getId())).build();
    }

    @PUT
    @Path("{id}")
    @ApiOperation(
        value = "Update a Book",
        response = DVD.class)
    public Response update(@PathParam("id") final Long id, final DVD dvd) {
        return ok(dvdRepository.update(dvd)).build();
    }

    @DELETE
    @Path("{id}")
    @ApiOperation(
        value = "Delete a Book",
        response = DVD.class, code = SC_NO_CONTENT)
    public Response delete(@PathParam("id") final Long id) {
        dvdRepository.delete(id);
        return noContent().build();
    }
}
