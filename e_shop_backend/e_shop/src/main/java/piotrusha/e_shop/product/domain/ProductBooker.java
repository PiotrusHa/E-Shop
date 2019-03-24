package piotrusha.e_shop.product.domain;

import io.vavr.Tuple2;
import io.vavr.control.Either;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.base.ListValidator;
import piotrusha.e_shop.product.domain.dto.BookProductDto;

import java.util.List;
import java.util.stream.Collectors;

class ProductBooker {

    Either<AppError, List<Product>> bookProducts(List<Tuple2<BookProductDto, Product>> productsToBook) {
        return validateCanBook(productsToBook)
                .map(this::book);
    }

    private Either<AppError, List<Tuple2<BookProductDto, Product>>> validateCanBook(List<Tuple2<BookProductDto, Product>> productsToBook) {
        return ListValidator.checkError(productsToBook, this::validateCanBook);
    }

    private Either<AppError, Tuple2<BookProductDto, Product>> validateCanBook(Tuple2<BookProductDto, Product> productToBook) {
        Product product = productToBook._2;
        Integer piecesNumber = productToBook._1.getPiecesNumber();

        boolean canBook = product.canBook(piecesNumber);
        if (!canBook) {
            return Either.left(AppError.cannotBookProduct(product.getName(), piecesNumber, product.getAvailablePiecesNumber()));
        }
        return Either.right(productToBook);
    }

    private List<Product> book(List<Tuple2<BookProductDto, Product>> productsToBook) {
        return productsToBook.stream()
                             .map(productToBook -> book(productToBook._2, productToBook._1.getPiecesNumber()))
                             .collect(Collectors.toList());
    }

    private Product book(Product productToBook, Integer piecesNumber) {
        productToBook.bookProduct(piecesNumber);
        return productToBook;
    }

}
