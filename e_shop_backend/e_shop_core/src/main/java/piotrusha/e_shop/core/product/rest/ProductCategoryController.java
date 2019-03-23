package piotrusha.e_shop.core.product.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import piotrusha.e_shop.core.product.domain.ProductFacade;
import piotrusha.e_shop.core.product.domain.dto.CreateProductCategoryDto;
import piotrusha.e_shop.core.product.domain.dto.ProductCategoryDto;

import java.util.List;

@RestController
@RequestMapping(value = "categories", produces = APPLICATION_JSON_UTF8_VALUE)
class ProductCategoryController {

    private final ProductFacade productFacade;

    @Autowired
    ProductCategoryController(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    List<ProductCategoryDto> getAllCategories() {
        return productFacade.findAllProductCategories();
    }

    @PostMapping("add")
    void addCategory(@RequestBody CreateProductCategoryDto categoryDto) {
        productFacade.createProductCategory(categoryDto);
    }

}
