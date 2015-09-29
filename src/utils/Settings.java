package utils;

/**
 * Created by neikila on 12.04.15.
 */
public class Settings {

    private static boolean printToFile = false;

    // Коэффициента уравнения
    private int elementAmount;
    private IntegralEquation equation = new IntegralEquation();

    private Constraint leftConstraint;
    private Constraint rightConstraint;

    public boolean isToPrintToFile() {
        return printToFile;
    }

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
//        try {
            // Scanner settings = new Scanner(Paths.get("settings"));
            elementAmount = 10;
            equation.set(Degree.num, -1.0);
            equation.set(Degree.u, 1.0);
            equation.set(Degree.Du_dx, 1.0);
            equation.set(Degree.D2u_dx2, 0.0);
//            equation.set(Degree.num, -1.0);
//            equation.set(Degree.u, 0.0);
//            equation.set(Degree.Du_dx, 7.0);
//            equation.set(Degree.D2u_dx2, 15.0);

            leftConstraint = new Constraint(1, 0, 0);
            rightConstraint = new Constraint(2, -2, 0);

            if (leftConstraint.getCoordinate() > rightConstraint.getCoordinate()) {
                Constraint temp;
                temp = leftConstraint;
                leftConstraint = rightConstraint;
                rightConstraint = temp;
            }

            System.out.println("Left: " + leftConstraint.getCoordinate());
            System.out.println("Right: " + rightConstraint.getCoordinate());
//
//        } catch (Exception e) {
//            System.out.print(e);
//            System.out.println("Ups. No Settings");
//            System.exit(-1);
//        }
    }
}