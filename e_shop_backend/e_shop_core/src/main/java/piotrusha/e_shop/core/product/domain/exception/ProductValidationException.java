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

    public static ProductValidationException wrongPiecesNumber() {
        return new ProductValidationException("Product pieces number has to be greater than zero.");
    }

    public static ProductValidationException notEnoughPiecesToBook(Integer availablePieces, Integer piecesToBook, String productName) {
        return new ProductValidationException(
                String.format("Cannot book %s pieces of product %s. Available pieces number to book is %s.", piecesToBook, productName,
                              availablePieces));
    }

    public static ProductValidationException notEnoughPiecesToCancel(Integer piecesToCancel, String productName) {
        return new ProductValidationException(String.format("Cannot cancel booking %s pieces of product %s.", piecesToCancel, productName));
    }

    public static ProductValidationException notEnoughPiecesToSell(Integer piecesToSell, String productName,
                                                                   Integer currentlyBookedPieces) {
        return new ProductValidationException(
                String.format("Cannot sell %s pieces of product %s. Currently booked pieces number is %s.", piecesToSell, productName,
                              currentlyBookedPieces));
    }

    private ProductValidationException(String message) {
        super(message);
    }

}
