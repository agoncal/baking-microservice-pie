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
package org.bakingpie.number.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.bakingpie.commons.rest.EnableCORS;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
// tag::adocSnippet[]
@Path("numbers")
@Produces(MediaType.TEXT_PLAIN)
// tag::adocCORS[]
@EnableCORS
// end::adocCORS[]
// tag::adocSwagger[]
@Api(value = "numbers", description = "Generating all sorts of numbers.")
// end::adocSwagger[]
public class NumberResource {

    @GET
    @Path("book")
    // tag::adocSwagger[]
    @ApiOperation(value = "Generates a book number.", response = String.class)
    // end::adocSwagger[]
    public Response generateBookNumber() {
        return Response.ok("BK-" + Math.random()).build();
    }
}
// end::adocSnippet[]

