package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    private static final String PATTERN = "dd MM yyyy kk:mm";


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
        if (parse.contains("сегодня")) {
            return LocalDateTime.now();
        } else if (parse.contains("вчера")) {
            return LocalDateTime.now().minusDays(1);
        }
        String[] splitDateTime = parse.split(", ");
        if (splitDateTime.length != 2) {
            throw new IllegalArgumentException("Wrong DateTime format...");
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN);
        StringBuilder sb = new StringBuilder();
        String[] splitDate = splitDateTime[0].split(" ");
        if (splitDate.length != 3) {
            throw new IllegalArgumentException("Wrong date format...");
        }
        int day = Integer.parseInt(splitDate[0]);
        int year = Integer.parseInt(splitDate[2]) + 2000;
        Integer mo = MD.get(splitDate[1]);
        if (mo == null) {
            throw new IllegalArgumentException("Wrong month format...");
        }
        LocalDateTime localDate = LocalDate
                .of(year, mo, day)
                .atTime(LocalTime.parse(splitDateTime[1]));
        sb.append(localDate.format(dateTimeFormatter));
        return LocalDateTime.parse(sb, dateTimeFormatter);
    }
}
