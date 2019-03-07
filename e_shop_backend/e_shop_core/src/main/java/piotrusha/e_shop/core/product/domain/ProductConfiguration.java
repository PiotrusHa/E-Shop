package piotrusha.e_shop.core.product.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ProductConfiguration {

    @Bean
    ProductFacade productFacade(CategoryRepository categoryRepository, ProductRepository productRepository) {
        CategoryConverter categoryConverter = new CategoryConverter();
        CategoryCreator categoryCreator = new CategoryCreator(categoryRepository);
        CategoryFinder categoryFinder = new CategoryFinder(categoryRepository, categoryConverter);

        ProductIdGenerator productIdGenerator = new ProductIdGenerator(productRepository);
        ProductConverter productConverter = new ProductConverter();
        DtoValidator dtoValidator = new DtoValidator(categoryRepository);
        ProductCreator productCreator = new ProductCreator(productRepository, productIdGenerator, productConverter);
        ProductModifier productModifier = new ProductModifier(productRepository);
        ProductFinder productFinder = new ProductFinder(productRepository, productConverter);
        ProductBooker productBooker = new ProductBooker(productRepository);
        ProductBookingCanceler productBookingCanceler = new ProductBookingCanceler(productRepository);
        ProductSeller productSeller = new ProductSeller(productRepository);

        return new ProductFacade(categoryCreator, categoryFinder, productCreator, productModifier, productBooker, productBookingCanceler,
                                 productFinder, productSeller, dtoValidator);
    }

    ProductFacade productFacade() {
        CategoryRepository categoryRepository = new InMemoryCategoryRepository();
        ProductRepository productRepository = new InMemoryProductRepository();
        return productFacade(categoryRepository, productRepository);
    }

}
