package piotrusha.e_shop.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import piotrusha.e_shop.core.model.Category;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, BigDecimal> {

    Optional<Category> findByName(String name);

}
