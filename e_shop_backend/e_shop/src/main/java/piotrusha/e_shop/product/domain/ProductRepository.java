package piotrusha.e_shop.product.domain;

import io.vavr.control.Option;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

interface ProductRepository extends Repository<Product, BigDecimal> {

    List<Product> findAll();

    Option<Product> findByProductId(BigDecimal productId);

    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.name = :categoryName")
    List<Product> findByCategoryName(@Param("categoryName") String categoryName);

    @Query("SELECT max(p.id) FROM Product p")
    Optional<BigDecimal> findMaxProductId();

    void save(Product product);

    void saveAll(List<Product> products);

}
