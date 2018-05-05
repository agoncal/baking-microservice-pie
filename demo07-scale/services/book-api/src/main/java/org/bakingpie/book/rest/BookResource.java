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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.bakingpie.book.client.api.NumbersApi;
import org.bakingpie.book.domain.Book;
import org.bakingpie.book.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

import static java.util.Optional.ofNullable;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.noContent;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.status;

@ApplicationScoped
@Path("books")
@Api(value = "books", description = "Operations for Books.")
public class BookResource {

    private final Logger log = LoggerFactory.getLogger(BookResource.class);

    // ======================================
    // =             Injection              =
    // ======================================
    @Inject
    private NumbersApi numbersApi;

    @Inject
    private BookRepository bookRepository;

    // ======================================
    // =              Methods               =
    // ======================================

    @GET
    @Path("/{id : \\d+}")
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "Find a Book by the Id.", response = Book.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Book found"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Book not found")
    })
    public Response findById(@PathParam("id") final Long id) {
        log.info("Getting the book " + id);
        return ofNullable(bookRepository.findById(id))
            .map(Response::ok)
            .orElse(status(NOT_FOUND))
            .build();
    }

    @GET
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "Find all Books", response = Book.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "All books found"),
        @ApiResponse(code = 404, message = "Books not found")}
    )
    public Response findAll() {
        log.info("Getting all the books");
        return ok(bookRepository.findAll()).build();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @ApiOperation(value = "Create a Book")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "The book is created"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 415, message = "Format is not JSon")
    })
    // tag::adocSnippet[]
    public Response create(@ApiParam(value = "Book to be created", required = true) Book book, @Context UriInfo uriInfo) {
        log.info("Creating the book " + book);

        log.info("Invoking the number-api");
        String isbn = numbersApi.generateBookNumber();
        book.setIsbn(isbn);

        log.info("Creating the book with ISBN " + book);
        final Book created = bookRepository.create(book);
        URI createdURI = uriInfo.getBaseUriBuilder().path(String.valueOf(created.getId())).build();
        return Response.created(createdURI).build();
    }
    // end::adocSnippet[]

    @PUT
    @Path("/{id : \\d+}")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "Update a Book", response = Book.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The book is updated"),
        @ApiResponse(code = 400, message = "Invalid input")
    })
    public Response update(@PathParam("id") final Long id, @ApiParam(value = "Book to be updated", required = true) Book book) {
        log.info("Updating the book " + book);
        return ok(bookRepository.update(book)).build();
    }

    @DELETE
    @Path("/{id : \\d+}")
    @ApiOperation(value = "Delete a Book")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Book has been deleted"),
        @ApiResponse(code = 400, message = "Invalid input")
    })
    public Response delete(@PathParam("id") final Long id) {
        log.info("Deleting the book " + id);
        bookRepository.deleteById(id);
        return noContent().build();
    }

    @GET
    @Path("health")
    @ApiOperation(value = "Checks the health of this REST endpoint", response = String.class)
    public Response health() {
        log.info("Alive and Kicking !!!");
        return Response.ok().build();
    }

    @GET
    @Path("number")
    @ApiOperation(value = "Wraps the Number API to retrive a Book Number", response = String.class)
    public Response number() {
        log.info("Invoking the number-api");
        return Response.ok(numbersApi.generateBookNumber()).build();
    }
}
