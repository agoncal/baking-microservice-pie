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
package org.bakingpie.client.producer;

import org.bakingpie.client.auth.BasicAuth;
import org.bakingpie.client.auth.BasicAuthFilter;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.security.SecureRandom;

@ApplicationScoped
public class ClientProducer {
    @Inject
    private BasicAuthFilter basicAuthFilter;

    @Produces
    public Client createClient(final InjectionPoint injectionPoint) {
        final Client client = createClient();

        if (injectionPoint.getBean().getBeanClass().isAnnotationPresent(BasicAuth.class)) {
            client.register(basicAuthFilter);
        }

        return client;
    }

    public void close(@Disposes final Client client) {
        client.close();
    }

    private static Client createClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                        }
                    }
            };

            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());

            return ClientBuilder.newBuilder()
                                //.register(new LoggingFeature())
                                .hostnameVerifier((hostname, session) -> true)
                                .sslContext(sslContext)
                                .build();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
