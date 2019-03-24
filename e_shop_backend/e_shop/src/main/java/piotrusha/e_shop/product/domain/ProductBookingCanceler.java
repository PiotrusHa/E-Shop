package piotrusha.e_shop.product.domain;

import io.vavr.Tuple2;
import io.vavr.control.Either;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.base.ListValidator;
import piotrusha.e_shop.product.domain.dto.CancelProductBookingDto;

import java.util.List;
import java.util.stream.Collectors;

class ProductBookingCanceler {

    Either<AppError, List<Product>> cancelBooking(List<Tuple2<CancelProductBookingDto, Product>> productsToCancel) {
        return validateCanCancel(productsToCancel)
                .map(this::cancel);
    }

    private Either<AppError, List<Tuple2<CancelProductBookingDto, Product>>> validateCanCancel(List<Tuple2<CancelProductBookingDto, Product>> productsToCancel) {
        return ListValidator.checkError(productsToCancel, this::validateCanCancel);
    }

    private Either<AppError, Tuple2<CancelProductBookingDto, Product>> validateCanCancel(Tuple2<CancelProductBookingDto, Product> productToCancel) {
        Product product = productToCancel._2;
        Integer piecesNumber = productToCancel._1.getPiecesNumber();

        boolean canCancel = product.canCancel(piecesNumber);
        if (!canCancel) {
            return Either.left(AppError.validation(String.format("Cannot cancel booking %s pieces of product %s.",
                                                                 piecesNumber, product.getName())));
        }
        return Either.right(productToCancel);
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
