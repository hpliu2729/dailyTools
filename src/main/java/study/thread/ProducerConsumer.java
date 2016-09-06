package study.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by xuyexin on 16/9/6.
 */
public class ProducerConsumer {
	int produceSpeed;
	int consumeSpeed;

	/**
	 * 构造
	 *
	 * @param produceSpeed
	 * @param consumeSpeed
	 */
	public ProducerConsumer(int produceSpeed, int consumeSpeed) {
		this.produceSpeed = produceSpeed;
		this.consumeSpeed = consumeSpeed;
	}


	/**
	 * 消费者
	 */
	class Consumer implements Runnable {
		private String name;
		private Storage s = null;

		public Consumer(String name, Storage s) {
			this.name = name;
			this.s = s;
		}

		public void run() {
			try {
				while (true) {
					if (s.count() == 0) {
						System.out.println(name + "没消费，库存没了");
					}
					Product product = s.pop();
					System.out.println(name + "消费(" + product.toString() + ")" + "库存:" + s.count());
					Thread.sleep(consumeSpeed);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * 生产者
	 */
	class Producer implements Runnable {
		private String name;
		private Storage s = null;

		public Producer(String name, Storage s) {
			this.name = name;
			this.s = s;
		}

		public void run() {
			try {
				while (true) {
					Product product = new Product((int) (Math.random() * 10000)); // 产生0~9999随机整数
					s.push(product);
					System.out.println(name + "生产(" + product.toString() + ") 库存:" + s.count());
					Thread.sleep(produceSpeed);
				}
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

		}
	}


	/**
	 * 仓库，用来存放产品
	 */
	public class Storage {
		BlockingQueue<Product> queues = new LinkedBlockingQueue<Product>(10);

		/**
		 * 入库
		 */
		public void push(Product p) throws InterruptedException {
			queues.put(p);
		}

		/**
		 * 出库
		 */
		public Product pop() throws InterruptedException {
			return queues.take();
		}

		/**
		 * 查看库存
		 */
		public int count() {
			return queues.size();
		}
	}

	/**
	 * 产品
	 */
	public class Product {
		private int id;

		public Product(int id) {
			this.id = id;
		}

		public String toString() {
			return "一加3，" + "ID:" + this.id;
		}
	}

	/**
	 * 主函数
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		ProducerConsumer pc = new ProducerConsumer(1000, 2000);
		Storage storage = pc.new Storage();
		ExecutorService service = Executors.newCachedThreadPool();
		Producer p = pc.new Producer("张三", storage);
		Producer p2 = pc.new Producer("李四", storage);
		Consumer c = pc.new Consumer("王五", storage);
		Consumer c2 = pc.new Consumer("老孙", storage);
		Consumer c3 = pc.new Consumer("老高", storage);
		service.submit(p);
		service.submit(p2);
		service.submit(c);
		service.submit(c2);
		service.submit(c3);
	}


}
