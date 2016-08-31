package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

/**
 * @author XuYexin
 * @Description :
 * @date 2016/8/31
 */
public class UuidTransfer {

    private static final Logger logger = LoggerFactory.getLogger(UuidTransfer.class);

    /**
     * 16进制UUID转换为32进制
     *
     * @param originUUID
     * @return
     */
    public static String to32(String originUUID) {
        BigInteger integer16 = new BigInteger(originUUID, 16);
        String integer32 = integer16.toString(32);
        while (26 > integer32.length())
            integer32 = "0" + integer32;
        return integer32;
    }

    /**
     * 16进制UUID转换为32进制
     *
     * @param originUUID
     * @return
     */
    public static String to16(String originUUID) {
        BigInteger integer32 = new BigInteger(originUUID, 32);
        String integer16 = integer32.toString(16);
        while (32 > integer16.length())
            integer16 = "0" + integer16;

        return integer16;
    }


}
