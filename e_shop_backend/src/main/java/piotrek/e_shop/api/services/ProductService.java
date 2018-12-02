package piotrek.e_shop.api.services;

import piotrek.e_shop.model.Product;
import piotrek.e_shop.model.PurchaseProduct;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<Product> findById(BigDecimal id);

    List<Product> findAll();

    List<Product> findByName(String name);

    List<Product> findByCategoryName(String categoryName);

    Product add(Product product);

    Product update(Product product);

    List<Product> addAll(List<Product> products);

}
