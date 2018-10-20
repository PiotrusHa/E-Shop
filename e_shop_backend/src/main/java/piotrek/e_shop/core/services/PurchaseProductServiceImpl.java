package piotrek.e_shop.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import piotrek.e_shop.api.services.ProductService;
import piotrek.e_shop.api.services.PurchaseProductService;
import piotrek.e_shop.core.converter.PurchaseProductConverter;
import piotrek.e_shop.core.validators.PurchaseProductValidator;
import piotrek.e_shop.model.PurchaseProduct;
import piotrek.e_shop.model.dto.PurchaseProductDto;

import java.util.List;

@Service
public class PurchaseProductServiceImpl implements PurchaseProductService {

    private ProductService productService;
    private PurchaseProductConverter purchaseProductConverter;

    @Autowired
    public PurchaseProductServiceImpl(ProductService productService, PurchaseProductConverter purchaseProductConverter) {
        this.productService = productService;
        this.purchaseProductConverter = purchaseProductConverter;
    }

    @Override
    public List<PurchaseProduct> preparePurchaseProducts(List<PurchaseProductDto> purchaseProductDtos) {
        List<PurchaseProduct> purchaseProducts = purchaseProductConverter.convertToDatabaseObjects(purchaseProductDtos);
        PurchaseProductValidator.validateProductsPiecesNumber(purchaseProducts);
        productService.updateProductsPiecesNumber(purchaseProducts);
        return purchaseProducts;
    }

}
