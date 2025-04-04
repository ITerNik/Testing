package third.models;

import config.TimingRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.ifmo.third.models.creatures.Creature;
import ru.ifmo.third.models.creatures.Human;
import ru.ifmo.third.models.creatures.Gender;
import ru.ifmo.third.models.environment.Climate;
import ru.ifmo.third.models.environment.Planet;
import ru.ifmo.third.models.environment.Sea;
import ru.ifmo.third.models.environment.Shade;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TimingRules.class)
class PlanetTests {

    private Planet alpha;
    private Human human;
    private Creature alien;

    @BeforeEach
    void setUp() {
        alpha = new Planet("Альфа Центавра", new Sea("Атлантический океан", Shade.BLUE), 24, Climate.TEMPERATE);
        human = new Human("Иван", Gender.MALE, false, true, true);
        alien = new Creature("Зорг", alpha, List.of(), true);
    }

    @Test
    @DisplayName("Создание планеты с корректными параметрами")
    void testPlanetCreation() {
        assertEquals("Альфа Центавра", alpha.getName());
        assertEquals("Атлантический океан", alpha.getSea().getName());
        assertEquals(Shade.BLUE, alpha.getSea().getShade());
        assertEquals(24, alpha.getDayLengthHours());
        assertEquals(Climate.TEMPERATE, alpha.getClimate());
        assertTrue(alpha.getPeople().isEmpty());
        assertTrue(alpha.getCreatures().isEmpty());
    }

    @Test
    @DisplayName("Добавление человека на планету")
    void testAddHuman() {
        alpha.addHuman(human);
        assertEquals(1, alpha.getPeople().size());
        assertEquals(human, alpha.getPeople().get(0));
        assertEquals(alpha, human.getPlanet());
    }

    @Test
    @DisplayName("Добавление существа на планету")
    void testAddCreature() {
        alpha.addCreature(alien);
        assertEquals(1, alpha.getCreatures().size());
        assertEquals(alien, alpha.getCreatures().get(0));
        assertEquals(alpha, alien.getPlanet());
    }

    @Test
    @DisplayName("Ошибка при добавлении ненастоящего человека на планету")
    void testAddFakeHumanThrowsException() {
        Human fakeHuman = new Human("НеНастоящий", Gender.MALE, false, false, false);
        fakeHuman.setAuthentic(false);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> alpha.addHuman(fakeHuman));
        assertEquals("НеНастоящий должен быть настоящим, чтобы жить на планете", exception.getMessage());
    }

    @Test
    @DisplayName("Ошибка при добавлении ненастоящего существа на планету")
    void testAddFakeCreatureThrowsException() {
        Creature fakeCreature = new Creature("Фантом", alpha, List.of(), false);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> alpha.addCreature(fakeCreature));
        assertEquals("Фантом должен быть настоящим, чтобы жить на планете", exception.getMessage());
    }

    @Test
    @DisplayName("Массовое добавление людей на планету через setPeople")
    void testSetPeople() {
        Human human2 = new Human("Мария", Gender.FEMALE, false, false, true);
        alpha.setPeople(List.of(human, human2));

        assertEquals(2, alpha.getPeople().size());
        assertEquals(alpha, human.getPlanet());
        assertEquals(alpha, human2.getPlanet());
    }

    @Test
    @DisplayName("Ошибка при массовом добавлении ненастоящих людей")
    void testSetPeopleWithFakeHumanThrowsException() {
        Human fakeHuman = new Human("НеНастоящий", Gender.MALE, false, false, false);
        fakeHuman.setAuthentic(false);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> alpha.setPeople(List.of(human, fakeHuman)));
        assertEquals("НеНастоящий должен быть настоящим, чтобы жить на планете", exception.getMessage());
    }

    @Test
    @DisplayName("Массовое добавление существ на планету через setCreatures")
    void testSetCreatures() {
        Creature alien2 = new Creature("Ксеноморф", alpha, List.of(), true);
        alpha.setCreatures(List.of(alien, alien2));

        assertEquals(2, alpha.getCreatures().size());
        assertEquals(alpha, alien.getPlanet());
        assertEquals(alpha, alien2.getPlanet());
    }

    @Test
    @DisplayName("Ошибка при массовом добавлении ненастоящих существ")
    void testSetCreaturesWithFakeCreatureThrowsException() {
        Creature fakeCreature = new Creature("Призрак", alpha, List.of(), false);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> alpha.setCreatures(List.of(alien, fakeCreature)));
        assertEquals("Призрак должен быть настоящим, чтобы жить на планете", exception.getMessage());
    }
}
