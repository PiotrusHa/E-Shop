package piotrusha.e_shop.stub.model;

import piotrusha.e_shop.core.model.Product;
import piotrusha.e_shop.stub.model.Categories.TestCategoryCosmetics;
import piotrusha.e_shop.stub.model.Categories.TestCategoryFood;

import java.math.BigDecimal;
import java.util.List;

public class Products {

    public static class TestProductBread {
        public static BigDecimal ID = BigDecimal.valueOf(1);
        public static String NAME = "bread";
        public static Product PRODUCT;

        static {
            PRODUCT = new Product();
            PRODUCT.setId(ID);
            PRODUCT.setName(NAME);
            PRODUCT.setAvailablePiecesNumber(111);
            PRODUCT.setSoldPiecesNumber(190);
            PRODUCT.setPrice(BigDecimal.valueOf(11.98));
            PRODUCT.setCategories(List.of(TestCategoryFood.CATEGORY));
        }
    }

    public static class TestProductBeer {
        public static BigDecimal ID = BigDecimal.valueOf(2);
        public static String NAME = "beer";
        public static Product PRODUCT;

        static {
            PRODUCT = new Product();
            PRODUCT.setId(ID);
            PRODUCT.setName(NAME);
            PRODUCT.setAvailablePiecesNumber(110);
            PRODUCT.setSoldPiecesNumber(975);
            PRODUCT.setPrice(BigDecimal.valueOf(3.99));
            PRODUCT.setCategories(List.of());
        }
    }

    public static class TestProductDesertEagle {
        public static BigDecimal ID = BigDecimal.valueOf(3);
        public static String NAME = "Desert Eagle";
        public static Product PRODUCT;

        static {
            PRODUCT = new Product();
            PRODUCT.setId(ID);
            PRODUCT.setName(NAME);
            PRODUCT.setAvailablePiecesNumber(13);
            PRODUCT.setSoldPiecesNumber(100087);
            PRODUCT.setPrice(BigDecimal.valueOf(599.99));
            PRODUCT.setCategories(List.of());
        }
    }

    public static class TestProductWith2Categories {
        public static BigDecimal ID = BigDecimal.valueOf(4);
        public static String NAME = "2 categories product";
        public static Product PRODUCT;

        static {
            PRODUCT = new Product();
            PRODUCT.setId(ID);
            PRODUCT.setName(NAME);
            PRODUCT.setAvailablePiecesNumber(54);
            PRODUCT.setSoldPiecesNumber(12);
            PRODUCT.setPrice(BigDecimal.valueOf(123.09));
            PRODUCT.setCategories(List.of(TestCategoryFood.CATEGORY,
                                          TestCategoryCosmetics.CATEGORY));
        }
    }

    public static class TestProductWith3Categories {
        public static BigDecimal ID = BigDecimal.valueOf(5);
        public static String NAME = "3 categories product";
        public static Product PRODUCT;

        static {
            PRODUCT = new Product();
            PRODUCT.setId(ID);
            PRODUCT.setName(NAME);
            PRODUCT.setAvailablePiecesNumber(544);
            PRODUCT.setSoldPiecesNumber(122);
            PRODUCT.setPrice(BigDecimal.valueOf(1234.09));
            PRODUCT.setCategories(List.of(TestCategoryFood.CATEGORY,
                    TestCategoryCosmetics.CATEGORY,
                    Categories.TestCategoryToys.CATEGORY));
        }
    }

    public static List<Product> TEST_PRODUCTS = List.of(TestProductBread.PRODUCT,
                                                        TestProductBeer.PRODUCT,
                                                        TestProductDesertEagle.PRODUCT,
                                                        TestProductWith2Categories.PRODUCT,
                                                        TestProductWith3Categories.PRODUCT);

}
