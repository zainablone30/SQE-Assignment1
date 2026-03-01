package search;

/**
 * Binary Search Algorithm
 * Works on SORTED arrays only.
 * Time Complexity : O(log n)
 * Space Complexity: O(1)
 */
public class BinarySearch {

    /**
     * Search for target in a sorted integer array.
     * @param arr    sorted integer array
     * @param target value to find
     * @return index of target if found, -1 otherwise
     */
    public int search(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        int low  = 0;
        int high = arr.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }
}