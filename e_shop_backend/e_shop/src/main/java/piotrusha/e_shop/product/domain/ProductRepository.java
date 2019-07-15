package piotrusha.e_shop.product.domain;

import io.vavr.control.Option;
import piotrusha.e_shop.product.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository {

    Option<ProductDto> findByProductId(BigDecimal productId);

    List<ProductDto> findByCategoryName(String categoryName);

    Option<BigDecimal> findMaxProductId();

    void save(ProductDto product);

    void saveAll(List<ProductDto> products);

}
