package piotrusha.e_shop.core.product.domain;

import io.vavr.Tuple2;
import piotrusha.e_shop.core.product.domain.dto.SellProductDto;
import piotrusha.e_shop.core.product.domain.exception.ProductValidationException;

import java.util.List;
import java.util.stream.Collectors;

class ProductSeller {

    List<Product> sellProducts(List<Tuple2<SellProductDto, Product>> productsToSell) {
        validateCanSell(productsToSell);
        return sell(productsToSell);
    }

    private void validateCanSell(List<Tuple2<SellProductDto, Product>> productsToSell) {
        for (Tuple2<SellProductDto, Product> productToSell : productsToSell) {
            validateCanSell(productToSell._2, productToSell._1.getPiecesNumber());
        }
    }

    private void validateCanSell(Product productToSell, Integer piecesNumber) {
        boolean canSell = productToSell.canSell(piecesNumber);
        if (!canSell) {
            throw ProductValidationException.notEnoughPiecesToSell(piecesNumber, productToSell.getName(),
                                                                   productToSell.getBookedPiecesNumber());
        }
    }

    private List<Product> sell(List<Tuple2<SellProductDto, Product>> productsToSell) {
        return productsToSell.stream()
                             .map(productToSell -> sell(productToSell._2, productToSell._1.getPiecesNumber()))
                             .collect(Collectors.toList());
    }

    private Product sell(Product productToSell, Integer piecesNumber) {
        productToSell.sellProduct(piecesNumber);
        return productToSell;
    }

}
