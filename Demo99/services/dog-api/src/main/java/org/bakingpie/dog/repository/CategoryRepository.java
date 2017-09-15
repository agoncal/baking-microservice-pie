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

import org.bakingpie.dog.domain.Category;

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
public class CategoryRepository {

    // ======================================
    // =             Injection              =
    // ======================================

    @PersistenceContext
    private EntityManager entityManager;

    // ======================================
    // =              Methods               =
    // ======================================

    public Category findById(final Long id) {
        return entityManager.find(Category.class, id);
    }

    public List<Category> findAll() {
        return entityManager.createNamedQuery(Category.FIND_ALL, Category.class).getResultList();
    }

    @Transactional(REQUIRED)
    public Category create(final Category vategory) {
        entityManager.persist(vategory);
        return vategory;
    }

    @Transactional(REQUIRED)
    public Category update(final Category category) {
        return entityManager.merge(category);
    }

    @Transactional(REQUIRED)
    public void deleteById(final Long id) {
        Optional.ofNullable(findById(id)).ifPresent(entityManager::remove);
    }
}
