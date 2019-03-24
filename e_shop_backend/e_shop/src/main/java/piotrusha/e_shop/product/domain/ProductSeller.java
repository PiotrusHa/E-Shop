package piotrusha.e_shop.product.domain;

import io.vavr.Tuple2;
import io.vavr.control.Either;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.base.ListValidator;
import piotrusha.e_shop.product.domain.dto.SellProductDto;

import java.util.List;
import java.util.stream.Collectors;

class ProductSeller {

    Either<AppError, List<Product>> sellProducts(List<Tuple2<SellProductDto, Product>> productsToSell) {
        return validateCanSell(productsToSell).map(this::sell);
    }

    private Either<AppError, List<Tuple2<SellProductDto, Product>>> validateCanSell(List<Tuple2<SellProductDto, Product>> productsToSell) {
        return ListValidator.checkError(productsToSell, this::validateCanSell);
    }

    private Either<AppError, Tuple2<SellProductDto, Product>> validateCanSell(Tuple2<SellProductDto, Product> productsToSell) {
        Product product = productsToSell._2;
        Integer piecesNumber = productsToSell._1.getPiecesNumber();

        boolean canSell = product.canSell(piecesNumber);
        if (!canSell) {
            return Either.left(AppError.validation(String.format("Cannot sell %s pieces of product %s. Currently booked pieces number is %s.",
                                                                 piecesNumber, product.getName(), product.getBookedPiecesNumber())));
        }
        return Either.right(productsToSell);
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
