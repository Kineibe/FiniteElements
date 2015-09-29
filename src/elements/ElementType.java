package elements;

/**
 * Created by neikila on 27.09.15.
 */
public enum ElementType {
    Linear(2),
    Second(4);

    private int localSize;

    private ElementType(int localSize) {
        this.localSize = localSize;
    }

    public int getLocalSize() {
        return localSize;
    }

    public int getLocalSizeReduced() {
        return localSize - 1;
    }
}
