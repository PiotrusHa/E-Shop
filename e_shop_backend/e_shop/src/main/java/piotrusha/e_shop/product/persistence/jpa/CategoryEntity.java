package piotrusha.e_shop.product.persistence.jpa;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import piotrusha.e_shop.product.domain.Category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class CategoryEntity {

    @Id
    @Column(name = "name", length = 30, nullable = false, unique = true)
    private String name;

    Category toDomainCategory() {
        return new Category(name);
    }

    static CategoryEntity fromDomainCategory(Category category) {
        return new CategoryEntity(category.getName());
    }

}
