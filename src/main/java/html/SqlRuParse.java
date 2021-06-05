package html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * <h2>Класс парсит заданный сайт, в размере 2 страниц.</h2>
 * https://www.sql.ru/forum/job-offers
 *
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 1.1
 * @since 23.05.2021
 */
public class SqlRuParse implements Parser {
    public static void main(String[] args) {
        SqlRuParse sqlRuParse = new SqlRuParse();
        String url = "https://www.sql.ru/forum/job-offers/";
        for (int i = 1; i <= 2; i++) {
            System.out.println(sqlRuParse.list(url + i));
        }
    }

    /**
     * Загрузка списка всех постов.
     *
     * @param link Ссылка на страницу с постами.
     * @return Список постов.
     */
    @Override
    public List<Post> list(String link) {
        List<Post> posts = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(link).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td: row) {
                Element href = td.child(0);
                String postUrl = href.attr("href");
                posts.add(detail(postUrl));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return posts;
    }

    /**
     * Метод загрузки деталей одного поста.
     *
     * @param link Ссылка на страницу поста.
     * @return Объект типа Post.
     */
    @Override
    public Post detail(String link) {
        Post post = new Post();
        try {
            Document doc = Jsoup.connect(link).get();
            post.setLink(link); //ссылка поста
            post.setHeading(doc.select(".messageHeader")
                               .get(0).text().trim());
            post.setText(getDescription(link));
            post.setCreated(date(link));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return post;
    }

    /**
     * Метод возвращает описание поста.
     *
     * @param url Ссылка на пост.
     * @return Строковое представление поста.
     * @throws IOException Возможное исключение.
     */
    private String getDescription(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements row = doc.select(".msgBody");
        return row.get(1).text();
    }

    /**
     * Метод возвращает дату публикации поста.
     *
     * @param url Ссылка на пост.
     * @return Дата и время публикации поста.
     * @throws IOException Возможное исключение.
     */
    private LocalDateTime date(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements row = doc.select(".msgFooter");
        String data = row.get(0).text().split("\\[")[0].trim();
        return new SqlRuDateTimeParser().parse(data);
    }
}
