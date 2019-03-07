package piotrusha.e_shop.core.product.domain;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import piotrusha.e_shop.core.product.domain.dto.SellProductDto;
import piotrusha.e_shop.core.product.domain.exception.ProductNotFoundException;
import piotrusha.e_shop.core.product.domain.exception.ProductValidationException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

class ProductSeller {

    private final ProductRepository productRepository;

    ProductSeller(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    void sellProducts(List<SellProductDto> dtos) {
        List<Tuple2<Product, Integer>> productsToCancel = get(dtos);
        canSell(productsToCancel);
        sell(productsToCancel);
        save(productsToCancel);
    }

    private List<Tuple2<Product, Integer>> get(List<SellProductDto> dtos) {
        return dtos.stream()
                   .map(dto -> Tuple.of(getProduct(dto.getProductId()), dto.getPiecesNumber()))
                   .collect(Collectors.toList());
    }

    private Product getProduct(BigDecimal productId) {
        return productRepository.findByProductId(productId)
                                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    private void canSell(List<Tuple2<Product, Integer>> productsToCancel) {
        for (Tuple2<Product, Integer> product : productsToCancel) {
            boolean canSell = product._1.canSell(product._2);
            if (!canSell) {
                throw ProductValidationException.notEnoughPiecesToSell(product._2, product._1.getName(), product._1.getBookedPiecesNumber());
            }
        }
    }

    private void sell(List<Tuple2<Product, Integer>> productsToCancel) {
        productsToCancel.forEach(productToCancel -> productToCancel._1.sellProduct(productToCancel._2));
    }

    private void save(List<Tuple2<Product, Integer>> productsToCancel) {
        List<Product> products = productsToCancel.stream()
                                                 .map(Tuple2::_1)
                                                 .collect(Collectors.toList());
        productRepository.saveAll(products);
    }

}
