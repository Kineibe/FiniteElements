package main;

import java.util.Vector;

/**
 * Created by neikila on 15.10.15.
 */
public class AnalyseResult {

    public static Double getAbsoluteDifference(Vector <Double> values, Double left, Double right) {
//        System.out.println("Left: " + left + "; right: " + right);
        Double maxDifference = 0.0;
        Double step = (right - left) / (values.size() - 1);
//        System.out.println("Step = " + step);
//        System.out.println("Right + i * step = " + (left + (values.size() - 1) * step) );
        for (int i = 0; i < values.size(); ++i) {
            Double diff = Math.abs((Analytical.function(left + i * step) - values.get(i)));
            if (maxDifference < diff) {
                maxDifference = diff;
            }
        }
        return maxDifference;
    }

}
