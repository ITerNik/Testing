package  ru.ifmo.first;

import java.io.IOException;
import java.util.function.DoubleUnaryOperator;

public class Main {
    public static void main(String[] args) throws IOException {
        CSVExporter csvExporter = new CSVExporter();

        DoubleUnaryOperator log2 = (x) -> Math.log(x) / Math.log(2);
        DoubleUnaryOperator log3 = (x) -> Math.log(x) / Math.log(3);
        DoubleUnaryOperator log5 = (x) -> Math.log(x) / Math.log(5);

        csvExporter.exportToCSVWithNumPoints("trig.csv", -2 * Math.PI, 2 * Math.PI, 15, Math::sin, Math::cos, Math::tan, (x) -> 1 / Math.tan(x));
        csvExporter.exportToCSVWithNumPoints("log.csv", 0.001, 10, 15, Math::log, Math::log10, log2, log3, log5);
    }
}

