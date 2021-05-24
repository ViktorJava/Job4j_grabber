package html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Класс парсинга сайта https://www.sql.ru/forum/job-offers.
 *
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 0.1
 * @since 23.05.2021
 */
public class SqlRuParse {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup
                .connect("https://www.sql.ru/forum/job-offers")
                .get();
        Elements row = doc.select(".postslisttopic");
        for (Element td: row) {
            Element href = td.child(0);
            System.out.println(href.attr("href"));
            System.out.println(href.text());
            System.out.println(td.text());
            Element dateTime = td.parent().child(5);
            System.out.println(dateTime.text());
        }
    }
}
