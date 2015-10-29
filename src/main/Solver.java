package main;

import elements.Element;
import elements.ElementLinear;
import elements.ElementTriple;
import elements.ElementType;
import utils.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neikila on 20.09.15.
 */
public class Solver {
    private final List <List <Double>> AMatrix;
    private List <Double> BMatrix;
    private final IntegralEquation equation;
    private final List <Element> elements;

    public List<Element> getElements() {
        return elements;
    }

    private Settings settings;
    private ElementType elementType;

    public Solver(Settings settings, ElementType elementType) {
        elements = new ArrayList<>();
        AMatrix = new ArrayList<>();
        BMatrix = new ArrayList<>();
        equation = settings.getEquationA();
        this.settings = settings;
        this.elementType = elementType;

        Double position = settings.getLeftX();
        for (int i = 0; i < settings.getElementAmount(); ++i) {
            Double length = (settings.getRightX() - settings.getLeftX()) / settings.getElementAmount();
            elements.add(createElement(position, length));
            position += length;
        }
    }

    private Element createElement(Double position, Double length) {
        if (elementType.equals(ElementType.Linear)) {
            return new ElementLinear(position, length, equation);
        } else {
            return new ElementTriple(position, length, equation);
        }
    }

    public ElementType getElementType() {
        return elementType;
    }

    public List <List <Double>> getAMatrix() {
        return AMatrix;
    }

    public List <Double> getBMatrix() {
        return BMatrix;
    }

    public void createAMatrix() {

        int size = settings.getElementAmount() * elementType.getLocalSizeReduced() + 1;

        AMatrix.add(new ArrayList<>(size));
        for (int j = 0; j < size; ++j) {
            AMatrix.get(0).add(0.0);
        }

        for (int i = 0; i < elements.size(); ++i) {

            for (int k = 0; k < elementType.getLocalSizeReduced(); ++k) {
                List<Double> temp = new ArrayList<>(size);
                for (int j = 0; j < size; ++j) {
                    temp.add(0.0);
                }
                AMatrix.add(temp);
            }

            int startIndex = i * elementType.getLocalSizeReduced();
            List <List <Double>> current = elements.get(i).getAMatrix(equation);
            for (int k = 0; k < current.size(); ++k) {
                for (int j = 0; j < current.get(k).size(); ++j) {
                    AMatrix.get(startIndex + k).set(startIndex + j,
                            AMatrix.get(startIndex + k).
                                    get(startIndex + j) +
                                    current.get(k).get(j)
                    );
                }
            }
        }
    }

    public void createBMatrix() {
        BMatrix.add(0.0);
        for (int i = 0; i < elements.size(); ++i) {
            int startIndex = i * elementType.getLocalSizeReduced();
            List <Double> current = elements.get(i).getBMatrix(equation);
            BMatrix.set(startIndex, BMatrix.get(startIndex) + current.get(0));
            for (int j = 1; j < current.size(); ++j) {
                BMatrix.add(current.get(j));
            }
        }
    }

    public void makeCorrectionsAccordingToConstraints() {

        Constraint constraint = settings.getLeftConstraint();
        int num = 0;
        if (constraint.getType().equals(ConstraintType.First)) {
            for (int i = 0; i < AMatrix.get(num).size(); ++i) {
                AMatrix.get(num).set(i, 0.0);
                BMatrix.set(num, constraint.getValue());
            }
            AMatrix.get(num).set(num, 1.0);
        }
        if (constraint.getType().equals(ConstraintType.Second)) {
            BMatrix.set(num, constraint.getValue() * equation.get(Degree.D2u_dx2)+ BMatrix.get(num));
        }

        constraint = settings.getRightConstraint();
        num = BMatrix.size() - 1;
        if (constraint.getType().equals(ConstraintType.First)) {
            for (int i = 0; i < AMatrix.get(num).size(); ++i) {
                AMatrix.get(num).set(i, 0.0);
                BMatrix.set(num, constraint.getValue());
            }
            AMatrix.get(num).set(num, 1.0);
        }
        if (constraint.getType().equals(ConstraintType.Second)) {
            BMatrix.set(num, -1 * constraint.getValue() * equation.get(Degree.D2u_dx2)+ BMatrix.get(num));
        }
    }

    public void solve() {
//        System.out.println();
//        System.out.println(elementType);
//        Functions.printMatrix(AMatrix);
//        Functions.printVector(BMatrix);
//        System.out.println();
        Gaus.solve(AMatrix, BMatrix);
        int counter = 0;
        for (Element el: elements) {
            List<Double> values = el.getValues();
            for (int i = 0; i < elementType.getLocalSizeReduced(); ++i) {
                values.add(BMatrix.get(counter++));
            }
            values.add(BMatrix.get(counter));
        }
        System.out.println();
//        Functions.printMatrix(AMatrix);
//        Functions.printVector(BMatrix);
//        System.out.println();
    }
}