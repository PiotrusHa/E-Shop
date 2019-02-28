package piotrusha.e_shop.core.product.domain;

import lombok.AllArgsConstructor;
import piotrusha.e_shop.core.product.domain.dto.BookProductDto;
import piotrusha.e_shop.core.product.domain.exception.ProductNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

class ProductBooker {

    private final ProductRepository productRepository;
    private final ProductValidator productValidator;

    ProductBooker(ProductRepository productRepository, ProductValidator productValidator) {
        this.productRepository = productRepository;
        this.productValidator = productValidator;
    }

    void bookProducts(List<BookProductDto> bookProductDtos) {
        validateDto(bookProductDtos);
        List<ProductToBook> productsToBook = get(bookProductDtos);
        validatePiecesNumber(productsToBook);
        book(productsToBook);
        save(productsToBook);
    }

    private void validateDto(List<BookProductDto> bookProductDtos) {
        bookProductDtos.forEach(productValidator::validateDto);
    }

    private List<ProductToBook> get(List<BookProductDto> dtos) {
        return dtos.stream()
                   .map(dto -> new ProductToBook(get(dto.getProductId()), dto.getPiecesNumber()))
                   .collect(Collectors.toList());
    }

    private Product get(BigDecimal productId) {
        return productRepository.findByProductId(productId)
                                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    private void validatePiecesNumber(List<ProductToBook> productsToBook) {
        productsToBook.forEach(productToBook -> productValidator.validatePiecesNumber(productToBook.product, productToBook.piecesNumber));
    }

    private void book(List<ProductToBook> productsToBook) {
        productsToBook.forEach(productToBook -> productToBook.product.bookProduct(productToBook.piecesNumber));
    }

    private void save(List<ProductToBook> productsToBook) {
        List<Product> products = productsToBook.stream()
                                               .map(productToBook -> productToBook.product)
                                               .collect(Collectors.toList());
        productRepository.saveAll(products);
    }

    @AllArgsConstructor
    private class ProductToBook {

        Product product;
        int piecesNumber;
    }

}
