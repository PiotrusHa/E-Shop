package piotrusha.e_shop.product.persistence.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import piotrusha.e_shop.product.domain.CategoryRepository;
import piotrusha.e_shop.product.domain.dto.ProductCategoryDto;

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
    public List<ProductCategoryDto> findAll() {
        return toDtos(repository.findAll());
    }

    @Override
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    @Override
    public void save(ProductCategoryDto category) {
        repository.save(CategoryEntity.fromDto(category));
    }

    private List<ProductCategoryDto> toDtos(List<CategoryEntity> entities) {
        return entities.stream()
                       .map(CategoryEntity::toDto)
                       .collect(Collectors.toList());
    }

}
