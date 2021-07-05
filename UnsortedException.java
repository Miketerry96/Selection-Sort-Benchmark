package benchmarksorts;

/*  Michael Terry
    Professor Potolea
    CMSC 451 6381
    8 February, 2021

    The purpose of this exception is to return a simple error message when
    thrown. The message is defined within the isSorted method which throws this
    exception.
*/

public class UnsortedException extends Exception {

    public UnsortedException() {    // Default constructor
    }

    public UnsortedException(String msg) {  // Error message 
        super(msg);
    }
}
