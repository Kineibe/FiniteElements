package elements;

import utils.IntegralEquation;

import java.util.Vector;

/**
 * Created by neikila on 27.09.15.
 */
public interface Element {
    Vector <Vector<Double>> getAMatrix(IntegralEquation eq);

    Vector <Double> getBMatrix(IntegralEquation eq);
}
