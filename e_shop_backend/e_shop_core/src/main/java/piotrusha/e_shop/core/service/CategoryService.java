package piotrusha.e_shop.core.service;

import piotrusha.e_shop.core.model.Category;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Optional<Category> findById(BigDecimal id);

    Optional<Category> findByName(String name);

    List<Category> findAll();

    Category save(Category category);

    List<Category> saveAll(List<Category> categories);

    List<Category> validateCategories(List<Category> categories);

}
