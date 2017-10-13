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
package org.bakingpie.dvd.repository;

import org.bakingpie.dvd.domain.DVD;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

@ApplicationScoped
@Transactional(SUPPORTS)
public class DVDRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public DVD findById(final Long id) {
        return entityManager.find(DVD.class, id);
    }

    public List<DVD> findAll() {
        return entityManager.createQuery("SELECT d FROM DVD d", DVD.class).getResultList();
    }

    @Transactional(REQUIRED)
    public DVD create(final DVD dvd) {
        entityManager.persist(dvd);
        return dvd;
    }

    @Transactional(REQUIRED)
    public DVD update(final DVD dvd) {
        return entityManager.merge(dvd);
    }

    @Transactional(REQUIRED)
    public void delete(final Long id) {
        Optional.ofNullable(findById(id)).ifPresent(entityManager::remove);
    }
}
