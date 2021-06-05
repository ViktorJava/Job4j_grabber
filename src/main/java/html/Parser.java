package html;

import java.util.List;

/**
 * Интерфейс описывающий парсинг сайта.
 *
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 0.1
 * @since 03.06.2021
 */
public interface Parser {
    /**
     * Загрузка списка всех постов.
     *
     * @param link Ссылка на страницу с постами.
     * @return Список постов.
     */
    List<Post> list(String link);

    /**
     * Метод загрузки деталей одного поста.
     *
     * @param link Ссылка на страницу поста.
     * @return Объект с деталями поста.
     */
    Post detail(String link);
}
