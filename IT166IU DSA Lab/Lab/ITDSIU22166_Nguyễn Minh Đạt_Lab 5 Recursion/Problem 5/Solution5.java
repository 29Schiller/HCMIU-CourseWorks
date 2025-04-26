public class Solution5 {
    public static void main(String[] args) {
        int[] array = {3, 12, 9, 5, 7 , 8, 17, 21};
        int n = array.length;
        displayArr(array);
        System.out.println("The sum of all elements in the array:");
        System.out.println(findSum(array, n));
    }

    public static int findSum(int[] nums, int size) {
        if (size == 0) {
            return 0;
        } else {
            return nums[size - 1] + findSum(nums, size - 1);
        }
    }
    public static void displayArr(int[] arr) {
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}