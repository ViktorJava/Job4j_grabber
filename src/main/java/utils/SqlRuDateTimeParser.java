package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс преобразующий строку в дату.
 *
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 0.1
 * @since 24.05.2021
 */
public class SqlRuDateTimeParser implements DateTimeParser {
    private static final Map<String, Integer> MD = new HashMap<>() {
        {
            put("янв", 1);
            put("фев", 2);
            put("мар", 3);
            put("апр", 4);
            put("май", 5);
            put("июн", 6);
            put("июл", 7);
            put("авг", 8);
            put("сен", 9);
            put("окт", 10);
            put("ноя", 11);
            put("дек", 12);
        }
    };

    @Override
    public LocalDateTime parse(String parse) {
        String[] splitDateTime = parse.split(", ");
        if (splitDateTime.length != 2) {
            throw new IllegalArgumentException("Wrong DateTime format...");
        }
        LocalDate localDate = convertDate(splitDateTime[0]);
        LocalTime localTime = LocalTime.parse(splitDateTime[1]);
        return LocalDateTime.of(localDate, localTime);
    }

    public LocalDate convertDate(String date) {
        if (date.contains("сегодня")) {
            return LocalDate.now();
        } else if (date.contains("вчера")) {
            return LocalDate.now().minusDays(1);
        }
        String[] splitDate = date.split(" ");
        if (splitDate.length != 3) {
            throw new IllegalArgumentException("Wrong date format...");
        }
        int day = Integer.parseInt(splitDate[0]);
        int year = Integer.parseInt(splitDate[2]) + 2000;
        Integer mo = MD.get(splitDate[1]);
        if (mo == null) {
            throw new IllegalArgumentException("Wrong month format...");
        }
        return LocalDate.of(year, mo, day);
    }
}
