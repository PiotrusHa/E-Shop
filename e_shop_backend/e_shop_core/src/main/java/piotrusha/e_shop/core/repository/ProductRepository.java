package piotrusha.e_shop.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import piotrusha.e_shop.core.model.Product;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, BigDecimal>, ProductRepositoryCustom {

    List<Product> findByName(String name);

}
