package piotrusha.e_shop.product.persistence.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

interface ProductJpaRepository extends Repository<ProductEntity, BigDecimal> {

    Optional<ProductEntity> findByProductId(BigDecimal productId);

    @Query("SELECT p FROM ProductEntity p JOIN p.categories c WHERE c.name = :categoryName")
    List<ProductEntity> findByCategoryName(@Param("categoryName") String categoryName);

    @Query("SELECT max(p.id) FROM ProductEntity p")
    Optional<BigDecimal> findMaxProductId();

    void save(ProductEntity productEntity);

}
