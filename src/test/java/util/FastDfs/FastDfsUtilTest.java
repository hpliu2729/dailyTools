package util.fastDFS;

import org.junit.Test;

import java.io.*;

/**
 * @author XuYexin
 * @Description :
 * @date 2016/7/13
 */
public class FastDfsUtilTest {

    @Test
    public void  testUpload(){
        FastDfsUtil fastDfsUtil = new FastDfsUtil();
        byte[] array = getBytes("C:\\abcd.txt");
        try {
            fastDfsUtil.saveFile("abcd.txt", array);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static byte[] getBytes(String filePath){
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
