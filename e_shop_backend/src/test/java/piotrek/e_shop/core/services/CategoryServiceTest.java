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

        when(categoryRepository.findById(id))
                .thenReturn(Optional.of(expectedCategory));

        Optional<Category> result = categoryService.findById(id);

        assertTrue(result.isPresent());
        assertCategory(expectedCategory, result.get());
    }

    @Test
    void findByIdReturnOptionalEmpty() {
        BigDecimal id = BigDecimal.valueOf(1410);

        when(categoryRepository.findById(id))
                .thenReturn(Optional.empty());

        Optional<Category> result = categoryService.findById(id);

        assertFalse(result.isPresent());
    }

    @Test
    void findByName() {
        Category expectedCategory = TestCategoryFood.CATEGORY;
        String name = expectedCategory.getName();

        when(categoryRepository.findByName(name))
                .thenReturn(Optional.of(expectedCategory));

        Optional<Category> result = categoryService.findByName(name);

        assertTrue(result.isPresent());
        assertCategory(expectedCategory, result.get());
    }

    @Test
    void findByNameReturnOptionalEmpty() {
        String name = "Example name";

        when(categoryRepository.findByName(name))
                .thenReturn(Optional.empty());

        Optional<Category> result = categoryService.findByName(name);

        assertFalse(result.isPresent());
    }

    @Test
    void save() {
        Category categoryToSave = TestCategoryToys.CATEGORY;

        when(categoryRepository.save(categoryToSave))
                .thenReturn(categoryToSave);

        Category result = categoryService.save(categoryToSave);

        assertCategory(categoryToSave, result);
    }

    @Test
    void saveAll() {
        List<Category> categoriesToSave = List.of(TestCategoryToys.CATEGORY, TestCategoryFood.CATEGORY);

        when(categoryRepository.saveAll(categoriesToSave))
                .thenReturn(categoriesToSave);

        List<Category> result = categoryService.saveAll(categoriesToSave);

        assertCategories(categoriesToSave, result);
    }

}
