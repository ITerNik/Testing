package third.models;

import config.TimingRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.ifmo.third.models.Ship;
import ru.ifmo.third.models.creatures.Human;
import ru.ifmo.third.models.creatures.Gender;
import ru.ifmo.third.models.environment.Planet;
import ru.ifmo.third.models.environment.Sea;
import ru.ifmo.third.models.environment.Shade;
import ru.ifmo.third.models.environment.Climate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TimingRules.class)
class ShipTests {

    private Planet earth;
    private Planet mars;
    private Human human1;
    private Human human2;

    @BeforeEach
    void setUp() {
        earth = new Planet("Земля", new Sea("Тихий океан", Shade.TURQUOISE), 24, Climate.TEMPERATE);
        mars = new Planet("Марс", new Sea("Марсианское море", Shade.AQUA), 24, Climate.DESERT);

        human1 = new Human("Иван", Gender.MALE, false, true, true);
        human2 = new Human("Мария", Gender.FEMALE, false, true, true);

        human1.setPlanet(earth);
        human2.setPlanet(earth);
    }

    @Test
    @DisplayName("Создание корабля с корректными параметрами")
    void testCreateShip() {
        Ship ship = new Ship(List.of(human1, human2), earth);
        assertNotNull(ship);
        assertEquals(earth, ship.getLocation());
        assertTrue(ship.getCrew().contains(human1));
        assertTrue(ship.getCrew().contains(human2));
    }

    @Test
    @DisplayName("Экипаж должен находиться на одной планете с кораблем")
    void testCreateShipWithCrewOnDifferentPlanets() {
        human2.setPlanet(mars);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Ship(List.of(human1, human2), earth));
        assertEquals("Мария находится на планете Марс, а корабль на Земля", exception.getMessage());
    }

    @Test
    @DisplayName("Перемещение корабля на другую планету")
    void testMoveShipToAnotherPlanet() {
        Ship ship = new Ship(List.of(human1, human2), earth);

        ship.move(mars);

        assertEquals(mars, ship.getLocation());
        assertEquals(mars, human1.getPlanet());
        assertEquals(mars, human2.getPlanet());
    }

    @Test
    @DisplayName("Перемещение корабля на ту же планету не изменяет состояние")
    void testMoveShipToSamePlanet() {
        Ship ship = new Ship(List.of(human1, human2), earth);

        ship.move(earth);

        assertEquals(earth, ship.getLocation());
        assertEquals(earth, human1.getPlanet());
        assertEquals(earth, human2.getPlanet());
    }
}
