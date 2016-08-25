package util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author XuYexin
 * @Description :
 * @date 2016/3/18
 */
public class StackTransfer {

    /**
     * 将Exception栈变为String
     * @param e
     * @return
     */
    public static String getString(Exception e){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return (sw.toString());
    }
}
