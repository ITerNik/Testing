package ru.ifmo.first;

public interface MathFunction {
    double calculate(double x);
    boolean isInDomain(double x);
    String getName();
}
