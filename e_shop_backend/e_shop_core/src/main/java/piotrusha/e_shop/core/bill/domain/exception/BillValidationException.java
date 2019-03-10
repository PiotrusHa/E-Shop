package piotrusha.e_shop.core.bill.domain.exception;

public class BillValidationException extends RuntimeException {

    public static BillValidationException emptyClientId() {
        return new BillValidationException("Client id cannot be empty.");
    }

    public static BillValidationException emptyRecords() {
        return new BillValidationException("Bill records cannot be empty.");
    }

    public static BillValidationException wrongPiecesNumber() {
        return new BillValidationException("Pieces number has to be greater than zero.");
    }

    public static BillValidationException emptyProductId() {
        return new BillValidationException("Product id cannot be empty.");
    }

    private BillValidationException(String message) {
        super(message);
    }

}
