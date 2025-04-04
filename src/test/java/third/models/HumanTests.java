package third.models;

import config.TimingRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.ifmo.third.models.creatures.Fear;
import ru.ifmo.third.models.creatures.Gender;
import ru.ifmo.third.models.creatures.Human;
import ru.ifmo.third.models.creatures.Preference;
import ru.ifmo.third.models.environment.Climate;
import ru.ifmo.third.models.environment.Planet;
import ru.ifmo.third.models.environment.Sea;
import ru.ifmo.third.models.environment.Shade;
import ru.ifmo.third.models.words.NonInclineWord;
import ru.ifmo.third.models.words.Word;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TimingRules.class)
class HumanTests {
    private Human human;
    private Preference preference;
    private Planet earth;
    private Planet mars;

    @BeforeEach
    void setUp() {
        preference = new Preference(
                List.of(Climate.TROPICAL, Climate.TEMPERATE),
                List.of(Shade.TURQUOISE, Shade.BLUE),
                22,
                26
        );

        earth = new Planet("Земля", new Sea("Атлантический океан", Shade.BLUE), 24, Climate.TEMPERATE);
        mars = new Planet("Марс", new Sea("Кислотное море", Shade.NAVY_BLUE), 25, Climate.DESERT);

        human = new Human(preference, "Иван", Gender.MALE, false, true, true);
    }

    @Test
    @DisplayName("Создание человека с корректными параметрами")
    void testHumanCreation() {
        assertEquals("Иван", human.getName());
        assertEquals(Gender.MALE, human.getGender());
        assertFalse(human.isPoor());
        assertTrue(human.isWorthyOfMention());
        assertTrue(human.isBraveHeart());
    }

    @Test
    @DisplayName("Создание бедного, но достойного упоминания человека")
    void testInvalidHumanCreation() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Human("Петр", Gender.MALE, true, true, false));
        assertEquals("Достойный упоминания, но бедный", exception.getMessage());
    }

    @Test
    @DisplayName("Бедный, но достойный упоминания человек")
    void testSetPoorToWorthyHuman() {
        Human worthyHuman = new Human("Анна", Gender.FEMALE, false, true, true);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> worthyHuman.setPoor(true));
        assertEquals("Достойный упоминания, но бедный", exception.getMessage());
    }

    @Test
    @DisplayName("Изменение планеты при удовлетворяющих предпочтениях")
    void testSetPlanetSatisfyingPreference() {
        human.setPlanet(earth);
        assertEquals(earth, human.getPlanet());
    }

    @Test
    @DisplayName("Изменение планеты, не удовлетворяющей предпочтениям бедного человека")
    void testSetPlanetNotSatisfyingPreferenceForPoor() {
        Human poorHuman = new Human(preference, "Борис", Gender.MALE, true, false, false);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> poorHuman.setPlanet(mars));
        assertEquals("Бедный, но недовольный условиями", exception.getMessage());
    }

    @Test
    @DisplayName("Изменение предпочтений, если планета не соответствует новым предпочтениям")
    void testSetPreferenceInvalidForCurrentPlanet() {
        human.setPlanet(earth);
        human.setWorthyOfMention(false);
        human.setPoor(true);
        Preference newPreference = new Preference(List.of(Climate.DESERT), List.of(Shade.NAVY_BLUE), 20, 22);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> human.setPreference(newPreference));
        assertEquals("Бедный, но недовольный условиями", exception.getMessage());
    }

    @Test
    @DisplayName("Бросить вызов неизвестности без препятствий")
    void testMeetUnknownWithoutFear() {
        assertDoesNotThrow(human::meetUnknown);
    }

    @Test
    @DisplayName("Бросить вызов неизвестности, если человек боится")
    void testMeetUnknownWithFear() {
        human.getFears().add(Fear.UNKNOWN);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> human.meetUnknown());
        assertEquals("Иван боится неизвестности", exception.getMessage());
    }

    @Test
    @DisplayName("Выполнение героического поступка без препятствий")
    void testDoBraveActionWithoutFear() {
        assertDoesNotThrow(() -> human.doBraveAction());
    }

    @Test
    @DisplayName("Геройствовать нельзя, если человек боится")
    void testDoBraveActionWithFear() {
        human.getFears().add(Fear.BRAVE_ACTION);
        Exception exception = assertThrows(NoSuchMethodException.class,
                () -> human.doBraveAction());
        assertEquals("Иван боится геройствовать", exception.getMessage());
    }

    @Test
    @DisplayName("Склонение существительного")
    void testInclineNoun() {
        Word word = new Word("кот");
        assertDoesNotThrow(() -> human.inclineWord(word));
    }

    @Test
    @DisplayName("Склонении несклоняемого глагола")
    void testInclineVerbThrowsException() {
        Word word = new NonInclineWord("бежать");
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> human.inclineWord(word));
        assertEquals("Иван может склонять только несклоняемые существительные, но не \"бежать\"", exception.getMessage());
    }

    @Test
    @DisplayName("Склонение несклоняемого слова, если человек боится")
    void testInclineNonInclineWordWithFear() {
        human.getFears().add(Fear.INCLINE);
        Word word = new NonInclineWord("кафе");
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> human.inclineWord(word));
        assertEquals("Иван боится склонять несклоняемое \"кафе\"", exception.getMessage());
    }

    @Test
    @DisplayName("Человек склоняет несклоняемое слово без препятствий")
    void testInclineNonInclineWordWithoutFear() {
        Word word = new NonInclineWord("кино");
        assertDoesNotThrow(() -> human.inclineWord(word));
    }
}
