package piotrusha.e_shop.product.domain;

import com.google.common.base.Strings;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Either;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.base.ListValidator;
import piotrusha.e_shop.product.domain.dto.BookProductDto;
import piotrusha.e_shop.product.domain.dto.CancelProductBookingDto;
import piotrusha.e_shop.product.domain.dto.CreateProductCategoryDto;
import piotrusha.e_shop.product.domain.dto.CreateProductDto;
import piotrusha.e_shop.product.domain.dto.ModifyProductDto;
import piotrusha.e_shop.product.domain.dto.SellProductDto;

import java.math.BigDecimal;
import java.util.List;

class DtoValidator {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    DtoValidator(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    Either<AppError, CreateProductCategoryDto> validateCreateCategoryDto(CreateProductCategoryDto dto) {
        return validateCategoryName(dto.getCategoryName())
                .map(x -> dto);
    }

    private Either<AppError, String> validateCategoryName(String categoryName) {
        if (Strings.isNullOrEmpty(categoryName)) {
            return Either.left(AppError.emptyDtoField("Category name"));
        }
        if (categoryRepository.existsByName(categoryName)) {
            return Either.left(AppError.categoryAlreadyExists(categoryName));
        }
        return Either.right(categoryName);
    }

    Either<AppError, CreateProductDto> validateCreateProductDto(CreateProductDto dto) {
        return validateProductName(dto.getProductName())
                .flatMap(x -> validateProductPrice(dto.getPrice()))
                .flatMap(x -> validatePiecesNumber(dto.getAvailablePiecesNumber()))
                .flatMap(x -> validateCategories(dto.getCategories()))
                .map(x -> dto);
    }

    Either<AppError, Tuple2<ModifyProductDto, Product>> validateModifyProductDto(ModifyProductDto dto) {
        Either<AppError, ?> result = Either.right(null);
        if (dto.getProductAvailablePiecesNumber() != null) {
            result = result.flatMap(x -> validatePiecesNumber(dto.getProductAvailablePiecesNumber()));
        }
        if (dto.getProductCategoriesToAssign() != null && !dto.getProductCategoriesToAssign()
                                                              .isEmpty()) {
            result = result.flatMap(x -> validateCategories(dto.getProductCategoriesToAssign()));
        }
        return result.flatMap(x -> validateProductId(dto.getProductId()))
                     .map(product -> Tuple.of(dto, product));
    }

    Either<AppError, List<Tuple2<BookProductDto, Product>>> validateBookDto(List<BookProductDto> dtos) {
        return ListValidator.validateAndTransform(dtos, this::validateBookDto);
    }

    private Either<AppError, Tuple2<BookProductDto, Product>> validateBookDto(BookProductDto dto) {
        return validatePiecesNumber(dto.getPiecesNumber()).flatMap(piecesNumber -> validateProductId(dto.getProductId()))
                                                          .map(product -> Tuple.of(dto, product));
    }

    Either<AppError, List<Tuple2<CancelProductBookingDto, Product>>> validateCancelDto(List<CancelProductBookingDto> dtos) {
        return ListValidator.validateAndTransform(dtos, this::validateCancelDto);

    }

    private Either<AppError, Tuple2<CancelProductBookingDto, Product>> validateCancelDto(CancelProductBookingDto dto) {
        return validatePiecesNumber(dto.getPiecesNumber()).flatMap(piecesNumber -> validateProductId(dto.getProductId()))
                                                          .map(product -> Tuple.of(dto, product));
    }

    Either<AppError, List<Tuple2<SellProductDto, Product>>> validateSellDto(List<SellProductDto> dtos) {
        return ListValidator.validateAndTransform(dtos, this::validateSellDto);

    }

    private Either<AppError, Tuple2<SellProductDto, Product>> validateSellDto(SellProductDto dto) {
        return validatePiecesNumber(dto.getPiecesNumber()).flatMap(piecesNumber -> validateProductId(dto.getProductId()))
                                                          .map(product -> Tuple.of(dto, product));
    }

    private Either<AppError, Product> validateProductId(BigDecimal productId) {
        if (productId == null) {
            return Either.left(AppError.emptyDtoField("Product id"));
        }

        return productRepository.findByProductId(productId)
                                .map(Product::fromDto)
                                .toEither(() -> AppError.productNotFound(productId));
    }

    private Either<AppError, Integer> validatePiecesNumber(Integer piecesNumber) {
        if (piecesNumber == null || piecesNumber <= 0) {
            return Either.left(AppError.numberShouldBePositive("Product pieces number"));
        }
        return Either.right(piecesNumber);
    }

    private Either<AppError, String> validateProductName(String productName) {
        if (Strings.isNullOrEmpty(productName)) {
            return Either.left(AppError.emptyDtoField("Product name"));
        }
        return Either.right(productName);
    }

    private Either<AppError, BigDecimal> validateProductPrice(BigDecimal productPrice) {
        if (productPrice == null) {
            return Either.left(AppError.emptyDtoField("Product price"));
        }
        return Either.right(productPrice);
    }

    private Either<AppError, List<String>> validateCategories(List<String> categories) {
        for (String categoryName : categories) {
            if (!categoryRepository.existsByName(categoryName)) {
                return Either.left(AppError.categoryDoesNotExists(categoryName));
            }
        }
        return Either.right(categories);
    }

}
