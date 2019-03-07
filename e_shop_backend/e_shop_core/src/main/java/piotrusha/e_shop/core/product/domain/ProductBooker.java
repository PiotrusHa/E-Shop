package piotrusha.e_shop.core.product.domain;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import piotrusha.e_shop.core.product.domain.dto.BookProductDto;
import piotrusha.e_shop.core.product.domain.exception.ProductNotFoundException;
import piotrusha.e_shop.core.product.domain.exception.ProductValidationException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

class ProductBooker {

    private final ProductRepository productRepository;

    ProductBooker(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    void bookProducts(List<BookProductDto> bookProductDtos) {
        List<Tuple2<Product, Integer>> productsToBook = get(bookProductDtos);
        validatePiecesNumber(productsToBook);
        book(productsToBook);
        save(productsToBook);
    }

    private List<Tuple2<Product, Integer>> get(List<BookProductDto> dtos) {
        return dtos.stream()
                   .map(dto -> Tuple.of(get(dto.getProductId()), dto.getPiecesNumber()))
                   .collect(Collectors.toList());
    }

    private Product get(BigDecimal productId) {
        return productRepository.findByProductId(productId)
                                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    private void validatePiecesNumber(List<Tuple2<Product, Integer>> productsToBook) {
        for (Tuple2<Product, Integer> productToBook : productsToBook) {
            boolean canBook = productToBook._1.canBook(productToBook._2);
            if (!canBook) {
                throw ProductValidationException.notEnoughPiecesToBook(productToBook._1.getAvailablePiecesNumber(),
                                                                       productToBook._2, productToBook._1.getName());
            }
        }
    }

    private void book(List<Tuple2<Product, Integer>> productsToBook) {
        productsToBook.forEach(productToBook -> productToBook._1.bookProduct(productToBook._2));
    }

    private void save(List<Tuple2<Product, Integer>> productsToBook) {
        List<Product> products = productsToBook.stream()
                                               .map(productToBook -> productToBook._1)
                                               .collect(Collectors.toList());
        productRepository.saveAll(products);
    }

}
