package piotrusha.e_shop.product.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import piotrusha.e_shop.base.rest.ResponseEntityCreator;
import piotrusha.e_shop.base.rest.ResponseErrorMapper;
import piotrusha.e_shop.product.domain.ProductFacade;
import piotrusha.e_shop.product.domain.dto.CreateProductCategoryDto;
import piotrusha.e_shop.product.domain.dto.ProductCategoryDto;

import java.util.List;

@RestController
@RequestMapping(value = "categories", produces = APPLICATION_JSON_UTF8_VALUE)
class ProductCategoryController {

    private final ProductFacade productFacade;
    private final ResponseErrorMapper responseErrorMapper;

    @Autowired
    ProductCategoryController(ProductFacade productFacade, ResponseErrorMapper responseErrorMapper) {
        this.productFacade = productFacade;
        this.responseErrorMapper = responseErrorMapper;
    }

    @GetMapping
    List<ProductCategoryDto> getAllCategories() {
        return productFacade.findAllProductCategories();
    }

    @PostMapping
    ResponseEntity<?> createCategory(@RequestBody CreateProductCategoryDto categoryDto) {
        return productFacade.createProductCategory(categoryDto)
                            .map(ResponseEntityCreator::created)
                            .getOrElseGet(responseErrorMapper::map);
    }

}
