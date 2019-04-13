package piotrusha.e_shop.product.persistence.jpa;

import org.springframework.data.repository.Repository;

import java.util.List;

interface CategoryJpaRepository extends Repository<CategoryEntity, String> {

    List<CategoryEntity> findAll();

    boolean existsByName(String name);

    void save(CategoryEntity categoryEntity);

}
