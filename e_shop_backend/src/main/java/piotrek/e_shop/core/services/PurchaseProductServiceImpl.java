package piotrek.e_shop.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import piotrek.e_shop.api.services.ProductService;
import piotrek.e_shop.api.services.PurchaseProductService;
import piotrek.e_shop.core.converter.PurchaseProductConverter;
import piotrek.e_shop.core.validators.PurchaseProductValidator;
import piotrek.e_shop.model.Product;
import piotrek.e_shop.model.PurchaseProduct;
import piotrek.e_shop.model.dto.PurchaseProductDto;

import java.util.List;

@Service
public class PurchaseProductServiceImpl implements PurchaseProductService {

    private PurchaseProductConverter purchaseProductConverter;
    private PurchaseProductValidator purchaseProductValidator;

    @Autowired
    public PurchaseProductServiceImpl(PurchaseProductConverter purchaseProductConverter,
                                      PurchaseProductValidator purchaseProductValidator) {
        this.purchaseProductConverter = purchaseProductConverter;
        this.purchaseProductValidator = purchaseProductValidator;
    }

    @Override
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

    @Override
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
