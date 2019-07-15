package piotrusha.e_shop.product.persistence.jpa;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import piotrusha.e_shop.product.domain.dto.ProductCategoryDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class CategoryEntity {

    @Id
    @Column(length = 30, nullable = false, unique = true)
    private String name;

    ProductCategoryDto toDto() {
        return new ProductCategoryDto(name);
    }

    static CategoryEntity fromDto(ProductCategoryDto category) {
        return new CategoryEntity(category.getName());
    }

}
