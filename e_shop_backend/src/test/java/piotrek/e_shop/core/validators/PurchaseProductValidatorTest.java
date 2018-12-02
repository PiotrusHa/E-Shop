package piotrek.e_shop.core.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import piotrek.e_shop.api.exceptions.InsufficientProductPiecesNumberException;
import piotrek.e_shop.base.BaseTest;
import piotrek.e_shop.model.PurchaseProduct;
import piotrek.e_shop.stub.model.PurchaseProducts.PurchaseProduct3Breads;
import piotrek.e_shop.stub.model.PurchaseProducts.PurchaseProductWithAvailablePiecesNumber;
import piotrek.e_shop.stub.model.PurchaseProducts.PurchaseProductWithNotAvailablePiecesNumber;

import java.util.List;

class PurchaseProductValidatorTest extends BaseTest {

    private PurchaseProductValidator validator = new PurchaseProductValidator();

    @Test
    void validateProductsPiecesNumber() {
        List<PurchaseProduct> purchaseProducts = List.of(PurchaseProductWithAvailablePiecesNumber.PURCHASE_PRODUCT,
                                                         PurchaseProduct3Breads.PURCHASE_PRODUCT);

        validator.validateProductsPiecesNumber(purchaseProducts);
    }

    @Test
    void validateProductsPiecesNumberThrowInsufficientProductPiecesNumberException() {
        PurchaseProduct purchaseProductWithNotAvailablePiecesNumber = PurchaseProductWithNotAvailablePiecesNumber.PURCHASE_PRODUCT;
        List<PurchaseProduct> purchaseProducts = List.of(PurchaseProductWithAvailablePiecesNumber.PURCHASE_PRODUCT,
                                                         purchaseProductWithNotAvailablePiecesNumber);

        InsufficientProductPiecesNumberException exception = assertThrows(InsufficientProductPiecesNumberException.class,
                                                                          () -> validator.validateProductsPiecesNumber(purchaseProducts));

        assertEquals(purchaseProductWithNotAvailablePiecesNumber.getPiecesNumber(), exception.getNeededPiecesNumber());
        assertProduct(purchaseProductWithNotAvailablePiecesNumber.getProduct(), exception.getProduct());
    }

}