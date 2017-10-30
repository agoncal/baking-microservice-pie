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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
@Path("numbers")
@Produces(MediaType.TEXT_PLAIN)
@Api(value = "numbers", description = "Generating all sorts of numbers.")
public class NumberResource {

    private final Logger log = LoggerFactory.getLogger(NumberResource.class);

    @GET
    @Path("book")
    @ApiOperation(value = "Generates a book number.", response = String.class)
    public Response generateBookNumber() throws InterruptedException {
        log.info("Waiting for 1 minute");
        TimeUnit.MINUTES.sleep(1);
        log.info("Generating a book number");
        return Response.ok("BK-" + Math.random()).build();
    }

    @GET
    @Path("health")
    @ApiOperation(value = "Checks the health of this REST endpoint", response = String.class)
    public Response health() {
        log.info("Alive and Kicking !!!");
        return Response.ok().build();
    }
}
