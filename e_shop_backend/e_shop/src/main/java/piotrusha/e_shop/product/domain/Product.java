package piotrusha.e_shop.product.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import piotrusha.e_shop.product.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Product {

    private BigDecimal productId;
    private String name;
    private BigDecimal price;
    private Integer availablePiecesNumber;
    private Integer bookedPiecesNumber;
    private Integer soldPiecesNumber;
    private String description;
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

    void modifyPrice(BigDecimal newPrice) {
        price = newPrice;
    }

    void modifyDescription(String newDescription) {
        description = newDescription;
    }

    void modifyAvailablePiecesNumber(Integer newAvailablePiecesNumber) {
        availablePiecesNumber = newAvailablePiecesNumber;
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
