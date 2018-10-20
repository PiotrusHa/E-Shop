package piotrek.e_shop.web;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import piotrek.e_shop.base.BaseControllerTest;
import piotrek.e_shop.model.Category;
import piotrek.e_shop.model.builder.CategoryBuilder;
import piotrek.e_shop.stub.model.Categories;

import java.util.List;

class CategoryControllerTest extends BaseControllerTest {

    @Autowired
    private CategoryController categoryController;

    @BeforeEach
    private void init() {
        super.init(categoryController);
    }

    @Test
    void getAllCategories() throws Exception {
        List<Category> expectedCategories = Categories.TEST_CATEGORIES;

        MvcResult mvcResult = mockMvc.perform(get("/e_shop/categories"))
                                     .andExpect(status().isOk())
                                     .andReturn();
        List<Category> categories = readValueAsList(mvcResult, Category.class);

        assertCategories(expectedCategories, categories);
    }

    @Test
    void addCategory() throws Exception {
        Category categoryToSave = new CategoryBuilder(Categories.TestCategoryToys.NAME).build();
        String categoryToSaveAsJson = writeValueAsJson(categoryToSave);

        MvcResult mvcResult = mockMvc.perform(post("/e_shop/categories/add")
                                                      .contentType(MediaType.APPLICATION_JSON_UTF8)
                                                      .content(categoryToSaveAsJson))
                                     .andExpect(status().isOk())
                                     .andReturn();

        Category categorySaved = readValueAsObject(mvcResult, Category.class);

        assertCategoryWithoutId(categoryToSave, categorySaved);
        assertNotNull(categorySaved.getId());
    }

}