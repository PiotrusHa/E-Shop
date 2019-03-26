package piotrusha.e_shop.base.rest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseEntityCreator {

    public static <P> ResponseEntity<P> ok(P payload) {
        return new ResponseEntity<>(payload, HttpStatus.OK);
    }

    public static <P> ResponseEntity<P> created(P payload) {
        return new ResponseEntity<>(payload, HttpStatus.CREATED);
    }

    public static ResponseEntity<?> noContent() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
