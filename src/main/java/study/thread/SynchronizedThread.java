package study.thread;

import org.apache.log4j.Logger;

public class SynchronizedThread extends Thread {

    public static int count = 0;
    public static final Logger log = Logger.getLogger(SynchronizedThread.class);

    public static void go() throws InterruptedException {
        int now = count;
        for (int i = 0; i < 5; i++) {
            count++;
            log.info(count);
        }
        log.info(now + "---" + count);
    }

    public void run() {
        // TODO Auto-generated method stub
        synchronized (this.getClass()) {
            try {
                go();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

}
