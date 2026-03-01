package search;

/**
 * Linear Search Algorithm
 * Works on ANY array — sorted or unsorted.
 * Scans each element one by one from left to right.
 *
 * Time Complexity : O(n)
 * Space Complexity: O(1)
 */
public class LinearSearch {

    /**
     * Search for target in an integer array.
     * @param arr    integer array (sorted or unsorted)
     * @param target value to find
     * @return index of target if found, -1 otherwise
     */
    public int search(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Search for target in a String array.
     * @param arr    String array
     * @param target String to find
     * @return index of target if found, -1 otherwise
     */
    public int searchString(String[] arr, String target) {
        if (arr == null || arr.length == 0 || target == null) {
            return -1;
        }

        for (int i = 0; i < arr.length; i++) {
            if (target.equals(arr[i])) {
                return i;
            }
        }

        return -1;
    }
}