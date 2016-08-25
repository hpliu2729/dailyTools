package study.datastructer;

/**
 * 动态规划，算出用多少硬币能够满足给出的金额
 * Created by xuyexin on 16/3/16.
 */
public class CoinDevide {

	public static Integer coin(int value) {
		//货币种类
		int[] v = new int[]{100, 2, 5, 10, 20, 50, 1};
		//金额数对应的最小张数
		int[] min = new int[value + 1];
		min[0] = 0;
		for (int i = 1; i < min.length; i++) {
			for (int j = 0; j < v.length; j++) {
				// 值大于货币面值
				if (i >= v[j]) {
					if (min[i] == 0) {
						min[i] = min[i - v[j]] + 1;
					} else
						min[i] = Math.min(min[i], min[i - v[j]] + 1);
				}
			}
		}
		return min[value];
	}


	public static void main(String arg0[]) {
		System.out.print(coin(108));
	}
}
