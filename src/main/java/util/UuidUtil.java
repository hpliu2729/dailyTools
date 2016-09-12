package util;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * @author XuYexin
 * @Description :
 * @date 2016/9/12
 */
public class UuidUtil {

    public static String uuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }


    public static String base58Uuid() {
        UUID uuid = UUID.randomUUID();
        return base58Uuid(uuid);
    }

    protected static String base58Uuid(UUID uuid) {

        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());

        return Base58Util.encode(bb.array());
    }

    public static String encodeBase58TransferUuid(String uuidString) {
        UUID uuid = UUID.fromString(uuidString);
        return base58Uuid(uuid);
    }

    public static String decodeBase58TransferUuid(String base58uuid) {
        byte[] byUuid = Base58Util.decode(base58uuid);
        ByteBuffer bb = ByteBuffer.wrap(byUuid);
        UUID uuid = new UUID(bb.getLong(), bb.getLong());
        return uuid.toString();
    }
    
}
