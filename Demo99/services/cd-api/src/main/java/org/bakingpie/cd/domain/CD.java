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
package org.bakingpie.cd.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@Entity
@NamedQueries({
    @NamedQuery(name = CD.SEARCH, query = "SELECT c FROM CD c WHERE UPPER(c.name) LIKE :keyword ORDER BY c.name"),
    @NamedQuery(name = CD.FIND_ALL, query = "SELECT c FROM CD c")
})
public class CD {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(length = 20, nullable = false)
    @NotNull
    @Size(min = 1, max = 20)
    private String identification;

    @Column(length = 30, nullable = false)
    @NotNull
    @Size(min = 1, max = 30)
    private String name;

    @Column(length = 3000)
    @Size(max = 3000)
    private String description;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "unit_cost", nullable = false)
    @NotNull
    private Float unitCost;

    // ======================================
    // =             Constants              =
    // ======================================

    public static final String SEARCH = "CD.search";
    public static final String FIND_ALL = "CD.findAll";

    // ======================================
    // =            Constructors            =
    // ======================================

    public CD() {
    }

    public CD(String identification, String name, Float unitCost, String imagePath, String description) {
        this.identification = identification;
        this.name = name;
        this.unitCost = unitCost;
        this.imagePath = imagePath;
        this.description = description;
    }

    // ======================================
    // =         Getters & setters          =
    // ======================================

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Float getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Float unitCost) {
        this.unitCost = unitCost;
    }

    // ======================================
    // =   Methods hash, equals, toString   =
    // ======================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CD dog = (CD) o;
        return Objects.equals(identification, dog.identification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identification);
    }

    @Override
    public String toString() {
        return "Dog{" +
            "id=" + id +
            ", identification='" + identification + '\'' +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", imagePath='" + imagePath + '\'' +
            ", unitCost=" + unitCost +
            '}';
    }
}
