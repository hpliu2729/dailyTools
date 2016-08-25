package study.quartz;


import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Copyright
 * <br/>Program Name:TestJavaProject
 * <br/>Comments:
 * <br/>JDK version used:
 * <br/>Create Date:2013-7-17
 *
 * @author LA
 */
public class CronTriggerRunner {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class).withIdentity("myjob", "job-group").build();
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("cronTrigger", "trigger-group").withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?")).build();
            SchedulerFactory sFactory = new StdSchedulerFactory();
            Scheduler scheduler = sFactory.getScheduler();
            scheduler.scheduleJob(jobDetail, cronTrigger);
            scheduler.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}