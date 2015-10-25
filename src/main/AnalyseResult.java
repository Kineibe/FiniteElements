package main;

import java.util.List;

/**
 * Created by neikila on 15.10.15.
 */
public class AnalyseResult {

    public static Double getAbsoluteDifference(List <Double> values, Double left, Double right) {
        Double maxDifference = 0.0;
        Double step = (right - left) / (values.size() - 1);
        for (int i = 0; i < values.size(); ++i) {
            Double diff = Math.abs((Analytical.function(left + i * step) - values.get(i)));
            if (maxDifference < diff) {
                maxDifference = diff;
            }
        }
        return maxDifference;
    }

}
