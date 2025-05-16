package ru.ifmo.first;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.DoubleUnaryOperator;

public class CSVExporter {
    private final String delimiter;
    private final String TEST_SOURCE = "src/test/resources/first/";

    public CSVExporter(String delimiter) {
        this.delimiter = delimiter;
    }

    public CSVExporter() {
        this(",");
    }

    public final void exportToCSV(String filename,
                                  double start, double end, double step, DoubleUnaryOperator ...functions) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TEST_SOURCE + filename))) {
                writer.print("x");
                for (int i = 0; i < functions.length; ++i) {
                    writer.print(String.format(delimiter + "control_%d", i));
                }
                writer.println();
                for (double x = start; x <= end; x += step) {
                    writer.print(x);
                    for (DoubleUnaryOperator func: functions) {
                        try {
                            writer.print(delimiter + func.applyAsDouble(x));
                        } catch (Exception err) {
                            writer.print(delimiter + "NaN");
                        }
                    }
                    writer.println();
                }
        }
    }

    public final void exportToCSVWithNumPoints(String filename,
                                               double start, double end, int numPoints, DoubleUnaryOperator ...additional) throws IOException {
        if (numPoints <= 1) {
            throw new IllegalArgumentException("Количество точек должно быть больше 1");
        }

        double step = (end - start) / (numPoints - 1);
        exportToCSV(filename, start, end, step, additional);
    }
}
