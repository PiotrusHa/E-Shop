package piotrusha.e_shop.core.product.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import piotrusha.e_shop.core.product.domain.ProductFacade;
import piotrusha.e_shop.core.product.domain.dto.CreateProductDto;
import piotrusha.e_shop.core.product.domain.dto.ModifyProductDto;
import piotrusha.e_shop.core.product.domain.dto.ProductDto;

import java.util.List;

@RestController
@RequestMapping(value = "products", produces = APPLICATION_JSON_UTF8_VALUE)
class ProductController {

    private final ProductFacade productFacade;

    @Autowired
    ProductController(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @GetMapping("{categoryName}")
    List<ProductDto> getProductsByCategory(@PathVariable String categoryName) {
        return productFacade.findProductsByCategoryName(categoryName);
    }

    @PostMapping("create")
    ProductDto createProduct(@RequestBody CreateProductDto createProductDto) {
        return productFacade.createProduct(createProductDto);
    }

    @PostMapping("modify")
    void modifyProduct(@RequestBody ModifyProductDto modifyProductDto) {
        productFacade.modifyProduct(modifyProductDto);
    }

}
