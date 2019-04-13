package piotrusha.e_shop.product.domain;

import io.vavr.control.Either;
import io.vavr.control.Option;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.product.domain.dto.BookProductDto;
import piotrusha.e_shop.product.domain.dto.CancelProductBookingDto;
import piotrusha.e_shop.product.domain.dto.CreateProductCategoryDto;
import piotrusha.e_shop.product.domain.dto.CreateProductDto;
import piotrusha.e_shop.product.domain.dto.ModifyProductDto;
import piotrusha.e_shop.product.domain.dto.ProductCategoryDto;
import piotrusha.e_shop.product.domain.dto.ProductDto;
import piotrusha.e_shop.product.domain.dto.SellProductDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ProductFacade {

    private final CategoryCreator categoryCreator;
    private final CategoryFinder categoryFinder;

    private final ProductCreator productCreator;
    private final ProductModifier productModifier;
    private final ProductBooker productBooker;
    private final ProductBookingCanceler productBookingCanceler;
    private final ProductFinder productFinder;
    private final ProductSeller productSeller;

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private final DtoValidator dtoValidator;

    ProductFacade(CategoryCreator categoryCreator, CategoryFinder categoryFinder, ProductCreator productCreator,
                  ProductModifier productModifier, ProductBooker productBooker, ProductBookingCanceler productBookingCanceler,
                  ProductFinder productFinder, ProductSeller productSeller, ProductRepository productRepository,
                  CategoryRepository categoryRepository, DtoValidator dtoValidator) {
        this.categoryCreator = categoryCreator;
        this.categoryFinder = categoryFinder;
        this.productCreator = productCreator;
        this.productModifier = productModifier;
        this.productBooker = productBooker;
        this.productBookingCanceler = productBookingCanceler;
        this.productFinder = productFinder;
        this.productSeller = productSeller;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.dtoValidator = dtoValidator;
    }

    public Either<AppError, ProductCategoryDto> createProductCategory(CreateProductCategoryDto createProductCategoryDto) {
        return dtoValidator.validateCreateCategoryDto(createProductCategoryDto)
                           .map(categoryCreator::createCategory)
                           .map(Category::toDto)
                           .peek(categoryRepository::save);
    }

    public Either<AppError, ProductDto> createProduct(CreateProductDto createProductDto) {
        return dtoValidator.validateCreateProductDto(createProductDto)
                           .map(productCreator::createProduct)
                           .map(Product::toDto)
                           .peek(productRepository::save);
    }

    public Either<AppError, ProductDto> modifyProduct(ModifyProductDto modifyProductDto) {
        return dtoValidator.validateModifyProductDto(modifyProductDto)
                           .map(productModifier::modifyProduct)
                           .map(Product::toDto)
                           .peek(productRepository::save);
    }

    public Either<AppError, List<ProductDto>> bookProducts(List<BookProductDto> bookProductDtos) {
        return dtoValidator.validateBookDto(bookProductDtos)
                           .flatMap(productBooker::bookProducts)
                           .map(this::toDto)
                           .peek(productRepository::saveAll);
    }

    public Either<AppError, List<ProductDto>> cancelBooking(List<CancelProductBookingDto> cancelProductBookingDtos) {
        return dtoValidator.validateCancelDto(cancelProductBookingDtos)
                           .flatMap(productBookingCanceler::cancelBooking)
                           .map(this::toDto)
                           .peek(productRepository::saveAll);
    }

    public Either<AppError, List<ProductDto>> sellProducts(List<SellProductDto> sellProductDtos) {
        return dtoValidator.validateSellDto(sellProductDtos)
                           .flatMap(productSeller::sellProducts)
                           .map(this::toDto)
                           .peek(productRepository::saveAll);
    }

    public List<ProductCategoryDto> findAllProductCategories() {
        return categoryFinder.findAll();
    }

    public Option<ProductDto> findProductByProductId(BigDecimal productId) {
        return productFinder.findByProductId(productId);
    }

    public List<ProductDto> findProductsByCategoryName(String categoryName) {
        return productFinder.findProductsByCategoryName(categoryName);
    }

    private List<ProductDto> toDto(List<Product> products) {
        return products.stream()
                       .map(Product::toDto)
                       .collect(Collectors.toList());
    }

}
