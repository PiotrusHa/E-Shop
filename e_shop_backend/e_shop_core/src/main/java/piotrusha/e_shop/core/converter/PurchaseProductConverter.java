package piotrusha.e_shop.core.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import piotrusha.e_shop.core.exception.EntityNotFoundException;
import piotrusha.e_shop.core.model.Product;
import piotrusha.e_shop.core.model.PurchaseProduct;
import piotrusha.e_shop.core.model.dto.PurchaseProductDto;
import piotrusha.e_shop.core.service.ProductService;

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
