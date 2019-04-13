package piotrusha.e_shop.product.domain;

import io.vavr.Tuple2;
import io.vavr.collection.Seq;
import io.vavr.control.Either;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.product.domain.dto.BookProductDto;

import java.util.List;
import java.util.stream.Collectors;

class ProductBooker {

    Either<AppError, List<Product>> bookProducts(List<Tuple2<BookProductDto, Product>> productsToBook) {
        return Either.sequenceRight(productsToBook.stream()
                                                  .map(this::bookProduct)
                                                  .collect(Collectors.toList()))
                     .map(Seq::asJava);
    }

    private Either<AppError, Product> bookProduct(Tuple2<BookProductDto, Product> productToBook) {
        int piecesNumber = productToBook._1.getPiecesNumber();
        return productToBook._2.bookProduct(piecesNumber);
    }

}
