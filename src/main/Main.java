package main;

import elements.ElementType;
import utils.Settings;

import java.util.List;

/**
 * Created by neikila on 10.04.15.
 */
public class Main {

    public static void main(String[] args) {
        Settings settings = new Settings();
        settings.getSet();
        Analytical.prepare(settings);
        Gnuplot gnuplot = new Gnuplot("./out/", settings);

//      Моделирование линейными элементами
        Solver linear = solve(ElementType.Linear, settings);
        gnuplot.printToFile(linear.getElements(),
                "text1.txt", "Two points");
        System.out.println("Linear Type Difference: " +
                AnalyseResult.getAbsoluteDifference(
                        linear.getBMatrix(),
                        settings.getLeftX(),
                        settings.getRightX()
                ));

//      Моделирование кубическими элементами
        Solver notLinear = solve(ElementType.Second, settings);
        gnuplot.printToFile(notLinear.getElements(),
                "text2.txt", "Four points");
        System.out.println("Triple Type Difference: " +
                AnalyseResult.getAbsoluteDifference(
                        notLinear.getBMatrix(),
                        settings.getLeftX(),
                        settings.getRightX()
                ));

//      Аналитическое решение
        List<Double> analytical = Analytical.getVector(settings);
        gnuplot.printToFileAnalytic(analytical,
                "text3.txt", "Analytical");

        gnuplot.prePrint();

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