package main;

import elements.Element;
import utils.Functions;
import utils.Settings;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neikila on 27.09.15.
 */
public class Gnuplot {

    private String directory;
    private Settings settings;
    private String scriptName;

    private Double min;
    private Double max;

    private List <String> titles;
    private List <String> fileNames;

    public Gnuplot(String directory, Settings settings) {
        this.settings = settings;
        this.directory = directory;
        this.scriptName = "script.gt";
        this.titles = new ArrayList<>();
        this.fileNames = new ArrayList<>();
        min = null;
        max = null;
    }

    public void prePrint() {
        if (fileNames.size() == 0) {
            System.out.println("Nothing to print to gnuplot script");
            return;
        }
        try {
            PrintWriter script = new PrintWriter(directory + scriptName);
            String scriptCode = "set terminal x11 size 1360, 700\n" +
                    "set title 'Result'\n" +
                    "set xlabel \"X\"\n" +
                    "set ylabel \"Y\"\n" +
                    "set xrange [" + settings.getLeftX() + ":" + settings.getRightX() + "]\n" +
                    "set yrange [" + min + ":" + max + "]\n" +
                    "set grid\n" + "plot ";
            for (int i = 0; i < fileNames.size() - 1; ++i) {
                scriptCode += "'" + fileNames.get(i) + "' using 1:2 w l lw 2 title '" + titles.get(i) + "',\\\n";
            }
            scriptCode += "'" + fileNames.get(fileNames.size() - 1) + "' using 1:2 w p lw 2 title '" + titles.get(fileNames.size() - 1) + "'\n";
            scriptCode += "pause -1";
            script.print(scriptCode);
            script.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
            System.out.println("Error while saving script.");
        }
    }

    public void printToFileAnalytic(List <Double> y2, String filename, String title) {
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
            Double step = (settings.getRightX() - settings.getLeftX()) / (y2.size() - 1);
            Double position = settings.getLeftX();
            for (Double value: y2) {
                file.println(" " + position + ' ' + value);
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

    public void printToFile(List<Element> y2, String filename, String title) {
        Double min = Functions.getMinFromElements(y2);
        Double max = Functions.getMaxFromElements(y2);

        if (this.min == null || this.min > min) {
            this.min = min;
        }

        if (this.max == null || this.max < max) {
            this.max = max;
        }

        try {
            PrintWriter file = new PrintWriter(directory + filename);
            Double step = y2.get(0).getLength() / (y2.get(0).getValues().size() - 1);
            file.println(" " + y2.get(0).getLeft() + ' ' + y2.get(0).getValues().get(0));
            for (Element el: y2) {
                List <Double> temp = el.getValues();
                Double left = el.getLeft();
                for (int i = 1; i < temp.size(); ++i) {
                    file.println(" " + (left + (i * step)) + ' ' + temp.get(i));
                }
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