package piotrusha.e_shop.product.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import piotrusha.e_shop.product.persistence.in_memory.InMemoryCategoryRepository;
import piotrusha.e_shop.product.persistence.in_memory.InMemoryProductRepository;

@Configuration
class ProductConfiguration {

    @Bean
    ProductFacade productFacade(CategoryRepository categoryRepository, ProductRepository productRepository) {
        CategoryCreator categoryCreator = new CategoryCreator();

        ProductIdGenerator productIdGenerator = new ProductIdGenerator(productRepository);
        DtoValidator dtoValidator = new DtoValidator(categoryRepository, productRepository);
        ProductCreator productCreator = new ProductCreator(productIdGenerator);
        ProductModifier productModifier = new ProductModifier();
        ProductBooker productBooker = new ProductBooker();
        ProductBookingCanceler productBookingCanceler = new ProductBookingCanceler();
        ProductSeller productSeller = new ProductSeller();

        return new ProductFacade(categoryCreator, productCreator, productModifier, productBooker, productBookingCanceler, productSeller,
                                 productRepository, categoryRepository, dtoValidator);
    }

    ProductFacade productFacade() {
        CategoryRepository categoryRepository = new InMemoryCategoryRepository();
        ProductRepository productRepository = new InMemoryProductRepository();
        return productFacade(categoryRepository, productRepository);
    }

}
