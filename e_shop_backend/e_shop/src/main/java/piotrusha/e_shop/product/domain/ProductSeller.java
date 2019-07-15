package piotrusha.e_shop.product.domain;

import io.vavr.Tuple2;
import io.vavr.collection.Seq;
import io.vavr.control.Either;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.product.domain.dto.SellProductDto;

import java.util.List;
import java.util.stream.Collectors;

class ProductSeller {

    Either<AppError, List<Product>> sellProducts(List<Tuple2<SellProductDto, Product>> productsToSell) {
        return Either.sequenceRight(productsToSell.stream()
                                                  .map(this::sellProduct)
                                                  .collect(Collectors.toList()))
                     .map(Seq::asJava);
    }

    private Either<AppError, Product> sellProduct(Tuple2<SellProductDto, Product> productToSell) {
        int piecesNumber = productToSell._1.getPiecesNumber();
        return productToSell._2.sellProduct(piecesNumber);
    }

}
