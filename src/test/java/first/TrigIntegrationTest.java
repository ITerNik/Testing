package first;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import ru.ifmo.first.base.SinRowFunction;
import ru.ifmo.first.derived.CosFunction;
import ru.ifmo.first.derived.TrigFunctions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TrigIntegrationTest {
    private CosFunction cos;
    private SinRowFunction sin;
    private TrigFunctions trigonometry;

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
    public void mockedCosValuesTanTest(double assertVal) {
        assertThrows(ArithmeticException.class, () -> trigonometry.tan(assertVal));
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
    public void mockedCosValuesCotTest(double cosVal) {
        assertThrows(ArithmeticException.class, () -> trigonometry.cot(cosVal));
    }
}
