package piotrusha.e_shop.core.validator;

import org.springframework.stereotype.Component;
import piotrusha.e_shop.core.exception.InsufficientProductPiecesNumberException;
import piotrusha.e_shop.core.model.PurchaseProduct;

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
