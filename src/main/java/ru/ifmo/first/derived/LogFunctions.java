package ru.ifmo.first.derived;

import ru.ifmo.first.base.LnRowFunction;

public class LogFunctions {
    private final LnRowFunction lnFunction;

    public LogFunctions(LnRowFunction lnFunction) {
        this.lnFunction = lnFunction;
    }

    public LogFunctions() {
        this(new LnRowFunction());
    }

    public double log2(double x) {
        return log(x, 2);
    }

    public double log3(double x) {
        return log(x, 3);
    }

    public double log5(double x) {
        return log(x, 5);
    }

    public double log10(double x) {
        return log(x, 10);
    }

    public double log(double x, double base) {
        if (base <= 0 || base == 1) {
            throw new IllegalArgumentException("Недопустимое основание логарифма");
        }
        return lnFunction.calculate(x) / lnFunction.calculate(base);
    }

    public boolean isInLogDomain(double x) {
        return x > 0;
    }
}
