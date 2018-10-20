package piotrek.e_shop.core.validators;

import piotrek.e_shop.api.exceptions.InsufficientProductPiecesNumberException;
import piotrek.e_shop.model.PurchaseProduct;

import java.util.List;

public class PurchaseProductValidator {

    public static void validateProductsPiecesNumber(List<PurchaseProduct> purchaseProducts) {
        purchaseProducts.forEach(PurchaseProductValidator::validateProductPiecesNumber);
    }

    private static void validateProductPiecesNumber(PurchaseProduct purchaseProduct) {
        if (purchaseProduct.getProduct().getAvailablePiecesNumber() < purchaseProduct.getPiecesNumber()) {
            throw new InsufficientProductPiecesNumberException(purchaseProduct.getProduct(), purchaseProduct.getPiecesNumber());
        }
    }

}
