package quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    private Properties properties;

    public AlertRabbit(Properties properties) {
        this.properties = properties;
    }

    public static void main(String[] args) {
        AlertRabbit alertRabbit = new AlertRabbit(getProperties("rabbit.properties"));

        try (Connection cn = alertRabbit.initDataBase(alertRabbit.properties)) {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

            JobDataMap data = new JobDataMap();
            data.put("store", cn);
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            //Создание расписания.
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(interval(alertRabbit.properties))
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
            System.out.println(cn);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Connection initDataBase(Properties properties) throws
            ClassNotFoundException, SQLException {
        Class.forName(properties.getProperty("driver-class-name"));
        return DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("username"),
                properties.getProperty("password"));
    }


    private static Properties getProperties(String path) {
        Properties properties = new Properties();
        try (InputStream in = AlertRabbit.class
                .getClassLoader()
                .getResourceAsStream(path)) {
            properties.load(in);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }


    public static int interval(Properties properties) {
        return Integer.parseInt(properties.getProperty("rabbit.interval"));
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
            Connection cn = (Connection) context
                    .getJobDetail()
                    .getJobDataMap()
                    .get("store");
            try (PreparedStatement ps = cn
                    .prepareStatement("insert into rabbit(created_date) values (?)")) {
                ps.setLong(1, System.currentTimeMillis());
                ps.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
