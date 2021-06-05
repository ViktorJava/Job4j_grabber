package html;

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
    private String heading;
    private String text;
    private String link;
    private LocalDateTime created;

    public Post() {
    }

    public Post(int id, String heading, String text, String link, LocalDateTime created) {
        this.id = id;
        this.heading = heading;
        this.text = text;
        this.link = link;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Post{"
                + "heading='" + heading + '\''
                + ", id='" + id + '\''
                + ", text='" + text + '\''
                + ", link='" + link + '\''
                + ", created=" + created + '}'
                + "\n\n";
    }
}
