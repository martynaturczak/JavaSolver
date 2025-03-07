package com.learning.solver.benchmarker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solver {
    public static List<String> insertionSort(List<String> array) {
        int length = array.size();
        for (int i = 1; i < length; i++) {
            String key = array.get(i);
            int j = i - 1;
            while (j >= 0 && array.get(j).compareTo(key) > 0) {
                array.set(j + 1, array.get(j));
                j = j - 1;
            }
            array.set(j + 1, key);
        }
        return array;
    }

    public static List<String> quickSort(List<String> array){
        return quickSortImplementation(array, 0, array.size() - 1);
    }

    private static List<String> quickSortImplementation(List<String> array, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(array, begin, end);

            quickSortImplementation(array, begin, partitionIndex - 1);
            quickSortImplementation(array, partitionIndex + 1, end);
        }
        return array;
    }

    private static int partition(List<String> array, int begin, int end) {
        String pivot = array.get(end);
        int i = begin - 1;
        for (int j = begin; j < end; j++) {
            if (array.get(j).compareTo(pivot) <= 0) {
                i++;
                Collections.swap(array, i, j);
            }
        }
        Collections.swap(array, i + 1,  end);
        return i + 1;
    }
    public static List<String> mergeSort(List<String> list) {
        if (list.size() < 2) {
            return list;
        }

        int mid = list.size() / 2;
        List<String> left = new ArrayList<>(list.subList(0, mid));
        List<String> right = new ArrayList<>(list.subList(mid, list.size()));

        mergeSort(left);
        mergeSort(right);
        merge(list, left, right);
        return list;
    }

    private static void merge(List<String> list, List<String> left, List<String> right) {
        int i = 0, j = 0, k = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i).compareTo(right.get(j)) <= 0) {
                list.set(k++, left.get(i++));
            } else {
                list.set(k++, right.get(j++));
            }
        }

        while (i < left.size()) {
            list.set(k++, left.get(i++));
        }

        while (j < right.size()) {
            list.set(k++, right.get(j++));
        }
    }

    public static List<String> heapSort(List<String> list) {
        int n = list.size();

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(list, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            Collections.swap(list, 0, i);
            heapify(list, i, 0);
        }
        return list;
    }

    private static void heapify(List<String> list, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && list.get(left).compareTo(list.get(largest)) > 0) {
            largest = left;
        }

        if (right < n && list.get(right).compareTo(list.get(largest)) > 0) {
            largest = right;
        }

        if (largest != i) {
            Collections.swap(list, i, largest);
            heapify(list, n, largest);
        }
    }
}
