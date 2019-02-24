/**
 * Created by highness on 2019/2/23 0023.
 */
public class ErFen {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        int index = get(arr, 0, arr.length - 1, 7);
        System.out.println(index);
    }

    public static int get(int[] arr, int low, int high, int num) {
        if (low > high) return -1;
        int mid = (low + high) / 2;
        if (num < arr[mid]) {
            return get(arr, low, mid - 1, num);
        }
        if (num > arr[mid]) {
            return get(arr, mid + 1, high, num);
        }
        return mid;
    }
}
