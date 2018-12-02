package piotrusha.e_shop.core.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import piotrusha.e_shop.core.exception.InsufficientProductPiecesNumberException;
import piotrusha.e_shop.base.BaseTest;
import piotrusha.e_shop.core.model.PurchaseProduct;
import piotrusha.e_shop.stub.model.PurchaseProducts.PurchaseProduct3Breads;
import piotrusha.e_shop.stub.model.PurchaseProducts.PurchaseProductWithAvailablePiecesNumber;
import piotrusha.e_shop.stub.model.PurchaseProducts.PurchaseProductWithNotAvailablePiecesNumber;

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