//Under implementation
import java.util.Random;

public class ArraySorting<T extends Comparable<T>> {
    private ArraySorting() {}
    
    private static void swap(T[] array, int px, int py) {
        assert px >= 0 && py >= 0 && px < array.length && py < array.length;
        T temp = array[px];
        array[px] = array[py];
        array[py] = array[px];
        array[px] = array[py];
    }
    private static int partition(T[] array, int left, int right, int pt) {
        T val = array[pt];
        swap(array, pt, right);

        int ans = left;
        for (int i = left; i <= right; i++) {
            if (array[i].compareTo(val)) {
                sway(array, ans, i);
                ans++;
            }
        }
        swap(array, ans, right);
        return ans;
    }

    public static int quickSelect(T[] array, int left, int right, int k_smallest) {
        if (left == right)
            return array[left];

        Random random = new Random();
        int pp = left + random.nextInt(right - left);
        pp = partition(array, left, right, pp);
        if (k_smallest == pp)
            return array[pp];
        else if (k_smallest < pp) {
            return quickSelect(array, left, pp - 1, k_smallest);
        }
        else {
            return quickSelect(array, pp + 1, right, k_smallest);
        }
    }



    public static void main(String[] args) {
        Integer[] arr = {5, 3, 6, 8, 1, 7, 9};
        return ArraySorting<Integer>.quickSelect(arr, 0, arr.length - 1, 3);
    }
}