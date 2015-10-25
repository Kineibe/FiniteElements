package main;

import utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by neikila on 27.09.15.
 */
public class Analytical {
    static Function result;
    static Double k1 = 0.0;
    static Double k2 = 0.0;
    static Double k3 = 0.0;

    static void prepare(Settings settings) {
        IntegralEquation equation = settings.getEquationA();
        if (equation.get(Degree.D2u_dx2) == 0.0) {
            System.out.println("Still not developed");
            System.exit(-1);
        }

        if (equation.get(Degree.u).equals(0.0)) {
            k1 = 0.0;
            if (!equation.get(Degree.Du_dx).equals(0.0)) {
                k2 = equation.get(Degree.num) / equation.get(Degree.Du_dx);
            } else {
                k3 = equation.get(Degree.num) / equation.get(Degree.u);
            }
        } else {
            k1 = equation.get(Degree.num) / equation.get(Degree.D2u_dx2) / 2.0;
        }

        Double discr = Math.pow(equation.get(Degree.Du_dx), 2) - 4.0 * equation.get(Degree.D2u_dx2) * equation.get(Degree.u);
        if (discr > 0.0) {
            final Double L1 = (-1 * equation.get(Degree.Du_dx) + Math.sqrt(discr)) / (2 * equation.get(Degree.D2u_dx2));
            final Double L2 = (-1 * equation.get(Degree.Du_dx) - Math.sqrt(discr)) / (2 * equation.get(Degree.D2u_dx2));

            List <List <Double>> A = new ArrayList<>();
            List <Double> B = new ArrayList<>();

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
                            - k1 * x * x
                            - k2 * x
                            - k3;
                }
            };
            System.out.println();
        }
        if (discr == 0.0) {
            final Double L = (-1 * equation.get(Degree.Du_dx) + Math.sqrt(discr)) / (2 * equation.get(Degree.D2u_dx2));

            List <List <Double>> A = new ArrayList<>();
            List <Double> B = new ArrayList<>();

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
                            - k1 * x * x
                            - k2 * x
                            - k3;
                }
            };
        }
        if (discr < 0.0) {
            System.out.println("Still not developed");
            System.exit(-1);
        }
    }

    static private void handleConstraint(Constraint constraint, IntegralEquation equation,
                                         List<List <Double>> A, List <Double> B,
                                         Double L1, Double L2, Double discr) {
        if (constraint.getType().equals(ConstraintType.First)) {
            List <Double> temp = new ArrayList<>();
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
            List <Double> temp = new ArrayList<>();
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
        return (Double) result.apply(x);
    }

    static public List<Double> getVector(Settings settings, Double frequency) {
        ArrayList<Double> result = new ArrayList<>();
        Double step = (settings.getRightX() - settings.getLeftX()) / settings.getElementAmount() / frequency;
        Double position = settings.getLeftX();
        for (int i = 0; i < settings.getElementAmount() * frequency + 1; ++i) {
            result.add(function(position));
            position += step;
        }
        return result;
    }

    static public List<Double> getVectorTemp(Settings settings, Double step) {
        ArrayList<Double> result = new ArrayList<>();
        Double position = settings.getLeftX();
        while (position <= settings.getRightX()) {
            result.add(function(position));
            position += step;
        }
        return result;
    }
}
