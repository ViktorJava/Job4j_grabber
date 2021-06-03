package utils;

import java.time.LocalDateTime;

/**
 * Интерфейс преобразования строки в дату.
 *
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 0.1
 * @since 24.05.2021
 */
public interface DateTimeParser {
    /**
     * Метод парсинга даты типа String в дату типа LocalDateTime.
     *
     * @param parse Дата и время типа String.
     * @return Дата и время типа LocalDateTime.
     */
    LocalDateTime parse(String parse);
}
