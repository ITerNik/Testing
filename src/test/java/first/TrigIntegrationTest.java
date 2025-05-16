package first;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import ru.ifmo.first.Main;
import ru.ifmo.first.base.SinRowFunction;
import ru.ifmo.first.derived.CosFunction;
import ru.ifmo.first.derived.TrigFunctions;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TrigIntegrationTest {
    private CosFunction cos;
    private SinRowFunction sin;
    private TrigFunctions trigonometry;

    @BeforeAll
    public static void generate() throws IOException {
        Main.main(new String[]{""});
    }

    @BeforeEach
    public void setup() {
        sin = Mockito.spy(SinRowFunction.class);
        CosFunction mockedCos = new CosFunction(sin);
        cos = Mockito.spy(mockedCos);
        trigonometry = new TrigFunctions(sin, cos);
    }

    @AfterEach
    public void cleanup() {
        Mockito.reset(sin);
        Mockito.reset(cos);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "trig.csv", numLinesToSkip = 1)
    public void expectedValuesTanTest(double x, double sinVal, double cosVal, double tanVal) {
        assertEquals(tanVal, trigonometry.tan(x), 1e-8);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Math.PI, 0})
    public void verifyModulesTanCalled(double x) {
        trigonometry.tan(x);

        verify(cos).calculate(x);
        verify(sin, times(2)).calculate(x);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "trig.csv", numLinesToSkip = 1)
    public void mockedSinValuesTanTest(double x, double sinVal, double cosVal, double tanVal) {
        doReturn(sinVal).when(sin).calculate(x);
        assertEquals(tanVal, trigonometry.tan(x), 1e-8);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "trig.csv", numLinesToSkip = 1)
    public void mockedCosValuesTanTest(double x, double sinVal, double cosVal, double tanVal) {
        doReturn(cosVal).when(cos).calculate(x);
        assertEquals(tanVal, trigonometry.tan(x), 1e-8);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Math.PI / 2, -Math.PI / 2, Math.PI / 2 * 3})
    public void mockedCosValuesTanTest(double x) {
        assertThrows(ArithmeticException.class, () -> trigonometry.tan(x));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "trig.csv", numLinesToSkip = 1)
    public void expectedValuesAndDomainCotTest(double x, double sinVal, double cosVal, double tanVal, double cotVal) {
        if (Math.abs(sinVal) < 1e-10) assertThrows(ArithmeticException.class,() -> trigonometry.cot(x));
        else assertEquals(cotVal, trigonometry.cot(x), 1e-8);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Math.PI / 2, -Math.PI / 2})
    public void verifyModulesCotCalled(double x) {
        trigonometry.cot(x);

        verify(cos).calculate(x);
        verify(sin, times(2)).calculate(x);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "trig.csv", numLinesToSkip = 1)
    public void mockedSinValuesCotTest(double x, double sinVal, double cosVal, double tanVal, double cotVal) {
        if (Math.abs(sinVal) < 1e-10) return;
        doReturn(sinVal).when(sin).calculate(x);
        assertEquals(cotVal, trigonometry.cot(x), 1e-8);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "trig.csv", numLinesToSkip = 1)
    public void mockedCosValuesCotTest(double x, double sinVal, double cosVal, double tanVal, double cotValue) {
        if (Math.abs(sinVal) < 1e-10) return;
        doReturn(cosVal).when(cos).calculate(x);
        assertEquals(cotValue, trigonometry.cot(x), 1e-8);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Math.PI, -Math.PI, 0})
    public void mockedCosValuesCotTest(double x) {
        assertThrows(ArithmeticException.class, () -> trigonometry.cot(x));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "trig.csv", numLinesToSkip = 1)
    public void expectedValuesAndDomainSecTest(double x, double sinVal, double cosVal) {
        assertEquals(1 / cosVal, trigonometry.sec(x), 1e-8);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Math.PI, 0, -Math.PI})
    public void verifyModulesSecCalled(double x) {
        trigonometry.sec(x);

        verify(cos).calculate(x);
        verify(sin).calculate(x);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "trig.csv", numLinesToSkip = 1)
    public void mockedSinValuesSecTest(double x, double sinVal, double cosVal) {
        doReturn(sinVal).when(sin).calculate(x);
        assertEquals(1 / cosVal, trigonometry.sec(x), 1e-8);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Math.PI / 2, -Math.PI / 2})
    public void mockedCosValuesSecTest(double x) {
        assertThrows(ArithmeticException.class, () -> trigonometry.sec(x));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "trig.csv", numLinesToSkip = 1)
    public void expectedValuesAndDomainCscTest(double x, double sinVal) {
        if (Math.abs(sinVal) < 1e-10) assertThrows(ArithmeticException.class,() -> trigonometry.csc(x));
        else assertEquals(1 / sinVal, trigonometry.csc(x), 1e-8);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Math.PI / 2, -Math.PI / 2})
    public void verifyModulesCscCalled(double x) {
        trigonometry.csc(x);

        verify(sin).calculate(x);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "trig.csv", numLinesToSkip = 1)
    public void mockedSinValuesCscTest(double x, double sinVal) {
        if (Math.abs(sinVal) < 1e-10) return;
        doReturn(sinVal).when(sin).calculate(x);
        assertEquals(1 / sinVal, trigonometry.csc(x), 1e-8);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "trig.csv", numLinesToSkip = 1)
    public void mockedCosValuesCscTest(double x, double sinVal, double cosVal) {
        if (Math.abs(sinVal) < 1e-10) return;
        doReturn(cosVal).when(cos).calculate(x);
        assertEquals(1 / sinVal, trigonometry.csc(x), 1e-8);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Math.PI, -Math.PI, 0})
    public void mockedCosValuesCscTest(double x) {
        assertThrows(ArithmeticException.class, () -> trigonometry.csc(x));
    }
}
