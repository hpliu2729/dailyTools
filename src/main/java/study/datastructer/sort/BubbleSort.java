package study.datastructer.sort;

public class BubbleSort {

	/**
	 * 冒泡（int）
	 *
	 * @param nums
	 * @return
	 */
	public static int[] sort(int[] nums) {
		for (int i = 0; i < nums.length - 1; i++) {
			for (int j = 0; j < nums.length - i - 1; j++) {
				if (nums[j] > nums[j + 1]) {
					int temp = nums[j];
					nums[j] = nums[j + 1];
					nums[j + 1] = temp;
				}
			}
		}
		return nums;
	}


	public static void main(String arg0[]) {
		int[] array = {4, 6, 7, 8, 2, 4, 6, 78, 9};
		sort(array);
		for (int i : array) {
			System.out.print(i + ",");
		}
	}

}
