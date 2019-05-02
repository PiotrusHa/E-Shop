package piotrusha.e_shop.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.base.AppError.ErrorType;
import piotrusha.e_shop.product.domain.dto.ProductDto;
import piotrusha.e_shop.product.domain.dto.SellProductDto;

import java.math.BigDecimal;
import java.util.List;

class ProductSellingTest extends ProductTest {

    private ProductDto productToSell;
    private ProductDto productToSell2;

    @BeforeEach
    protected void init() {
        super.init();

        productToSell = createBookedProduct();
        productToSell2 = createBookedProduct2();
    }

    @Test
    void sell() {
        SellProductDto sellProductDto = sellProductDto(productToSell.getProductId(), 10);
        ProductDto expectedChangedProduct = expectedProductDto(productToSell, sellProductDto);

        Either<AppError, List<ProductDto>> result = productFacade.sellProducts(List.of(sellProductDto));

        assertTrue(result.isRight());
        assertProductDto(expectedChangedProduct, result.get().get(0));
        assertWithProductFromDatabase(productToSell.getProductId(), expectedChangedProduct);
    }

    @Test
    void sellTwoProducts() {
        SellProductDto sellProductDto = sellProductDto(productToSell.getProductId(), 10);
        SellProductDto sellProductDto2 = sellProductDto(productToSell2.getProductId(), 21);
        ProductDto expectedChangedProduct = expectedProductDto(productToSell, sellProductDto);
        ProductDto expectedChangedProduct2 = expectedProductDto(productToSell2, sellProductDto2);

        Either<AppError, List<ProductDto>> result = productFacade.sellProducts(List.of(sellProductDto, sellProductDto2));

        assertTrue(result.isRight());
        assertProductDto(expectedChangedProduct, result.get().get(0));
        assertProductDto(expectedChangedProduct2, result.get().get(1));
        assertWithProductFromDatabase(productToSell.getProductId(), expectedChangedProduct);
        assertWithProductFromDatabase(productToSell2.getProductId(), expectedChangedProduct2);
    }

    @Test
    void sellNotEnoughPiecesNumber() {
        int piecesNumber = productToSell.getAvailablePiecesNumber() + 1;
        SellProductDto dto = sellProductDto(productToSell.getProductId(), piecesNumber);
        String expectedMessage = String.format("Cannot sell %s pieces of product %s. Currently booked pieces number is %s.",
                                               piecesNumber, productToSell.getName(), productToSell.getBookedPiecesNumber());
        ErrorType expectedErrorType = ErrorType.CANNOT_SELL_PRODUCT;

        Either<AppError, List<ProductDto>> result = productFacade.sellProducts(List.of(dto));

        assertTrue(result.isLeft());
        assertEquals(expectedMessage, result.getLeft().getErrorMessage());
        assertEquals(expectedErrorType, result.getLeft().getErrorType());
        assertProductDidNotChange(productToSell);
    }

    @Test
    void sellFirstOkSecondNotEnoughPiecesNumber() {
        Integer piecesNumber = productToSell2.getAvailablePiecesNumber() + 1;
        SellProductDto dto = sellProductDto(productToSell.getProductId(), 1);
        SellProductDto dto2 = sellProductDto(productToSell2.getProductId(), piecesNumber);
        String expectedMessage = String.format("Cannot sell %s pieces of product %s. Currently booked pieces number is %s.",
                                               piecesNumber, productToSell2.getName(), productToSell2.getBookedPiecesNumber());
        ErrorType expectedErrorType = ErrorType.CANNOT_SELL_PRODUCT;

        Either<AppError, List<ProductDto>> result = productFacade.sellProducts(List.of(dto, dto2));

        assertTrue(result.isLeft());
        assertEquals(expectedMessage, result.getLeft().getErrorMessage());
        assertEquals(expectedErrorType, result.getLeft().getErrorType());
        assertProductDidNotChange(productToSell);
        assertProductDidNotChange(productToSell2);
    }

    private ProductDto expectedProductDto(ProductDto currentProduct, SellProductDto dto) {
        return currentProduct.toBuilder()
                             .bookedPiecesNumber(currentProduct.getBookedPiecesNumber() - dto.getPiecesNumber())
                             .soldPiecesNumber(currentProduct.getSoldPiecesNumber() + dto.getPiecesNumber())
                             .build();
    }

    private SellProductDto sellProductDto(BigDecimal productId, int piecesNumber) {
        return new SellProductDto(productId, piecesNumber);
    }

}
