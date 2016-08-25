package util;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.jar.JarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author XuYexin
 * @Description :
 * @date 2015/12/3
 */
public class CompressUtil {
    private static final Logger logger = LoggerFactory.getLogger(CompressUtil.class);
    private static int BUFFER_LEN = 1024000;

    /**
     * 压缩文件执行函数
     *
     * @param zipDir
     * @throws Exception
     */
    public static void toWar(String zipDir, String zipFile, String removePrefix) throws Exception {
        File outFile = new File(zipFile);
        outFile.createNewFile();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outFile));
        ArchiveOutputStream out = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.JAR,
                bufferedOutputStream);

        if (zipDir.charAt(zipDir.length() - 1) != '/') {
            zipDir += '/';
        }

        Iterator<File> files = FileUtils.iterateFiles(new File(zipDir), null, true);
        while (files.hasNext()) {
            File file = files.next();
            String entryPath = file.getPath().replaceAll(removePrefix, "");
            logger.info("war压缩路径：" + entryPath);
            ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(file, entryPath);
            out.putArchiveEntry(zipArchiveEntry);
            IOUtils.copy(new FileInputStream(file), out);
            out.closeArchiveEntry();
        }
        out.finish();
        out.close();
    }

    /**
     * 打包成Tar文件
     *
     * @param zipFile
     * @param zipDir
     * @throws Exception
     */
    public static String toTar(String zipDir, String zipFile, String removePrefix) throws Exception {
        File outFile = new File(zipFile);
        outFile.createNewFile();
        Iterator<File> files = FileUtils.iterateFiles(new File(zipDir), null, true);
        TarArchiveOutputStream out = null;
        out = new TarArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(outFile), BUFFER_LEN));
        while (files.hasNext()) {
            File srcFile = files.next();
            InputStream is = null;
            try {
                is = new BufferedInputStream(new FileInputStream(srcFile), BUFFER_LEN);
                String entryPath = srcFile.getPath().replaceAll(removePrefix, "");
                logger.info("tar压缩路径：" + entryPath);
                TarArchiveEntry entry = new TarArchiveEntry(srcFile, entryPath);
                entry.setSize(srcFile.length());
                out.setLongFileMode(2);
                out.putArchiveEntry(entry);
                org.apache.commons.io.IOUtils.copyLarge(is, out, new byte[BUFFER_LEN]);
                out.closeArchiveEntry();
            } finally {
                org.apache.commons.io.IOUtils.closeQuietly(is);
            }
        }
        org.apache.commons.io.IOUtils.closeQuietly(out);
        return zipFile;
    }

    /**
     * 通过Gzip压缩
     *
     * @param srcFile
     * @param targetFile
     * @throws java.io.IOException
     */
    public static void toTargz(String srcFile, String targetFile) throws IOException {
        File sourceFile = new File(srcFile);
        File target = new File(targetFile);
        FileInputStream in = null;
        GZIPOutputStream gout = null;
        try {
            in = new FileInputStream(sourceFile);
            gout = new GZIPOutputStream(new FileOutputStream(target));
            byte[] array = new byte[BUFFER_LEN];
            int number = -1;
            while ((number = in.read(array, 0, array.length)) != -1) {
                gout.write(array, 0, number);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (gout != null) {
                try {
                    gout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将文件夹打包成tar.gz
     *
     * @param srcDir
     * @param targetFile
     * @return
     * @throws Exception
     */
    public static String dirToTargz(String srcDir, String targetFile, String removePrefix) throws Exception {
        String tarFile = toTar(srcDir, srcDir + ".tar", removePrefix);
        toTargz(tarFile, targetFile);
        FileUtil.deleteFile(srcDir + ".tar");
        return targetFile;
    }

    /**
     * 解压tar.gz文件
     *
     * @param targzPath
     * @param unTargzDir
     * @return
     */
    public static boolean unTargz(String targzPath, String unTargzDir) throws Exception {
        _UnCompression(targzPath, unTargzDir, ArchiveStreamFactory.JAR, true);
        return true;
    }

    /**
     * 解压War文件
     * @param warPath
     * @param unWarDir
     * @return
     * @throws Exception
     */
    public static boolean unWar(String warPath, String unWarDir) throws Exception {
        _UnCompression(warPath, unWarDir, ArchiveStreamFactory.JAR, false);
        return true;
    }

    /**
     * 解压文件
     *
     * @param zipPath
     * @param unzipPath
     * @param type
     */
    private static void _UnCompression(String zipPath, String unzipPath, String type, boolean isGz) throws Exception {
        File zipFile = new File(zipPath);
        File unzipDir = new File(unzipPath);
        if (unzipDir.isDirectory() && unzipDir.exists()) {
            FileUtil.deleteDirectory(unzipPath);
        }

        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(zipFile));
        ArchiveInputStream in = null;
        if (isGz) {

            FileInputStream fis = new FileInputStream(zipPath);
            GZIPInputStream gis = new GZIPInputStream(new BufferedInputStream(fis));
            in = new ArchiveStreamFactory().createArchiveInputStream("tar", gis);
        } else {
            in = new ArchiveStreamFactory().createArchiveInputStream(type,
                    bufferedInputStream);
        }

        if (isGz) {
            TarArchiveEntry tarEntry = null;
            while ((tarEntry = (TarArchiveEntry) in.getNextEntry()) != null) {
                if (tarEntry.isDirectory()) {
                    new File(unzipPath, tarEntry.getName()).mkdir();
                } else {
                    OutputStream out = FileUtils.openOutputStream(new File(unzipPath, tarEntry.getName()));
                    IOUtils.copy(in, out);
                    out.close();
                }
            }
        } else {
            JarArchiveEntry jarEntry = null;
            while ((jarEntry = (JarArchiveEntry) in.getNextEntry()) != null) {
                if (jarEntry.isDirectory()) {
                    new File(unzipPath, jarEntry.getName()).mkdir();
                } else {
                    OutputStream out = FileUtils.openOutputStream(new File(unzipPath, jarEntry.getName()));
                    IOUtils.copy(in, out);
                    out.close();
                }
            }
        }
        in.close();
    }

}
