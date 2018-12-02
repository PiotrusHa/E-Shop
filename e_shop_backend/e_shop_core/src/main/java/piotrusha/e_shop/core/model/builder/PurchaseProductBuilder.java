package piotrusha.e_shop.core.model.builder;

import piotrusha.e_shop.core.model.Product;
import piotrusha.e_shop.core.model.PurchaseProduct;

import java.math.BigDecimal;

public class PurchaseProductBuilder {

    private PurchaseProduct purchaseProduct;

    public PurchaseProductBuilder() {
        purchaseProduct = new PurchaseProduct();
    }

    public PurchaseProductBuilder(Product product, int piecesNumber) {
        this();
        purchaseProduct.setProduct(product);
        purchaseProduct.setPiecesNumber(piecesNumber);
        purchaseProduct.setPiecePrice(product.getPrice());
    }

    public PurchaseProductBuilder id(BigDecimal id) {
        purchaseProduct.setId(id);
        return this;
    }

    public PurchaseProductBuilder product(Product product) {
        purchaseProduct.setProduct(product);
        return this;
    }

    public PurchaseProductBuilder piecesNumber(int piecesNumber) {
        purchaseProduct.setPiecesNumber(piecesNumber);
        return this;
    }

    public PurchaseProductBuilder piecePrice(BigDecimal piecePrice) {
        purchaseProduct.setPiecePrice(piecePrice);
        return this;
    }

    public PurchaseProduct build() {
        return purchaseProduct;
    }

}
