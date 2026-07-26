public class LinearSearch {
     public static int search(int[] arr, int x)
    {
        // Iterate over the array in order to
        // find the key x
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == x)
                return i;
        }
        return -1;
    }
}
