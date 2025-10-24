package benchmark;

import java.util.Arrays;
import java.util.Random;

public class MergeSort {

    // --------------------- MERGE SORT ---------------------
    public static void mergeSort(int[] arr) {
        if (arr.length <= 1) return;
        int mid = arr.length / 2;
        int[] left = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, arr.length);
        mergeSort(left);
        mergeSort(right);
        merge(arr, left, right);
    }

    private static void merge(int[] arr, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                arr[k++] = left[i++];
            } else {
                arr[k++] = right[j++];
            }
        }
        while (i < left.length) arr[k++] = left[i++];
        while (j < right.length) arr[k++] = right[j++];
    }

    // --------------------- QUICK SORT ---------------------
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i]; arr[i] = arr[j]; arr[j] = temp;
            }
        }
        int temp = arr[i + 1]; arr[i + 1] = arr[high]; arr[high] = temp;
        return i + 1;
    }

    // --------------------- SHELL SORT ---------------------
    public static void shellSort(int[] arr) {
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                int temp = arr[i];
                int j = i;
                while (j >= gap && arr[j - gap] > temp) {
                    arr[j] = arr[j - gap];
                    j -= gap;
                }
                arr[j] = temp;
            }
        }
    }

    // --------------------- INSERTION SORT ---------------------
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    // --------------------- SELECTION SORT ---------------------
    public static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) minIndex = j;
            }
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }

    // --------------------- BUBBLE SORT ---------------------
    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // --------------------- BENCHMARK ---------------------
    public static void main(String[] args) {
        int[] sizes = {1000, 5000, 10000, 50000, 100000};
        Random rand = new Random();

        System.out.printf("%-12s %-10s %-10s %-10s %-10s %-10s %-10s%n",
                "Array Size", "Bubble", "Selection", "Insertion", "Shell", "Quick", "Merge");
        System.out.println("-------------------------------------------------------------------------------");

        for (int size : sizes) {
            int[] base = rand.ints(size, 0, 100000).toArray();

            System.out.printf("%-12d", size);

            System.out.printf("%-10d", measure(() -> bubbleSort(Arrays.copyOf(base, base.length))));
            System.out.printf("%-10d", measure(() -> selectionSort(Arrays.copyOf(base, base.length))));
            System.out.printf("%-10d", measure(() -> insertionSort(Arrays.copyOf(base, base.length))));
            System.out.printf("%-10d", measure(() -> shellSort(Arrays.copyOf(base, base.length))));
            System.out.printf("%-10d", measure(() -> quickSort(Arrays.copyOf(base, base.length), 0, base.length - 1)));
            System.out.printf("%-10d%n", measure(() -> mergeSort(Arrays.copyOf(base, base.length))));
        }
    }

    // --------------------- TIMER ---------------------
    private static long measure(Runnable sortMethod) {
        long start = System.currentTimeMillis();
        sortMethod.run();
        return System.currentTimeMillis() - start;
    }
}
