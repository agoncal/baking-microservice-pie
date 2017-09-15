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
package org.bakingpie.dog.repository;

import org.bakingpie.dog.domain.Dog;

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
public class DogRepository {

    // ======================================
    // =             Injection              =
    // ======================================

    @PersistenceContext
    private EntityManager entityManager;

    // ======================================
    // =              Methods               =
    // ======================================

    public Dog findById(final Long id) {
        return entityManager.find(Dog.class, id);
    }

    public List<Dog> findAll() {
        return entityManager.createNamedQuery(Dog.FIND_ALL, Dog.class).getResultList();
    }

    public List<Dog> findByCategoryId(final String categoryId) {
        TypedQuery<Dog> query = entityManager.createNamedQuery(Dog.FIND_BY_CATEGORY, Dog.class);
        query.setParameter("categoryId", categoryId);
        return query.getResultList();
    }

    public List<Dog> searchByKeyword(final String keyword) {
        TypedQuery<Dog> query = entityManager.createNamedQuery(Dog.SEARCH, Dog.class);
        query.setParameter("keyword", keyword);
        return query.getResultList();
    }

    @Transactional(REQUIRED)
    public Dog create(final Dog dog) {
        entityManager.persist(dog);
        return dog;
    }

    @Transactional(REQUIRED)
    public Dog update(final Dog dog) {
        return entityManager.merge(dog);
    }

    @Transactional(REQUIRED)
    public void deleteById(final Long id) {
        Optional.ofNullable(findById(id)).ifPresent(entityManager::remove);
    }
}
