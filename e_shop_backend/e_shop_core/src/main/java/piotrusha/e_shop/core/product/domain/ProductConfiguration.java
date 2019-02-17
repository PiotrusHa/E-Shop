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
        ProductCreator productCreator = new ProductCreator(productRepository, categoryRepository, productIdGenerator, productConverter);
        ProductFinder productFinder = new ProductFinder(productRepository, productConverter);

        return new ProductFacade(categoryCreator, categoryFinder, productCreator, productFinder);
    }

    ProductFacade productFacade() {
        CategoryRepository categoryRepository = new InMemoryCategoryRepository();
        ProductRepository productRepository = new InMemoryProductRepository();
        return productFacade(categoryRepository, productRepository);
    }

}
