package piotrek.e_shop.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import piotrek.e_shop.model.Product;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, BigDecimal>, ProductRepositoryCustom {

    List<Product> findByName(String name);

}
