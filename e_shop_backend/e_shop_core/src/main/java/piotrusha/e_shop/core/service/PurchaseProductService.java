package piotrusha.e_shop.core.service;

import piotrusha.e_shop.core.model.PurchaseProduct;
import piotrusha.e_shop.core.model.dto.PurchaseProductDto;

import java.util.List;

public interface PurchaseProductService {

    List<PurchaseProduct> preparePurchaseProducts(List<PurchaseProductDto> purchaseProductDtos);

    void cancelPurchaseProducts(List<PurchaseProduct> purchaseProducts);

}
