package piotrusha.e_shop.product.persistence.jpa;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import piotrusha.e_shop.product.domain.dto.ProductCategoryDto;
import piotrusha.e_shop.product.domain.dto.ProductDto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "products")
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Accessors(chain = true)
class ProductEntity {

    @Id
    @Column(nullable = false, unique = true, columnDefinition = "DECIMAL(19,0)")
    private BigDecimal productId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer availablePiecesNumber;

    @Column(nullable = false)
    private Integer bookedPiecesNumber;

    @Column(nullable = false)
    private Integer soldPiecesNumber;

    @Column(length = 200)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "products_categories",
               joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "productId"),
               inverseJoinColumns = @JoinColumn(name = "category_name", referencedColumnName = "name")
    )
    private Set<CategoryEntity> categories;

    ProductDto toDto() {
        Set<String> categories = this.categories.stream()
                                                 .map(CategoryEntity::toDto)
                                                 .map(ProductCategoryDto::getName)
                                                 .collect(Collectors.toSet());
        return ProductDto.builder()
                         .productId(productId)
                         .name(name)
                         .price(price)
                         .availablePiecesNumber(availablePiecesNumber)
                         .bookedPiecesNumber(bookedPiecesNumber)
                         .soldPiecesNumber(soldPiecesNumber)
                         .description(description)
                         .categories(categories)
                         .build();
    }

    static ProductEntity fromDto(ProductDto product) {
        Set<CategoryEntity> categories = product.getCategories()
                                                .stream()
                                                .map(ProductCategoryDto::new)
                                                .map(CategoryEntity::fromDto)
                                                .collect(Collectors.toSet());
        return new ProductEntity().setProductId(product.getProductId())
                                  .setName(product.getName())
                                  .setPrice(product.getPrice())
                                  .setAvailablePiecesNumber(product.getAvailablePiecesNumber())
                                  .setBookedPiecesNumber(product.getBookedPiecesNumber())
                                  .setSoldPiecesNumber(product.getSoldPiecesNumber())
                                  .setDescription(product.getDescription())
                                  .setCategories(categories);
    }

}
