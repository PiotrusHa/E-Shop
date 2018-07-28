package piotrek.e_shop.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import piotrek.e_shop.model.Category;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, BigDecimal> {

    Optional<Category> findByName(String name);

}
