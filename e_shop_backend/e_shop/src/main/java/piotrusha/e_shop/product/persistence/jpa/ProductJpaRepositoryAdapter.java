package piotrusha.e_shop.product.persistence.jpa;

import io.vavr.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import piotrusha.e_shop.product.domain.Product;
import piotrusha.e_shop.product.domain.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
class ProductJpaRepositoryAdapter implements ProductRepository {

    private final ProductJpaRepository repository;

    @Autowired
    ProductJpaRepositoryAdapter(ProductJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> findAll() {
        return toDomainProducts(repository.findAll());
    }

    @Override
    public Option<Product> findByProductId(BigDecimal productId) {
        return Option.ofOptional(repository.findById(productId)
                                           .map(ProductEntity::toDomainProduct));
    }

    @Override
    public List<Product> findByCategoryName(String categoryName) {
        return toDomainProducts(repository.findByCategoryName(categoryName));
    }

    @Override
    public Option<BigDecimal> findMaxProductId() {
        return Option.ofOptional(repository.findMaxProductId());
    }

    @Override
    public void save(Product product) {
        repository.save(ProductEntity.fromDomainProduct(product));
    }

    @Override
    public void saveAll(List<Product> products) {
        repository.saveAll(fromDomainProducts(products));
    }

    private List<Product> toDomainProducts(List<ProductEntity> entities) {
        return entities.stream()
                       .map(ProductEntity::toDomainProduct)
                       .collect(Collectors.toList());
    }

    private List<ProductEntity> fromDomainProducts(List<Product> products) {
        return products.stream()
                       .map(ProductEntity::fromDomainProduct)
                       .collect(Collectors.toList());
    }

}
