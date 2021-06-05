package html;

import java.util.List;

/**
 * Метод хранения данных в памяти.
 *
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 0.1
 * @since 05.06.2021
 */
public class MemStore implements Store {
    private List<Post> posts;

    /**
     * Метод сохранения объявления.
     *
     * @param post Объявление типа Post.
     */
    @Override
    public void save(Post post) {
        posts.add(post);
    }

    /**
     * Метод возвращает все существующие объявления.
     *
     * @return Список объявлений.
     */
    @Override
    public List<Post> getAll() {
        return posts;
    }

    /**
     * Поиск поста по id.
     *
     * @param id Идентификатор записи.
     * @return Найденный пост.
     */
    @Override
    public Post findById(String id) {
        return null;
    }
}
