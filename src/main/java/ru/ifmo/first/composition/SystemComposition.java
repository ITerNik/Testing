package ru.ifmo.first.composition;

import ru.ifmo.first.MathFunction;
import ru.ifmo.first.base.LnRowFunction;
import ru.ifmo.first.base.SinRowFunction;
import ru.ifmo.first.derived.CosFunction;
import ru.ifmo.first.derived.LogFunctions;
import ru.ifmo.first.derived.TrigFunctions;

public class SystemComposition implements MathFunction {
    private final SinRowFunction sinFunction;
    private final CosFunction cosFunction;
    private final TrigFunctions trigFunctions;
    private final LnRowFunction lnFunction;
    private final LogFunctions logFunctions;

    public SystemComposition(
            SinRowFunction sinFunction,
            CosFunction cosFunction,
            TrigFunctions trigFunctions,
            LnRowFunction lnFunction,
            LogFunctions logFunctions) {
        this.sinFunction = sinFunction;
        this.cosFunction = cosFunction;
        this.trigFunctions = trigFunctions;
        this.lnFunction = lnFunction;
        this.logFunctions = logFunctions;
    }

    public SystemComposition() {
        this.sinFunction = new SinRowFunction();
        this.cosFunction = new CosFunction(this.sinFunction);
        this.trigFunctions = new TrigFunctions(this.sinFunction, this.cosFunction);
        this.lnFunction = new LnRowFunction();
        this.logFunctions = new LogFunctions(this.lnFunction);
    }

    @Override
    public double calculate(double x) {
        if (x <= 0) {
            return calculateTrigFunction(x);
        } else {
            return calculateLogFunction(x);
        }
    }

    private double calculateTrigFunction(double x) {
        if (!isInTrigDomain(x)) {
            throw new ArithmeticException("Аргумент вне области определения тригонометрической функции: " + x);
        }

        double sinX = sinFunction.calculate(x);
        double cosX = cosFunction.calculate(x);
        double tanX = trigFunctions.tan(x);
        double secX = trigFunctions.sec(x);
        double cscX = trigFunctions.csc(x);

        return (Math.pow(((((((tanX * tanX) * (tanX * tanX) * (tanX * tanX)) + cosX) * sinX) * tanX) - (tanX + sinX)) * (tanX * (tanX + sinX)), 6) - ((cscX + ((tanX / cosX) + secX)) - ((cosX - (cscX * cscX * cscX)) / secX)) * sinX) / cscX;

    }

    private double calculateLogFunction(double x) {
        if (!isInLogDomain(x)) {
            throw new ArithmeticException("Аргумент вне области определения логарифмической функции: " + x);
        }

        double lnX = lnFunction.calculate(x);
        double log3X = logFunctions.log3(x);
        double log5X = logFunctions.log5(x);

        return Math.pow((((log5X + log3X) / (lnX * log5X) - (log5X / lnX)) / ((log3X / log5X) - log5X)), 2);
    }

    @Override
    public boolean isInDomain(double x) {
        if (x <= 0) {
            return isInTrigDomain(x);
        } else {
            return isInLogDomain(x);
        }
    }

    private boolean isInTrigDomain(double x) {
        return trigFunctions.isInCscDomain(x) && trigFunctions.isInTanDomain(x);
    }

    private boolean isInLogDomain(double x) {
        if (!logFunctions.isInLogDomain(x)) {
            return false;
        }

        double log3X = logFunctions.log3(x);
        double log5X = logFunctions.log5(x);
        double denominator = log3X / log5X - log5X;

        return Math.abs(denominator) > 1e-10;
    }

    @Override
    public String getName() {
        return "System Function";
    }
}