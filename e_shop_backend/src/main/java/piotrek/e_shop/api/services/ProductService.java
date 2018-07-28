package piotrek.e_shop.api.services;

import piotrek.e_shop.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<Product> findById(BigDecimal id);

    List<Product> findByName(String name);

    List<Product> findByCategoryName(String categoryName);

    Product save(Product product);

    List<Product> saveAll(List<Product> products);

}
