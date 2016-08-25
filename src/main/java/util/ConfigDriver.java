package util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author XuYexin
 * @Description :
 * @date 2015/10/19
 */
public class ConfigDriver {

    static Properties properties =  new Properties();

    /**
     * 获取配置文件数据
     */
    static {
        try {
            InputStreamReader reader = new InputStreamReader(
                    ClassLoader.getSystemResourceAsStream("config.properties"), "UTF-8");
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
            properties = null;
        }
    }

    private ConfigDriver(){}

    public static String getString(String name){
        if(null == properties){
            return "";
        }
        String result = (String) properties.get(name);
        return result;
    }

    public static int getInt(String name){
        if(null == properties){
            return -1;
        }
        return (Integer.parseInt((String)properties.get(name)));
    }
}
