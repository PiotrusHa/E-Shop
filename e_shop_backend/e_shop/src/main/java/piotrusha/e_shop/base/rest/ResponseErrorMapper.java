package piotrusha.e_shop.base.rest;

import static com.google.common.collect.Maps.newHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.base.AppError.ErrorType;

import java.util.Map;

@Service
public class ResponseErrorMapper {

    private final static Map<ErrorType, HttpStatus> HTTP_STATUS_MAP = newHashMap();

    static {
        HTTP_STATUS_MAP.put(ErrorType.EMPTY_DTO_FIELD,                  HttpStatus.BAD_REQUEST);
        HTTP_STATUS_MAP.put(ErrorType.CATEGORY_ALREADY_EXISTS,          HttpStatus.BAD_REQUEST);
        HTTP_STATUS_MAP.put(ErrorType.PRODUCT_NOT_FOUND,                HttpStatus.NOT_FOUND);
        HTTP_STATUS_MAP.put(ErrorType.BILL_NOT_FOUND,                   HttpStatus.NOT_FOUND);
        HTTP_STATUS_MAP.put(ErrorType.CATEGORY_DOES_NOT_EXISTS,         HttpStatus.BAD_REQUEST);
        HTTP_STATUS_MAP.put(ErrorType.NUMBER_SHOULD_BE_POSITIVE,        HttpStatus.BAD_REQUEST);
        HTTP_STATUS_MAP.put(ErrorType.CANNOT_BOOK_PRODUCT,              HttpStatus.BAD_REQUEST);
        HTTP_STATUS_MAP.put(ErrorType.CANNOT_SELL_PRODUCT,              HttpStatus.BAD_REQUEST);
        HTTP_STATUS_MAP.put(ErrorType.CANNOT_CANCEL_PRODUCT_BOOKING,    HttpStatus.BAD_REQUEST);
        HTTP_STATUS_MAP.put(ErrorType.CANNOT_PAY_BILL,                  HttpStatus.BAD_REQUEST);
        HTTP_STATUS_MAP.put(ErrorType.CANNOT_CANCEL_BILL,               HttpStatus.BAD_REQUEST);
        HTTP_STATUS_MAP.put(ErrorType.WRONG_BILL_STATE,                 HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity map(AppError error) {
        HttpStatus httpStatus = HTTP_STATUS_MAP.getOrDefault(error.getErrorType(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error.getErrorMessage(), httpStatus);
    }

}
