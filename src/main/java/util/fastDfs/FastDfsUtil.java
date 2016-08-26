package util.fastDFS;

import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.StorageClient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XuYexin
 * @Description :
 * @date 2016/7/13
 */
public class FastDfsUtil {

    private FastDFSManager fastDFSManager = new FastDFSManager();

    /**
     * 文件名
     */
    private static final String FILENAME_KEY = "fileName";

    /**
     * 文件扩展名
     */
    private static final String FILEEXTNAME_KEY = "fileExtName";

    private static Logger logger = LoggerFactory.getLogger(FastDfsUtil.class);

    public String saveFile(String fileName, byte[] bytes) throws Exception {
        // 文件扩展名
        if (null == fileName || fileName.lastIndexOf(".") <= 0
                || fileName.lastIndexOf(".") >= (fileName.length() - 1)
                || bytes == null || bytes.length <= 0) {
            return null;
        }


        // 文件名、后缀
        String fileSrcName = fileName.substring(0, fileName.lastIndexOf("."));
        String fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);

        // 文件属性
        List<NameValuePair> nvpList = new ArrayList<NameValuePair>();
        nvpList.add(new NameValuePair(FILENAME_KEY, fileSrcName));
        nvpList.add(new NameValuePair(FILEEXTNAME_KEY, fileExtName));
        NameValuePair[] metas = nvpList.toArray(new NameValuePair[0]);

        // 上传文件
        try {
            StorageClient sc = fastDFSManager.getStorageClient();
            String[] result = sc.upload_file(bytes, fileExtName, metas);
            if (result == null || result.length < 2) {
                logger.info("上传文件失败，文件名：" + fileName + ", results: " + JSONArray.fromObject(result));
                return null;
            } else {
                logger.info("上传文件成功，文件名：" + fileName + ", results: " + JSONArray.fromObject(result));
                return this.getId(result);
            }
        } catch (Exception e) {
            logger.error("上传文件到FastDFS服务器异常，文件名：" + fileName, e);
            return null;
        }

    }

    /**
     * 获取文件ID
     *
     * @return
     * @date 2014年12月2日 上午10:11:55
     */
    private String getId(String[] results) {
        return results[0] + ":" + results[1];
    }


}
