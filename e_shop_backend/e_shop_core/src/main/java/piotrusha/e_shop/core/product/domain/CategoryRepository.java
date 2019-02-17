package piotrusha.e_shop.core.product.domain;

import org.springframework.data.repository.Repository;

import java.util.List;

interface CategoryRepository extends Repository<Category, String> {

    List<Category> findAll();

    boolean existsByName(String name);

    void save(Category category);

}
