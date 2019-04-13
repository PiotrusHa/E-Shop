package piotrusha.e_shop.base;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class DateTimeProvider {

    public LocalDate currentDate() {
        return LocalDate.now();
    }

    public LocalTime currentTime() {
        return LocalTime.now();
    }

    public LocalDateTime currentDateTime() {
        return LocalDateTime.now();
    }

}
