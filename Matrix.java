public class Matrix {
    private final int rows;
    private final int cols;
    private final Complex[][] data;

    public Matrix(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException(
                "Matrix dimensions must be positive"
            );
        }

        this.rows = rows;
        this.cols = cols;
        this.data = new Complex[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = new Complex(0, 0);
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Complex get(int row, int col) {
        return data[row][col];
    }

    public void set(int row, int col, Complex value) {
        if (value == null) {
            throw new IllegalArgumentException(
                "Matrix element cannot be null"
            );
        }

        data[row][col] = value;
    }

    public Matrix add(Matrix other) {
        if (rows != other.rows || cols != other.cols) {
            throw new IllegalArgumentException(
                "Matrices must have the same dimensions"
            );
        }

        Matrix result = new Matrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[i][j] =
                    data[i][j].add(other.data[i][j]);
            }
        }

        return result;
    }

    public Matrix multiply(Matrix other) {
        if (cols != other.rows) {
            throw new IllegalArgumentException(
                "Number of columns of the first matrix "
                + "must equal number of rows of the second"
            );
        }

        Matrix result = new Matrix(rows, other.cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                Complex sum = new Complex(0, 0);

                for (int k = 0; k < cols; k++) {
                    sum = sum.add(
                        data[i][k].multiply(other.data[k][j])
                    );
                }

                result.data[i][j] = sum;
            }
        }

        return result;
    }

    public Matrix transpose() {
        Matrix result = new Matrix(cols, rows);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[j][i] = data[i][j];
            }
        }

        return result;
    }

    public Complex determinant() {
        if (rows != cols) {
            throw new IllegalArgumentException(
                "Determinant is defined only for square matrices"
            );
        }

        if (rows == 1) {
            return data[0][0];
        }

        if (rows == 2) {
            return data[0][0].multiply(data[1][1])
                .subtract(data[0][1].multiply(data[1][0]));
        }

        Complex result = new Complex(0, 0);

        for (int j = 0; j < cols; j++) {
            Matrix submatrix = minor(0, j);

            Complex term = data[0][j].multiply(
                submatrix.determinant()
            );

            if (j % 2 == 0) {
                result = result.add(term);
            } else {
                result = result.subtract(term);
            }
        }

        return result;
    }

    private Matrix minor(int excludedRow, int excludedCol) {
        Matrix result = new Matrix(rows - 1, cols - 1);

        int newRow = 0;

        for (int i = 0; i < rows; i++) {
            if (i == excludedRow) {
                continue;
            }

            int newCol = 0;

            for (int j = 0; j < cols; j++) {
                if (j == excludedCol) {
                    continue;
                }

                result.data[newRow][newCol] = data[i][j];
                newCol++;
            }

            newRow++;
        }

        return result;
    }

    public Matrix inverse() {
        if (rows != cols) {
            throw new IllegalArgumentException(
                "Only square matrices can be inverted"
            );
        }

        if (rows == 1) {
            if (data[0][0].isZero()) {
                throw new ArithmeticException(
                    "Matrix is singular and has no inverse"
                );
            }

            Matrix result = new Matrix(1, 1);

            result.data[0][0] =
                new Complex(1, 0).divide(data[0][0]);

            return result;
        }

        Complex det = determinant();

        if (det.isZero()) {
            throw new ArithmeticException(
                "Matrix is singular and has no inverse"
            );
        }

        Matrix result = new Matrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Complex cofactor = minor(i, j).determinant();

                if ((i + j) % 2 != 0) {
                    cofactor =
                        new Complex(0, 0).subtract(cofactor);
                }

                result.data[j][i] = cofactor.divide(det);
            }
        }

        return result;
    }

    public Matrix divide(Matrix other) {
        return multiply(other.inverse());
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < rows; i++) {
            result.append("[ ");

            for (int j = 0; j < cols; j++) {
                result.append(data[i][j]);

                if (j < cols - 1) {
                    result.append(", ");
                }
            }

            result.append(" ]\n");
        }

        return result.toString();
    }
}