package org.bakingpie.dog.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Antonio Goncalves http://www.antoniogoncalves.org --
 */

@Entity
@Cacheable
@NamedQueries({
    @NamedQuery(name = Dog.FIND_BY_CATEGORY, query = "SELECT d FROM Dog d WHERE d.category.id = :categoryId"),
    @NamedQuery(name = Dog.SEARCH, query = "SELECT d FROM Dog d WHERE UPPER(d.name) LIKE :keyword OR UPPER(d.category.name) LIKE :keyword ORDER BY d.category.name"),
    @NamedQuery(name = Dog.FIND_ALL, query = "SELECT d FROM Dog d")
})
@XmlRootElement
public class Dog implements Serializable {

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

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_fk", nullable = false)
    @XmlTransient
    private Category category;

    // ======================================
    // =             Constants              =
    // ======================================

    public static final String FIND_BY_CATEGORY = "Dog.findByCategoryId";
    public static final String SEARCH = "Dog.search";
    public static final String FIND_ALL = "Dog.findAll";

    // ======================================
    // =            Constructors            =
    // ======================================

    public Dog() {
    }

    public Dog(String name, Float unitCost, String imagePath, String description, Category category) {
        this.name = name;
        this.unitCost = unitCost;
        this.imagePath = imagePath;
        this.description = description;
        this.category = category;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // ======================================
    // =   Methods hash, equals, toString   =
    // ======================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dog dog = (Dog) o;
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
