package ru.ifmo.first.derived;

import ru.ifmo.first.base.SinRowFunction;

public class TrigFunctions {
    private final SinRowFunction sinFunction;
    private final CosFunction cosFunction;

    public TrigFunctions(SinRowFunction sinFunction, CosFunction cosFunction) {
        this.sinFunction = sinFunction;
        this.cosFunction = cosFunction;
    }

    public TrigFunctions() {
        this.sinFunction = new SinRowFunction();
        this.cosFunction = new CosFunction(this.sinFunction);
    }

    public double tan(double x) {
        double cosX = cosFunction.calculate(x);
        if (Math.abs(cosX) < 1e-10) {
            throw new ArithmeticException("Тангенс не определен при x = π/2 + πn");
        }

        return sinFunction.calculate(x) / cosX;
    }

    public double cot(double x) {
        double sinX = sinFunction.calculate(x);
        if (Math.abs(sinX) < 1e-10) {
            throw new ArithmeticException("Котангенс не определен при x = πn");
        }

        return cosFunction.calculate(x) / sinX;
    }

    public double sec(double x) {
        double cosX = cosFunction.calculate(x);
        if (Math.abs(cosX) < 1e-10) {
            throw new ArithmeticException("Секанс не определен при x = π/2 + πn");
        }

        return 1 / cosX;
    }

    public double csc(double x) {
        double sinX = sinFunction.calculate(x);
        if (Math.abs(sinX) < 1e-10) {
            throw new ArithmeticException("Косеканс не определен при x = πn");
        }

        return 1 / sinX;
    }

    public boolean isInTanDomain(double x) {
        double normalizedX = x % Math.PI;
        return Math.abs(normalizedX - Math.PI / 2) > 1e-10;
    }

    public boolean isInCotDomain(double x) {
        return Math.abs(x % Math.PI) > 1e-10;
    }

    public boolean isInSecDomain(double x) {
        return isInTanDomain(x);
    }

    public boolean isInCscDomain(double x) {
        return isInCotDomain(x);
    }
}
