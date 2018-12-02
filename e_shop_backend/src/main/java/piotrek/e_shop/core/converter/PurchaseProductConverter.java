package piotrek.e_shop.core.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import piotrek.e_shop.api.exceptions.EntityNotFoundException;
import piotrek.e_shop.api.services.ProductService;
import piotrek.e_shop.model.Product;
import piotrek.e_shop.model.PurchaseProduct;
import piotrek.e_shop.model.dto.PurchaseProductDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class PurchaseProductConverter {

    private ProductService productService;

    @Autowired
    public PurchaseProductConverter(ProductService productService) {
        this.productService = productService;
    }

    public List<PurchaseProduct> convertToDatabaseObjects(List<PurchaseProductDto> dtos) {
        List<PurchaseProduct> purchaseProducts = new ArrayList<>();
        for (PurchaseProductDto dto : dtos) {
            PurchaseProduct purchaseProduct = convertToDatabaseObject(dto);
            purchaseProducts.add(purchaseProduct);
        }
        return purchaseProducts;
    }

    private PurchaseProduct convertToDatabaseObject(PurchaseProductDto dto) {
        Product product = productService.findById(dto.getProductId())
                                        .orElseThrow(() -> new EntityNotFoundException(Product.class, dto.getProductId()));
        return new PurchaseProduct(product.getPrice(), dto.getPiecesNumber(), product);
    }

}
