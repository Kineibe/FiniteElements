package main;

import elements.ElementType;
import utils.Settings;

import java.util.Vector;

/**
 * Created by neikila on 10.04.15.
 */
public class Main {

    public static void main(String[] args) {
        Settings settings = new Settings();
        settings.getSet();
        Analytical.prepare(settings);
        Gnuplot gnuplot = new Gnuplot("./out/", settings);

        Solver linear = solve(ElementType.Linear, settings);
        Solver notLinear = solve(ElementType.Second, settings);

        Double frequency = settings.getFrequency();
        Vector <Double> analytical = Analytical.getVector(settings, frequency);

        gnuplot.printToFile(linear.getBMatrix(), (double) linear.getElementType().getLocalSizeReduced(),
                "text1.txt", "Two points");
        gnuplot.printToFile(notLinear.getBMatrix(), (double)notLinear.getElementType().getLocalSizeReduced(),
                "text2.txt", "Three points");
        gnuplot.printToFile(analytical, frequency,
                "text3.txt", "Analytical");
        gnuplot.prePrint();

        System.out.println("Linear Type Difference: " + AnalyseResult.getAbsoluteDifference(linear.getBMatrix(), settings.getLeftX(), settings.getRightX()));
        System.out.println("Linear Type Difference: " + AnalyseResult.getAbsoluteDifference(notLinear.getBMatrix(), settings.getLeftX(), settings.getRightX()));
    }

    public static Solver solve(ElementType type, Settings settings) {
        Solver solver = new Solver(settings, type);
        solver.createAMatrix();
        solver.createBMatrix();
        solver.makeCorrectionsAccordingToConstraints();
        solver.solve();

        return solver;
    }

}