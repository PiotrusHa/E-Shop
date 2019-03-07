package piotrusha.e_shop.core.product.domain;

import piotrusha.e_shop.core.product.domain.dto.BookProductDto;
import piotrusha.e_shop.core.product.domain.dto.CancelProductBookingDto;
import piotrusha.e_shop.core.product.domain.dto.CreateProductCategoryDto;
import piotrusha.e_shop.core.product.domain.dto.CreateProductDto;
import piotrusha.e_shop.core.product.domain.dto.ModifyProductDto;
import piotrusha.e_shop.core.product.domain.dto.ProductCategoryDto;
import piotrusha.e_shop.core.product.domain.dto.ProductDto;
import piotrusha.e_shop.core.product.domain.dto.SellProductDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProductFacade {

    private final CategoryCreator categoryCreator;
    private final CategoryFinder categoryFinder;

    private final ProductCreator productCreator;
    private final ProductModifier productModifier;
    private final ProductBooker productBooker;
    private final ProductBookingCanceler productBookingCanceler;
    private final ProductFinder productFinder;
    private final ProductSeller productSeller;

    private final DtoValidator dtoValidator;

    ProductFacade(CategoryCreator categoryCreator, CategoryFinder categoryFinder, ProductCreator productCreator,
                  ProductModifier productModifier, ProductBooker productBooker, ProductBookingCanceler productBookingCanceler,
                  ProductFinder productFinder, ProductSeller productSeller, DtoValidator dtoValidator) {
        this.categoryCreator = categoryCreator;
        this.categoryFinder = categoryFinder;
        this.productCreator = productCreator;
        this.productModifier = productModifier;
        this.productBooker = productBooker;
        this.productBookingCanceler = productBookingCanceler;
        this.productFinder = productFinder;
        this.productSeller = productSeller;
        this.dtoValidator = dtoValidator;
    }

    public void createProductCategory(CreateProductCategoryDto createProductCategoryDto) {
        dtoValidator.validateDto(createProductCategoryDto);
        categoryCreator.createCategory(createProductCategoryDto);
    }

    public ProductDto createProduct(CreateProductDto createProductDto) {
        dtoValidator.validateDto(createProductDto);
        return productCreator.createProduct(createProductDto);
    }

    public void modifyProduct(ModifyProductDto modifyProductDto) {
        dtoValidator.validateDto(modifyProductDto);
        productModifier.modifyProduct(modifyProductDto);
    }

    public void bookProducts(List<BookProductDto> bookProductDtos) {
        bookProductDtos.forEach(dtoValidator::validateDto);
        productBooker.bookProducts(bookProductDtos);
    }

    public void cancelBooking(List<CancelProductBookingDto> cancelProductBookingDtos) {
        cancelProductBookingDtos.forEach(dtoValidator::validateDto);
        productBookingCanceler.cancelBooking(cancelProductBookingDtos);
    }

    public void sellProducts(List<SellProductDto> sellProductDtos) {
        sellProductDtos.forEach(dtoValidator::validateDto);
        productSeller.sellProducts(sellProductDtos);
    }

    public List<ProductCategoryDto> findAllProductCategories() {
        return categoryFinder.findAll();
    }

    public Optional<ProductDto> findProductByProductId(BigDecimal productId) {
        return productFinder.findByProductId(productId);
    }

}
