package quartz;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Тест интерваллера.
 *
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 0.1
 * @since 21.05.2021
 */
public class AlertRabbitTest {
    @Test
    public void whenIntervalProperti() {
        int interval = AlertRabbit.interval("rabbit.properties");
        assertThat(interval, is(10));
    }
}
