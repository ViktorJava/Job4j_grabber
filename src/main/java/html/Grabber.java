package html;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Класс граббера.
 *
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 0.1
 * @since 09.06.2021
 */
public class Grabber implements Grab {
    private final Properties cfg = new Properties();

    /**
     * Метод инициализации хранилища в базе данных.
     *
     * @return Объект хранилища.
     */
    public Store store() {
        return new PsqlStore(cfg);
    }

    /**
     * Метод создания планировщика.
     *
     * @return Объект планировщика.
     * @throws SchedulerException Possible exception.
     */
    public Scheduler scheduler() throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        return scheduler;
    }

    /**
     * Инициализация проперти.
     *
     * @throws IOException Possible exception.
     */
    public void cfg() throws IOException {
        try (InputStream in = Grabber.class
                .getClassLoader()
                .getResourceAsStream("psql.properties")) {
            cfg.load(in);
        }
    }

    /**
     * Метод инициализации граббера.
     *
     * @param parser    Объект типа Parser.
     * @param store     Объект типа Store.
     * @param scheduler Объект типа Scheduler.
     * @throws SchedulerException Possible exception.
     */
    @Override
    public void init(Parser parser, Store store, Scheduler scheduler) throws
            SchedulerException {
        JobDataMap data = new JobDataMap();
        data.put("store", store);
        data.put("parse", parser);
        JobDetail job = newJob(GrabJob.class)
                .usingJobData(data)
                .build();
        SimpleScheduleBuilder times = simpleSchedule()
                .withIntervalInSeconds(Integer.parseInt(cfg.getProperty("jdbc.time")))
                .repeatForever();
        Trigger trigger = newTrigger()
                .startNow()
                .withSchedule(times)
                .build();
        scheduler.scheduleJob(job, trigger);
    }

    /**
     * Метод открывает серверный сокет и выдаёт клиенту содержимое базы данных.
     *
     * @param store Объект хранилище в базе данных.
     */
    public void web(Store store) {
        new Thread(() -> {
            try (ServerSocket server = new ServerSocket(Integer.parseInt(cfg.getProperty("port")))) {
                while (!server.isClosed()) {
                    Socket socket = server.accept();
                    try (OutputStream out = socket.getOutputStream()) {
                        out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                        for (Post post: store.getAll()) {
                            out.write(post
                                    .toString()
                                    .getBytes(Charset.forName("Windows-1251")));
                            out.write(System
                                    .lineSeparator()
                                    .getBytes());
                        }
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static class GrabJob implements Job {

        @Override
        public void execute(JobExecutionContext context) {
            JobDataMap map = context.getJobDetail().getJobDataMap();
            Store store = (Store) map.get("store");
            Parser parse = (Parser) map.get("parse");
            List<Post> posts = parse.list("https://www.sql.ru/forum/job-offers");
            posts.forEach(store::save);
        }
    }

    public static void main(String[] args) throws Exception {
        Grabber grab = new Grabber();
        grab.cfg();
        Scheduler scheduler = grab.scheduler();
        Store store = grab.store();
        grab.init(new SqlRuParse(), store, scheduler);
        grab.web(store);
    }
}
