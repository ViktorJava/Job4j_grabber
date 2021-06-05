package html;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;

/**
 * Интерфейс запуска парсера.
 *
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 0.1
 * @since 05.06.2021
 */
public interface Grab {
    /**
     * Метод инициализации парсера.
     *
     * @param parser    Объект типа Parser.
     * @param store     Объект типа Store.
     * @param scheduler Объект типа Scheduler.
     * @throws SchedulerException Possible exception.
     */
    void init(Parser parser, Store store, Scheduler scheduler) throws SchedulerException;
}
