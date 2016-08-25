package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public MD5Util() {
    }

    private static byte[] createChecksum(String filename) {
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(filename);
            byte[] e = new byte[1024];
            MessageDigest complete = MessageDigest.getInstance("MD5");

            int numRead1;
            while ((numRead1 = fis.read(e)) != -1) {
                complete.update(e, 0, numRead1);
            }

            byte[] var6 = complete.digest();
            return var6;
        } catch (FileNotFoundException var18) {
            var18.printStackTrace();
        } catch (NoSuchAlgorithmException var19) {
            var19.printStackTrace();
        } catch (IOException var20) {
            var20.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException var17) {
                ;
            }

        }

        return null;
    }

    public static String getMD5Checksum(String filename) {
        File file = new File(filename);
        if (!file.isFile()) {
            return null;
        } else {
            byte[] b = createChecksum(filename);
            if (b == null) {
                return null;
            } else {
                StringBuilder result = new StringBuilder();

                for (int i = 0; i < b.length; ++i) {
                    result.append(Integer.toString((b[i] & 255) + 256, 16)
                            .substring(1));
                }

                result.append("   ").append(file.getName());
                return result.toString();
            }
        }
    }

    public static String createMD5File(String filePath) {
        BufferedWriter output = null;
        try {
            String result = getMD5Checksum(filePath);
            String md5File = filePath.substring(0, filePath.lastIndexOf("."))
                    + ".md5";
            File file = new File(md5File);
            output = new BufferedWriter(new FileWriter(file));
            output.write(result);
            return file.getPath();
        } catch (Exception var14) {
            var14.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return "";

    }
}