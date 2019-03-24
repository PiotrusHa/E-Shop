package piotrusha.e_shop.base;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AppError {

    public enum ErrorType {
        EMPTY_DTO_FIELD,
        CATEGORY_ALREADY_EXISTS,
        PRODUCT_NOT_FOUND,
        BILL_NOT_FOUND,
        CATEGORY_DOES_NOT_EXISTS,
        NUMBER_SHOULD_BE_POSITIVE,
        CANNOT_BOOK_PRODUCT,
        CANNOT_SELL_PRODUCT,
        CANNOT_CANCEL_PRODUCT_BOOKING,
        CANNOT_PAY_BILL,
        CANNOT_CANCEL_BILL,
        WRONG_BILL_STATE
    }

    private ErrorType errorType;
    private String errorMessage;

    public static AppError productNotFound(BigDecimal productId) {
        return new AppError(ErrorType.PRODUCT_NOT_FOUND, String.format("Product with productId %s not found.", productId));
    }

    public static AppError billNotFound(BigDecimal billId) {
        return new AppError(ErrorType.BILL_NOT_FOUND, String.format("Bill with billId %s not found.", billId));
    }

    public static AppError categoryDoesNotExists(String categoryName) {
        return new AppError(ErrorType.CATEGORY_DOES_NOT_EXISTS, String.format("Category with name %s does not exists.", categoryName));
    }

    public static AppError emptyDtoField(String fieldName) {
        return new AppError(ErrorType.EMPTY_DTO_FIELD, String.format("%s cannot be empty.", fieldName));
    }

    public static AppError categoryAlreadyExists(String categoryName) {
        return new AppError(ErrorType.CATEGORY_ALREADY_EXISTS, String.format("Category with name %s already exists.", categoryName));
    }

    public static AppError numberShouldBePositive(String fieldName) {
        return new AppError(ErrorType.NUMBER_SHOULD_BE_POSITIVE, String.format("%s has to be greater than zero.", fieldName));
    }

    public static AppError cannotBookProduct(String productName, Integer piecesToBook, Integer availablePiecesNumber) {
        return new AppError(ErrorType.CANNOT_BOOK_PRODUCT,
                            String.format("Cannot book %s pieces of product %s. Available pieces number to book is %s.",
                                          piecesToBook, productName, availablePiecesNumber));
    }

    public static AppError cannotSellProduct(String productName, Integer piecesToSell, Integer currentlyBooked) {
        return new AppError(ErrorType.CANNOT_SELL_PRODUCT,
                            String.format("Cannot sell %s pieces of product %s. Currently booked pieces number is %s.",
                                          piecesToSell, productName, currentlyBooked));
    }

    public static AppError cannotCancelProductBooking(String productName, Integer piecesToCancel) {
        return new AppError(ErrorType.CANNOT_CANCEL_PRODUCT_BOOKING,
                            String.format("Cannot cancel booking %s pieces of product %s.", piecesToCancel, productName));
    }

    public static AppError cannotPayBill(String billState) {
        return new AppError(ErrorType.CANNOT_PAY_BILL, String.format("Cannot pay bill with state %s", billState));
    }

    public static AppError cannotCancelBill(String billState) {
        return new AppError(ErrorType.CANNOT_CANCEL_BILL, String.format("Cannot cancel bill with state %s", billState));
    }

    public static AppError wrongBillState(String billState) {
        return new AppError(ErrorType.WRONG_BILL_STATE, String.format("Wrong bill state: %s", billState));
    }

}
