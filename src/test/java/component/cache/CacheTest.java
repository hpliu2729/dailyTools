package component.cache;

import org.junit.Test;


public class CacheTest {


	@Test
	public void testManyCache() {
		int cacheNumber = 10;
		int liveTime = 0;
		Cache<String, Integer> cache = new Cache<String, Integer>();

		for (int i = 0; i < cacheNumber; i++) {
			liveTime = 10;
			System.out.println(i + "  " + liveTime);
			cache.put(i + "", i, liveTime);
		}

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
