package quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;


/**
 * Выполнение действий с заданной периодичностью.
 *
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 0.1
 * @since 20.05.2021
 */
public class AlertRabbit {
    public static void main(String[] args) {
        try {
            List<Long> store = new ArrayList<>();
            //Конфигурирование.
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("store", store);
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            //Создание расписания.
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(interval("rabbit.properties"))
                    .repeatForever();
            //В триггере можно указать, когда начинать запуск.
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            //Загрузка задачи и триггера в планировщик
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
            System.out.println(store);
        } catch (Exception se) {
            se.printStackTrace();
        }
    }

    /**
     * Метод получает имя properties файла, возвращает интервал.
     *
     * @param path Имя файла properties.
     * @return Интервал считанный в файле.
     */
    public static int interval(String path) {
        String interval;
        try (InputStream in = AlertRabbit.class
                .getClassLoader()
                .getResourceAsStream(path)) {
            Properties config = new Properties();
            config.load(in);
            interval = config.getProperty("rabbit.interval");
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return Integer.parseInt(interval);
    }

    /**
     * Класс который отрабатывает с периодичностью.
     */
    public static class Rabbit implements Job {
        public Rabbit() {
            System.out.println(hashCode());
        }

        @Override
        public void execute(JobExecutionContext context) {
            System.out.println("Rabbit runs here ...");
            List<Long> store = (List<Long>) context
                    .getJobDetail()
                    .getJobDataMap()
                    .get("store");
            store.add(System.currentTimeMillis());
        }
    }
}
