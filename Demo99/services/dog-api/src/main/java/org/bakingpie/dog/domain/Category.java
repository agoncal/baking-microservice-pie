package org.bakingpie.dog.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Cacheable
@NamedQueries({
    @NamedQuery(name = Category.FIND_ALL, query = "SELECT c FROM Category c")
})
@XmlRootElement
public class Category implements Serializable {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(length = 30, nullable = false)
    @NotNull
    @Size(min = 1, max = 30)
    private String name;

    @Column(length = 3000, nullable = false)
    @NotNull
    @Size(max = 3000)
    private String description;

    // ======================================
    // =             Constants              =
    // ======================================

    public static final String FIND_BY_NAME = "Category.findByName";
    public static final String FIND_ALL = "Category.findAll";

    // ======================================
    // =            Constructors            =
    // ======================================

    public Category() {
    }

    public Category(String name, String description) {
        this.name = name;
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

    // ======================================
    // =   Methods hash, equals, toString   =
    // ======================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Category{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            '}';
    }
}
