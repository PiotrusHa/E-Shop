package piotrek.e_shop.stub.model;

import piotrek.e_shop.model.Product;
import piotrek.e_shop.model.PurchaseProduct;
import piotrek.e_shop.model.builder.PurchaseProductBuilder;
import piotrek.e_shop.stub.model.Products.TestProductBread;
import piotrek.e_shop.stub.model.Products.TestProductWith2Categories;
import piotrek.e_shop.stub.model.Products.TestProductWith3Categories;

import java.math.BigDecimal;

public class PurchaseProducts {

    public static class PurchaseProductWithAvailablePiecesNumber {
        public static BigDecimal ID = BigDecimal.valueOf(1);
        public static Product PRODUCT = TestProductWith3Categories.PRODUCT;
        public static PurchaseProduct PURCHASE_PRODUCT = new PurchaseProductBuilder().id(ID)
                                                                                     .product(PRODUCT)
                                                                                     .piecesNumber(PRODUCT.getAvailablePiecesNumber() - 1)
                                                                                     .piecePrice(PRODUCT.getPrice())
                                                                                     .build();
    }

    public static class PurchaseProductWithNotAvailablePiecesNumber {
        public static BigDecimal ID = BigDecimal.valueOf(2);
        public static Product PRODUCT = TestProductWith2Categories.PRODUCT;
        public static PurchaseProduct PURCHASE_PRODUCT = new PurchaseProductBuilder().id(ID)
                                                                                     .product(PRODUCT)
                                                                                     .piecesNumber(PRODUCT.getAvailablePiecesNumber() + 1)
                                                                                     .piecePrice(PRODUCT.getPrice())
                                                                                     .build();
    }

    public static class PurchaseProduct3Breads {
        public static BigDecimal ID = BigDecimal.valueOf(3);
        public static Product PRODUCT = TestProductBread.PRODUCT;
        public static PurchaseProduct PURCHASE_PRODUCT = new PurchaseProductBuilder().id(ID)
                                                                                     .product(PRODUCT)
                                                                                     .piecesNumber(3)
                                                                                     .piecePrice(PRODUCT.getPrice())
                                                                                     .build();
    }

}
