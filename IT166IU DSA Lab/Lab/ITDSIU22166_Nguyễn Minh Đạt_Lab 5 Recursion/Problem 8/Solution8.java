import java.util.ArrayList;

public class Solution8 {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4};
        System.out.println("The array:");
        displayArr(nums);
        System.out.println("The subsets:");
        ArrayList<ArrayList<Integer>> res = subsets(nums);

        for (ArrayList<Integer> subset : res) {
            for (int num : subset) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }
    public static void subsetRecur(int i, int[] arr, ArrayList<ArrayList<Integer>> res, ArrayList<Integer> subset) {
        // add subset at end of array
        if (i == arr.length) {
            res.add(new ArrayList<>(subset));
            return;
        }

        // include the current value and
        // recursively find all subsets
        subset.add(arr[i]);
        subsetRecur(i + 1, arr, res, subset);

        // exclude the current value and
        // recursively find all subsets
        subset.remove(subset.size() - 1);
        subsetRecur(i + 1, arr, res, subset);
    }

    public static ArrayList<ArrayList<Integer>> subsets(int[] arr) {
        ArrayList<Integer> subset = new ArrayList<>();
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        subsetRecur(0, arr, res, subset);
        return res;
    }

    public static void displayArr(int[] arr) {
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println("");
    }
}