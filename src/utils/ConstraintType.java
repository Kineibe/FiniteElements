package utils;

/**
 * Created by neikila on 14.08.15.
 */
public enum ConstraintType {
     Error, First, Second;

    public static ConstraintType getType(int num) {
        switch (num) {
            case 1: return First;
            case 2: return Second;
            default: return Error;
        }
    }
}
