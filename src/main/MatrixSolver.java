package main;

import java.util.Vector;

/**
 * Created by neikila on 05.10.15.
 */

public interface MatrixSolver {
    void solve();

    Vector <Double> getResult();

    void setA(Vector <Vector <Double>> A);

    void setB(Vector <Double> B);
}
