/**
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

import org.bakingpie.dvd.domain.DVD;
import org.bakingpie.dvd.repository.DVDRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;

@ApplicationScoped
@Path("dvd")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DVDRest {
    @Inject
    private DVDRepository service;

    @GET
    @Path("{id}")
    public DVD find(@PathParam("id") final Long id) {
        return service.find(id);
    }

    @GET
    public List<DVD> getMovies(@QueryParam("first") final Integer first,
                               @DefaultValue("20")
                               @QueryParam("max") final Integer max,
                               @QueryParam("field") final String field,
                               @QueryParam("searchTerm") final String searchTerm) {
        return service.getMovies(first, max, field, searchTerm);
    }

    @GET
    @Path("genres")
    public Collection<String> getGenres() {
        return service.getGenres();
    }

    @POST
    @Consumes("application/json")
    public DVD addMovie(final DVD movie) {
        service.addMovie(movie);
        return movie;
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public DVD editMovie(final DVD movie) {
        service.editMovie(movie);
        return movie;
    }

    @DELETE
    @Path("{id}")
    public void deleteMovie(@PathParam("id") final long id) {
        service.deleteMovie(id);
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public int count(@QueryParam("field") final String field, @QueryParam("searchTerm") final String searchTerm) {
        return service.count(field, searchTerm);
    }
}
