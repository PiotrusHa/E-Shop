package piotrusha.e_shop.core.base;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AppError {

    private ErrorType errorType;
    private String errorMessage;

    public static AppError validation(String errorMessage) {
        return new AppError(ErrorType.VALIDATION, errorMessage);
    }

    public static AppError notFound(String errorMessage) {
        return new AppError(ErrorType.NOT_FOUND, errorMessage);
    }

    public enum ErrorType {
        VALIDATION,
        NOT_FOUND
    }

}
