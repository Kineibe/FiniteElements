package utils;

import elements.Element;

import java.util.List;

/**
 * Created by neikila on 20.09.15.
 */
public class Functions {
    public static void swap() {}

    // TODO fill it
    public static Double getMin(List <Double> vector) {
        Double min = vector.get(0);
        for (Double el: vector) {
            if (el < min) {
                min = el;
            }
        }
        return min;
    }

    public static Double getMinFromElements(List<Element> vector) {
        Double min = vector.get(0).getValues().get(0);
        for (Element el: vector) {
            Double localMin = getMin(el.getValues());
            if (localMin < min) {
                min = localMin;
            }
        }
        return min;
    }

    public static Double getMax(List <Double> vector) {
        Double max = vector.get(0);
        for (Double el: vector) {
            if (el > max) {
                max = el;
            }
        }
        return max;
    }

    public static Double getMaxFromElements(List<Element> vector) {
        Double max = vector.get(0).getValues().get(0);
        for (Element el: vector) {
            Double localMax = getMax(el.getValues());
            if (localMax > max) {
                max = localMax;
            }
        }
        return max;
    }

    public static void printMatrix(List <List <Double>> matrix) {
        for (List <Double> line : matrix) {
            for (Double a: line) {
                System.out.print(" " + a);
            }
            System.out.println();
        }
    }

    public static void printVector(List <Double> array) {
        for (Double a: array) {
            System.out.println(a);
        }
    }
}
