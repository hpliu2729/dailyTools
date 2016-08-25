package study.thread;

import org.apache.log4j.Logger;
import org.junit.Test;

public class SingleTonTest {
    public static final Logger log = Logger.getLogger(SingleTon.class);

    @Test
    public void testSingleTon(){
        
        for(int i=0;i<10;i++){
            Thread thread = new Thread(new MyThread(i+""));
            thread.start();
        }
        
    }
    
    private class MyThread implements Runnable{
        String name;
        public MyThread(String name){
            this.name = name;
        }
        public void run() {
            // TODO Auto-generated method stub
            SingleTon single = SingleTon.getInstance();
            SingleTon nosingle = new SingleTon();
            log.info("Thread "+" name:"+name+" --"+single.getTime());
            log.info("Thread "+" name:"+name+" --"+nosingle.getTime());
            
            
        }
        
    }
    
}
