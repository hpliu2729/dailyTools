package study.nio;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author XuYexin
 * @Description :
 * @date 2016/8/5
 */
public class NioFileReader {
	private ByteBuffer readBuffer;
	private FileChannel monitorFileChannel = null;
	private static ScheduledExecutorService executor;
	public static final int BUFFER_SIZE = 1000;
	private ByteBuffer outBuffer;
	private static int lastLinePosition = 0;
	private static int lastReadPosition = 0;
	private FileMonitorThread fileMonitorThread;

	public NioFileReader() {
		this.readBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
		this.outBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
		this.executor = Executors.newSingleThreadScheduledExecutor();
		this.fileMonitorThread = new FileMonitorThread();
		try {
			this.monitorFileChannel = new RandomAccessFile(new File("D:\\in.log"), "r").getChannel();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readFile() throws Exception {
		executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleWithFixedDelay(fileMonitorThread, 500, 1000, TimeUnit.MILLISECONDS);
		Thread.sleep(10000000);
		System.out.print("end");

	}


	/**
	 * 内部执行类
	 */
	class FileMonitorThread implements Runnable {

		@Override
		public void run() {
			int bytesRead = 0;
			try {
				bytesRead = monitorFileChannel.read(readBuffer);
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println("bytesRead : " + bytesRead);
			if (bytesRead == -1) {
				return;
			}

			int bufferGetCount = 0;
			readBuffer.flip();
			while (readBuffer.hasRemaining()) {
				byte tempByte = readBuffer.get();
				bufferGetCount++;
				if (tempByte == (byte) '\n') {
					lastLinePosition = bufferGetCount - 1;
				}
			}
			System.out.println("lastLinePosition:" + lastLinePosition);

			if (lastLinePosition != 0) {
				printByte(lastLinePosition);
				lastReadPosition += lastLinePosition;

			} else {
				System.out.println("没有完整的行");
				printByte(readBuffer.position());
			}

			try {
				monitorFileChannel.position(lastReadPosition);
				lastLinePosition = 0;
				readBuffer.clear();
				outBuffer.clear();
				System.out.println("文件指针：" + lastReadPosition);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		/**
		 * 输出
		 *
		 * @param end
		 */
		public void printByte(int end) {
			System.out.println("End:" + end);
			readBuffer.flip();
			outBuffer.clear();
			for (int i = 0; i < end; i++) {
				byte temp1 = readBuffer.get();
				outBuffer.put(temp1);
			}
			System.out.println("outbuffer position:" + outBuffer.position());
			outBuffer.flip();
			byte[] tempBytes = outBuffer.array();
			System.out.println("Bytes 长度:" + tempBytes.length);
			System.out.println(Charset.forName("utf-8").decode(outBuffer));
		}
	}
}
