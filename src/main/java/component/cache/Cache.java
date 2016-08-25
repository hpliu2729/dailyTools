package component.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 利用delayQueue实现的一个延时缓存，即过给定时间之后移除缓存
 * @param <K>
 * @param <V>
 */
public class Cache<K, V> {

	public ConcurrentHashMap<K, V> map = new ConcurrentHashMap<K, V>();
	public DelayQueue<DelayedItem<K>> queue = new DelayQueue<DelayedItem<K>>();
	public Cache() {
		Thread t = new Thread() {
			@Override
			public void run() {
				dameonCheckOverdueKey();
			}
		};
		t.setDaemon(true);
		t.start();
	}

	//更新delayqueue，先删除原始值然后再进行put
	public void put(K k, V v, long liveTime) {
		V v2 = map.put(k, v);
		DelayedItem<K> tmpItem = new DelayedItem<K>(k, liveTime);
		if (v2 != null) {
			queue.remove(tmpItem);
		}
		queue.put(tmpItem);
	}


	//检查到期的Cache数据，并清除
	public void dameonCheckOverdueKey() {
		while (true) {
			DelayedItem<K> delayedItem = queue.poll();
			if (delayedItem != null) {
				map.remove(delayedItem.getT());
				System.out.println(System.nanoTime() + " remove "
					+ delayedItem.getT() + " from cache");
			}
			try {
				System.out.println("live");
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}

class DelayedItem<T> implements Delayed {

	private T t;
	private long liveTime;
	private long removeTime;

	public DelayedItem(T t, long liveTime) {
		this.setT(t);
		this.liveTime = liveTime;
		this.removeTime = TimeUnit.NANOSECONDS.convert(liveTime,
			TimeUnit.SECONDS) + System.nanoTime();
	}

	@SuppressWarnings("unchecked")
	public int compareTo(Delayed o) {
		if (o == null)
			return 1;
		if (o == this)
			return 0;
		if (o instanceof DelayedItem) {
			DelayedItem<T> tmpDelayedItem = (DelayedItem<T>) o;
			if (liveTime > tmpDelayedItem.liveTime) {
				return 1;
			} else if (liveTime == tmpDelayedItem.liveTime) {
				return 0;
			} else {
				return -1;
			}
		}
		long diff = getDelay(TimeUnit.NANOSECONDS)
			- o.getDelay(TimeUnit.NANOSECONDS);
		return diff > 0 ? 1 : diff == 0 ? 0 : -1;
	}

	public long getDelay(TimeUnit unit) {
		return unit.convert(removeTime - System.nanoTime(), unit);
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	@Override
	public int hashCode() {
		return t.hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof DelayedItem) {
			return object.hashCode() == hashCode() ? true : false;
		}
		return false;
	}

}