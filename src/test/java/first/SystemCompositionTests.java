package first;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import ru.ifmo.first.Main;
import ru.ifmo.first.base.LnRowFunction;
import ru.ifmo.first.base.SinRowFunction;
import ru.ifmo.first.composition.SystemComposition;
import ru.ifmo.first.derived.CosFunction;
import ru.ifmo.first.derived.LogFunctions;
import ru.ifmo.first.derived.TrigFunctions;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

public class SystemCompositionTests {
    private CosFunction cos;
    private SinRowFunction sin;
    private TrigFunctions trigonometry;
    private LnRowFunction ln;
    private LogFunctions logFunctions;
    private SystemComposition system;

    @BeforeAll
    public static void generate() throws IOException {
        Main.main(new String[]{""});
    }

    @BeforeEach
    public void setup() {
        sin = Mockito.spy(SinRowFunction.class);
        CosFunction mockedCos = new CosFunction(sin);
        cos = Mockito.spy(mockedCos);
        TrigFunctions mockedTrigonometry = new TrigFunctions(sin, cos);
        trigonometry = Mockito.spy(mockedTrigonometry);
        ln = Mockito.spy(LnRowFunction.class);
        LogFunctions mockedLogFunctions = new LogFunctions(ln);
        logFunctions = Mockito.spy(mockedLogFunctions);
        system = new SystemComposition(sin, cos, trigonometry, ln, logFunctions);
    }

