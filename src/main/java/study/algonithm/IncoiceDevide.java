package study.algonithm;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by xuyexin on 16/02/19.
 * 现在手上大概有发票1000张，单张面值都是小于660元的
 * 因为我们公司报餐费需要660元的发票，问怎么搭配凑660
 * 1、使得误差（凑不到整660，多出来的发票面值）总计最小
 * 2、总的660的发票面值次数是最多
 * 问题来源见：https://www.zhihu.com/question/37896761/answer/87104699
 */
public class IncoiceDevide {

	/**
	 * 克隆一个列表
	 *
	 * @param element
	 * @return
	 */
	private static ArrayList<ArrayList<Integer>> Clone(ArrayList<ArrayList<Integer>> element) {

		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < element.size(); i++) {
			ArrayList<Integer> a = new ArrayList<Integer>();
			ArrayList<Integer> ca = (ArrayList<Integer>) element.get(i);
			for (int j = 0; j < ca.size(); j++)
				a.add(new Integer(ca.get(j)));
			result.add(a);
		}
		return result;
	}

	/**
	 * 核心算法
	 *
	 * @param n
	 * @return
	 */
	private static ArrayList<ArrayList<ArrayList<Integer>>> Partition(int n) {
		ArrayList<ArrayList<ArrayList<Integer>>> array = new ArrayList<ArrayList<ArrayList<Integer>>>();
		if (n == 1) {
			ArrayList<ArrayList<Integer>> a = new ArrayList<ArrayList<Integer>>();
			ArrayList<Integer> b = new ArrayList<Integer>();
			b.add(new Integer(1));
			a.add(b);
			array.add(a);
			return array;
		}
		ArrayList<ArrayList<ArrayList<Integer>>> tmpArray = Partition(n - 1);
		for (int i = 0; i < tmpArray.size(); i++) {
			ArrayList<ArrayList<Integer>> element = (ArrayList<ArrayList<Integer>>) tmpArray.get(i);

			ArrayList<ArrayList<Integer>> newelement = null;
			for (int j = 0; j < element.size(); j++) {
				newelement = Clone(element);

				((ArrayList<Integer>) (newelement.get(j))).add(new Integer(n));
				array.add(newelement);
			}
			newelement = Clone(element);
			newelement = (ArrayList<ArrayList<Integer>>) element.clone();
			ArrayList<Integer> app = new ArrayList<Integer>();
			app.add(new Integer(n));
			newelement.add(app);
			array.add(newelement);
		}
		return array;
	}


	/**
	 * 从生成的数组获取最终结果
	 *
	 * @param array
	 */
	private static void getBestArray(ArrayList<ArrayList<ArrayList<Integer>>> array, int originArray[], int
		flagPrice) {
		int resultOkSum = 0;
		int resultWastePrice = 0;
		ArrayList<ArrayList<Integer>> resultArray = (ArrayList<ArrayList<Integer>>) array.get(0);

		for (int i = 0; i < array.size(); i++) {
			ArrayList<ArrayList<Integer>> a = (ArrayList<ArrayList<Integer>>) array.get(i);
			int allGroupOkSum = 0;
			int allGroupWastePrice = 0;
			for (int j = 0; j < a.size(); j++) {
				ArrayList<Integer> b = (ArrayList<Integer>) (a.get(j));
				int oneGroupPrice = 0;
				for (int k = 0; k < b.size(); k++) {
					int oneInvoicePrice = originArray[b.get(k) - 1];
					oneGroupPrice += oneInvoicePrice;
				}
				if (oneGroupPrice >= flagPrice) {
					allGroupWastePrice += oneGroupPrice - flagPrice;
					allGroupOkSum += 1;
				} else {
					allGroupWastePrice += oneGroupPrice;
				}
			}

			if (i == 0 || allGroupOkSum > resultOkSum || (allGroupOkSum == resultOkSum && allGroupWastePrice <
				resultWastePrice)) {
				resultOkSum = allGroupOkSum;
				resultArray = a;
				resultWastePrice = allGroupWastePrice;
			}
		}
		System.out.println("达标组合:" + resultOkSum);
		System.out.println("浪费金额:" + resultWastePrice + "元");
		PrintOneList(resultArray, originArray);
	}

	/**
	 * 输出原始数据
	 *
	 * @param array
	 */
	private static void PrintOriginArray(int[] array) {
		System.out.print("原始数据:");
		for (int i = 0; i < array.length; i++) {
			if (i == array.length - 1)
				System.out.print(array[i]);
			else
				System.out.print(array[i] + ",");
		}
		System.out.println();

	}

	/**
	 * 输出所有列表（1列表包含所有分组，1分组有X个单数据)
	 *
	 * @param array
	 */
	private static void PrintAllList(ArrayList<ArrayList<ArrayList<Integer>>> array) {
		for (int i = 0; i < array.size(); i++) {
			ArrayList<ArrayList<Integer>> a = (ArrayList<ArrayList<Integer>>) array.get(i);
			for (int j = 0; j < a.size(); j++) {
				ArrayList<Integer> b = (ArrayList<Integer>) (a.get(j));
				for (int k = 0; k < b.size(); k++)
					System.out.print(b.get(k));
				System.out.print(" ");
			}
			System.out.println();
		}
	}

	/**
	 * 输出一个列表（1列表包含所有分组，1分组有X个单数据)
	 *
	 * @param array
	 */
	private static void PrintOneList(ArrayList<ArrayList<Integer>> array, int[] originArray) {
		System.out.print("列表为:");
		for (int j = 0; j < array.size(); j++) {
			ArrayList<Integer> b = (ArrayList<Integer>) (array.get(j));
			System.out.print("(");
			for (int k = 0; k < b.size(); k++) {
				if (k == b.size() - 1)
					System.out.print(originArray[b.get(k) - 1]);
				else
					System.out.print(originArray[b.get(k) - 1] + ",");

			}
			System.out.print(") ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int originArray[] = {450, 280, 306, 390, 509, 178, 485, 640, 324, 555};
		int flagPrice = 660;
		int n = originArray.length;
		PrintOriginArray(originArray);
		System.out.println("数据个数:" + n);
		System.out.println("达标金额:" + flagPrice + "元");
		long start = new Date().getTime();
		ArrayList<ArrayList<ArrayList<Integer>>> array = Partition(n);
		long end = new Date().getTime();
		getBestArray(array, originArray, flagPrice);
		System.out.println("计算所用的时间：" + (end - start) + "ms");
	}

}
