package study.io;

import java.io.IOException;
import java.io.PipedOutputStream;

public class PipedSend implements Runnable{

    private PipedOutputStream pout = new PipedOutputStream();
    
    
    public void run() {
        // TODO Auto-generated method stub
        String messege = "this is send messege";
        try {
            pout.write(messege.getBytes());
            pout.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }


    public PipedOutputStream getPout() {
        return pout;
    }


    public void setPout(PipedOutputStream pout) {
        this.pout = pout;
    }

}
