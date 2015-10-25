package utils;

/**
 * Created by neikila on 12.04.15.
 */
public class Settings {

    // Коэффициента уравнения
    private int elementAmount;
    private IntegralEquation equation = new IntegralEquation();

    private Constraint leftConstraint;
    private Constraint rightConstraint;

    public int getElementAmount() {
        return elementAmount;
    }

    public IntegralEquation getEquationA() {
        return equation;
    }

    public Double getLeftX() {
        return leftConstraint.getCoordinate();
    }

    public Double getRightX() {
        return rightConstraint.getCoordinate();
    }

    public Constraint getRightConstraint() {
        return rightConstraint;
    }

    public Constraint getLeftConstraint() {
        return leftConstraint;
    }

    public Double getFrequency() {
        return 2.0;
    }

    public void getSet() {
        elementAmount = 20;

        equation.set(Degree.num, 12.0);
        equation.set(Degree.u, 0.0);
        equation.set(Degree.Du_dx, -3.0);
        equation.set(Degree.D2u_dx2, 2.0);

        leftConstraint = new Constraint(2, -3, 5);
        rightConstraint = new Constraint(1, 2, 10);

        if (leftConstraint.getCoordinate() > rightConstraint.getCoordinate()) {
            Constraint temp;
            temp = leftConstraint;
            leftConstraint = rightConstraint;
            rightConstraint = temp;
        }

        System.out.println("Left: " + leftConstraint.getCoordinate());
        System.out.println("Right: " + rightConstraint.getCoordinate());
    }
}