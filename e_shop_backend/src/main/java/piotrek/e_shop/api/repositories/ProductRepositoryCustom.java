package piotrek.e_shop.api.repositories;

import piotrek.e_shop.model.Product;

import java.util.List;

public interface ProductRepositoryCustom {

    List<Product> findByCategoryName(String categoryName);

}
