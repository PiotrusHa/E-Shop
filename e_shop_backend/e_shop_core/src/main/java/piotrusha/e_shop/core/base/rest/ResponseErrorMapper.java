package piotrusha.e_shop.core.base.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import piotrusha.e_shop.core.base.AppError;
import piotrusha.e_shop.core.base.AppError.ErrorType;

import java.util.Map;

@Service
public class ResponseErrorMapper {

    private final static Map<ErrorType, HttpStatus> HTTP_STATUS_MAP =
            Map.of(
                    ErrorType.VALIDATION, HttpStatus.BAD_REQUEST,
                    ErrorType.NOT_FOUND, HttpStatus.NOT_FOUND
            );

    public ResponseEntity map(AppError error) {
        HttpStatus httpStatus = HTTP_STATUS_MAP.getOrDefault(error.getErrorType(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error.getErrorMessage(), httpStatus);
    }

}
