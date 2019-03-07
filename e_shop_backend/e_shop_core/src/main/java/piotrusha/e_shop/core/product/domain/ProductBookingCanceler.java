package piotrusha.e_shop.core.product.domain;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import piotrusha.e_shop.core.product.domain.dto.CancelProductBookingDto;
import piotrusha.e_shop.core.product.domain.exception.ProductNotFoundException;
import piotrusha.e_shop.core.product.domain.exception.ProductValidationException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

class ProductBookingCanceler {

    private final ProductRepository productRepository;

    ProductBookingCanceler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    void cancelBooking(List<CancelProductBookingDto> dtos) {
        List<Tuple2<Product, Integer>> productsToCancel = get(dtos);
        canCancel(productsToCancel);
        cancel(productsToCancel);
        save(productsToCancel);
    }

    private List<Tuple2<Product, Integer>> get(List<CancelProductBookingDto> dtos) {
        return dtos.stream()
                   .map(dto -> Tuple.of(getProduct(dto.getProductId()), dto.getPiecesNumber()))
                   .collect(Collectors.toList());
    }

    private Product getProduct(BigDecimal productId) {
        return productRepository.findByProductId(productId)
                                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    private void canCancel(List<Tuple2<Product, Integer>> productsToCancel) {
        for (Tuple2<Product, Integer> product : productsToCancel) {
            if (!product._1.canCancel(product._2)) {
                throw ProductValidationException.notEnoughPiecesToCancel(product._2, product._1.getName());
            }
        }
    }

    private void cancel(List<Tuple2<Product, Integer>> productsToCancel) {
        productsToCancel.forEach(productToCancel -> productToCancel._1.cancelBooking(productToCancel._2));
    }

    private void save(List<Tuple2<Product, Integer>> productsToCancel) {
        List<Product> products = productsToCancel.stream()
                                                .map(Tuple2::_1)
                                                .collect(Collectors.toList());
        productRepository.saveAll(products);
    }

}
