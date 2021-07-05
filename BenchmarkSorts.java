package benchmarksorts;

/*  Michael Terry
    Professor Potolea
    CMSC 451 6381
    8 February, 2021

    The purpose of the class is to randomly generate 50 data sets of 10 different
    sizes, for a total of 500 data sets. The data sets are arranged into arrays,
    passed to two sorting algorithms in the YourSort class, and the returned
    benchmark data is recorded into respective .txt files.
*/

import java.io.*;

public class BenchmarkSorts {

    /* Warm-up phase, allows JIT to complete first pass. */
    static int[] testArray = new int[4000000];

    static {
        for (int i = 0; i < 4000000; i++) {
            testArray[i] = i;
        }
    }

    public static int[] array;

    public static void main(String[] args) throws UnsortedException, IOException {

        /* File writing for recursive output */
        File recursiveFile = new File("recursion.txt");
        FileWriter recursiveFW = new FileWriter(recursiveFile);
        PrintWriter recursivePW = new PrintWriter(recursiveFW);

        /* File writing for iterative output */
        File iterativeFile = new File("iteration.txt");
        FileWriter iterativeFW = new FileWriter(iterativeFile);
        PrintWriter iterativePW = new PrintWriter(iterativeFW);

        /* Iterates over 10,000 and only includes factors of 1000 within the
        nested for-loop, which iterates 50 times */
        for (int i = 100; i < 10000 + 1; i++) {
            if (i % 1000 == 0) {
                iterativePW.print(i + " ");
                recursivePW.print(i + " ");
                for (int j = 0; j < 50; j++) {

                    /* Creates a new array of a length that is a factor of
                    100. Creates a new sort object and sorts the array 
                    iteratively and recursively. Ensures output array is
                    sorted correctly. */
                    array = newArray(i);
                    YourSort sort = new YourSort();
                    sort.isSorted(sort.iterativeSort(array));
                    sort.isSorted(sort.recursiveSort(array, 0, array.length));

                    /* Writes counts and times to .txt files */
                    iterativePW.print(sort.getCount("iterative") + " ");
                    iterativePW.print(sort.getTime("iterative") + " ");
                    recursivePW.print(sort.getCount("recursive") + " ");
                    recursivePW.print(sort.getTime("recursive") + " ");
                }

                /* Next line to .txt files */
                recursivePW.println("\t");
                iterativePW.println("\t");
            }
        }

        /* Close writers, finalize .txt files */
        recursivePW.close();
        iterativePW.close();
    }

    /* Creates a new array of the given size.
       Generates a random integer (1 - 1000) 
       for each index of the array. */
    public static int[] newArray(int size) {
        array = new int[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * 10000);
        }
        return array;
    }
}
