package piotrusha.e_shop.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import piotrusha.e_shop.core.converter.PurchaseProductConverter;
import piotrusha.e_shop.core.validator.PurchaseProductValidator;
import piotrusha.e_shop.core.model.Product;
import piotrusha.e_shop.core.model.PurchaseProduct;
import piotrusha.e_shop.core.model.dto.PurchaseProductDto;

import java.util.List;

@Service
public class PurchaseProductService {

    private PurchaseProductConverter purchaseProductConverter;
    private PurchaseProductValidator purchaseProductValidator;

    @Autowired
    public PurchaseProductService(PurchaseProductConverter purchaseProductConverter,
                                  PurchaseProductValidator purchaseProductValidator) {
        this.purchaseProductConverter = purchaseProductConverter;
        this.purchaseProductValidator = purchaseProductValidator;
    }

    public List<PurchaseProduct> preparePurchaseProducts(List<PurchaseProductDto> purchaseProductDtos) {
        List<PurchaseProduct> purchaseProducts = purchaseProductConverter.convertToDatabaseObjects(purchaseProductDtos);
        purchaseProductValidator.validateProductsPiecesNumber(purchaseProducts);
        reduceProductPiecesNumber(purchaseProducts);
        return purchaseProducts;
    }

    private void reduceProductPiecesNumber(List<PurchaseProduct> purchaseProducts) {
        purchaseProducts.forEach(purchaseProduct -> {
                Product product = purchaseProduct.getProduct();
                product.setAvailablePiecesNumber(product.getAvailablePiecesNumber() - purchaseProduct.getPiecesNumber());
                product.setSoldPiecesNumber(product.getSoldPiecesNumber() + purchaseProduct.getPiecesNumber());
        });
    }

    public void cancelPurchaseProducts(List<PurchaseProduct> purchaseProducts) {
        increaseProductPiecesNumber(purchaseProducts);
    }

    private void increaseProductPiecesNumber(List<PurchaseProduct> purchaseProducts) {
        purchaseProducts.forEach(purchaseProduct -> {
            Product product = purchaseProduct.getProduct();
            product.setAvailablePiecesNumber(product.getAvailablePiecesNumber() + purchaseProduct.getPiecesNumber());
            product.setSoldPiecesNumber(product.getSoldPiecesNumber() - purchaseProduct.getPiecesNumber());
        });
    }

}
