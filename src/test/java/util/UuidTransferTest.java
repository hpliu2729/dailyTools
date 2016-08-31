package util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @author XuYexin
 * @Description :
 * @date 2016/8/31
 */
public class UuidTransferTest {
    private static final Logger logger = LoggerFactory.getLogger(UuidTransfer.class);

    @Test
    public void testTransfer() {
        int allCount = 0;
        int rightCount = 0;
        for (int i=0 ;i<1000; i++){
            UUID uuid = UUID.randomUUID();
            String uuid16 = uuid.toString().replaceAll("-", "");
            logger.info(uuid16 + " origin length:" + uuid16.length());
            String uuid32 = UuidTransfer.to32(uuid16);
            String uuid16Now = UuidTransfer.to16(uuid32);
            logger.info(uuid16Now + " after  length:" +uuid16Now.length());
            logger.info(uuid32 + " To32 length:" + uuid32.length());
            if (uuid16Now.equals(uuid16) && uuid32.length() == 26){
                rightCount ++;
            }else {
                logger.error(uuid16 + "origin:" + uuid16.length());
                logger.error(uuid32 + "  length:" + uuid32.length());
                logger.error(uuid16Now + "  length:" +uuid16Now.length());
            }
            allCount ++;
        }
        logger.info("总共创建uuid:" + allCount);
        logger.info("比对成功uuid:" + rightCount);

    }


}
