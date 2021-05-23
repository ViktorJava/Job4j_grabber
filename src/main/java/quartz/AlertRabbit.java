package quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;


/**
 * Выполнение действий с заданной периодичностью.
 * Job c параметром.
 *
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 0.1
 * @since 20.05.2021
 */
public class AlertRabbit {
    private final Properties properties;

    public AlertRabbit(Properties properties) {
        this.properties = properties;
    }

    public static void main(String[] args) {
        AlertRabbit alertRabbit = new AlertRabbit(getProperties("rabbit.properties"));
        try (Connection cn = alertRabbit.initDataBase(alertRabbit.properties)) {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            //Загрузка задачи и триггера в планировщик
            scheduler.scheduleJob(getJob(cn), setSheduller(alertRabbit.properties));
            Thread.sleep(10000);
            scheduler.shutdown();
            System.out.println("-ok");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод создания Job.
     *
     * @param cn Передаём в Job готовый объект Connection.
     * @return Job object.
     */
    private static JobDetail getJob(Connection cn) {
        JobDataMap data = new JobDataMap();
        data.put("store", cn);
        return newJob(Rabbit.class)
                .usingJobData(data)
                .build();
    }

    /**
     * Метод создания задачи.
     *
     * @param pr Объект типа Properties.
     * @return Trigger необходимый для Scheduler.
     */
    private static Trigger setSheduller(Properties pr) {
        //Создание Scheduler.
        SimpleScheduleBuilder times = simpleSchedule()
                .withIntervalInSeconds(interval(pr))
                .repeatForever();
        //В триггере указываем, когда и как начинать запуск.
        return newTrigger()
                .startNow()
                .withSchedule(times)
                .build();
    }

    /**
     * Инициализация базы данных.
     *
     * @param properties Объект конфигурации.
     * @return Объект Connection.
     * @throws ClassNotFoundException Possible exception.
     * @throws SQLException           Possible exception.
     */
    private Connection initDataBase(Properties properties) throws
            ClassNotFoundException, SQLException {
        Class.forName(properties.getProperty("driver-class-name"));
        return DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("username"),
                properties.getProperty("password"));
    }

    /**
     * Метод извлечения интервала из конфигурационных данных.
     *
     * @param properties Объект типа Properties.
     * @return Интервал.
     */
    public static int interval(Properties properties) {
        return Integer.parseInt(properties.getProperty("rabbit.interval"));
    }

    /**
     * Метод чтения файла конфигурации.
     *
     * @param path имя файла конфигурации.
     * @return Объект конфигурации.
     */
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

    /**
     * Класс который отрабатывает с периодичностью.
     */
    public static class Rabbit implements Job {
        public Rabbit() {
            System.out.println(hashCode());
        }

        /**
         * Метод исполнения Job.
         *
         * @param context Параметры Job.
         */
        @Override
        public void execute(JobExecutionContext context) {
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
