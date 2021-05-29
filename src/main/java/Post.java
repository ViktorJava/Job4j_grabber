import java.time.LocalDateTime;

/**
 * Модель данных, описывающая пост на сайте sql.ru.
 *
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 0.1
 * @since 29.05.2021
 */
public class Post {
    private int id;
    private String topic;
    private String author;
    private String body;
    private String url;
    private LocalDateTime date;
    private int answers;
    private int views;

    public Post(int id, String topic, String author, String body, String url,
                LocalDateTime date, int answers, int views) {
        this.id = id;
        this.topic = topic;
        this.author = author;
        this.body = body;
        this.url = url;
        this.date = date;
        this.answers = answers;
        this.views = views;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getAnswers() {
        return answers;
    }

    public void setAnswers(int answers) {
        this.answers = answers;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
