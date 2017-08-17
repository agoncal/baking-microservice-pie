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

import java.util.ArrayList;
import java.util.List;

import static org.bakingpie.client.auth.Users.User.user;

public class Users {
    private static Users ourInstance = new Users();

    private List<User> users;

    private static Users getInstance() {
        return ourInstance;
    }

    private Users() {
        users = new ArrayList<>();
        users.add(user("goku", "demo"));
        users.add(user("vegeta", "demo"));
        users.add(user("beerus", "demo"));
        users.add(user("whis", "demo"));
        users.add(user("gohan", "demo"));
        users.add(user("goten", "demo"));
        users.add(user("trunks", "demo"));
        users.add(user("piccolo", "demo"));
    }

    static List<User> getUsers() {
        return getInstance().users;
    }

    static class User {
        private String username;
        private String password;

        User(final String username, final String password) {
            this.username = username;
            this.password = password;
        }

        static User user(final String username, final String password) {
            return new User(username, password);
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}
