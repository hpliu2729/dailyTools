package util.jsch;

import org.junit.Test;

/**
 * @author XuYexin
 * @Description :
 * @date 2016/3/12
 */
public class SshDriverTest {

    @Test
    public void execTest(){
        SshDriver driver = getOutPut();
        try {
            Thread.sleep(9000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("最终exitCode：" + driver.getExitCode());
        System.out.println("最终status：" + driver.getStatus());
        System.out.println("最终log：" + driver.getResultBuffer());
        System.out.println("最终error：" + driver.getError());
    }

    public SshDriver getOutPut(){
        System.out.println("------开始------");
        SshDriver sshDriver = new SshDriver("root","000","112.74.217.86");
        sshDriver.setCommand("/data/abc.sh chaos 245 许业欣");
        sshDriver.execCommand();
        new LogReader(sshDriver.getResultBuffer()).start();
        System.out.println("------结束------");
        return sshDriver;
    }

    class LogReader extends Thread {
        StringBuffer resultBuffer;
        public LogReader(StringBuffer resultBuffer){
            this.resultBuffer = resultBuffer;
        }
        @Override
        public void run() {
            System.out.println("Reader Start");
            int temp = 0;
            while (temp<3){
//                System.out.println("reader=============日志内容:" + temp + "- " + resultBuffer.toString());
                temp ++;
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Reader End");
        }
    }



}
