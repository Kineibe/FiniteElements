package utils;

import java.util.Vector;

/**
 * Created by neikila on 14.08.15.
 */
public class IntegralEquation {
    private Vector<Double> a;

    public IntegralEquation() {
        this.a = new Vector<>();
        a.add(0.0);
        a.add(0.0);
        a.add(0.0);
        a.add(0.0);
    }

    public void set(Degree degree, Double value) {
        switch (degree) {
            case num: a.set(0, value); break;
            case Du_dx: a.set(1, value); break;
            case D2u_dx2: a.set(2, value); break;
            case u: a.set(3, value);
        }
    }

    public Double get(Degree degree) {
        switch (degree) {
            case num: return a.get(0);
            case Du_dx: return a.get(1);
            case D2u_dx2: return a.get(2);
            case u: return a.get(3);
            default: return null;
        }
    }
}