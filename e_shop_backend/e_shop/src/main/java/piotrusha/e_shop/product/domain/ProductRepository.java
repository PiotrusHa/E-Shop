package piotrusha.e_shop.product.domain;

import io.vavr.control.Option;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository {

    List<Product> findAll();

    Option<Product> findByProductId(BigDecimal productId);

    List<Product> findByCategoryName(String categoryName);

    Option<BigDecimal> findMaxProductId();

    void save(Product product);

    void saveAll(List<Product> products);

}
