package piotrusha.e_shop.core.product.domain;

import io.vavr.Tuple2;
import piotrusha.e_shop.core.product.domain.dto.BookProductDto;
import piotrusha.e_shop.core.product.domain.exception.ProductValidationException;

import java.util.List;
import java.util.stream.Collectors;

class ProductBooker {

    List<Product> bookProducts(List<Tuple2<BookProductDto, Product>> productsToBook) {
        validateCanBook(productsToBook);
        return book(productsToBook);
    }

    private void validateCanBook(List<Tuple2<BookProductDto, Product>> productsToBook) {
        for (Tuple2<BookProductDto, Product> productToBook : productsToBook) {
            validateCanBook(productToBook._2, productToBook._1.getPiecesNumber());
        }
    }

    private void validateCanBook(Product productToBook, Integer piecesNumber) {
        boolean canBook = productToBook.canBook(piecesNumber);
        if (!canBook) {
            throw ProductValidationException.notEnoughPiecesToBook(productToBook.getAvailablePiecesNumber(), piecesNumber,
                                                                   productToBook.getName());
        }
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
