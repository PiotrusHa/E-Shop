package piotrusha.e_shop.product.persistence.in_memory;

import io.vavr.control.Option;
import piotrusha.e_shop.product.domain.ProductRepository;
import piotrusha.e_shop.product.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryProductRepository implements ProductRepository {

    private Map<BigDecimal, ProductDto> map = new ConcurrentHashMap<>();

    private List<ProductDto> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Option<ProductDto> findByProductId(BigDecimal id) {
        return Option.of(map.get(id));
    }

    @Override
    public List<ProductDto> findByCategoryName(String categoryName) {
        return findAll().stream()
                        .filter(product -> product.getCategories()
                                                  .contains(categoryName))
                        .collect(Collectors.toList());
    }

    @Override
    public Option<BigDecimal> findMaxProductId() {
        return Option.ofOptional(map.values()
                                    .stream()
                                    .map(ProductDto::getProductId)
                                    .max(BigDecimal::compareTo));
    }

    @Override
    public void save(ProductDto product) {
        map.put(product.getProductId(), product);
    }

    @Override
    public void saveAll(List<ProductDto> products) {
        products.forEach(this::save);
    }

}
