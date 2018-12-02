package piotrusha.e_shop.core.repository;

import piotrusha.e_shop.core.model.Product;

import java.util.List;

public interface ProductRepositoryCustom {

    List<Product> findByCategoryName(String categoryName);

}
