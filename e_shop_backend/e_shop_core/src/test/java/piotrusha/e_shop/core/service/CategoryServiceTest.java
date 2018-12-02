package piotrusha.e_shop.core.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import piotrusha.e_shop.core.exception.EntityNotFoundException;
import piotrusha.e_shop.base.BaseTestWithDatabase;
import piotrusha.e_shop.core.model.Category;
import piotrusha.e_shop.core.model.builder.CategoryBuilder;
import piotrusha.e_shop.stub.model.Categories;
import piotrusha.e_shop.stub.model.Categories.TestCategoryFood;
import piotrusha.e_shop.stub.model.Categories.TestCategoryToys;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Category Service Test")
class CategoryServiceTest extends BaseTestWithDatabase {

    @Autowired
    protected CategoryService categoryService;

    @Test
    void findById() {
        Category expectedCategory = TestCategoryFood.CATEGORY;
        BigDecimal id = expectedCategory.getId();

        Optional<Category> result = categoryService.findById(id);

        assertTrue(result.isPresent());
        assertCategory(expectedCategory, result.get());
    }

    @Test
    void findByIdReturnOptionalEmpty() {
        BigDecimal id = BigDecimal.valueOf(1410);

        Optional<Category> result = categoryService.findById(id);

        assertFalse(result.isPresent());
    }

    @Test
    void findByName() {
        Category expectedCategory = TestCategoryFood.CATEGORY;
        String name = expectedCategory.getName();

        Optional<Category> result = categoryService.findByName(name);

        assertTrue(result.isPresent());
        assertCategory(expectedCategory, result.get());
    }

    @Test
    void findByNameReturnOptionalEmpty() {
        String name = "Example name";

        Optional<Category> result = categoryService.findByName(name);

        assertFalse(result.isPresent());
    }

    @Test
    void findAll() {
        List<Category> expectedResult = Categories.TEST_CATEGORIES;

        List<Category> result = categoryService.findAll();

        assertCategories(expectedResult, result);
    }

    @Test
    void save() {
        Category categoryToSave = new CategoryBuilder(TestCategoryToys.NAME).build();

        Category result = categoryService.save(categoryToSave);

        assertCategoryWithoutId(categoryToSave, result);
        assertNotNull(result.getId());
    }

    @Test
    void saveAll() {
        List<Category> categoriesToSave = List.of(new CategoryBuilder(TestCategoryToys.NAME).build(),
                                                  new CategoryBuilder(TestCategoryFood.NAME).build());

        List<Category> result = categoryService.saveAll(categoriesToSave);

        assertEquals(categoriesToSave.size(), result.size());
        for (int i = 0; i < result.size(); i++) {
            assertCategoryWithoutId(categoriesToSave.get(i), result.get(i));
            assertNotNull(result.get(i).getId());
        }
    }

    @Test
    void validateCategories() {
        Category category1 = new Category();
        category1.setId(TestCategoryFood.ID);
        Category category2 = new Category();
        category2.setId(TestCategoryToys.ID);
        List<Category> categoriesToValidate = List.of(category1, category2);
        List<Category> expectedValidatedCategories = List.of(TestCategoryFood.CATEGORY, TestCategoryToys.CATEGORY);

        List<Category> result = categoryService.validateCategories(categoriesToValidate);

        assertCategories(expectedValidatedCategories, result);
    }

    @Test
    void validateCategoriesThrowEntityNotFoundException() {
        BigDecimal notFoundId = BigDecimal.valueOf(1410);
        Category category1 = new Category();
        category1.setId(TestCategoryFood.ID);
        Category category2 = new Category();
        category2.setId(notFoundId);
        List<Category> categoriesToValidate = List.of(category1, category2);

        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> categoryService.validateCategories(categoriesToValidate));
        assertEquals(Category.class, exception.getResourceClass());
        assertEquals(notFoundId, exception.getResourceId());
    }

}
