package html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * <h2>Класс парсит заданный сайт, в размере 6 страниц.</h2>
 * https://www.sql.ru/forum/job-offers
 *
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 0.1
 * @since 23.05.2021
 */
public class SqlRuParse {
    public static void main(String[] args) throws IOException {
        SqlRuParse sqlRuParse = new SqlRuParse();
        SqlRuDateTimeParser sqlRuDateTimeParser = new SqlRuDateTimeParser();
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
        Document doc = Jsoup.connect(url).get();
        Elements row = doc.select(".postslisttopic");
        for (Element td: row) {
            Element href = td.child(0);
            String postUrl = href.attr("href");
            System.out.println(postUrl);
            System.out.println(href.text());
            System.out.println(getDescription(postUrl));
            Element dateTime = td.parent().child(5);
            System.out.println(dateTime.text());
            //System.out.println(date(href.attr("href")));
        }
    }

    private String getDescription(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements row = doc.select(".msgBody");
        return row.get(1).text();
    }

    private static LocalDateTime date(String str) throws IOException {
        Document doc = Jsoup.connect(str).get();
        return null;
    }
}
