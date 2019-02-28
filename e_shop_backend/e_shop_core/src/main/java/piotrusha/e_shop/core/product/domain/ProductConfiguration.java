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
        ProductValidator productValidator = new ProductValidator(categoryRepository);
        ProductCreator productCreator = new ProductCreator(productRepository, productValidator, productIdGenerator, productConverter);
        ProductModifier productModifier = new ProductModifier(productRepository, productValidator);
        ProductFinder productFinder = new ProductFinder(productRepository, productConverter);
        ProductBooker productBooker = new ProductBooker(productRepository, productValidator);

        return new ProductFacade(categoryCreator, categoryFinder, productCreator, productModifier, productBooker, productFinder);
    }

    ProductFacade productFacade() {
        CategoryRepository categoryRepository = new InMemoryCategoryRepository();
        ProductRepository productRepository = new InMemoryProductRepository();
        return productFacade(categoryRepository, productRepository);
    }

}
