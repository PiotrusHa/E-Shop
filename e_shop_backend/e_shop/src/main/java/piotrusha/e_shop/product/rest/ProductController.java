package piotrusha.e_shop.product.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import piotrusha.e_shop.base.rest.ResponseEntityCreator;
import piotrusha.e_shop.base.rest.ResponseErrorMapper;
import piotrusha.e_shop.product.domain.ProductFacade;
import piotrusha.e_shop.product.domain.dto.CreateProductDto;
import piotrusha.e_shop.product.domain.dto.ModifyProductDto;
import piotrusha.e_shop.product.domain.dto.ProductDto;

import java.util.List;

@RestController
@RequestMapping(value = "products", produces = APPLICATION_JSON_UTF8_VALUE)
class ProductController {

    private final ProductFacade productFacade;
    private final ResponseErrorMapper responseErrorMapper;

    @Autowired
    ProductController(ProductFacade productFacade, ResponseErrorMapper responseErrorMapper) {
        this.productFacade = productFacade;
        this.responseErrorMapper = responseErrorMapper;
    }

    @GetMapping("{categoryName}")
    List<ProductDto> getProductsByCategory(@PathVariable String categoryName) {
        return productFacade.findProductsByCategoryName(categoryName);
    }

    @PostMapping("create")
    ResponseEntity<?> createProduct(@RequestBody CreateProductDto createProductDto) {
        return productFacade.createProduct(createProductDto)
                            .map(ResponseEntityCreator::ok)
                            .getOrElseGet(responseErrorMapper::map);
    }

    @PostMapping("modify")
    ResponseEntity<?> modifyProduct(@RequestBody ModifyProductDto modifyProductDto) {
        return productFacade.modifyProduct(modifyProductDto)
                            .map(ResponseEntityCreator::ok)
                            .getOrElseGet(responseErrorMapper::map);
    }

}
