package study.datastructer;

import java.util.ArrayList;
import java.util.Date;

/**
 * 集合划分，列出所有的组合，暴力计算
 * Created by xuyexin on 15/10/29.
 *
 */
public class SetDevide {


	/**
	 * 克隆一个列表
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
	 * 输出结果
	 * @param array
	 */
	private static void PrintResult(ArrayList<ArrayList<ArrayList<Integer>>> array) {
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

	public static void main(String[] args) {
		int n = 5;
		long start = new Date().getTime();
		ArrayList<ArrayList<ArrayList<Integer>>> array = Partition(n);
		long end = new Date().getTime();
		System.out.print("计算所用的时间：" +(end-start));
		PrintResult(array);
	}

}
