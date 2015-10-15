package main;

import java.util.List;
import java.util.Vector;

/**
 * Created by neikila on 20.09.15.
 */
public class Gaus {

    public static void solve(Vector <Vector <Double>> AMatrix, Vector <Double> BMatrix) {

        for (int i = 0; i < AMatrix.size(); ++i) {
            check(AMatrix, i, BMatrix);
            toDefault(AMatrix.get(i), i, BMatrix);
            for (int j = i + 1; j < AMatrix.size(); j++) {
                BMatrix.set(j,
                        BMatrix.get(j) -
                        BMatrix.get(i) *
                        AMatrix.get(j).get(i));
                differ(AMatrix.get(i), AMatrix.get(j), i);
            }
        }

        for (int i = AMatrix.size() - 1; i >= 0; --i) {
            for (int j = i - 1; j >= 0; --j) {
                BMatrix.set(j, BMatrix.get(j) - BMatrix.get(i) * AMatrix.get(j).get(i));
                AMatrix.get(j).set(i, 0.0);
            }
        }
    }

    private static void check(List <Vector <Double>> array, int index, List <Double> b) {
        if (array.get(index).get(index) == 0.0) {
            for (int i = index + 1; i < array.size(); ++i) {
                if (array.get(i).get(index) != 0.0) {
                    Vector <Double> temp = array.get(index);
                    array.set(index, array.get(i));
                    array.set(i, temp);

                    Double tempB = b.get(index);
                    b.set(index, b.get(i));
                    b.set(i, tempB);
                    break;
                }
            }
        }
    }

    private static void toDefault(Vector <Double> array, int startFrom, Vector <Double> b) {
        Double divider = array.get(startFrom);
        if (divider == 0.0) {
            divider = 1.0;
        }
        for (int i = 0; i < array.size(); ++i) {
            array.set(i, array.get(i) / divider);
        }
        b.set(startFrom, b.get(startFrom) / divider);
    }

    private static void differ(Vector <Double> base, Vector <Double> toEdit, int startFrom) {
        Double multiplier = toEdit.get(startFrom);
        for (int i = startFrom; i < toEdit.size(); ++i) {
            toEdit.set(i, toEdit.get(i) - base.get(i) * multiplier);
        }
    }
}