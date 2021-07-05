package benchmarksorts;

/*  Michael Terry
    Professor Potolea
    CMSC 451 6381
    8 February, 2021

    The purpose of this interface is to serve as a template for the YourSort
    class. The default functions are defined below.
*/

public interface SortInterface {
    
    public int[] recursiveSort(int[] list, int i, int n);
    public int[] iterativeSort(int[] list);
    public boolean isSorted(int[] list) throws UnsortedException;
    public int getCount(String type);
    public long getTime(String type);
        
}


