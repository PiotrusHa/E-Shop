package piotrusha.e_shop.product.persistence.jpa;

import io.vavr.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import piotrusha.e_shop.product.domain.ProductRepository;
import piotrusha.e_shop.product.domain.dto.ProductDto;

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
    public Option<ProductDto> findByProductId(BigDecimal productId) {
        return Option.ofOptional(repository.findById(productId)
                                           .map(ProductEntity::toDto));
    }

    @Override
    public List<ProductDto> findByCategoryName(String categoryName) {
        return toDomainProducts(repository.findByCategoryName(categoryName));
    }

    @Override
    public Option<BigDecimal> findMaxProductId() {
        return Option.ofOptional(repository.findMaxProductId());
    }

    @Override
    public void save(ProductDto product) {
        repository.save(ProductEntity.fromDto(product));
    }

    @Override
    public void saveAll(List<ProductDto> products) {
        repository.saveAll(fromDtos(products));
    }

    private List<ProductDto> toDomainProducts(List<ProductEntity> entities) {
        return entities.stream()
                       .map(ProductEntity::toDto)
                       .collect(Collectors.toList());
    }

    private List<ProductEntity> fromDtos(List<ProductDto> products) {
        return products.stream()
                       .map(ProductEntity::fromDto)
                       .collect(Collectors.toList());
    }

}
