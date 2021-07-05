# Selection-Sort-Benchmark

The purpose of this project is to analyze the efficiency of performing the selection-sort method upon extremely large arrays. 



******************** #BenchmarkSorts.jar ********************

BenchmarkSorts.jar is an executable java file that generates fifty data sets of ten different sizes, incrementing by one-thousand. 
Each data set is an iterative selection-sort method and a recursive selection-sort method. Critical operations and runtime are then
written to iteration.txt and recursion.txt for each respective method.

******************** #BenchmarkReport.jar ********************

BenchmarkReport.jar is an executable java file that utilizes the data stored from iteration.txt and recursion.txt. The following
calculations are made and displayed within a GUI table. 

•Array Size

•Average Critical Operations

•Coefficient of Variance of Critical Operations

•Average runtime (ns)

•Coefficient of Variance of Runtime

******************** iteration.txt ********************

iteration.txt is a text file generated by BenchmarkSorts.jar to store the results of the iterative selection-sort method. It is not
necessary to create a file manually. 

******************** recursion.txt ********************

recursion.txt is a text file generated by BenchmarkSorts.jar to store the results of the recursive selection-sort method. It is not
necessary to create a file manually. 

![image](https://user-images.githubusercontent.com/78992098/124406206-ac019e80-dd0e-11eb-9ad9-ea922103e32d.png)
