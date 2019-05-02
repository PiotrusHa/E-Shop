package piotrusha.e_shop.base;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TestDateTimeProvider extends DateTimeProvider {

    public static final LocalDateTime CURRENT_DATE_TIME = LocalDateTime.of(1995, 7, 31, 13, 59);

    @Override
    public LocalDate currentDate() {
        return CURRENT_DATE_TIME.toLocalDate();
    }

    @Override
    public LocalTime currentTime() {
        return CURRENT_DATE_TIME.toLocalTime();
    }

    @Override
    public LocalDateTime currentDateTime() {
        return CURRENT_DATE_TIME;
    }

}
