package ru.ifmo.first;

public class Trigonometry {
        public static double arcsin(double x, long terms) {
            if (Math.abs(x) > 1) {
                throw new IllegalArgumentException("Аргумент должен быть в диапазоне [-1;1]");
            }
            int n = 0;
            double t = x;
            double s = t;

            while (n < terms) {
                n++;
                t = t * (2 * n) * (2 * n - 1) * x * x / (4 * Math.pow(n, 2));
                s += t / (2 * n + 1);
            }

            return s;
        }

    public static void main(String[] args) {
        double x = 0.5f;
        int terms = 100000;

        double result = arcsin(x, terms);

        System.out.println("Taylor series = " + result);
        System.out.println("Math.asin(" + x + ") = " + Math.asin(x));
    }
}
