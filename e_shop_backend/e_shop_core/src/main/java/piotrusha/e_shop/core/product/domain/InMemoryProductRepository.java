package piotrusha.e_shop.core.product.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

class InMemoryProductRepository implements ProductRepository {

    private Map<BigDecimal, Product> map = new ConcurrentHashMap<>();

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<Product> findByProductId(BigDecimal id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public List<Product> findByCategoryName(String categoryName) {
        Category category = new Category(categoryName);
        return findAll().stream()
                        .filter(product -> product.getCategories()
                                                  .contains(category))
                        .collect(Collectors.toList());
    }

    @Override
    public Optional<BigDecimal> findMaxProductId() {
        return map.values()
                  .stream()
                  .map(Product::getProductId)
                  .max(BigDecimal::compareTo);
    }

    @Override
    public void save(Product product) {
        map.put(product.getProductId(), product);
    }

    @Override
    public void saveAll(List<Product> products) {
        products.forEach(this::save);
    }

}
