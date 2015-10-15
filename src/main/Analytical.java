package main;

import utils.*;

import java.util.Vector;
import java.util.function.Function;

/**
 * Created by neikila on 27.09.15.
 */
public class Analytical {
    static Double L1;
    static Double L2;
    static Double C2;
    static Double C1;
    static Function result;

    static void prepare(Settings settings) {
        IntegralEquation equation = settings.getEquationA();
        if (equation.get(Degree.D2u_dx2) == 0.0) {
            System.out.println("Still not developed");
            System.exit(-1);
        }
        Double discr = Math.pow(equation.get(Degree.Du_dx), 2) - 4.0 * equation.get(Degree.D2u_dx2) * equation.get(Degree.u);
        if (discr > 0.0) {
            L1 = (-1 * equation.get(Degree.Du_dx) + Math.sqrt(discr)) / (2 * equation.get(Degree.D2u_dx2));
            L2 = (-1 * equation.get(Degree.Du_dx) - Math.sqrt(discr)) / (2 * equation.get(Degree.D2u_dx2));

            Vector <Vector <Double>> A = new Vector<>();
            Vector <Double> B = new Vector<>();

            handleConstraint(settings.getLeftConstraint(), equation, A, B);
            handleConstraint(settings.getRightConstraint(), equation, A, B);

            Gaus.solve(A, B);
            System.out.println("1) C2 = " + (-1 * 34.0 * 15.0 / (49.0 * Math.exp(7.0 / 3.0))));
            C2 = B.get(1);
            System.out.print("2) C2 = " + C2);
            C1 = B.get(0);

            result = new Function() {
                @Override
                public Object apply(Object o) {
                    Double x = (Double) o;
                    return C1 * Math.exp(L1 * x) + C2 * Math.exp(L2 * x) + x * 1.0 / 7.0;
                }
            };
        }
        if (discr == 0.0) {
            
        }
        if (discr < 0.0) {

        }
    }

    static private void handleConstraint(Constraint constraint, IntegralEquation equation, Vector <Vector <Double>> A, Vector <Double> B) {
        if (constraint.getType().equals(ConstraintType.First)) {
            Vector <Double> temp = new Vector<>();
            temp.add(Math.exp(constraint.getCoordinate() * L1));
            temp.add(Math.exp(constraint.getCoordinate() * L2));
            A.add(temp);

            Double BTemp = constraint.getValue();
            if (equation.get(Degree.u) != 0.0) {
                BTemp += equation.get(Degree.num) / equation.get(Degree.u);
            } else {
                BTemp += constraint.getCoordinate() * equation.get(Degree.num) / equation.get(Degree.Du_dx);
            }
            B.add(BTemp);
        } else {
            Vector <Double> temp = new Vector<>();
            temp.add(L1 * Math.exp(constraint.getCoordinate() * L1));
            temp.add(L2 * Math.exp(constraint.getCoordinate() * L2));
            A.add(temp);

            Double BTemp = constraint.getValue();
            if (equation.get(Degree.u) == 0.0) {
                BTemp += equation.get(Degree.num) / equation.get(Degree.Du_dx);
            }
            B.add(BTemp);
        }
    }

    static public Double function(Double x) {
//        return 1.0/49.0 * (7.0 * x + 15.0 * Math.exp(-7.0 / 15.0 * (x + 6.0)) - 15.0 / Math.exp(14.0/5.0));
//        return 1 - Math.exp(-x);
//        return 0.0;
        return (Double) result.apply(x);
//        return 1.0/49.0 * (7.0 * (x - 70.0) - 510.0 * Math.exp(-7.0 / 15.0 * (x + 5.0)) + 510.0 / Math.exp(7.0/3.0));
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
