package third.models;

import config.TimingRules;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;
import ru.ifmo.third.model.adventures.Adventure;
import ru.ifmo.third.model.alive.SpaceCaptain;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(TimingRules.class)
@DisplayName("Сущность капитана")
class SpaceCaptainTests {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SpaceCaptainTests.class);
    private SpaceCaptain captain;
    private Adventure mockAdventure;
    private Logger logger;
    private Handler mockHandler;

    @BeforeEach
    void setUp() {
        captain = new SpaceCaptain("Капитан Звездный");
        mockAdventure = mock(Adventure.class);
        logger = Logger.getLogger(SpaceCaptain.class.getName());
        mockHandler = mock(Handler.class);
        logger.addHandler(mockHandler);
    }

    @AfterEach
    void cleanUp() {
        logger.removeHandler(mockHandler);
    }

    @Test
    @DisplayName("Капитан успешно начинает приключение")
    void testCaptainStartsAdventureSuccessfully() {
        captain.beginAdventure(mockAdventure);

        assertTrue(captain.isOnAdventure());
        verify(mockAdventure).addParticipant(captain);
    }

    @Test
    @DisplayName("Капитан не может начать второе приключение, пока участвует в первом")
    void testCaptainCannotStartTwoAdventures() {
        captain.beginAdventure(mockAdventure);

        Adventure secondAdventure = mock(Adventure.class);
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            captain.beginAdventure(secondAdventure);
        });

        assertEquals("Капитан Звездный уже участвует в приключении!", exception.getMessage());
        verify(secondAdventure, never()).addParticipant(captain);
    }

    @Test
    @DisplayName("Капитан успешно завершает приключение")
    void testCaptainEndsAdventureSuccessfully() {
        captain.beginAdventure(mockAdventure);
        captain.endAdventure();

        assertFalse(captain.isOnAdventure());
        verify(mockAdventure).addParticipant(captain);
    }

    @Test
    @DisplayName("Завершение приключения без начала не вызывает ошибок и логируется")
    void testEndAdventureWhenNoneStartedDoesNotThrow() {
        captain.endAdventure();

        verify(mockHandler, atLeastOnce()).publish(Mockito.argThat((LogRecord lr) ->
                lr.getMessage().contains("не находится в приключении")));

    }

    @Test
    @DisplayName("Логгирование начала приключения работает корректно")
    void testLoggerWhenAdventureStarts() {
        logger.addHandler(mockHandler);

        captain.beginAdventure(mockAdventure);

        verify(mockHandler, atLeastOnce()).publish(Mockito.argThat((LogRecord lr) ->
                lr.getMessage().contains("начинает новое приключение")));
    }

    @Test
    @DisplayName("Логгирование завершения приключения работает корректно")
    void testLoggerWhenAdventureEnds() {
        captain.beginAdventure(mockAdventure);
        captain.endAdventure();

        verify(mockHandler, atLeastOnce()).publish(Mockito.argThat((LogRecord lr) ->
                lr.getMessage().contains("завершает приключение")));

    }
}