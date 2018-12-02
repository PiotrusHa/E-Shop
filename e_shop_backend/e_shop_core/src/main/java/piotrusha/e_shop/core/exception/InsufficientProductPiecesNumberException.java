package piotrusha.e_shop.core.exception;

import piotrusha.e_shop.core.model.Product;

public class InsufficientProductPiecesNumberException extends RuntimeException {

    private Product product;
    private Integer neededPiecesNumber;

    public InsufficientProductPiecesNumberException(Product product, Integer neededPiecesNumber) {
        super(String.format("Product with name '%s' and id %s have only %d available pieces, but %d is needed",
                            product.getName(), String.valueOf(product.getId()), product.getAvailablePiecesNumber(), neededPiecesNumber));
        this.product = product;
        this.neededPiecesNumber = neededPiecesNumber;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getNeededPiecesNumber() {
        return neededPiecesNumber;
    }

}
