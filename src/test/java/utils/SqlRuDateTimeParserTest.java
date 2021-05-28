package utils;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

/**
 * Тесты преобразователя даты.
 *
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 0.1
 * @since 25.05.2021
 */
public class SqlRuDateTimeParserTest {
    @Test
    public void whenSingleNumberDay() {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        String s = "2 дек 19, 22:29";
        LocalDateTime expected = LocalDateTime.of(2019, 12, 2, 22, 29);
        LocalDateTime result = parser.parse(s);
        assertEquals(expected, result);
    }

    @Test
    public void whenDoubleNumberDay() {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        String s = "25 июн 18, 21:56";
        LocalDateTime expected = LocalDateTime.of(2018, 6, 25, 21, 56);
        LocalDateTime result = parser.parse(s);
        assertEquals(expected, result);
    }

    @Test
    public void whenToday() {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        String s = "сегодня, 02:30";
        LocalDate expected = LocalDate.now();
        LocalDateTime result = parser.parse(s);
        assertEquals(expected, result.toLocalDate());
    }
}
