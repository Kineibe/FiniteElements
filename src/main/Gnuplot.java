package main;

import utils.Functions;
import utils.Settings;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Vector;

/**
 * Created by neikila on 27.09.15.
 */
public class Gnuplot {

    private String directory;
    private Settings settings;
    private String scriptName;

    private Double min;
    private Double max;

    private Vector <String> titles;
    private Vector <String> fileNames;

    public Gnuplot(String directory, Settings settings) {
        this.settings = settings;
        this.directory = directory;
        this.scriptName = "script.gt";
        this.titles = new Vector<>();
        this.fileNames = new Vector<>();
        min = null;
        max = null;
    }

    public void prePrint() {
        try {
            PrintWriter script = new PrintWriter(directory + scriptName);
            script.print("set terminal x11 size 1360, 700\n" +
                    "set title 'Result'\n" +
                    "set xlabel \"X\"\n" +
                    "set ylabel \"Y\"\n" +
                    "set xrange [" + settings.getLeftX() + ":" + settings.getRightX() + "]\n" +
                    "set yrange [" + min + ":" + max + "]\n" +
                    "set grid\n" +
                    "plot '" + fileNames.get(0) + "' using 1:2 w l lw 2 title '" + titles.get(0) + "',\\\n" +
                    "     '" + fileNames.get(1) + "' using 1:2 w l lw 2 title '" + titles.get(1) + "',\\\n" +
                    "     '" + fileNames.get(2) + "' using 1:2 w p lw 2 title '" + titles.get(2) + "'\n" +
                    "pause -1");
            script.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
            System.out.println("Error while saving script.");
        }
    }

    public void printToFile(Vector <Double> y2, Double pointsPerElement, String filename, String title) {
        Double min = Functions.getMin(y2);
        Double max = Functions.getMax(y2);

        if (this.min == null || this.min > min) {
            this.min = min;
        }

        if (this.max == null || this.max < max) {
            this.max = max;
        }

        try {
            PrintWriter file = new PrintWriter(directory + filename);
            Double step = (settings.getRightX() - settings.getLeftX()) / settings.getElementAmount() / pointsPerElement;
            Double position = settings.getLeftX();
            for (int i = 0; i < y2.size(); i += 1) {
                file.println(" " + position + ' ' + y2.get(i));
                position += step;
            }
            file.close();
            fileNames.add(filename);
            titles.add(title);
        } catch (FileNotFoundException e) {
            System.out.println(e);
            System.out.println("Error while saving file.");
        }

    }
}