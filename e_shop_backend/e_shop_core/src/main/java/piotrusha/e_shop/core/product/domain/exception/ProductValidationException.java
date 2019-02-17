package piotrusha.e_shop.core.product.domain.exception;

public class ProductValidationException extends RuntimeException {

    public static ProductValidationException emptyName() {
        return new ProductValidationException("Product name cannot be empty.");
    }

    public static ProductValidationException emptyPrice() {
        return new ProductValidationException("Product price cannot be empty.");
    }

    public static ProductValidationException wrongAvailablePiecesNumber() {
        return new ProductValidationException("Product available pieces number has to be greater than zero.");
    }

    public static ProductValidationException categoryDoesNotExists(String categoryName) {
        return new ProductValidationException(String.format("Category with name %s does not exists.", categoryName));
    }

    public static ProductValidationException emptyProductId() {
        return new ProductValidationException("Product id cannot be empty.");
    }

    private ProductValidationException(String message) {
        super(message);
    }

}
