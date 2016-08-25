package study.quartz;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright
 * <br/>Program Name:TestJavaProject
 * <br/>Comments:
 * <br/>JDK version used:
 * <br/>Create Date:2013-7-17
 * @author LA
 * @version
 */
public class SimpleJob implements Job{
    private Logger logger=LoggerFactory.getLogger(SimpleJob.class);
    @Override
    public void execute(JobExecutionContext jobCtx) throws JobExecutionException {
        System.out.println("I can count to 10->");
        //输出1-10
        for(int i=1;i<=10;i++){
            System.out.println(" | "+i+" ");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO: handle exception
            }
        }
        System.out.println("<-See  I did it.");
        JobDataMap properties=jobCtx.getJobDetail().getJobDataMap();
        System.out.println("Previous fire time: "+jobCtx.getPreviousFireTime());
        System.out.println("curent file time: "+jobCtx.getFireTime());
        System.out.println("next fire time: "+jobCtx.getNextFireTime());
    }

}