package com.bobocode;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class DemoApp {
    public static void main(String[] args) {
//        int[] array = {5, 3, 8, 1, 2, 7, 4, 6};
//        int[] array = {1, 19, 15, 6, 12, 2, 4};
//        int[] array = {1248647395, 153521005, 1679144537, -285469190, -1324688946, -1369290507, -457677298, 835897849, -1835671867, -1878948294};
         int[] array = IntStream.range(0, 50_000).map(i -> new Random().nextInt()).toArray();
//        printArray(array);
        int[] mySortArray = array.clone();
//        calculateSortDuration(mySortArray, DemoApp::myInsertionSort);
//        calculateSortDuration(mySortArray, DemoApp::myBubbleSort);
        calculateSortDuration(mySortArray, 0, array.length - 1, DemoApp::myMergeSort);
        int[] sortArray = array.clone();
        // insertionSort(array);
//        calculateSortDuration(sortArray, DemoApp::insertionSort);
        // selectionSort(array);
        // bubbleSort(array);
//        calculateSortDuration(sortArray, DemoApp::bubbleSort);
        // quickSort(array, 0, array.length - 1);
//        calculateSortDuration(sortArray, 0, array.length - 1, DemoApp::quickSort);
        // mergeSort(array, 0, array.length - 1);
//        calculateSortDuration(sortArray, 0, array.length - 1, DemoApp::mergeSort);
//        printArray(sortArray);
//        printArray(myMergeSortArray);
        System.out.println(Arrays.equals(sortArray, mySortArray));
    }

    private static void calculateSortDuration(int[] array, Consumer<int[]> sortFunction) {
        long start = System.currentTimeMillis();
        sortArray(array, sortFunction);
        long end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start) + " ms");
    }

    private static void calculateSortDuration(int[] array, int start, int end, TriConsumer<int[], Integer, Integer> sortFunction) {
        long startTimer = System.currentTimeMillis();
        sortArray(array, start, end, sortFunction);
        long endTimer = System.currentTimeMillis();
        System.out.println("Time: " + (endTimer - startTimer) + " ms");
    }

    private static void printArray(int[] array) {
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    private static void sortArray(int[] array, Consumer<int[]> sortFunction){
        sortFunction.accept(array);
    }

    private static void sortArray(int[] array, int start, int end, TriConsumer<int[], Integer, Integer> sortFunction){
        sortFunction.accept(array, start, end);
    }

    private static void myInsertionSort(int[] array) {
        int arraySize = array.length;
        for (int i = 1; i < arraySize; i++) {
            int current = array[i];
            int j = i - 1;
            while (j >= 0 && current < array[j]) {
                array[j + 1] = array[j];
                array[j] = current;
                j --;
            }
        }
    }

    private static void myBubbleSort(int[] array) {
        int arraySize = array.length;
        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int current = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = current;
//                    array[j + 1] = array[j] + array[j + 1];
//                    array[j] = array[j + 1] - array[j];
//                    array[j + 1] = array[j + 1] - array[j];
                }
            }
        }
    }

    private static void myMergeSort(int[] array, int start, int end) {
        int[] auxArray = new int[array.length];
        divideArray(array, auxArray, start, end);
    }

    private static void divideArray(int[] array, int[] auxArray, int left, int right) {
        if (right <= left) {
            return;
        }
        int middle = (right + left) / 2;
//        System.out.println(Arrays.toString(new int[]{start, end, middle}));
        divideArray(array, auxArray, left, middle);
        divideArray(array, auxArray, middle + 1, right);
//        System.out.println(Arrays.toString(array));
        mergeArray(array, auxArray, left, middle, right);
    }

    private static void mergeArray(int[] array, int[] auxArray, int left, int middle, int right) {
//        System.out.println(Arrays.toString(new int[]{left, middle, right}));
        int i = 0;
        int leftIterator = left;
        int rightIterator = middle + 1;
//        System.out.print("Entering array: ");
//        System.out.println(Arrays.toString(auxArray));
        while (leftIterator <= middle && rightIterator <= right) {
//            System.out.println(Arrays.toString(new int[]{leftIterator, array[leftIterator], rightIterator, array[rightIterator]}));
            if (array[leftIterator] > array[rightIterator]) {
                auxArray[i++] = array[rightIterator++];
            } else {
                auxArray[i++] = array[leftIterator++];
            }
        }
        while (leftIterator <= middle) {
            auxArray[i++] = array[leftIterator++];
        }
        while (rightIterator <= right) {
            auxArray[i++] = array[rightIterator++];
        }
//        System.out.print("Resulting array: ");
//        System.out.println(Arrays.toString(result));
        for (int j = left, k = 0; j <= right; j ++, k++) {
            array[j] = auxArray[k];
        }
//        System.out.println(Arrays.toString(array));
    }

    private static void heapify(int[] array, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < n && array[left] > array[largest]) {
            largest = left;
        }
        if (right < n && array[right] > array[largest]) {
            largest = right;
        }
        if (largest != i) {
            int temp = array[i];
            array[i] = array[largest];
            array[largest] = temp;
            heapify(array, n, largest);
        }
    }
}
