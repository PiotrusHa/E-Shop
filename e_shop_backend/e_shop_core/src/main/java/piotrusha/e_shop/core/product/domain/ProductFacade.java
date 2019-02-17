package piotrusha.e_shop.core.product.domain;

import piotrusha.e_shop.core.product.domain.dto.CreateProductCategoryDto;
import piotrusha.e_shop.core.product.domain.dto.CreateProductDto;
import piotrusha.e_shop.core.product.domain.dto.ModifyProductDto;
import piotrusha.e_shop.core.product.domain.dto.ProductCategoryDto;
import piotrusha.e_shop.core.product.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProductFacade {

    private final CategoryCreator categoryCreator;
    private final CategoryFinder categoryFinder;

    private final ProductCreator productCreator;
    private final ProductModifier productModifier;
    private final ProductFinder productFinder;

    ProductFacade(CategoryCreator categoryCreator, CategoryFinder categoryFinder, ProductCreator productCreator,
                  ProductModifier productModifier, ProductFinder productFinder) {
        this.categoryCreator = categoryCreator;
        this.categoryFinder = categoryFinder;
        this.productCreator = productCreator;
        this.productModifier = productModifier;
        this.productFinder = productFinder;
    }

    public void createProductCategory(CreateProductCategoryDto createProductCategoryDto) {
        categoryCreator.createCategory(createProductCategoryDto);
    }

    public ProductDto createProduct(CreateProductDto createProductDto) {
        return productCreator.createProduct(createProductDto);
    }

    public void modifyProduct(ModifyProductDto modifyProductDto) {
        productModifier.modifyProduct(modifyProductDto);
    }

    public List<ProductCategoryDto> findAllProductCategories() {
        return categoryFinder.findAll();
    }

    public Optional<ProductDto> findProductByProductId(BigDecimal productId) {
        return productFinder.findByProductId(productId);
    }

}
