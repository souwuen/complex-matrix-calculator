import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    private static Matrix matrixA;
    private static Matrix matrixB;

    public static void main(String[] args) {
        boolean running = true;

        System.out.println("=== Complex Matrix Calculator ===");

        while (running) {
            printMenu();

            int choice = readInt("Choose an operation: ");

            try {
                switch (choice) {
                    case 1:
                        matrixA = readMatrix("A");
                        break;

                    case 2:
                        matrixB = readMatrix("B");
                        break;

                    case 3:
                        displayMatrices();
                        break;

                    case 4:
                        requireMatrices();
                        System.out.println("A + B:");
                        System.out.println(matrixA.add(matrixB));
                        break;

                    case 5:
                        requireMatrices();
                        System.out.println("A * B:");
                        System.out.println(matrixA.multiply(matrixB));
                        break;

                    case 6:
                        requireMatrices();
                        System.out.println("A / B:");
                        System.out.println(matrixA.divide(matrixB));
                        break;

                    case 7:
                        Matrix matrix = chooseMatrix();
                        System.out.println("Transposed matrix:");
                        System.out.println(matrix.transpose());
                        break;

                    case 8:
                        matrix = chooseMatrix();
                        System.out.println("Determinant:");
                        System.out.println(matrix.determinant());
                        break;

                    case 0:
                        running = false;
                        System.out.println("Goodbye!");
                        break;

                    default:
                        System.out.println("Unknown operation. Try again.");
                }
            } catch (IllegalArgumentException | ArithmeticException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("1. Create matrix A");
        System.out.println("2. Create matrix B");
        System.out.println("3. Display matrices");
        System.out.println("4. Add matrices (A + B)");
        System.out.println("5. Multiply matrices (A * B)");
        System.out.println("6. Divide matrices (A / B)");
        System.out.println("7. Transpose a matrix");
        System.out.println("8. Calculate determinant");
        System.out.println("0. Exit");
    }

    private static int readInt(String message) {
        while (true) {
            System.out.print(message);

            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer.");
            }
        }
    }

    private static double readDouble(String message) {
        while (true) {
            System.out.print(message);

            try {
                return Double.parseDouble(
                    scanner.nextLine().trim().replace(',', '.')
                );
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static Matrix readMatrix(String name) {
        System.out.println("Creating matrix " + name);

        int rows = readPositiveInt("Number of rows: ");
        int cols = readPositiveInt("Number of columns: ");

        Matrix matrix = new Matrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.println(
                    "Element [" + (i + 1) + "][" + (j + 1) + "]"
                );

                double real = readDouble("  Real part: ");
                double imaginary = readDouble("  Imaginary part: ");

                matrix.set(i, j, new Complex(real, imaginary));
            }
        }

        System.out.println("Matrix " + name + " created successfully.");
        return matrix;
    }

    private static int readPositiveInt(String message) {
        while (true) {
            int value = readInt(message);

            if (value > 0) {
                return value;
            }

            System.out.println("The number must be positive.");
        }
    }

    private static void requireMatrices() {
        if (matrixA == null || matrixB == null) {
            throw new IllegalArgumentException(
                "Create both matrices A and B first."
            );
        }
    }

    private static Matrix chooseMatrix() {
        while (true) {
            System.out.print("Choose matrix (A/B): ");
            String choice = scanner.nextLine().trim();

            if (choice.equalsIgnoreCase("A")) {
                if (matrixA != null) {
                    return matrixA;
                }

                System.out.println("Matrix A has not been created.");
            } else if (choice.equalsIgnoreCase("B")) {
                if (matrixB != null) {
                    return matrixB;
                }

                System.out.println("Matrix B has not been created.");
            } else {
                System.out.println("Enter A or B.");
            }
        }
    }

    private static void displayMatrices() {
        if (matrixA == null && matrixB == null) {
            System.out.println("No matrices have been created yet.");
            return;
        }

        if (matrixA != null) {
            System.out.println("Matrix A:");
            System.out.println(matrixA);
        }

        if (matrixB != null) {
            System.out.println("Matrix B:");
            System.out.println(matrixB);
        }
    }
}