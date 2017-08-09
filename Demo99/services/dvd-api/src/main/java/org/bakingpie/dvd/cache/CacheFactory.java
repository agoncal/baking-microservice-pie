/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.bakingpie.dvd.cache;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

public class CacheFactory {

    @Inject
    private CacheManager mgr;

    @Produces
    @MovieCache
    public Cache<Object, Object> createCache(final InjectionPoint injectionPoint) {

        final MovieCache movieCache = injectionPoint.getAnnotated().getAnnotation(MovieCache.class);

        Cache<Object, Object> cache = mgr.getCache(movieCache.name());
        if (cache == null) {
            final MutableConfiguration<Object, Object> config = new MutableConfiguration<Object, Object>()
                    .setTypes(Object.class, Object.class)
                    .setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(Duration.ONE_HOUR))
                    .setStatisticsEnabled(false);

            cache = mgr.createCache(movieCache.name(), config);
        }

        return cache;
    }

}
