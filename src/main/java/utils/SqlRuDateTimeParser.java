package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Класс преобразующий строку в дату.
 *
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 0.1
 * @since 24.05.2021
 */
public class SqlRuDateTimeParser implements DateTimeParser {
    @Override
    public LocalDateTime parse(String parse) {
        //String year = "2021";
        //return LocalDateTime.of(Integer.parseInt(year),2,12,2,22,12);
        String[] strings = parse.split(",");
        SqlRuDateTimeParser sqlRuDateTimeParser = new SqlRuDateTimeParser();
        LocalTime.parse(strings[1]);
        LocalDate.parse(strings[0]);
        return LocalDateTime.of(LocalDate.now(), LocalTime.now());
    }
}
