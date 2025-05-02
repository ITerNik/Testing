package ru.ifmo.first.base;

import ru.ifmo.first.MathFunction;

public class SinRowFunction implements MathFunction {
    private final double epsilon;

    public SinRowFunction(double epsilon) {
        this.epsilon = epsilon;
    }

    public SinRowFunction() {
        this(1e-10);
    }

    @Override
    public double calculate(double x) {
        x = normalizeArg(x);

        double result = 0.0;
        double term = x;

        for (int n = 1;; n++) {
            if (Math.abs(term) <= epsilon) break;
            result += term;

            term = -term * x * x / ((2 * n) * (2 * n + 1));
        }

        return result;
    }

    private double normalizeArg(double x) {
        final double PI2 = 2 * Math.PI;

        x = x % PI2;

        if (x > Math.PI) {
            x -= PI2;
        } else if (x < -Math.PI) {
            x += PI2;
        }

        return x;
    }

    @Override
    public boolean isInDomain(double x) {
        return true;
    }

    @Override
    public String getName() {
        return "sin(x)";
    }
}