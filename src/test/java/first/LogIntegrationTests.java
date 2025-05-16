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
import ru.ifmo.first.derived.LogFunctions;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

public class LogIntegrationTests {
    private LnRowFunction ln;
    private LogFunctions logFunctions;

    @BeforeAll
    public static void generate() throws IOException {
        Main.main(new String[]{""});
    }

    @BeforeEach
    public void setup() {
        ln = Mockito.spy(LnRowFunction.class);
        logFunctions = new LogFunctions(ln);
    }

    @AfterEach
    public void cleanup() {
        Mockito.reset(ln);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "log.csv", numLinesToSkip = 1)
    public void expectedValuesTest(double x, double lnVal, double logVal, double log2, double log3, double log5) {
        assertEquals(logVal, logFunctions.log10(x), 1e-8);
        assertEquals(log2, logFunctions.log2(x), 1e-8);
        assertEquals(log3, logFunctions.log3(x), 1e-8);
        assertEquals(log5, logFunctions.log5(x), 1e-8);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 1.4, 2, 3.2, 105, 103432})
    public void modulesCalledTest(double x) {
        logFunctions.log10(x);

        verify(ln).calculate(x);
    }
}
