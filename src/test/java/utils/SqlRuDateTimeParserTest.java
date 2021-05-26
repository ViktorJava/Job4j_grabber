package utils;

import org.junit.Test;

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
    public void whenLD() {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        String s = "22 августа 79, 23:00";
        LocalDateTime expected = LocalDateTime.of(1979, 8, 22, 23, 0);
        LocalDateTime result = parser.parse(s);
        assertEquals(expected, result);
    }
}
