Complex Matrix Calculator

A Java console application for working with matrices of complex numbers.

Features

* Create two matrices of complex numbers.
* Add matrices.
* Multiply matrices.
* Divide matrices using the inverse of the second matrix.
* Transpose a matrix.
* Calculate the determinant of a square matrix.
* Calculate the inverse of a square matrix.
* Handle invalid operations and incompatible matrix dimensions.

Requirements

* Java Development Kit (JDK) 21 or later.

Project Structure

* Complex.java — represents complex numbers and implements arithmetic operations.
* Matrix.java — represents matrices and implements matrix operations.
* Main.java — provides the console interface.

How to Run

Compile the source files:

javac Complex.java Matrix.java Main.java

Run the application:

java Main

Notes

Matrix addition requires matrices of the same dimensions.

Matrix multiplication requires the number of columns in the first matrix to equal the number of rows in the second matrix.

The determinant and inverse are defined only for square matrices. A matrix must have a non-zero determinant to have an inverse.

Matrix division is implemented as multiplication by the inverse of the second matrix.