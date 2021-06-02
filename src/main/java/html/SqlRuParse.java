package html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * <h2>Класс парсит заданный сайт, в размере 2 страниц.</h2>
 * https://www.sql.ru/forum/job-offers
 *
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 0.1
 * @since 23.05.2021
 */
public class SqlRuParse {
    public static void main(String[] args) throws IOException {
        SqlRuParse sqlRuParse = new SqlRuParse();
        String url = "https://www.sql.ru/forum/job-offers/";
        for (int i = 1; i <= 2; i++) {
            sqlRuParse.parsePage(url + i);
        }
    }

    /**
     * Метод парсит сайт по заданному адресу.
     *
     * @param url Адрес сайта.
     * @throws IOException Возможное исключение.
     */
    private void parsePage(String url) throws IOException {
        SqlRuDateTimeParser sqlRuDateTimeParser = new SqlRuDateTimeParser();
        Document doc = Jsoup.connect(url).get();
        Elements row = doc.select(".postslisttopic");
        for (Element td: row) {
            Element href = td.child(0);
            String postUrl = href.attr("href");
            System.out.println(postUrl);
            System.out.println(href.text());
            System.out.println(getDescription(postUrl));
            //Element dateTime = td.parent().child(5);
            //LocalDateTime ldt = sqlRuDateTimeParser.parse(String.valueOf(dateTime.text()));
            //System.out.println(dateTime.text());
            //System.out.println(ldt);
            System.out.println(date(postUrl));
            //System.out.println(date(href.attr("href")));
        }
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
