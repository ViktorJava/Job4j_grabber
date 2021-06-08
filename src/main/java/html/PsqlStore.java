package html;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Класс хранилища в базе данных.
 *
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 0.1
 * @since 06.06.2021
 */
public class PsqlStore implements Store, AutoCloseable {
    private final Connection cnn;

    public PsqlStore(Properties cfg) {
        try (InputStream in = PsqlStore.class
                .getClassLoader()
                .getResourceAsStream("psql.properties")) {
            cfg.load(in);
            Class.forName(cfg.getProperty("jdbc.driver"));
            cnn = DriverManager.getConnection(
                    cfg.getProperty("jdbc.url"),
                    cfg.getProperty("jdbc.username"),
                    cfg.getProperty("jdbc.password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Метод сохраняет объявление в базу данных.
     *
     * @param post Объявление типа Post.
     */
    @Override
    public void save(Post post) {
        try {
            PreparedStatement ps = cnn.prepareStatement(
                    "insert into post (name, text, link, created) values (?, ?, ?, ?)");
            ps.setString(1, post.getName());
            ps.setString(2, post.getText());
            ps.setString(3, post.getLink());
            ps.setTimestamp(4, Timestamp.valueOf(post.getCreated()));
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Метод читает все объявления из базы данных.
     *
     * @return Список объявлений.
     */
    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement statement = cnn.prepareStatement("select * from post")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    posts.add(new Post(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("text"),
                            resultSet.getString("link"),
                            resultSet.getTimestamp("created")
                                     .toLocalDateTime()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    /**
     * Поиск записи в базе данных по уникальному идентификатору.
     *
     * @param id Идентификатор записи.
     * @return Объявление найденное по id.
     */
    @Override
    public Post findById(String id) {
        Post post = new Post();
        try (PreparedStatement statement = cnn.prepareStatement("select * from post where id = ?")) {
            statement.setInt(1, Integer.parseInt(id));
            statement.execute();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                post.setId(resultSet.getInt("id"));
                post.setName(resultSet.getString("name"));
                post.setText(resultSet.getString("text"));
                post.setLink(resultSet.getString("link"));
                post.setCreated(resultSet
                        .getTimestamp("created")
                        .toLocalDateTime());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return post;
    }

    /**
     * Закрытие ресурсов Connection.
     *
     * @throws Exception Possible Exception.
     */
    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }

    public static void main(String[] args) {
        Properties properties = new Properties();
        PsqlStore psqlStore = new PsqlStore(properties);
        Post java = new SqlRuParse()
                .detail("https://www.sql.ru/forum/1336341/java-razrabotchik-v-finteh-kompaniu");
        Post sql = new SqlRuParse()
                .detail("https://www.sql.ru/forum/1335674/sql-blizhe-k-middle-v-lubom-regione-rf-do-80k");
        psqlStore.save(java);
        psqlStore.save(sql);
        System.out.println(psqlStore.getAll());
        System.out.println(psqlStore.findById("2"));
    }
}
