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
import piotrek.e_shop.model.Product;
import piotrek.e_shop.model.builder.ProductBuilder;
import piotrek.e_shop.stub.model.Categories.TestCategoryCosmetics;
import piotrek.e_shop.stub.model.Products;
import piotrek.e_shop.stub.model.Products.TestProductWith2Categories;
import piotrek.e_shop.stub.model.Products.TestProductWith3Categories;

import java.util.List;

class ProductControllerTest extends BaseControllerTest {

    @Autowired
    private ProductController productController;

    @BeforeEach
    private void init() {
        super.init(productController);
    }

    @Test
    void getAllProducts() throws Exception {
        List<Product> expectedProducts = Products.TEST_PRODUCTS;

        MvcResult mvcResult = mockMvc.perform(get("/products"))
                                     .andExpect(status().isOk())
                                     .andReturn();
        List<Product> products = readValueAsList(mvcResult, Product.class);

        assertProducts(expectedProducts, products);
    }

    @Test
    void getProductsByCategory() throws Exception {
        String categoryName = TestCategoryCosmetics.NAME;
        List<Product> expectedProducts = List.of(TestProductWith2Categories.PRODUCT,
                                                 TestProductWith3Categories.PRODUCT);

        MvcResult mvcResult = mockMvc.perform(get("/products/{categoryName}", categoryName))
                                     .andExpect(status().isOk())
                                     .andReturn();
        List<Product> products = readValueAsList(mvcResult, Product.class);

        assertProducts(expectedProducts, products);
    }

    @Test
    void addProduct() throws Exception {
        Product productToSave = new ProductBuilder(Products.TestProductBread.PRODUCT).build();
        String productToSaveAsJson = writeValueAsJson(productToSave);

        MvcResult mvcResult = mockMvc.perform(post("/products/add")
                                                      .contentType(MediaType.APPLICATION_JSON_UTF8)
                                                      .content(productToSaveAsJson))
                                     .andExpect(status().isOk())
                                     .andReturn();
        Product productSaved = readValueAsObject(mvcResult, Product.class);

        assertProductWithoutId(productToSave, productSaved);
        assertNotNull(productSaved.getId());
    }

    @Test
    void updateProduct() throws Exception {
        String changedExtraInfo = "example extra info";
        Product productToSave = new ProductBuilder(Products.TestProductBread.PRODUCT)
                .id(Products.TestProductBread.ID)
                .extraInfo(changedExtraInfo).build();
        String productToSaveAsJson = writeValueAsJson(productToSave);

        MvcResult mvcResult = mockMvc.perform(post("/products/update")
                                                      .contentType(MediaType.APPLICATION_JSON_UTF8)
                                                      .content(productToSaveAsJson))
                                     .andExpect(status().isOk())
                                     .andReturn();
        Product productSaved = readValueAsObject(mvcResult, Product.class);

        assertProduct(productToSave, productSaved);
    }

    // TODO add tests with failed add/update with ExceptionHandler
}