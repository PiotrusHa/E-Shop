package piotrusha.e_shop.core.product.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ProductConfiguration {

    @Bean
    ProductFacade productFacade(CategoryRepository categoryRepository) {
        CategoryConverter categoryConverter = new CategoryConverter();
        CategoryCreator categoryCreator = new CategoryCreator(categoryRepository);
        CategoryFinder categoryFinder = new CategoryFinder(categoryRepository, categoryConverter);
        return new ProductFacade(categoryCreator, categoryFinder);
    }

    ProductFacade productFacade() {
        CategoryRepository categoryRepository = new InMemoryCategoryRepository();
        return productFacade(categoryRepository);
    }

}
