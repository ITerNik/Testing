package ru.ifmo.first.derived;

import ru.ifmo.first.MathFunction;
import ru.ifmo.first.base.SinRowFunction;

public class CosFunction implements MathFunction {
    private final SinRowFunction sinFunction;

    public CosFunction(SinRowFunction sinFunction) {
        this.sinFunction = sinFunction;
    }

    public CosFunction() {
        this(new SinRowFunction());
    }

    @Override
    public double calculate(double x) {
        double sinX = sinFunction.calculate(x);

        if (Math.abs(sinX) > 1 - 1e-11) {
            return 0;
        }

        double result = Math.sqrt(1 - sinX * sinX);
        double normalized = x % (2 * Math.PI);

        if (normalized < 0) {
            normalized += 2 * Math.PI;
        }

        if (normalized > Math.PI / 2 && normalized < 3 * Math.PI / 2) {
            result = -result;
        }

        return result;
    }

    @Override
    public boolean isInDomain(double x) {
        return true;
    }

    @Override
    public String getName() {
        return "cos(x)";
    }
}