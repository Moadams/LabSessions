import java.util.Scanner;

public class MatrixMultiplication {

    
    public static void main(String[] args) {
        // scanner declaration
        Scanner sc = new Scanner(System.in);

        //  get the size of the 2D arrays
        System.out.print("Enter the number of rows for array A: ");
        int rowsA = sc.nextInt();

        System.out.print("Enter the number of columns for array A: ");
        int colsA = sc.nextInt();


        System.out.print("Enter the number of rows for array B: ");
        int rowsB = sc.nextInt();

        System.out.print("Enter the number of columns for array B: ");
        int colsB = sc.nextInt();


        // Now check if the number of columns of A is equal to the number of rows of B
        if (colsA != rowsB) {
            System.out.println("The number of columns of A should be equal to the number of rows of B.");
            return;
        }

        // take value inputs
        int[][] matrixA = getMatrix(sc, rowsA, colsA, "A");
        int[][] matrixB = getMatrix(sc, rowsB, colsB, "B");

        int[][] resultMatrix = multiplyMatrices(matrixA, matrixB);

        
        printMatrix(matrixA, "Matrix A");
        printMatrix(matrixB, "Matrix B");
        printMatrix(resultMatrix, "Result Matrix");
    }

    public static int[][] getMatrix(Scanner sc, int rows, int cols, String matrixName) {
        // declare the matrix
        int[][] matrix = new int[rows][cols];

        // take value inputs
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("Enter the value for " + matrixName + "[" + i + "][" + j + "]: ");
                matrix[i][j] = sc.nextInt();
            }
        }
        return matrix;
    }

    public static int[][] multiplyMatrices(int[][] matrixA, int[][] matrixB) {
        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;
        int colsB = matrixB[0].length;

        // the result array will be number of rows for the first array by the number of columns for the second array
        int[][] resultMatrix = new int[rowsA][colsB]; 

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    // multiply the rows of the first array with the columns of the second array
                    resultMatrix[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }

        


        return resultMatrix;
    }

    private static void printMatrix(int[][] matrix, String matrixName) {
        System.out.println(matrixName + ": ");
        for (int i = 0; i < matrix.length; i++) { // Loop through rows
            for (int j = 0; j < matrix[i].length; j++) { // Loop through columns
                System.out.print(matrix[i][j] + " "); // Print each number with a space
            }
            System.out.println(); // Move to a new line after printing a row
        }
        System.out.println("\n");
    }

}
