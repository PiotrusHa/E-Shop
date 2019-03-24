package piotrusha.e_shop.product.persistence.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import piotrusha.e_shop.product.domain.Category;
import piotrusha.e_shop.product.domain.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
class CategoryJpaRepositoryAdapter implements CategoryRepository {

    private final CategoryJpaRepository repository;

    @Autowired
    CategoryJpaRepositoryAdapter(CategoryJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Category> findAll() {
        return toDomainCategories(repository.findAll());
    }

    @Override
    public boolean existsByName(String name) {
        return repository.existsById(name);
    }

    @Override
    public void save(Category category) {
        repository.save(CategoryEntity.fromDomainCategory(category));
    }

    private List<Category> toDomainCategories(List<CategoryEntity> entities) {
        return entities.stream()
                       .map(CategoryEntity::toDomainCategory)
                       .collect(Collectors.toList());
    }

}
