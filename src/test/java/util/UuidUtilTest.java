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
public class UuidUtilTest {
    private static final Logger logger = LoggerFactory.getLogger(Base58Util.class);


    @Test
    public void test() {
        int count = 0;
        for (int i = 0; i < 100; i++) {
            String uuid = UuidUtil.base58Uuid();
            logger.info(uuid + ",length :" + uuid.length());
            String originUUID = UuidUtil.decodeBase58TransferUuid(uuid);
            if (UuidUtil.encodeBase58TransferUuid(originUUID).equals(uuid)) {
                count++;
            }
        }
        logger.info("合格的uuid：" + count);


    }


}
