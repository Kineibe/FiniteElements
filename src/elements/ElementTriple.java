package elements;

import utils.Degree;
import utils.IntegralEquation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neikila on 15.08.15.
 */
public class ElementTriple implements Element {
    private final Double x;
    private final Double length;
    private ArrayList <Double> values;

    public ElementTriple(double x, double length, IntegralEquation eq) {
        this.x = x;
        this.length = length;
        this.values = new ArrayList<>(4);
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

    public List<Double> getValues() {
        return values;
    }

    @Override
    public List <List <Double>> getAMatrix(IntegralEquation eq) {
        List <Double> temp = new ArrayList<>();
        Double c1 = eq.get(Degree.D2u_dx2);
        Double c2 = eq.get(Degree.Du_dx);
        Double c3 = eq.get(Degree.num);
        Double c4 = eq.get(Degree.u);

        Double a = length;
        List <List <Double>> AMatrix = new ArrayList<>();

        temp.add(8.0 * a * c4 / 105.0 - 37.0 * c1 / (10.0 * a) - c2 / 2.0);
        temp.add(57.0 * c2 / 80.0 + 189.0 * c1 / (40.0 * a) + 33.0 * a * c4 / 560.0);
        temp.add(-3.0 * c2 / 10.0 - 27.0 * c1 / (20.0 * a) - 3 * a * c4 / 140.0);
        temp.add(7.0 * c2 / 80.0 + 13.0 * c1 / (40.0 * a) + 19.0 * a * c4 / 1680.0);
        AMatrix.add(temp);

        temp = new ArrayList<>();
        temp.add(189.0 * c1 / (40.0 * a) - 57.0 * c2 / 80.0 + 33.0 * a * c4 / 560.0);
        temp.add(27.0 * a * c4 / 70.0 - 54.0 * c1 / (5 * a));
        temp.add(81.0 * c2 / 80.0 + 297.0 * c1 / (40.0 * a) - 27.0 * a * c4 / 560.0);
        temp.add(-3.0 * c2 / 10.0 - 27.0 * c1 / (20.0 * a) - 3 * a * c4 / 140.0);
        AMatrix.add(temp);

        temp = new ArrayList<>();
        temp.add(3.0 * c2 / 10.0 - 27.0 * c1 / (20.0 * a) - 3 * a * c4 / 140.0);
        temp.add(297.0 * c1 / (40.0 * a) - 81.0 * c2 / 80.0 - 27.0 * a * c4 / 560.0);
        temp.add(27.0 * a * c4 / 70.0 - 54.0 * c1 / (5 * a));
        temp.add(189.0 * c1 / (40.0 * a) + 57.0 * c2 / 80.0 + 33.0 * a * c4 / 560.0);
        AMatrix.add(temp);

        temp = new ArrayList<>();
        temp.add(-7.0 * c2 / 80.0 + 13.0 * c1 / (40.0 * a) + 19.0 * a * c4 / 1680.0);
        temp.add(3.0 * c2 / 10.0 - 27.0 * c1 / (20.0 * a) - 3 * a * c4 / 140.0);
        temp.add(-57.0 * c2 / 80.0 + 189.0 * c1 / (40.0 * a) + 33.0 * a * c4 / 560.0);
        temp.add(8.0 * a * c4 / 105.0 - 37.0 * c1 / (10.0 * a) + c2 / 2.0);
        AMatrix.add(temp);
        return AMatrix;
    }

    @Override
    public List <Double> getBMatrix(IntegralEquation eq) {
        Double c3 = eq.get(Degree.num);

        Double a = length;

        List <Double> BVector = new ArrayList<>();

        BVector.add(-1 * c3 * a / 8.0);
        BVector.add(-3 * c3 * a / 8.0);
        BVector.add(-3 * c3 * a / 8.0);
        BVector.add(-1 * c3 * a / 8.0);

        return BVector;
    }
}