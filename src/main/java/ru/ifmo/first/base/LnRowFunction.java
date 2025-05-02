package ru.ifmo.first.base;

import ru.ifmo.first.MathFunction;

public class LnRowFunction implements MathFunction {
    private final double epsilon;

    public LnRowFunction(double epsilon) {
        this.epsilon = epsilon;
    }

    public LnRowFunction() {
        this(1e-10);
    }

    @Override
    public double calculate(double x) {
        if (!isInDomain(x)) {
            throw new IllegalArgumentException("Аргумент находится вне области определения: " + x);
        }

        if (x < 1) {
            return -calculate(1 / x);
        }

        int powCount = 0;
        while (x > 1.5) {
            x = Math.sqrt(x);
            powCount++;
        }

        double z = (x - 1) / (x + 1);
        double term = z;
        double sum = term;

        for(int n = 1;; n += 2) {
            if (Math.abs(term) <= epsilon) break;
            term = term * z * z * n / (n + 2);
            sum += term;
        }

        double result = 2 * sum;

        return result * (1 << powCount);
    }

    @Override
    public boolean isInDomain(double x) {
        return x > 0;
    }

    @Override
    public String getName() {
        return "ln(x)";
    }
}