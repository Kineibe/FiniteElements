package main;

import utils.Settings;

import java.util.Vector;

/**
 * Created by neikila on 27.09.15.
 */
public class Analytical {
    static public Double function(Double x) {
//        return 1.0/49.0 * (7.0 * x + 15.0 * Math.exp(-7.0 / 15.0 * (x + 6.0)) - 15.0 / Math.exp(14.0/5.0));
        return 1 - Math.exp(-x);
    }

    static public Vector<Double> getVector(Settings settings, Double frequency) {
        Vector <Double> result = new Vector<>();
        Double step = (settings.getRightX() - settings.getLeftX()) / settings.getElementAmount() / frequency;
        Double position = settings.getLeftX();
        for (int i = 0; i < settings.getElementAmount() * frequency + 1; ++i) {
            result.add(function(position));
            position += step;
        }
        return result;
    }
}
