package benchmarkreport;

/*  Michael Terry
    Professor Potolea
    CMSC 451 6381
    8 February, 2021

    The purpose of the class is to calculate the following:
    -Average quantity of critical operations
    -Average run time of algorithms
    -Coefficient of variance of average quantity of critical operations
    -Coefficient of variance of average run time of algorithms

    Additionally, the GUI subclass will display a graphical user-interface 
    in which the data is displayed in a table.
*/

import java.awt.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Scanner;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class BenchmarkReport {

    public static File file;                                        // File
    public static Object[][] data = new Object[10][5];              // Table data
    public static DecimalFormat df2p = new DecimalFormat("#.##%");  // Formatting
    public static DecimalFormat df2 = new DecimalFormat("#.##");

    // Arrays for each necessary form of data
    static double[] avgCounts;
    static double[] coefCounts;
    static double[] avgTimes;
    static double[] coefTimes;

    public static void main(String[] args) throws IOException {
        
        // Queue user to select a file via JFileChooser, assign to file
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Select File");
        fc.showOpenDialog(null);
        file = fc.getSelectedFile();

        sortFile();                 // Call sortFile()
        GUI gui = new GUI();        // Display GUI

    }

    public static void sortFile() throws IOException {

        int totalRows = getLineCount();     // Determine quantity of lines

        int[] sizes = getSizes(totalRows);  // Determine size of each data set

        avgCounts = getAverageCounts(totalRows);    // Determine average counts

        avgTimes = getAverageTimes(totalRows);      // Determine average times

        coefCounts = getCountCoefs(totalRows);      // Determine count coefficients
            
        coefTimes = getTimeCoefs(totalRows);        // Determine time coefficients

        // Assign each form of data to the appropriate table row/colummn
        
        // Sizes
        for (int i = 0; i < totalRows; i++) {
            data[i][0] = sizes[i];
        }
        
        // Average Counts
        for (int i = 0; i < totalRows; i++) {
            data[i][1] = df2.format(avgCounts[i]);
        }
        
        // Coefficient of Variance of Counts
        for (int i = 0; i < totalRows; i++) {
            data[i][2] = df2p.format(coefCounts[i]);
        }
        
        // Average Times
        for (int i = 0; i < totalRows; i++) {
            data[i][3] = df2.format(avgTimes[i]);
        }
        
        // Coefficient of Variance of Times
        for (int i = 0; i < totalRows; i++) {
            data[i][4] = df2p.format(coefTimes[i]);
        }
    }

    public static int getLineCount() throws FileNotFoundException, IOException {

        /* This method counts the number of lines in the selected file */
        
        BufferedReader reader = new BufferedReader(new FileReader(file));
        int lines = 0;

        while (reader.readLine() != null) {
            lines++;
        }

        reader.close();
        return lines;

    }

    public static int[] getSizes(int rows) throws FileNotFoundException, IOException {

        /* This method iterates through each line in the selected file
           and adds the first value of each line to the array sizesInt[] */
        
        int[] sizesInt = new int[rows];

        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {

                for (int i = 0; i < rows; i++) {
                    sizesInt[i] = Integer.parseInt(scanner.next());
                    scanner.nextLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sizesInt;
    }

    public static double[] getAverageCounts(int rows) throws FileNotFoundException {

        /* This method iterates through each row of the selected file and 
           each value in the observed row. It then assigns even indices
           to the rowCounts array, beginning at 0. The even indices 
           represent the counts of each data set. The sum is 
           calculated within the nested for loops and used to equate the
           average/mean, which is then stored in averageCounts[].*/        
        
        Scanner scanner = new Scanner(file);    // Reads selected file
        double[] rowCounts = new double[50];    // Holds each even index of the line
        String[] words = new String[101];       // Holds each value of the line
        int counter = 0;                        // Counts even iterations
        double sum = 0;                         // Holds sum of counts
        
        double[] averageCounts = new double[10];    // Holds all averages
        
        // Iterate each row, split the row by spaces and assign to words[]
        for(int i = 0; i < rows; i++){
            words = scanner.nextLine().split("\\s+");
            
            // Iterate each value, store even indices in rowCounts[], total sum
            for(int j = 0; j < words.length; j++){
                if(j % 2 != 0){
                    rowCounts[counter] = Double.parseDouble(words[j]);
                    sum = sum + rowCounts[counter];
                }
            }
            
            
            averageCounts[i] = (sum / rowCounts.length); // Calculate average
            sum = 0;                                     // Reset sum
        }
        return averageCounts;
    }

    public static double[] getAverageTimes(int rows) throws FileNotFoundException {

        /* This method iterates through each row of the selected file and 
           each value in the observed row. It then assigns odd indices
           to the rowTimes array, beginning at 1. The odd indices 
           represent the times of each data set. The sum is 
           calculated within the nested for loops and used to equate the
           average/mean, which is then stored in averageTimes[].*/
        
        Scanner scanner = new Scanner(file);    // Reads selected file
        double[] rowTimes = new double[50];     // Holds each odd index of the line
        String[] words = new String[101];       // Holds each value of the line
        int counter = 0;                        // Counts odd iterations
        double sum = 0;                         // Holds sum of counts
        
        double[] averageTimes = new double[10];     // Holds all averages
        
        // Iterate each row, split the row by spaces and assign to words[]
        for(int i = 0; i < rows; i++){
            words = scanner.nextLine().split("\\s+");
            
            // Iterate each value, store odd indices in rowTimes[], total sum
            for(int j = 0; j < words.length; j++){
                if(j != 0 && j % 2 == 0){   // Do not include data set size [0]
                    rowTimes[counter] = Double.parseDouble(words[j]);
                        sum = sum + rowTimes[counter];
                }
            }

            averageTimes[i] = (sum / rowTimes.length);  // Calculate average
            sum = 0;                                    // Reset sum
        }
        return averageTimes;

    }

    public static double[] getCountCoefs(int rows) throws FileNotFoundException {

        /* This method iterates through each row of the selected file and 
           each value in the observed row. It then assigns odd indices
           to the rowTimes array, beginning at 0. The odd indices 
           represent the times of each data set. The rowTimes array is
           passed to the getCofficientOfVariance method.*/
        
        Scanner scanner = new Scanner(file);    // Scanner object to read file
        double[] rowTimes = new double[50];     // Array to hold time values
        String[] words = new String[101];       // Array to hold all values
        int counter = 0;                        // Counts odd indices
        
        double[] coefficientOfVariance = new double[10];

        for (int i = 0; i < rows; i++) {                // Iterate for each row
            words = scanner.nextLine().split("\\s+");   // Split row[i] by space

            for (int j = 0; j < words.length; j++) {                    // Iterate for each value
                if(j % 2 != 0){                                         // Find odd indices
                    rowTimes[counter] = Double.parseDouble(words[j]);   // Assign odd indices
 
                    counter++;                                          // Increment counter
                    
                }
            }
            
            counter = 0;    // Reset counter
            
            // Assign the returned value of the called method to the array[i]
            coefficientOfVariance[i] = getCoefficientOfVariance(rowTimes);

        }
        
        return coefficientOfVariance;
        
    }

    public static double[] getTimeCoefs(int rows) throws FileNotFoundException {

        /* This method iterates through each row of the selected file and 
           each value in the observed row. It then assigns even indices
           to the rowTimes array, beginning at 0. The even indices 
           represent the times of each data set. The rowTimes array is
           passed to the getCofficientOfVariance method.*/
        
        Scanner scanner = new Scanner(file);    // Scanner object to read file
        double[] rowTimes = new double[51];     // Array to hold time values
        String[] words = new String[101];       // Array to hold all values
        int counter = 0;                        // Counts even indices
        
        double[] coefficientOfVariance = new double[10];

        for (int i = 0; i < rows; i++) {                // Iterate for each row
            words = scanner.nextLine().split("\\s+");   // Split row[i] by space

            for (int j = 0; j < words.length; j++) {                            // Iterate for each value
                if(j != 0 && j % 2 == 0){ // Do not include data set size [0]   // Find even indices
                    rowTimes[counter] = Double.parseDouble(words[j]);           // Assign even indices
                    counter++;                                                  // Increment counter
                }
            }
            
            counter = 0;    // Reset counter

            // Assign the returned value of the called method to the array[i]
            coefficientOfVariance[i] = getCoefficientOfVariance(rowTimes);

        }

        return coefficientOfVariance;
    }
    
    public static double getCoefficientOfVariance(double[] times){
        
        /* This method iterates through each value of the passed array 
           and calculates the sum. It calculates the sum of squares,
           the standard deviation and the coefficient of variance.*/
        
        double sum = 0;
        double mean = 0;
        double sumOfSquares = 0;
        double standardDeviation = 0;
        double coefficientVar = 0;
        
        // Calculate sum of passed array
        for(int i = 0; i < times.length; i++){
            sum = sum + times[i];
        }
        
        // Calculate mean of sum
        mean = sum / times.length;
        
        // Calculate sum of squares
        for(int i = 0; i < times.length; i++){
            sumOfSquares = sumOfSquares + ((times[i] - mean) * (times[i] - mean));
        }
        
        // Calculate standard deviation
        standardDeviation = Math.sqrt((sumOfSquares / times.length));
        
        // Calculate coefficient of variance
        coefficientVar = ((standardDeviation / mean));
        
        return coefficientVar;
        
    }

    public static class GUI extends JFrame {

        // Containers
        static JFrame frame = new JFrame("Benchmark Report");
        BorderLayout layout = new BorderLayout();
        String[] columnNames = {"Size", "Avg Count", "Coef Count",
            "Avg Time", "Coef Time"};
        JTable table = new JTable(data, columnNames);

        // Constructor
        GUI() {
            frame.setSize(500, 290);
            frame.setResizable(true);
            frame.setLayout(layout);
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

            frame.add(new JScrollPane(table));

            frame.setVisible(true);

        }

    }

}
