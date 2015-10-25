package elements;

import utils.Degree;
import utils.IntegralEquation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neikila on 15.08.15.
 */
public class ElementLinear implements Element {
    private final Double x;
    private final Double length;
    private List <Double> values;

    public ElementLinear(double x, double length, IntegralEquation eq) {
        this.x = x;
        this.length = length;
        this.values = new ArrayList<>(2);
    }

    public List<Double> getValues() {
        return values;
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

    public List <List <Double>> getAMatrix(IntegralEquation eq) {
        List <List <Double>> AMatrix = new ArrayList<>();

        List <Double> temp = new ArrayList<>();

        temp.add(-1* eq.get(Degree.D2u_dx2) / length - eq.get(Degree.Du_dx) / 2 + eq.get(Degree.u) * length / 3);
        temp.add(eq.get(Degree.D2u_dx2) / length + eq.get(Degree.Du_dx) / 2 + eq.get(Degree.u) * length / 6);
        AMatrix.add(temp);

        temp = new ArrayList<>();
        temp.add(eq.get(Degree.D2u_dx2) / length - eq.get(Degree.Du_dx) / 2 + eq.get(Degree.u) * length / 6);
        temp.add(-1 * eq.get(Degree.D2u_dx2) / length + eq.get(Degree.Du_dx) / 2 + eq.get(Degree.u) * length / 3);

        AMatrix.add(temp);

        return AMatrix;
    }

    public List <Double> getBMatrix(IntegralEquation eq) {
        List<Double> BVector = new ArrayList<>();

        BVector.add(-1 * eq.get(Degree.num) * length / 2.0);
        BVector.add(-1 * eq.get(Degree.num) * length / 2.0);

        return BVector;
    }
}