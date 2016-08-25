package study.io;

import java.io.IOException;
import java.io.PipedInputStream;

import org.apache.log4j.Logger;

public class PipedSave implements Runnable{
    private static final Logger log = Logger.getLogger(PipedSave.class);
    private PipedInputStream pin = new PipedInputStream();

    public PipedInputStream getPin() {
        return pin;
    }

    public void setPin(PipedInputStream pin) {
        this.pin = pin;
    }

    public void run() {
        // TODO Auto-generated method stub
        byte buffer[] = new byte[100];
        String result = "";
        try {
            while((pin.read(buffer, 0, buffer.length)) != -1){
                
                result += new String(buffer);
            }
            
            pin.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        log.info("Save messege : " + result);
    }
    
    
    
    

}
