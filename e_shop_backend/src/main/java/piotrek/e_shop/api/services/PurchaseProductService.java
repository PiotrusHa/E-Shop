package piotrek.e_shop.api.services;

import piotrek.e_shop.model.PurchaseProduct;
import piotrek.e_shop.model.dto.PurchaseProductDto;

import java.util.List;

public interface PurchaseProductService {

    List<PurchaseProduct> preparePurchaseProducts(List<PurchaseProductDto> purchaseProductDtos);

    void cancelPurchaseProducts(List<PurchaseProduct> purchaseProducts);

}
