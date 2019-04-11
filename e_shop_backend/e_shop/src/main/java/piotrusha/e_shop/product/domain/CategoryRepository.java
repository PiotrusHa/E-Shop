package piotrusha.e_shop.product.domain;

import piotrusha.e_shop.product.domain.dto.ProductCategoryDto;

import java.util.List;

public interface CategoryRepository {

    List<ProductCategoryDto> findAll();

    boolean existsByName(String name);

    void save(ProductCategoryDto category);

}
