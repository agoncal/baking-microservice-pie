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
package org.bakingpie.client.auth;

import org.bakingpie.client.auth.Users.User;
import org.bakingpie.client.scenario.WeightedRandomResult;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class BasicAuthFilter implements ClientRequestFilter {
    private WeightedRandomResult<User> users;

    @PostConstruct
    private void init() {
        final List<User> userList = Users.getUsers().stream().collect(ArrayList::new, (list, user) -> {
            for (int i = 0; i <= (Math.random() * 10); i++) {
                list.add(user);
            }
        }, ArrayList::addAll);

        Collections.shuffle(userList);

        users = new WeightedRandomResult<>(userList);
    }

    @Override
    public void filter(final ClientRequestContext requestContext) throws IOException {
        final User user = users.get();
        requestContext.getHeaders().add(HttpHeaders.AUTHORIZATION, generateBasicAuth(user));
    }

    private String generateBasicAuth(final User user) {
        final String username = user.getUsername();
        final String password = Math.random() * 100 < 95 ? user.getPassword() : "";
        return "Basic " + new String(Base64.getEncoder().encode((username + ":" + password).getBytes()));
    }
}
