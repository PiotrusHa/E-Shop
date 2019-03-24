package piotrusha.e_shop.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.vavr.control.Either;
import io.vavr.control.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.product.domain.dto.BookProductDto;
import piotrusha.e_shop.product.domain.dto.CreateProductDto;
import piotrusha.e_shop.product.domain.dto.ProductDto;
import piotrusha.e_shop.product.domain.dto.SellProductDto;

import java.math.BigDecimal;
import java.util.List;

class ProductSellingTest {

    private static final CreateProductDto CREATE_PRODUCT_DTO = new CreateProductDto("Name", BigDecimal.TEN, 20, "", List.of());
    private static final CreateProductDto CREATE_PRODUCT_DTO_2 = new CreateProductDto("Name2", BigDecimal.TEN, 11, "", List.of());

    private ProductFacade productFacade;
    private ProductDto product;
    private ProductDto product2;

    @BeforeEach
    void init() {
        productFacade = new ProductConfiguration().productFacade();
        product = productFacade.createProduct(CREATE_PRODUCT_DTO).get();
        product2 = productFacade.createProduct(CREATE_PRODUCT_DTO_2).get();

        BookProductDto bookProductDto = new BookProductDto(product.getProductId(), 2);
        BookProductDto bookProduct2Dto = new BookProductDto(product2.getProductId(), 5);
        productFacade.bookProducts(List.of(bookProductDto, bookProduct2Dto));

        product = productFacade.findProductByProductId(product.getProductId())
                               .get();
        product2 = productFacade.findProductByProductId(product2.getProductId())
                                .get();
    }

    @Test
    void sell() {
        int piecesNumberToSell = 1;
        SellProductDto dto = new SellProductDto(product.getProductId(), piecesNumberToSell);

        productFacade.sellProducts(List.of(dto));

        Option<ProductDto> productOpt = productFacade.findProductByProductId(product.getProductId());
        assertTrue(productOpt.isDefined());
        ProductDto productDto = productOpt.get();
        assertEquals(product.getBookedPiecesNumber() - piecesNumberToSell, (int) productDto.getBookedPiecesNumber());
        assertEquals(product.getSoldPiecesNumber() + piecesNumberToSell, (int) productDto.getSoldPiecesNumber());
    }

    @Test
    void sellNotEnoughPiecesNumber() {
        Integer piecesNumber = product.getAvailablePiecesNumber() + 1;
        SellProductDto dto = new SellProductDto(product.getProductId(), piecesNumber);
        String expectedMessage =
                String.format("Cannot sell %s pieces of product %s. Currently booked pieces number is %s.", piecesNumber, product.getName(),
                              product.getBookedPiecesNumber());
        AppError.ErrorType expectedErrorType = AppError.ErrorType.VALIDATION;

        Either<AppError, List<ProductDto>> result = productFacade.sellProducts(List.of(dto));

        assertTrue(result.isLeft());
        assertEquals(expectedMessage, result.getLeft().getErrorMessage());
        assertEquals(expectedErrorType, result.getLeft().getErrorType());
        assertProductDidNotChange(product);
    }

    @Test
    void sellFirstOkSecondNotEnoughPiecesNumber() {
        Integer piecesNumber = product2.getAvailablePiecesNumber() + 1;
        SellProductDto dto = new SellProductDto(product.getProductId(), 1);
        SellProductDto dto2 = new SellProductDto(product2.getProductId(), piecesNumber);
        String expectedMessage =
                String.format("Cannot sell %s pieces of product %s. Currently booked pieces number is %s.", piecesNumber,
                              product2.getName(), product2.getBookedPiecesNumber());
        AppError.ErrorType expectedErrorType = AppError.ErrorType.VALIDATION;

        Either<AppError, List<ProductDto>> result = productFacade.sellProducts(List.of(dto, dto2));

        assertTrue(result.isLeft());
        assertEquals(expectedMessage, result.getLeft().getErrorMessage());
        assertEquals(expectedErrorType, result.getLeft().getErrorType());
        assertProductDidNotChange(product);
        assertProductDidNotChange(product2);
    }

    private void assertProductDidNotChange(ProductDto productDto) {
        Option<ProductDto> productOpt = productFacade.findProductByProductId(productDto.getProductId());
        assertTrue(productOpt.isDefined());
        Assertions.assertProductDto(productDto, productOpt.get());
    }

}
