package html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

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
        String url = "https://www.sql.ru/forum/job-offers";
        for (int i = 1; i <= 5; i++) {
            parsePage(url + "/" + i);
        }
    }

    /**
     * Метод парсит сайт по заданному адресу.
     *
     * @param url Адрес сайта.
     * @throws IOException Возможное исключение.
     */
    private static void parsePage(String url) throws IOException {
        Document doc = Jsoup
                .connect(url)
                .get();
        Elements row = doc.select(".postslisttopic");
        for (Element td: row) {
            Element href = td.child(0);
            System.out.println(href.attr("href"));
            System.out.println(href.text());
            Element dateTime = td.parent().child(5);
            System.out.println(dateTime.text());
        }
    }
}
