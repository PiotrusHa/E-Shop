package piotrusha.e_shop.core.product.domain;

import io.vavr.control.Either;
import io.vavr.control.Option;
import piotrusha.e_shop.core.base.AbstractFacade;
import piotrusha.e_shop.core.base.AppError;
import piotrusha.e_shop.core.product.domain.dto.AbstractProductActionDto;
import piotrusha.e_shop.core.product.domain.dto.BookProductDto;
import piotrusha.e_shop.core.product.domain.dto.CancelProductBookingDto;
import piotrusha.e_shop.core.product.domain.dto.CreateProductCategoryDto;
import piotrusha.e_shop.core.product.domain.dto.CreateProductDto;
import piotrusha.e_shop.core.product.domain.dto.ModifyProductDto;
import piotrusha.e_shop.core.product.domain.dto.ProductCategoryDto;
import piotrusha.e_shop.core.product.domain.dto.ProductDto;
import piotrusha.e_shop.core.product.domain.dto.SellProductDto;
import piotrusha.e_shop.core.product.domain.exception.ProductNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public class ProductFacade extends AbstractFacade<Product, AbstractProductActionDto> {

    private final CategoryCreator categoryCreator;
    private final CategoryFinder categoryFinder;

    private final ProductCreator productCreator;
    private final ProductModifier productModifier;
    private final ProductBooker productBooker;
    private final ProductBookingCanceler productBookingCanceler;
    private final ProductFinder productFinder;
    private final ProductSeller productSeller;

    private final ProductRepository productRepository;

    private final DtoValidator dtoValidator;

    ProductFacade(CategoryCreator categoryCreator, CategoryFinder categoryFinder, ProductCreator productCreator,
                  ProductModifier productModifier, ProductBooker productBooker, ProductBookingCanceler productBookingCanceler,
                  ProductFinder productFinder, ProductSeller productSeller, ProductRepository productRepository,
                  DtoValidator dtoValidator) {
        this.categoryCreator = categoryCreator;
        this.categoryFinder = categoryFinder;
        this.productCreator = productCreator;
        this.productModifier = productModifier;
        this.productBooker = productBooker;
        this.productBookingCanceler = productBookingCanceler;
        this.productFinder = productFinder;
        this.productSeller = productSeller;
        this.productRepository = productRepository;
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
        performAction(productModifier::modifyProduct, modifyProductDto);
    }

    public Either<AppError, List<Product>> bookProducts(List<BookProductDto> bookProductDtos) {
        return dtoValidator.validateBookDto(bookProductDtos)
                           .flatMap(productBooker::bookProducts)
                           .peek(this::saveAll);
    }

    public Either<AppError, List<Product>> cancelBooking(List<CancelProductBookingDto> cancelProductBookingDtos) {
        return dtoValidator.validateCancelDto(cancelProductBookingDtos)
                           .flatMap(productBookingCanceler::cancelBooking)
                           .peek(this::saveAll);
    }

    public Either<AppError, List<Product>> sellProducts(List<SellProductDto> sellProductDtos) {
        return dtoValidator.validateSellDto(sellProductDtos)
                           .flatMap(productSeller::sellProducts)
                           .peek(this::saveAll);
    }

    public List<ProductCategoryDto> findAllProductCategories() {
        return categoryFinder.findAll();
    }

    public Option<ProductDto> findProductByProductId(BigDecimal productId) {
        return productFinder.findByProductId(productId);
    }

    @Override
    protected Product findEntity(AbstractProductActionDto dto) {
        return productRepository.findByProductId(dto.getProductId())
                                .getOrElseThrow(() -> new ProductNotFoundException(dto.getProductId()));
    }

    @Override
    protected void save(Product product) {
        productRepository.save(product);
    }

    @Override
    protected void saveAll(List<Product> products) {
        productRepository.saveAll(products);
    }

}
