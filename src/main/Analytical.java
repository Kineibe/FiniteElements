package main;

import utils.*;

import java.util.List;
import java.util.Vector;
import java.util.function.Function;

/**
 * Created by neikila on 27.09.15.
 */
public class Analytical {
    static Function result;
    static int flag1 = 1;
    static int flag2 = 0;
    static int flag3 = 0;

    static void prepare(Settings settings) {
        IntegralEquation equation = settings.getEquationA();
        if (equation.get(Degree.D2u_dx2) == 0.0) {
            System.out.println("Still not developed");
            System.exit(-1);
        }
        if (equation.get(Degree.u).equals(0.0)) {
            flag1 = 0;
            if (!equation.get(Degree.Du_dx).equals(0.0)) {
                flag2 = 1;
            } else {
                flag3 = 1;
            }
        }
        Double discr = Math.pow(equation.get(Degree.Du_dx), 2) - 4.0 * equation.get(Degree.D2u_dx2) * equation.get(Degree.u);
        if (discr > 0.0) {
            final Double L1 = (-1 * equation.get(Degree.Du_dx) + Math.sqrt(discr)) / (2 * equation.get(Degree.D2u_dx2));
            final Double L2 = (-1 * equation.get(Degree.Du_dx) - Math.sqrt(discr)) / (2 * equation.get(Degree.D2u_dx2));

            Vector <Vector <Double>> A = new Vector<>();
            Vector <Double> B = new Vector<>();

            handleConstraint(settings.getLeftConstraint(), equation, A, B, L1, L2, discr);
            handleConstraint(settings.getRightConstraint(), equation, A, B, L1, L2, discr);

            Gaus.solve(A, B);
            final Double C2 = B.get(1);
            final Double C1 = B.get(0);

            result = new Function() {
                @Override
                public Object apply(Object o) {
                    Double x = (Double) o;
                    return C1 * Math.exp(L1 * x) + C2 * Math.exp(L2 * x)
                            - flag1 * x * x * equation.get(Degree.num) / equation.get(Degree.D2u_dx2) / 2.0
                            - flag2 * x * equation.get(Degree.num) / equation.get(Degree.Du_dx)
                            - flag3 * equation.get(Degree.num) / equation.get(Degree.u);
                }
            };
        }
        if (discr == 0.0) {
            final Double L = (-1 * equation.get(Degree.Du_dx) + Math.sqrt(discr)) / (2 * equation.get(Degree.D2u_dx2));

            Vector <Vector <Double>> A = new Vector<>();
            Vector <Double> B = new Vector<>();

            handleConstraint(settings.getLeftConstraint(), equation, A, B, L, L, discr);
            handleConstraint(settings.getRightConstraint(), equation, A, B, L, L, discr);

            Gaus.solve(A, B);
            final Double C2 = B.get(1);
            final Double C1 = B.get(0);

            result = new Function() {
                @Override
                public Object apply(Object o) {
                    Double x = (Double) o;
                    return C1 * Math.exp(L * x) + C2 * x * Math.exp(L * x)
                            - flag1 * x * x * equation.get(Degree.num) / equation.get(Degree.D2u_dx2) / 2.0
                            - flag2 * x * equation.get(Degree.num) / equation.get(Degree.Du_dx)
                            - flag3 * equation.get(Degree.num) / equation.get(Degree.u);
                }
            };
        }
        if (discr < 0.0) {

        }
    }

    static private void handleConstraint(Constraint constraint, IntegralEquation equation,
                                         List<Vector <Double>> A, List <Double> B,
                                         Double L1, Double L2, Double discr) {
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

            if (discr.equals(0.0)) {
                temp.set(1, temp.get(1) * constraint.getCoordinate());
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
            if (discr.equals(0.0)) {
                temp.set(1, temp.get(1) + L1 * Math.exp(L1 * constraint.getCoordinate()));
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
