package piotrusha.e_shop.web.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import piotrusha.e_shop.core.model.Category;
import piotrusha.e_shop.core.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping(value = "categories", produces = APPLICATION_JSON_UTF8_VALUE)
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    List<Category> getAllCategories() {
        return categoryService.findAll();
    }

    @PostMapping("add")
    Category addCategory(@RequestBody Category category) {
        category.setId(null);
        return categoryService.save(category);
    }

}
