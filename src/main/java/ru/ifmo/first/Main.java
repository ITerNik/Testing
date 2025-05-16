package  ru.ifmo.first;

import java.io.IOException;
import java.util.function.DoubleUnaryOperator;

public class Main {
    private static final DoubleUnaryOperator log2 = (x) -> Math.log(x) / Math.log(2);
    private static final DoubleUnaryOperator log3 = (x) -> Math.log(x) / Math.log(3);
    private static final DoubleUnaryOperator log5 = (x) -> Math.log(x) / Math.log(5);

    private static final DoubleUnaryOperator secX = (x) -> 1 / Math.cos(x);
    private static final DoubleUnaryOperator cscX = (x) -> 1 / Math.sin(x);

    private static final DoubleUnaryOperator lnX = Math::log;

    public static void main(String[] args) throws IOException {
        CSVExporter csvExporter = new CSVExporter();

        csvExporter.exportToCSVWithNumPoints("trig.csv", -2 * Math.PI, 2 * Math.PI, 15, Math::sin, Math::cos, Math::tan, (x) -> 1 / Math.tan(x));
        csvExporter.exportToCSVWithNumPoints("log.csv", 0.001, 10, 15, Math::log, Math::log10, log2, log3, log5);
        csvExporter.exportToCSV("sys.csv", -100, 100, 15.55, Main::systemFunction, Math::sin, Math::cos, Math::tan, (x) -> 1 / Math.tan(x), Math::log10, log2, log3, log5, Math::log);
    }
    
    private static double trigFunction(double x) {
        return (Math.pow(((((((Math.tan(x) * Math.tan(x)) * (Math.tan(x) * Math.tan(x)) * (Math.tan(x) * Math.tan(x))) + Math.cos(x)) * Math.sin(x)) * Math.tan(x)) - (Math.tan(x) + Math.sin(x))) * (Math.tan(x) * (Math.tan(x) + Math.sin(x))), 6) - ((cscX.applyAsDouble(x) + ((Math.tan(x) / Math.cos(x)) + secX.applyAsDouble(x))) - ((Math.cos(x) - (cscX.applyAsDouble(x) * cscX.applyAsDouble(x) * cscX.applyAsDouble(x))) / secX.applyAsDouble(x))) * Math.sin(x)) / cscX.applyAsDouble(x);
    }

    private static double logFunction(double x) {
        return Math.pow((((log5.applyAsDouble(x) + log3.applyAsDouble(x)) / (lnX.applyAsDouble(x) * log5.applyAsDouble(x)) - (log5.applyAsDouble(x) / lnX.applyAsDouble(x))) / ((log3.applyAsDouble(x) / log5.applyAsDouble(x)) - log5.applyAsDouble(x))), 2);
    }

    private static double systemFunction(double x) {
        if (x > 0) return logFunction(x);
        else return trigFunction(x);
    }

}

