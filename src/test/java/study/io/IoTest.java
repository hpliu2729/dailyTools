package study.io;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;
import java.util.zip.*;

public class IoTest {
    private static final Logger log = Logger.getLogger(IoTest.class);

    /**
     * 创建删除文件
     */
    @Test
    @Ignore
    public void createFileTest() {

        File file = new File("/Users/xuyexin/abc.txt");
        File file2 = new File("/Users/xuyexin/abc/abc111");
        try {
            file.createNewFile();
            file.delete();
            file2.mkdirs();
            file2.delete();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 获取文件分隔符
     */
    @Test
    @Ignore
    public void filePathTest() {
        log.info(File.separator);
        log.info(File.pathSeparator);
    }

    /**
     * 列出文件名
     */
    @Test
    @Ignore
    public void listFileTest() {
        File file = new File("/Users/xuyexin");
        for (String filename : file.list()) {
            log.info(filename);
        }

        for (File f : file.listFiles()) {
            log.info(f + "--->" + file.isDirectory());
        }
    }

    /**
     * 查找文件函数
     * 
     * @param filename
     * @param path
     */
    private void findFilePath(String filename, String path) {
        if (path.substring(path.lastIndexOf("/") + 1).equals(filename)) {
            log.info("Find result:" + path);
        } else {
            File file = new File(path);
            for (File f : file.listFiles()) {
                if (f.isDirectory()) {
                    findFilePath(filename, f.getPath());
                } else {
                    if (f.getName().equals(filename)) {
                        log.info("Find result:" + f.getPath());
                    }
                }
            }
        }

    }

    @Test
    @Ignore
    public void testFindFile() {
        log.info("Start");
        findFilePath("dfd", "/Users/xuyexin");
        log.info("End");
    }

    /**
     * 字节输出流
     */
    @Test
    @Ignore
    public void testOutputStream() {
        File file = new File("/Users/xuyexin/abc.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        try {
            OutputStream out = new FileOutputStream(file, true);
            String str = "abcd";
            byte[] by = str.getBytes();
            out.write(by);
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 字节流读取
     * 
     */
    @Test
    @Ignore
    public void testInputStream() {
        File file = new File("/Users/xuyexin/abc.txt");
        try {
            InputStream in = new FileInputStream(file);
            byte[] by = new byte[1024];
            int temp;
            int count = 0;
            while ((temp = in.read()) != -1) {
                by[count++] = (byte) temp;
            }
            in.close();
            log.info(new String(by));

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 字符流输出
     */
    @Test
    @Ignore
    public void testFileWriterAndReader() {
        File file = new File("/Users/xuyexin/abc.txt");
        try {
            Writer write = new FileWriter(file, true);
            write.write("xyx");
            write.close();

            Reader read = new FileReader(file);
            char[] c = new char[1024];
            int count = read.read(c);
            read.close();
            log.info(new String(c));
            log.info(count);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 字节流实现文件复制
     */
    @Test
    @Ignore
    public void testCopyFile() {
        File file = new File("/Users/xuyexin/desktop.jpg");
        File copyFile = new File("/Users/xuyexin/abccopy.jpg");
        try {
            
            if (!copyFile.exists()) {
                copyFile.createNewFile();
            }
            
            InputStream in = new FileInputStream(file);
            OutputStream out = new FileOutputStream(copyFile , true);
            
            byte buffer[] = new byte[10];
            while ( (in.read(buffer , 0 , buffer.length) )!= -1){
                out.write(buffer);
            }
            in.close();
            out.close();
            log.info("Copy End");
            
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    /**
     * 进程间管道通信
     */
    @Test
    @Ignore
    public void testPipedIO(){
        PipedSave save = new PipedSave();
        PipedSend send = new PipedSend();
        
        try {
            send.getPout().connect(save.getPin());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        new Thread(send).start();
        new Thread(save).start();
        
    }
    
    /**
     * buffer mark与reset方法
     */
    @Test
    @Ignore
    public void testMarkReset(){
        String str = "abcdefghigklmn";
        BufferedReader br = new BufferedReader(new StringReader(str),100);
        try {
            log.info(br.read());
            br.mark(1);
            log.info(br.read());
            log.info(br.read());
            log.info(br.read());
            log.info(br.read());
            log.info(br.read());
            br.reset();
            log.info(br.read());
            log.info(br.read());
            log.info(br.read());
            log.info(br.read());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * Scanner基础方法
     */
    @Test
    @Ignore
    public void testScanner(){
        File file = new File("/Users/xuyexin/abc.txt");
        try {
            @SuppressWarnings("resource")
            Scanner sc = new Scanner(file);
            String result = "";
            while(sc.hasNext()){
                result += sc.next();
            }
            log.info(result);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * DataInputStream与DataOutputStream
     * @exception java.io.IOException
     */
    @Test
    @Ignore
    public void testDataStream(){
        File file = new File("/Users/xuyexin/abc.txt");
        try {
            DataInputStream din = new DataInputStream(new BufferedInputStream( new FileInputStream(file)));
            String result = din.readUTF()+"";
            log.info(result);
            din.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * 合并流
     */
    @Test
    @Ignore
    public void testSequenceStream(){
        File file1 = new File("/Users/xuyexin/abc.txt");
        File file2 = new File("/Users/xuyexin/abc2.txt");
        File file3 = new File("/Users/xuyexin/abc3.txt");
        
        try {
            FileInputStream in1 = new FileInputStream(file1);
            FileInputStream in2 = new FileInputStream(file2);
            FileOutputStream out1 = new FileOutputStream(file3,true);
            
            SequenceInputStream sis = new SequenceInputStream(in1, in2);
            byte[] buffer = new byte[100];
            @SuppressWarnings("unused")
            int temp =-1 ;
            while((temp = sis.read(buffer, 0, buffer.length)) != -1){
                out1.write(buffer);
            }
            sis.close();
            out1.close();
            in1.close();
            in2.close();
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    /**
     * 重定向流
     */
    @Test
    @Ignore
    public void testReTarget(){
        String str = "hello";
        File file = new File("/Users/xuyexin/abc.txt");
        System.out.print(str);
        try {
            System.setOut(new PrintStream(new FileOutputStream(file)));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.print(str);
    }
    
    /**
     * zip压缩流
     */
    @SuppressWarnings("static-access")
    @Test
    @Ignore
    public void testZipStream(){
        
        File file = new File("/Users/xuyexin/abc");
        File zipFile = new File("/Users/xuyexin/abc.zip");
        InputStream in = null;
        try {
            ZipOutputStream zout  = new ZipOutputStream(new FileOutputStream(zipFile));
            zout.setComment("abc.zip");
            File files[] = file.listFiles();
            for(int i = 0 ; i<files.length  ; i++){
                in = new FileInputStream(files[i]);
                zout.putNextEntry(new ZipEntry(file.getName()+file.separator+files[i].getName()));
                int temp;
                while((temp = in.read()) != -1){
                    zout.write(temp);
                }
                in.close();
            }
            zout.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     *解压zip 
     */
    @Test
    @Ignore
    public void testUnzipStream(){
        File file = new File("/Users/xuyexin/aaa.zip");
        try {
            @SuppressWarnings("resource")
            ZipFile zFile = new ZipFile(file);
            File oFile = null;
            @SuppressWarnings("resource")
            ZipInputStream zIn = new ZipInputStream(new FileInputStream(file));
            ZipEntry zEntry = null;
            InputStream in = null;
            OutputStream out = null;
            
           while((zEntry = zIn.getNextEntry()) != null){
               oFile = new File("/Users/xuyexin/"+zEntry.getName());
               log.info(oFile.getName());
               if(!oFile.exists()){
                   oFile.createNewFile();
               }
               in = zFile.getInputStream(zEntry);
               out = new FileOutputStream(oFile);
               int temp;
               while((temp = in.read()) != -1){
                   out.write(temp);
               }
               in.close();
               out.close();
           }
            
        } catch (ZipException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    
    
    

}
