package piotrusha.e_shop.web.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import piotrusha.e_shop.core.model.Product;
import piotrusha.e_shop.core.service.ProductService;

import java.util.List;

@RestController
@RequestMapping(value = "products", produces = APPLICATION_JSON_UTF8_VALUE)
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("{categoryName}")
    public List<Product> getProductsByCategory(@PathVariable String categoryName) {
        return productService.findByCategoryName(categoryName);
    }

    @PostMapping("add")
    public Product addProduct(@RequestBody Product product) {
        return productService.add(product);
    }

    @PostMapping("update")
    public Product updateProduct(@RequestBody Product product) {
        return productService.update(product);
    }

}