    @AfterEach
    public void cleanup() {
        Mockito.reset(sin);
        Mockito.reset(cos);
        Mockito.reset(ln);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "sys.csv", numLinesToSkip = 1)
    public void expectedValuesTest(double x, double res) {
        assertEquals(res, system.calculate(x), 1e-8);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, -45, -100})
    public void modulesTrigCalledTest(double x) {
        system.calculate(x);

        verify(sin, times(6)).calculate(anyDouble());
        verify(cos, times(3)).calculate(anyDouble());
        verify(trigonometry).tan(anyDouble());
        verify(trigonometry).sec(anyDouble());
        verify(trigonometry).csc(anyDouble());

        verify(ln, never()).calculate(anyDouble());
        verify(logFunctions, never()).log(anyDouble(), anyDouble());
        verify(logFunctions, never()).log2(anyDouble());
        verify(logFunctions, never()).log10(anyDouble());
        verify(logFunctions, never()).log3(anyDouble());
        verify(logFunctions, never()).log5(anyDouble());
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.6, 45, 100})
    public void modulesLogCalledTest(double x) {
        system.calculate(x);

        verify(sin, never()).calculate(anyDouble());
        verify(cos, never()).calculate(anyDouble());
        verify(trigonometry, never()).tan(anyDouble());
        verify(trigonometry, never()).sec(anyDouble());
        verify(trigonometry, never()).csc(anyDouble());

        verify(ln, times(9)).calculate(anyDouble());
        verify(logFunctions, times(4)).log(anyDouble(), anyDouble());
        verify(logFunctions, times(2)).log3(anyDouble());
        verify(logFunctions, times(2)).log5(anyDouble());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "sys.csv", numLinesToSkip = 1)
    public void cosIntegrationTest(double x, double res, double sinVal, double cosVal, double tanVal, double cotVal, double logVal, double log2, double log3, double log5, double lnVal) {
        doReturn(sinVal).when(sin).calculate(anyDouble());
        doReturn(tanVal).when(trigonometry).tan(anyDouble());
        doReturn(cotVal).when(trigonometry).cot(anyDouble());
        doReturn(1 / cosVal).when(trigonometry).sec(anyDouble());
        doReturn(1 / sinVal).when(trigonometry).csc(anyDouble());

        doReturn(logVal).when(logFunctions).log10(anyDouble());
        doReturn(log2).when(logFunctions).log2(anyDouble());
        doReturn(log3).when(logFunctions).log3(anyDouble());
        doReturn(log5).when(logFunctions).log5(anyDouble());
        doReturn(lnVal).when(ln).calculate(anyDouble());

        assertEquals(res, system.calculate(x), 1e-8);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "sys.csv", numLinesToSkip = 1)
    public void sinIntegrationTest(double x, double res, double sinVal, double cosVal, double tanVal, double cotVal, double logVal, double log2, double log3, double log5, double lnVal) {
        doReturn(cosVal).when(cos).calculate(anyDouble());
        doReturn(tanVal).when(trigonometry).tan(anyDouble());
        doReturn(cotVal).when(trigonometry).cot(anyDouble());
        doReturn(1 / cosVal).when(trigonometry).sec(anyDouble());
        doReturn(1 / sinVal).when(trigonometry).csc(anyDouble());

        doReturn(logVal).when(logFunctions).log10(anyDouble());
        doReturn(log2).when(logFunctions).log2(anyDouble());
        doReturn(log3).when(logFunctions).log3(anyDouble());
        doReturn(log5).when(logFunctions).log5(anyDouble());
        doReturn(lnVal).when(ln).calculate(anyDouble());

        assertEquals(res, system.calculate(x), 1e-8);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "sys.csv", numLinesToSkip = 1)
    public void tanIntegrationTest(double x, double res, double sinVal, double cosVal, double tanVal, double cotVal, double logVal, double log2, double log3, double log5, double lnVal) {
        doReturn(cosVal).when(cos).calculate(anyDouble());
        doReturn(sinVal).when(sin).calculate(anyDouble());
        doReturn(cotVal).when(trigonometry).cot(anyDouble());
        doReturn(1 / cosVal).when(trigonometry).sec(anyDouble());
        doReturn(1 / sinVal).when(trigonometry).csc(anyDouble());

        doReturn(logVal).when(logFunctions).log10(anyDouble());
        doReturn(log2).when(logFunctions).log2(anyDouble());
        doReturn(log3).when(logFunctions).log3(anyDouble());
        doReturn(log5).when(logFunctions).log5(anyDouble());
        doReturn(lnVal).when(ln).calculate(anyDouble());

        assertEquals(res, system.calculate(x), 1e-8);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "sys.csv", numLinesToSkip = 1)
    public void secIntegrationTest(double x, double res, double sinVal, double cosVal, double tanVal, double cotVal, double logVal, double log2, double log3, double log5, double lnVal) {
        doReturn(cosVal).when(cos).calculate(anyDouble());
        doReturn(sinVal).when(sin).calculate(anyDouble());
        doReturn(cotVal).when(trigonometry).cot(anyDouble());
        doReturn(tanVal).when(trigonometry).tan(anyDouble());
        doReturn(1 / sinVal).when(trigonometry).csc(anyDouble());

        doReturn(logVal).when(logFunctions).log10(anyDouble());
        doReturn(log2).when(logFunctions).log2(anyDouble());
        doReturn(log3).when(logFunctions).log3(anyDouble());
        doReturn(log5).when(logFunctions).log5(anyDouble());
        doReturn(lnVal).when(ln).calculate(anyDouble());

        assertEquals(res, system.calculate(x), 1e-8);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "sys.csv", numLinesToSkip = 1)
    public void cscIntegrationTest(double x, double res, double sinVal, double cosVal, double tanVal, double cotVal, double logVal, double log2, double log3, double log5, double lnVal) {
        doReturn(cosVal).when(cos).calculate(anyDouble());
        doReturn(sinVal).when(sin).calculate(anyDouble());
        doReturn(cotVal).when(trigonometry).cot(anyDouble());
        doReturn(tanVal).when(trigonometry).tan(anyDouble());
        doReturn(1 / cosVal).when(trigonometry).sec(anyDouble());

        doReturn(logVal).when(logFunctions).log10(anyDouble());
        doReturn(log2).when(logFunctions).log2(anyDouble());
        doReturn(log3).when(logFunctions).log3(anyDouble());
        doReturn(log5).when(logFunctions).log5(anyDouble());
        doReturn(lnVal).when(ln).calculate(anyDouble());

        assertEquals(res, system.calculate(x), 1e-8);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "sys.csv", numLinesToSkip = 1)
    public void log10IntegrationTest(double x, double res, double sinVal, double cosVal, double tanVal, double cotVal, double logVal, double log2, double log3, double log5, double lnVal) {
        doReturn(cosVal).when(cos).calculate(anyDouble());
        doReturn(sinVal).when(sin).calculate(anyDouble());
        doReturn(cotVal).when(trigonometry).cot(anyDouble());
        doReturn(tanVal).when(trigonometry).tan(anyDouble());
        doReturn(1 / cosVal).when(trigonometry).sec(anyDouble());
        doReturn(1 / sinVal).when(trigonometry).csc(anyDouble());

        doReturn(log2).when(logFunctions).log2(anyDouble());
        doReturn(log3).when(logFunctions).log3(anyDouble());
        doReturn(log5).when(logFunctions).log5(anyDouble());
        doReturn(lnVal).when(ln).calculate(anyDouble());

        assertEquals(res, system.calculate(x), 1e-8);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "sys.csv", numLinesToSkip = 1)
    public void lnIntegrationTest(double x, double res, double sinVal, double cosVal, double tanVal, double cotVal, double logVal, double log2, double log3, double log5, double lnVal) {
        doReturn(cosVal).when(cos).calculate(anyDouble());
        doReturn(sinVal).when(sin).calculate(anyDouble());
        doReturn(cotVal).when(trigonometry).cot(anyDouble());
        doReturn(tanVal).when(trigonometry).tan(anyDouble());
        doReturn(1 / cosVal).when(trigonometry).sec(anyDouble());
        doReturn(1 / sinVal).when(trigonometry).csc(anyDouble());

        doReturn(log2).when(logFunctions).log2(anyDouble());
        doReturn(logVal).when(logFunctions).log10(anyDouble());
        doReturn(log3).when(logFunctions).log3(anyDouble());
        doReturn(log5).when(logFunctions).log5(anyDouble());

        assertEquals(res, system.calculate(x), 1e-8);
    }
}