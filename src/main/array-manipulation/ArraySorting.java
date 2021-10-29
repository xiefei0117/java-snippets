//Under implementation

public class ArraySorting {
    private ArraySorting() {}
    
    private static swap(int[] array, int px, int py) {
        assert px >= 0 && py >= 0 && px < array.length && py < array.length;
        if (px == py)
            return;
        array[px] ^= array[py];
        array[py] ^= array[px];
        array[px] ^= array[py]; 
    }
    private static int partition(int[] array, int left, int right, int pt, boolean isIncreasing) {
        int val = array[pt];
        swap(array, pt, right);

        int ans = left;
        for (int i = left; i <= right; i++) {
            // small on the left
            if (isIncreasing) {
                if (array[left] < )
            }
            //large on the left
            else {

            }
        }
        
    }



    public static void main(String[] args) {

    }
}