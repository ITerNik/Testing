package third.models;

import config.TimingRules;
import mocks.ModelMocks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.ifmo.third.models.creatures.Creature;
import ru.ifmo.third.models.environment.Planet;
import ru.ifmo.third.models.words.Word;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TimingRules.class)
public class CreatureTests {
    @Test
    @DisplayName("Создание существа с правильной планетой")
    void testCreatureCreationSuccess() {
        Planet alpha = ModelMocks.getInhabitPlanetMock();
        alpha.setName("Альфа Центавра");
        Creature creature = new Creature("Мохнатик", alpha, List.of(), true);

        assertEquals("Мохнатик", creature.getName());
        assertEquals(alpha, creature.getPlanet());
        assertTrue(creature.isAuthentic());
        assertTrue(creature.getFears().isEmpty());
    }

    @Test
    @DisplayName("Мохнатые существа могут жить только на Альфа Центавре")
    void testCreatureCreationFailure() {
        Planet planet = ModelMocks.getInhabitPlanetMock();

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Creature("Зорк", planet, List.of(), true)
        );

        assertEquals("Мохнатые существа живут только на Альфа Центавре", exception.getMessage());
    }

    @Test
    @DisplayName("Существо не умеет склонять слова")
    void testCreatureCannotInclineWords() {
        Planet alpha = ModelMocks.getInhabitPlanetMock();
        alpha.setName("Альфа Центавра");

        Creature creature = new Creature("Боб", alpha, List.of(), true);
        Word word = new Word("склон");

        NoSuchMethodException exception = assertThrows(NoSuchMethodException.class, () ->
                creature.inclineWord(word)
        );

        assertEquals("Боб не умеет склонять слова", exception.getMessage());
    }
}
