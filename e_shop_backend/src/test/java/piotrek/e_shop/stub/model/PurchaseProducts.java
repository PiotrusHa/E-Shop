package piotrek.e_shop.stub.model;

import piotrek.e_shop.model.Product;
import piotrek.e_shop.model.PurchaseProduct;
import piotrek.e_shop.model.builder.PurchaseProductBuilder;
import piotrek.e_shop.stub.model.Products.TestProductBeer;
import piotrek.e_shop.stub.model.Products.TestProductBread;
import piotrek.e_shop.stub.model.Products.TestProductDesertEagle;
import piotrek.e_shop.stub.model.Products.TestProductWith2Categories;
import piotrek.e_shop.stub.model.Products.TestProductWith3Categories;

import java.math.BigDecimal;
import java.util.List;

public class PurchaseProducts {

    public static class PurchaseProductWithAvailablePiecesNumber {
        public static final BigDecimal ID = BigDecimal.valueOf(1);
        public static final Product PRODUCT = TestProductWith3Categories.PRODUCT;
        public static final PurchaseProduct PURCHASE_PRODUCT = new PurchaseProductBuilder().id(ID)
                                                                                     .product(PRODUCT)
                                                                                     .piecesNumber(PRODUCT.getAvailablePiecesNumber() - 1)
                                                                                     .piecePrice(PRODUCT.getPrice())
                                                                                     .build();
    }

    // stub object to test fail bill creation
    public static class PurchaseProductWithNotAvailablePiecesNumber {
        // ID not specified because object isn't stored in db
        public static final Product PRODUCT = TestProductWith2Categories.PRODUCT;
        public static final PurchaseProduct PURCHASE_PRODUCT = new PurchaseProductBuilder().product(PRODUCT)
                                                                                     .piecesNumber(PRODUCT.getAvailablePiecesNumber() + 1)
                                                                                     .piecePrice(PRODUCT.getPrice())
                                                                                     .build();
    }

    public static class PurchaseProduct3Breads {
        public static final BigDecimal ID = BigDecimal.valueOf(2);
        public static final Product PRODUCT = TestProductBread.PRODUCT;
        public static final PurchaseProduct PURCHASE_PRODUCT = new PurchaseProductBuilder().id(ID)
                                                                                     .product(PRODUCT)
                                                                                     .piecesNumber(3)
                                                                                     .piecePrice(PRODUCT.getPrice())
                                                                                     .build();
    }

    public static class PurchaseProduct1Bread {
        public static final BigDecimal ID = BigDecimal.valueOf(3);
        public static final Product PRODUCT = TestProductBread.PRODUCT;
        public static final PurchaseProduct PURCHASE_PRODUCT = new PurchaseProductBuilder().id(ID)
                                                                                     .product(PRODUCT)
                                                                                     .piecesNumber(1)
                                                                                     .piecePrice(PRODUCT.getPrice())
                                                                                     .build();
    }

    public static class PurchaseProduct4Beers {
        public static final BigDecimal ID = BigDecimal.valueOf(4);
        public static final Product PRODUCT = TestProductBeer.PRODUCT;
        public static final PurchaseProduct PURCHASE_PRODUCT = new PurchaseProductBuilder().id(ID)
                                                                                     .product(PRODUCT)
                                                                                     .piecesNumber(4)
                                                                                     .piecePrice(PRODUCT.getPrice())
                                                                                     .build();
    }

    public static class PurchaseProduct1Beer {
        public static final BigDecimal ID = BigDecimal.valueOf(5);
        public static final Product PRODUCT = TestProductBeer.PRODUCT;
        public static final PurchaseProduct PURCHASE_PRODUCT = new PurchaseProductBuilder().id(ID)
                                                                                     .product(PRODUCT)
                                                                                     .piecesNumber(1)
                                                                                     .piecePrice(PRODUCT.getPrice())
                                                                                     .build();
    }

    public static class PurchaseProduct1DesertEagle {
        public static final BigDecimal ID = BigDecimal.valueOf(6);
        public static final Product PRODUCT = TestProductDesertEagle.PRODUCT;
        public static final PurchaseProduct PURCHASE_PRODUCT = new PurchaseProductBuilder().id(ID)
                                                                                     .product(PRODUCT)
                                                                                     .piecesNumber(1)
                                                                                     .piecePrice(PRODUCT.getPrice())
                                                                                     .build();
    }

    public static class PurchaseProduct9Breads {
        public static final BigDecimal ID = BigDecimal.valueOf(7);
        public static final Product PRODUCT = TestProductBread.PRODUCT;
        public static final PurchaseProduct PURCHASE_PRODUCT = new PurchaseProductBuilder().id(ID)
                                                                                           .product(PRODUCT)
                                                                                           .piecesNumber(9)
                                                                                           .piecePrice(PRODUCT.getPrice())
                                                                                           .build();
    }

    public static class PurchaseProduct10Beers {
        public static final BigDecimal ID = BigDecimal.valueOf(8);
        public static final Product PRODUCT = TestProductBeer.PRODUCT;
        public static final PurchaseProduct PURCHASE_PRODUCT = new PurchaseProductBuilder().id(ID)
                                                                                           .product(PRODUCT)
                                                                                           .piecesNumber(10)
                                                                                           .piecePrice(PRODUCT.getPrice())
                                                                                           .build();
    }

    public static final List<PurchaseProduct> TEST_PURCHASE_PRODUCTS = List.of(PurchaseProductWithAvailablePiecesNumber.PURCHASE_PRODUCT,
                                                                               PurchaseProduct3Breads.PURCHASE_PRODUCT,
                                                                               PurchaseProduct1Bread.PURCHASE_PRODUCT,
                                                                               PurchaseProduct4Beers.PURCHASE_PRODUCT,
                                                                               PurchaseProduct1Beer.PURCHASE_PRODUCT,
                                                                               PurchaseProduct1DesertEagle.PURCHASE_PRODUCT,
                                                                               PurchaseProduct9Breads.PURCHASE_PRODUCT,
                                                                               PurchaseProduct10Beers.PURCHASE_PRODUCT);

}
