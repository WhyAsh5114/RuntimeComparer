package SortingAlgorithms;
import java.lang.Thread;

public class SelectionSort extends Thread {
    int[] arr;

    public SelectionSort(int[] arr) {
        this.arr = arr;
    }

    public void run() {
        for (int i = 0; i < arr.length; i++) {
            int min_idx = i;
            for (int j = i + 1; j < arr.length; j++)
                if (arr[j] < arr[min_idx])
                    min_idx = j;

            int temp = arr[min_idx];
            arr[min_idx] = arr[i];
            arr[i] = temp;
        }

    }
}
