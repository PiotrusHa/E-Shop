package piotrusha.e_shop.product.domain;

import java.util.List;

public interface CategoryRepository {

    List<Category> findAll();

    boolean existsByName(String name);

    void save(Category category);

}
