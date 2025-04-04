package third.models;

import config.TimingRules;
import mocks.ModelMocks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.ifmo.third.models.creatures.Preference;
import ru.ifmo.third.models.environment.Climate;
import ru.ifmo.third.models.environment.Planet;
import ru.ifmo.third.models.environment.Sea;
import ru.ifmo.third.models.environment.Shade;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TimingRules.class)
class PreferenceTests {
    private final Preference mockPreference = new Preference(
            List.of(Climate.TROPICAL),
            List.of(Shade.AQUA),
            20, 26
    );

    private final Sea seaMock = ModelMocks.getSeaMock();

    @Test
    @DisplayName("Планета соответствует предпочтениям")
    void testPlanetMatchesPreferences() {
        Planet planet = new Planet("Океания", seaMock, 24, Climate.TROPICAL);

        assertTrue(mockPreference.check(planet));
    }

    @Test
    @DisplayName("Несовпадение климата")
    void testClimateMismatch() {
        Planet planet = new Planet("Пустыня", seaMock, 24, Climate.MEDITERRANEAN);

        assertFalse(mockPreference.check(planet));
    }

    @Test
    @DisplayName("Несовпадение оттенка моря")
    void testShadeMismatch() {
        Planet planet = new Planet("Океания", new Sea("Sea", Shade.BLUE), 24, Climate.TROPICAL);

        assertFalse(mockPreference.check(planet));
    }

    @Test
    @DisplayName("Длина суток меньше минимальной")
    void testDayLengthTooShort() {
        Planet planet = new Planet("Океания", seaMock, 18, Climate.TROPICAL);

        assertFalse(mockPreference.check(planet));
    }

    @Test
    @DisplayName("Длина суток больше максимальной")
    void testDayLengthTooLong() {
        Planet planet = new Planet("Океания", seaMock, 28, Climate.TROPICAL);

        assertFalse(mockPreference.check(planet));
    }
}

