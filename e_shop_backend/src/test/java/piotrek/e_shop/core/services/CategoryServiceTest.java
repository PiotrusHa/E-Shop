package piotrek.e_shop.core.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piotrek.e_shop.base.BaseServiceTest;
import piotrek.e_shop.model.Category;
import piotrek.e_shop.stub.model.Categories.TestCategoryFood;
import piotrek.e_shop.stub.model.Categories.TestCategoryToys;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@DisplayName("Category Service Test")
class CategoryServiceTest extends BaseServiceTest {

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
    void save() {
        Category categoryToSave = new Category();
        categoryToSave.setName(TestCategoryToys.NAME);
        Category categorySaved = TestCategoryToys.CATEGORY;

        when(categoryRepositoryMock.save(categoryToSave))
                .thenReturn(categorySaved);

        Category result = categoryServiceDbSaveMock.save(categoryToSave);

        assertCategory(categorySaved, result);
    }

    @Test
    void saveAll() {
        Category categoryToSave1 = new Category();
        categoryToSave1.setName(TestCategoryToys.NAME);
        Category categoryToSave2 = new Category();
        categoryToSave2.setName(TestCategoryFood.NAME);
        List<Category> categoriesToSave = List.of(categoryToSave1, categoryToSave2);
        List<Category> categoriesSaved = List.of(TestCategoryToys.CATEGORY, TestCategoryFood.CATEGORY);

        when(categoryRepositoryMock.saveAll(categoriesToSave))
                .thenReturn(categoriesSaved);

        List<Category> result = categoryServiceDbSaveMock.saveAll(categoriesToSave);

        assertCategories(categoriesSaved, result);
    }

}
