package main;

import elements.Element;
import elements.ElementLinear;
import elements.ElementTriple;
import elements.ElementType;
import utils.*;

import java.util.Vector;

/**
 * Created by neikila on 20.09.15.
 */
public class Solver {
    private final Vector <Vector<Double>> AMatrix;
    private Vector <Double> BMatrix;
    private final IntegralEquation equation;
    private final Vector <Element> elements;
    private Settings settings;
    private ElementType elementType;

    public Solver(Settings settings, ElementType elementType) {
        elements = new Vector<>();
        AMatrix = new Vector<>();
        BMatrix = new Vector<>();
        equation = settings.getEquationA();
        this.settings = settings;
        this.elementType = elementType;

        Double length = (settings.getRightX() - settings.getLeftX()) / settings.getElementAmount();
        Double position = settings.getLeftX();
        switch (elementType) {
            case Linear:
                for (int i = 0; i < settings.getElementAmount(); ++i) {
                    elements.add(new ElementLinear(position, length, equation));
                    position += length;
                }
                break;
            case Second:
                for (int i = 0; i < settings.getElementAmount(); ++i) {
                    elements.add(new ElementTriple(position, length, equation));
                    position += length;
                }
                break;
        }
        Functions.printMatrix(elements.get(0).getAMatrix());
        Functions.printVector(elements.get(0).getBMatrix());
    }

    public ElementType getElementType() {
        return elementType;
    }

    public Vector <Vector <Double>> getAMatrix() {
        return AMatrix;
    }

    public Vector <Double> getBMatrix() {
        return BMatrix;
    }

    public void createAMatrix() {

        int size = settings.getElementAmount() * elementType.getLocalSizeReduced() + 1;

        AMatrix.add(new Vector<>(size));
        for (int j = 0; j < size; ++j) {
            AMatrix.get(0).add(0.0);
        }

        for (int i = 0; i < elements.size(); ++i) {

            for (int k = 0; k < elementType.getLocalSizeReduced(); ++k) {
                Vector<Double> temp = new Vector<>(size);
                for (int j = 0; j < size; ++j) {
                    temp.add(0.0);
                }
                AMatrix.add(temp);
            }

            int startIndex = i * elementType.getLocalSizeReduced();
            Vector <Vector <Double>> current = elements.get(i).getAMatrix();
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
            Vector <Double> current = elements.get(i).getBMatrix();
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
//        Functions.printMatrix(AMatrix);
//        Functions.printVector(BMatrix);
//        System.out.println();
        Functions.printMatrix(AMatrix);

        Gaus.solve(AMatrix, BMatrix);
//        System.out.println();
//        Functions.printMatrix(AMatrix);
//        Functions.printVector(BMatrix);
//        System.out.println();
    }
}