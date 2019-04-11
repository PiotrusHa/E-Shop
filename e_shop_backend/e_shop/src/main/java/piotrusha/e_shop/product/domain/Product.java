package piotrusha.e_shop.product.domain;

import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.product.domain.dto.ProductCategoryDto;
import piotrusha.e_shop.product.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
class Product {

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

    void modifyPrice(BigDecimal newPrice) {
        price = newPrice;
    }

    void modifyDescription(String newDescription) {
        description = newDescription;
    }

    void modifyAvailablePiecesNumber(Integer newAvailablePiecesNumber) {
        availablePiecesNumber = newAvailablePiecesNumber;
    }

    Either<AppError, Product> bookProduct(int piecesNumber) {
        if (!canBook(piecesNumber)) {
            return Either.left(AppError.cannotBookProduct(name, piecesNumber, availablePiecesNumber));
        }

        availablePiecesNumber -= piecesNumber;
        bookedPiecesNumber += piecesNumber;
        return Either.right(this);
    }

    private boolean canBook(int piecesNumber) {
        return availablePiecesNumber >= piecesNumber;
    }

    Either<AppError, Product> cancelBooking(int piecesNumber) {
        if (!canCancel(piecesNumber)) {
            return Either.left(AppError.cannotCancelProductBooking(name, piecesNumber));
        }

        bookedPiecesNumber -= piecesNumber;
        availablePiecesNumber += piecesNumber;
        return Either.right(this);
    }

    private boolean canCancel(int piecesNumber) {
        return bookedPiecesNumber >= piecesNumber;
    }

    Either<AppError, Product> sellProduct(int piecesNumber) {
        if (!canSell(piecesNumber)) {
            return Either.left(AppError.cannotSellProduct(name, piecesNumber, bookedPiecesNumber));
        }

        bookedPiecesNumber -= piecesNumber;
        soldPiecesNumber += piecesNumber;
        return Either.right(this);
    }

    private boolean canSell(int piecesNumber) {
        return bookedPiecesNumber >= piecesNumber;
    }

    ProductDto toDto() {
        List<String> categoriesDto = categories.stream()
                                               .map(Category::toDto)
                                               .map(ProductCategoryDto::getName)
                                               .collect(Collectors.toList());

        return ProductDto.builder()
                         .productId(productId)
                         .name(name)
                         .price(price)
                         .availablePiecesNumber(availablePiecesNumber)
                         .bookedPiecesNumber(bookedPiecesNumber)
                         .soldPiecesNumber(soldPiecesNumber)
                         .description(description)
                         .categories(categoriesDto)
                         .build();
    }

    static Product fromDto(ProductDto dto) {
        Set<Category> categories = dto.getCategories()
                                      .stream()
                                      .map(Category::new)
                                      .collect(Collectors.toSet());

        return Product.builder()
                      .productId(dto.getProductId())
                      .name(dto.getName())
                      .price(dto.getPrice())
                      .availablePiecesNumber(dto.getAvailablePiecesNumber())
                      .bookedPiecesNumber(dto.getBookedPiecesNumber())
                      .soldPiecesNumber(dto.getSoldPiecesNumber())
                      .description(dto.getDescription())
                      .categories(categories)
                      .build();
    }

}
