package first;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import ru.ifmo.first.Main;
import ru.ifmo.first.base.SinRowFunction;
import ru.ifmo.first.derived.CosFunction;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CosIntegrationTest {
    private CosFunction cos;
    private SinRowFunction sin;

    @BeforeAll
    public static void generate() throws IOException {
        Main.main(new String[]{""});
    }

    @BeforeEach
    public void setup() {
        sin = Mockito.spy(SinRowFunction.class);
        cos = new CosFunction(sin);
    }

    @AfterEach
    public void cleanup() {
        Mockito.reset(sin);
    }

    @ParameterizedTest
    @DisplayName("Проверка табличных значений")
    @CsvFileSource(resources = "trig.csv", numLinesToSkip = 1)
    public void expectedValuesTest(double x, double sinVal, double cosVal) {
        assertEquals(cosVal, cos.calculate(x), 1e-9);
    }

    @ParameterizedTest
    @DisplayName("Косинус вызывает модуль синуса")
    @ValueSource(doubles = {Math.PI, 0})
    public void sinCalledTest(double x) {
        cos.calculate(x);
        verify(sin).calculate(x);
    }
}
