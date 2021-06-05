package html;

import java.util.List;

/**
 * Интерфейс хранилища.
 *
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 0.1
 * @since 05.06.2021
 */
public interface Store {
    /**
     * Метод сохранения объявления в базе.
     *
     * @param post Объявление типа Post.
     */
    void save(Post post);

    /**
     * Метод возвращает из базы, список всех объявлений.
     *
     * @return Список объявлений.
     */
    List<Post> getAll();

    /**
     * Метод поиска объявлений в базе, по уникальному идентификатору.
     *
     * @param id Идентификатор записи.
     * @return Найденное объявление.
     */
    Post findById(String id);
}
