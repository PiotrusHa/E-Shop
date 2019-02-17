package piotrusha.e_shop.core.product.domain;

import piotrusha.e_shop.core.product.domain.dto.ProductDto;

import java.util.List;
import java.util.stream.Collectors;

class ProductConverter {

    ProductDto toDto(Product product) {
        List<String> categories = product.getCategories()
                                         .stream()
                                         .map(Category::getName)
                                         .collect(Collectors.toList());

        return new ProductDto().setProductId(product.getProductId())
                               .setName(product.getName())
                               .setPrice(product.getPrice())
                               .setAvailablePiecesNumber(product.getAvailablePiecesNumber())
                               .setBookedPiecesNumber(product.getBookedPiecesNumber())
                               .setSoldPiecesNumber(product.getSoldPiecesNumber())
                               .setDescription(product.getDescription())
                               .setCategories(categories);
    }

    List<ProductDto> toDto(List<Product> products) {
        return products.stream()
                       .map(this::toDto)
                       .collect(Collectors.toList());
    }

}
