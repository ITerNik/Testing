package third.models;

import config.TimingRules;
import mocks.ModelMocks;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ifmo.third.models.creatures.Creature;
import ru.ifmo.third.models.environment.Planet;
import ru.ifmo.third.models.words.Word;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(TimingRules.class)
@ExtendWith(MockitoExtension.class)
public class CreatureTests {
    @Mock
    private Planet planet;

    @BeforeEach
    public void setUp() {
        when(planet.getName()).thenReturn("Альфа Центавра");
    }

    @AfterEach
    public void cleanUp() {
        Mockito.reset(planet);
    }

    @Test
    @DisplayName("Создание существа с правильной планетой")
    void testCreatureCreationSuccess() {
        Creature creature = new Creature("Мохнатик", planet, List.of(), true);

        assertEquals("Мохнатик", creature.getName());
        assertEquals(planet, creature.getPlanet());
        assertTrue(creature.isAuthentic());
        assertTrue(creature.getFears().isEmpty());
    }

    @Test
    @DisplayName("Мохнатые существа могут жить только на Альфа Центавре")
    void testCreatureCreationFailure() {
        when(planet.getName()).thenReturn("Земля");

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Creature("Зорк", planet, List.of(), true)
        );

        assertEquals("Мохнатые существа живут только на Альфа Центавре", exception.getMessage());
    }

    @Test
    @DisplayName("Существо не умеет склонять слова")
    void testCreatureCannotInclineWords() {
        Creature creature = new Creature("Боб", planet, List.of(), true);

        Word word = mock(Word.class);

        NoSuchMethodException exception = assertThrows(NoSuchMethodException.class, () ->
                creature.inclineWord(word)
        );

        assertEquals("Боб не умеет склонять слова", exception.getMessage());
        verify(word, never()).incline();
    }
}
