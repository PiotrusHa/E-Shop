package piotrusha.e_shop.product.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryCategoryRepository implements CategoryRepository {

    private Map<String, Category> map = new ConcurrentHashMap<>();

    @Override
    public List<Category> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public boolean existsByName(String name) {
        return map.containsKey(name);
    }

    @Override
    public void save(Category category) {
        map.put(category.getName(), category);
    }

}
