package piotrusha.e_shop.core.product.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import piotrusha.e_shop.core.product.domain.dto.ProductCategoryDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
class Category {

    @Id
    @Column(name = "name", length = 30, nullable = false, unique = true)
    private String name;

    ProductCategoryDto toDto() {
        return new ProductCategoryDto(name);
    }

}
