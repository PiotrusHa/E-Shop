package piotrek.e_shop.core.validators;

import org.springframework.stereotype.Component;
import piotrek.e_shop.api.exceptions.InsufficientProductPiecesNumberException;
import piotrek.e_shop.model.PurchaseProduct;

import java.util.List;

@Component
public class PurchaseProductValidator {

    public void validateProductsPiecesNumber(List<PurchaseProduct> purchaseProducts) {
        purchaseProducts.forEach(this::validateProductPiecesNumber);
    }

    private void validateProductPiecesNumber(PurchaseProduct purchaseProduct) {
        if (purchaseProduct.getProduct().getAvailablePiecesNumber() < purchaseProduct.getPiecesNumber()) {
            throw new InsufficientProductPiecesNumberException(purchaseProduct.getProduct(), purchaseProduct.getPiecesNumber());
        }
    }

}
