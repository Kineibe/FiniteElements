package elements;

import utils.Degree;
import utils.IntegralEquation;

import java.util.Vector;

/**
 * Created by neikila on 15.08.15.
 */
public class ElementLinear implements Element {
    private final Double x;
    private final Double length;
    private final Vector <Vector <Double>> AMatrix;
    private final Vector <Double> BVector;

    public ElementLinear(double x, double length, IntegralEquation eq) {
        this.x = x;
        this.length = length;
        AMatrix = new Vector<>();
        BVector = new Vector<>();

        Vector <Double> temp = new Vector<>();

        temp.add(-1* eq.get(Degree.D2u_dx2) / length - eq.get(Degree.Du_dx) / 2 + eq.get(Degree.u) * length / 3);
        temp.add(eq.get(Degree.D2u_dx2) / length + eq.get(Degree.Du_dx) / 2 + eq.get(Degree.u) * length / 6);
        AMatrix.add(temp);

        temp = new Vector<>();
        temp.add(eq.get(Degree.D2u_dx2) / length - eq.get(Degree.Du_dx) / 2 + eq.get(Degree.u) * length / 6);
        temp.add(-1 * eq.get(Degree.D2u_dx2) / length + eq.get(Degree.Du_dx) / 2 + eq.get(Degree.u) * length / 3);

        AMatrix.add(temp);

        BVector.add(-1 * eq.get(Degree.num) * length / 2.0);
        BVector.add(-1 * eq.get(Degree.num) * length / 2.0);
    }

    public Double getLeft() {
        return x;
    }

    public Double getRight() {
        return x + length;
    }

    public Double getLength() {
        return length;
    }

    public Vector <Vector <Double>> getAMatrix() {
        return AMatrix;
    }

    public Vector <Double> getBMatrix() {
        return BVector;
    }
}