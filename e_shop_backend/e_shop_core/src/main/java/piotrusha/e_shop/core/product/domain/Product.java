package piotrusha.e_shop.core.product.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import piotrusha.e_shop.core.product.domain.dto.ProductDto;

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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
class Product {

    @Id
    @Column(nullable = false, unique = true, columnDefinition = "DECIMAL(19,0)")
    private BigDecimal productId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer availablePiecesNumber;

    private Integer bookedPiecesNumber;

    private Integer soldPiecesNumber;

    @Column(length = 200)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "products_categories",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_name", referencedColumnName = "name")
    )
    private Set<Category> categories;

    void assignCategories(List<Category> categories) {
        this.categories.addAll(categories);
    }

    void unassignCategories(List<Category> categories) {
        this.categories.removeAll(categories);
    }

    boolean canBook(int piecesNumber) {
        return availablePiecesNumber >= piecesNumber;
    }

    boolean canCancel(int piecesNumber) {
        return bookedPiecesNumber >= piecesNumber;
    }

    boolean canSell(int piecesNumber) {
        return bookedPiecesNumber >= piecesNumber;
    }

    void bookProduct(int piecesNumber) {
        availablePiecesNumber -= piecesNumber;
        bookedPiecesNumber += piecesNumber;
    }

    void cancelBooking(int piecesNumber) {
        bookedPiecesNumber -= piecesNumber;
        availablePiecesNumber += piecesNumber;
    }

    void sellProduct(int piecesNumber) {
        bookedPiecesNumber -= piecesNumber;
        soldPiecesNumber += piecesNumber;
    }

    ProductDto toDto() {
        List<String> categories = this.getCategories()
                                         .stream()
                                         .map(Category::getName)
                                         .collect(Collectors.toList());

        return new ProductDto().setProductId(this.getProductId())
                               .setName(this.getName())
                               .setPrice(this.getPrice())
                               .setAvailablePiecesNumber(this.getAvailablePiecesNumber())
                               .setBookedPiecesNumber(this.getBookedPiecesNumber())
                               .setSoldPiecesNumber(this.getSoldPiecesNumber())
                               .setDescription(this.getDescription())
                               .setCategories(categories);
    }

}
