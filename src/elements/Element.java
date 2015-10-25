package elements;

import utils.IntegralEquation;

import java.util.List;

/**
 * Created by neikila on 27.09.15.
 */
public interface Element {
    List <List<Double>> getAMatrix(IntegralEquation eq);

    List <Double> getBMatrix(IntegralEquation eq);

    List <Double> getValues();

    Double getLeft();

    Double getRight();

    Double getLength();
}
