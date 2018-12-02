package piotrusha.e_shop.web.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import piotrusha.e_shop.base.BaseControllerTest;
import piotrusha.e_shop.core.model.Category;
import piotrusha.e_shop.core.model.builder.CategoryBuilder;
import piotrusha.e_shop.stub.model.Categories;

import java.util.List;

class CategoryControllerTest extends BaseControllerTest {

    @Autowired
    private CategoryController categoryController;

    @BeforeAll
    private void init() {
        super.init(categoryController);
    }

    @Test
    void getAllCategories() throws Exception {
        List<Category> expectedCategories = Categories.TEST_CATEGORIES;

        MvcResult mvcResult = mockMvc.perform(get("/categories"))
                                     .andExpect(status().isOk())
                                     .andReturn();
        List<Category> categories = readValueAsList(mvcResult, Category.class);

        assertCategories(expectedCategories, categories);
    }

    @Test
    void addCategory() throws Exception {
        Category categoryToSave = new CategoryBuilder(Categories.TestCategoryToys.NAME).build();
        String categoryToSaveAsJson = writeValueAsJson(categoryToSave);

        MvcResult mvcResult = mockMvc.perform(post("/categories/add")
                                                      .contentType(MediaType.APPLICATION_JSON_UTF8)
                                                      .content(categoryToSaveAsJson))
                                     .andExpect(status().isOk())
                                     .andReturn();

        Category categorySaved = readValueAsObject(mvcResult, Category.class);

        assertCategoryWithoutId(categoryToSave, categorySaved);
        assertNotNull(categorySaved.getId());
    }

}