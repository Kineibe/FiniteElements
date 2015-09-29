package utils;

/**
 * Created by neikila on 14.08.15.
 */
public class Constraint {
    private ConstraintType type;
    private Double coordinate;
    private Double value;

    public Constraint(int constraintType, double coordinate, double value) {
        type = ConstraintType.getType(constraintType);
        System.out.println(type);
        this.coordinate = new Double(coordinate);
        this.value = new Double(value);
    }

    public ConstraintType getType() {
        return type;
    }

    public Double getCoordinate() {
        return coordinate;
    }

    public Double getValue() {
        return value;
    }
}

