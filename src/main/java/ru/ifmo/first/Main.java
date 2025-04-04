package  ru.ifmo.first;

public class Main {
    public static void main(String[] args) {
        System.out.println("   x    | arcsin(x)");
        System.out.println("---------------------");

        for (double x = -1.0; x <= 1.0; x += 0.1) {
            double result = Trigonometry.arcsin(x, (long) 1e7);
            System.out.printf("%6.1f  | %15.13f | %15.13f | %15.13f \n", x, Math.asin(x), result, result - Math.asin(x));
        }
    }
}
