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

    public static BillValidationException emptyBillId() {
        return new BillValidationException("Bill id cannot be empty.");
    }

    public static BillValidationException cannotCancel(String billState) {
        return new BillValidationException("Cannot cancel bill with state " + billState);
    }

    private BillValidationException(String message) {
        super(message);
    }

}
