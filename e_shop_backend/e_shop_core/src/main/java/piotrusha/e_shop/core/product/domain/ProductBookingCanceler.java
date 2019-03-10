package piotrusha.e_shop.core.product.domain;

import io.vavr.Tuple2;
import piotrusha.e_shop.core.product.domain.dto.CancelProductBookingDto;
import piotrusha.e_shop.core.product.domain.exception.ProductValidationException;

import java.util.List;
import java.util.stream.Collectors;

class ProductBookingCanceler {

    List<Product> cancelBooking(List<Tuple2<CancelProductBookingDto, Product>> productsToCancel) {
        validateCanCancel(productsToCancel);
        return cancel(productsToCancel);
    }

    private void validateCanCancel(List<Tuple2<CancelProductBookingDto, Product>> productsToCancel) {
        for (Tuple2<CancelProductBookingDto, Product> productToCancel : productsToCancel) {
            validateCanCancel(productToCancel._2, productToCancel._1.getPiecesNumber());
        }
    }

    private void validateCanCancel(Product productToCancel, Integer piecesNumber) {
        boolean canCancel = productToCancel.canCancel(piecesNumber);
        if (!canCancel) {
            throw ProductValidationException.notEnoughPiecesToCancel(piecesNumber, productToCancel.getName());
        }
    }

    private List<Product> cancel(List<Tuple2<CancelProductBookingDto, Product>> productsToCancel) {
        return productsToCancel.stream()
                               .map(productToCancel -> cancel(productToCancel._2, productToCancel._1.getPiecesNumber()))
                               .collect(Collectors.toList());
    }

    private Product cancel(Product productToCancel, Integer piecesNumber) {
        productToCancel.cancelBooking(piecesNumber);
        return productToCancel;
    }

}
