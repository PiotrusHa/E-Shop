package piotrusha.e_shop.product.persistence.in_memory;

import piotrusha.e_shop.product.domain.CategoryRepository;
import piotrusha.e_shop.product.domain.dto.ProductCategoryDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCategoryRepository implements CategoryRepository {

    private Map<String, ProductCategoryDto> map = new ConcurrentHashMap<>();

    @Override
    public List<ProductCategoryDto> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public boolean existsByName(String name) {
        return map.containsKey(name);
    }

    @Override
    public void save(ProductCategoryDto category) {
        map.put(category.getName(), category);
    }

}
