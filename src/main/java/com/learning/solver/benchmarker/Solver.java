package com.learning.solver.benchmarker;

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

    public static List<String> librarySort(List<String> array){
        return array.stream().sorted().toList();
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
}
