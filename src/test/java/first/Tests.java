package first;

import config.TimingRules;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.ifmo.first.Trigonometry;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TimingRules.class)
public class Tests {
    private static final double EPS = 1e-4;
    private static final long TERMS = (long) 1e8;
    private static final double PI_HALF = Math.PI / 2;

    @Test
    @DisplayName("arcsin(0)")
    public void testArcsinZero() {
        double result = Trigonometry.arcsin(0, TERMS);
        assertEquals(result, 0, EPS);
    }

    @Test
    @DisplayName("arcsin(1)")
    public void testArcsinOne() {
        double result = Trigonometry.arcsin(1, TERMS);
        assertEquals(result, PI_HALF, EPS);
    }

    @Test
    @DisplayName("arcsin(-1)")
    public void testArcsinNegativeOne() {
        double result = Trigonometry.arcsin(-1, TERMS);
        assertEquals(result, -PI_HALF, EPS);
    }

    @Test
    @DisplayName("arcsin(0.5) - сравнение с Math.asin()")
    public void testArcsinPositiveHalf() {
        double result = Trigonometry.arcsin(0.5, TERMS);
        assertEquals(result, Math.asin(0.5), EPS);
    }

    @Test
    @DisplayName("arcsin(x > 1)")
    public void testArcsinLargeValue() {
        assertThrows(IllegalArgumentException.class, () -> Trigonometry.arcsin(1.1, TERMS));
    }

    @Test
    @DisplayName("arcsin(x -> 0)")
    public void testArcsinSmallValue() {
        double result = Trigonometry.arcsin(EPS, TERMS);
        assertEquals(result, EPS, EPS);
    }

    @Test
    @DisplayName("Увеличенная точность. arcsin(0,5) - сравнение с Math.asin()")
    public void testArcsinHighTerms() {
        double result = Trigonometry.arcsin(0.5, TERMS * 10L);
        assertEquals(result, Math.asin(0.5), EPS);
    }

    @ParameterizedTest
    @DisplayName("Параметризованный тест")
    @CsvSource({
            "0.0, 0.0",
            "1.0, 1.5707179143908",
            "-1.0, -1.5707179143915",
            "0.9, 1.1197695149986",
            "-0.9, -1.1197695149986",
            "0.8, 0.9272952180016",
            "-0.8, -0.9272952180016",
            "0.7, 0.7753974966108",
            "-0.7, -0.7753974966108",
            "0.6, 0.6435011087933",
            "0.5, 0.5235987755983",
            "-0.6, -0.6435011087933",
            "-0.5, -0.5235987755983"
    })
    void testArcsinValues(double input, double expected) {
        double result = Trigonometry.arcsin(input, TERMS / 10L);
        assertEquals(result, expected, EPS);
    }

    @Test
    @DisplayName("Переполнение в аргументе")
    public void testArcsinVeryLargeNumber() {
        assertThrows(IllegalArgumentException.class, () -> Trigonometry.arcsin(1e100, TERMS));
    }
}
