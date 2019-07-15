package piotrusha.e_shop.product.domain;

import io.vavr.Tuple2;
import io.vavr.collection.Seq;
import io.vavr.control.Either;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.product.domain.dto.CancelProductBookingDto;

import java.util.List;
import java.util.stream.Collectors;

class ProductBookingCanceler {

    Either<AppError, List<Product>> cancelBooking(List<Tuple2<CancelProductBookingDto, Product>> productsToCancel) {
        return Either.sequenceRight(productsToCancel.stream()
                                                    .map(this::cancelBooking)
                                                    .collect(Collectors.toList()))
                     .map(Seq::asJava);
    }

    private Either<AppError, Product> cancelBooking(Tuple2<CancelProductBookingDto, Product> productToCancel) {
        int piecesNumber = productToCancel._1.getPiecesNumber();
        return productToCancel._2.cancelBooking(piecesNumber);
    }

}
