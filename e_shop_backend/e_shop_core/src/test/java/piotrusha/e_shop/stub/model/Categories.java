package piotrusha.e_shop.stub.model;

import piotrusha.e_shop.core.model.Category;

import java.math.BigDecimal;
import java.util.List;

public class Categories {

    public static class TestCategoryFood {
        public static BigDecimal ID = BigDecimal.valueOf(1);
        public static String NAME = "food";
        public static Category CATEGORY;

        static {
            CATEGORY = new Category();
            CATEGORY.setId(ID);
            CATEGORY.setName(NAME);
        }
    }

    public static class TestCategoryToys {
        public static BigDecimal ID = BigDecimal.valueOf(2);
        public static String NAME = "toys";
        public static Category CATEGORY;

        static {
            CATEGORY = new Category();
            CATEGORY.setId(ID);
            CATEGORY.setName(NAME);
        }
    }

    public static class TestCategoryCosmetics {
        public static BigDecimal ID = BigDecimal.valueOf(3);
        public static String NAME = "cosmetics";
        public static Category CATEGORY;

        static {
            CATEGORY = new Category();
            CATEGORY.setId(ID);
            CATEGORY.setName(NAME);
        }
    }

    public static List<Category> TEST_CATEGORIES = List.of(TestCategoryFood.CATEGORY,
                                                           TestCategoryToys.CATEGORY,
                                                           TestCategoryCosmetics.CATEGORY);

}
