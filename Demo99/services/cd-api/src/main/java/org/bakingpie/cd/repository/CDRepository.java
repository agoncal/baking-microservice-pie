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
package org.bakingpie.cd.repository;

import org.bakingpie.cd.domain.CD;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

@ApplicationScoped
@Transactional(SUPPORTS)
public class CDRepository {

    // ======================================
    // =             Injection              =
    // ======================================

    @PersistenceContext
    private EntityManager entityManager;

    // ======================================
    // =              Methods               =
    // ======================================

    public CD findById(final Long id) {
        return entityManager.find(CD.class, id);
    }

    public List<CD> findAll() {
        return entityManager.createNamedQuery(CD.FIND_ALL, CD.class).getResultList();
    }

    public List<CD> searchByKeyword(final String keyword) {
        TypedQuery<CD> query = entityManager.createNamedQuery(CD.SEARCH, CD.class);
        query.setParameter("keyword", keyword);
        return query.getResultList();
    }

    @Transactional(REQUIRED)
    public CD create(final CD dog) {
        entityManager.persist(dog);
        return dog;
    }

    @Transactional(REQUIRED)
    public CD update(final CD dog) {
        return entityManager.merge(dog);
    }

    @Transactional(REQUIRED)
    public void deleteById(final Long id) {
        Optional.ofNullable(findById(id)).ifPresent(entityManager::remove);
    }
}
