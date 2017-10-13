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

import org.bakingpie.cd.domain.Item;

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
public class ItemRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Item findByNumber(final String number) {
        return entityManager.find(Item.class, number);
    }

    public List<Item> findAll() {
        return entityManager.createQuery("SELECT i FROM Item i", Item.class).getResultList();
    }

    @Transactional(REQUIRED)
    public Item create(final Item item) {
        entityManager.persist(item);
        return item;
    }

    @Transactional(REQUIRED)
    public Item update(final Item item) {
        return entityManager.merge(item);
    }

    @Transactional(REQUIRED)
    public void delete(final String number) {
        Optional.ofNullable(findByNumber(number)).ifPresent(entityManager::remove);
    }
}
