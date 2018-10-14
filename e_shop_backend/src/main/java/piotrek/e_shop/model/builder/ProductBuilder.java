package piotrek.e_shop.model.builder;

import piotrek.e_shop.model.Category;
import piotrek.e_shop.model.Product;

import java.math.BigDecimal;
import java.util.List;

public class ProductBuilder {

    private Product product;

    private ProductBuilder() {
        product = new Product();
    }

    public ProductBuilder(String name, BigDecimal price) {
        this();
        product.setName(name);
        product.setPrice(price);
    }

    public ProductBuilder(Product product) {
        this();
        this.product.setName(product.getName());
        this.product.setAvailablePiecesNumber(product.getAvailablePiecesNumber());
        this.product.setSoldPiecesNumber(product.getSoldPiecesNumber());
        this.product.setPrice(product.getPrice());
        this.product.setExtraInfo(product.getExtraInfo());
        this.product.setCategories(product.getCategories());
    }

    public ProductBuilder id(BigDecimal id) {
        product.setId(id);
        return this;
    }

    public ProductBuilder availablePiecesNumber(Integer availablePiecesNumber) {
        product.setAvailablePiecesNumber(availablePiecesNumber);
        return this;
    }

    public ProductBuilder soldPiecesNumber(Integer soldPiecesNumber) {
        product.setSoldPiecesNumber(soldPiecesNumber);
        return this;
    }

    public ProductBuilder extraInfo(String extraInfo) {
        product.setExtraInfo(extraInfo);
        return this;
    }

    public ProductBuilder categories(List<Category> categories) {
        product.setCategories(categories);
        return this;
    }

    public Product build() {
        return product;
    }

}
