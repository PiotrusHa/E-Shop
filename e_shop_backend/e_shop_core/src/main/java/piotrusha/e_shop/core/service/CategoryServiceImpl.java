package piotrusha.e_shop.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import piotrusha.e_shop.core.exception.EntityNotFoundException;
import piotrusha.e_shop.core.model.Category;
import piotrusha.e_shop.core.repository.CategoryRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<Category> findById(BigDecimal id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> saveAll(List<Category> categories) {
        return categoryRepository.saveAll(categories);
    }

    @Override
    public List<Category> validateCategories(List<Category> categories) {
        List<Category> categoriesFromDatabase = new ArrayList<>();
        categories.forEach(category -> {
            Category categoryFromDb = findById(category.getId())
                                        .orElseThrow(() -> new EntityNotFoundException(Category.class, category.getId()));
            categoriesFromDatabase.add(categoryFromDb);
        });
        return categoriesFromDatabase;
    }

}
