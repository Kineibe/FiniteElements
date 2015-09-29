package utils;

import java.util.Vector;

/**
 * Created by neikila on 20.09.15.
 */
public class Functions {
    public static void swap() {}

    // TODO fill it
    public static Double getMin(Vector <Double> vector) {
        Double min = vector.get(0);
        for (Double el: vector) {
            if (el < min) {
                min = el;
            }
        }
        return min;
    }

    public static Double getMax(Vector <Double> vector) {
        Double max = vector.get(0);
        for (Double el: vector) {
            if (el > max) {
                max = el;
            }
        }
        return max;
    }

    public static void printMatrix(Vector <Vector <Double>> matrix) {
        for (Vector <Double> line : matrix) {
            for (Double a: line) {
                System.out.print(" " + a);
            }
            System.out.println();
        }
    }

    public static void printVector(Vector <Double> array) {
        for (Double a: array) {
            System.out.println(a);
        }
    }
}
