package benchmarksorts;

/*  Michael Terry
    Professor Potolea
    CMSC 451 6381
    8 February, 2021

    The purpose of the class is to perform both recursive and iterative sorting
    of the data sets generated from BenchmarkSorts.java. After the sets are 
    sorted, the benchmark information such as critical operation counts and 
    calculated time, in nanoseconds, and sorted validity is returned.
*/

public class YourSort implements SortInterface {

    /* Warm-up phase, allows JIT to complete first pass. */
    static int[] testArray = new int[4000000];

    static {
        for (int i = 0; i < 4000000; i++) {
            testArray[i] = i;
        }
    }

    // Holds the count of critical operations of the respective function type
    int recursiveCount = 0;
    int iterativeCount = 0;

    // Holds time values for recursive selection sort
    long recursiveStartTime = 0;
    long recursiveEndTime = 0;
    long recursiveTotalTime = 0;

    // Holds time values for iterative selection sort
    long iterativeStartTime = 0;
    long iterativeEndTime = 0;
    long iterativeTotalTime = 0;

    /* Selection sort method that utilizes recursion */
    @Override
    public int[] recursiveSort(int[] list, int i, int n) {

        // Records time at initial index
        if(i == 0){
            recursiveStartTime = System.nanoTime();
        }

        int min = i;
        
        // Compares list[i] to list[i + 1]. Smallest value becomes min.
        for (int j = i + 1; j < n; j++) {
            if (list[j] < list[min]) {
                min = j;
            }
            recursiveCount++;
        }

        // Swaps index values
        int temp = list[min];
        list[min] = list[i];
        list[i] = temp;

        // Call recursiveSort if there are more values to be sorted
        if (i + 1 < n) {
            recursiveSort(list, i + 1, n);
        } else {
            return list;
        }

        // Record end time, compute total time in nanoseconds
        recursiveEndTime = System.nanoTime();
        recursiveTotalTime = recursiveEndTime - recursiveStartTime;
        return list;
    }

    /* Selection sort method that utilizes iteration */
    @Override
    public int[] iterativeSort(int[] list) {

        // Records time at start of function
        iterativeStartTime = System.nanoTime();

        int n = list.length;

        // Determine the initial index of comparison
        for (int i = 0; i < n - 1; i++) {
            int min_idx = i;

            // Compares list[i] to list[i + 1]. Smallest value becomes min_idx
            for (int j = i + 1; j < n; j++) {
                if (list[j] < list[min_idx]) {
                    min_idx = j;
                }
                iterativeCount++;
            }
            
            // Swap index values
            int temp = list[min_idx];
            list[min_idx] = list[i];
            list[i] = temp;
        }

        // Record end time, compute total time in nanoseconds
        iterativeEndTime = System.nanoTime();
        iterativeTotalTime = iterativeEndTime - iterativeStartTime;
        return list;
    }

    /* Return critical operation counts dependent on type passed */
    @Override
    public int getCount(String type) {

        if ("recursive".equals(type)) {
            int output = recursiveCount;
            recursiveCount = 0;
            return output;
        } else {
            int output = iterativeCount;
            iterativeCount = 0;
            return output;
        }

    }

    /* Return total times dependent on type passed */
    @Override
    public long getTime(String type) {

        if ("recursive".equals(type)) {
            long output = recursiveTotalTime;
            recursiveTotalTime = 0;
            return output;
        } else {
            long output = iterativeTotalTime;
            iterativeTotalTime = 0;
            return output;
        }
    }

    /* Function to throw exception if array is unsorted */
    @Override
    public boolean isSorted(int[] list) throws UnsortedException {
        for (int i = 0; i < list.length - 1; i++) {
            if (list[i] > list[i + 1]) {
                throw new UnsortedException("Error. Unsorted array.");
            }
        }
        return true;
    }

}
