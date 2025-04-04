package third.models;

import config.TimingRules;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import ru.ifmo.third.model.Action;
import ru.ifmo.third.model.adventures.Adventure;
import ru.ifmo.third.model.alive.SpaceCaptain;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(TimingRules.class)
@DisplayName("Сущность приключений")
class AdventureTests {

    private SpaceCaptain captain1;
    private SpaceCaptain captain2;
    private Adventure adventure;

    @BeforeEach
    void setUp() {
        captain1 = mock(SpaceCaptain.class);
        when(captain1.getName()).thenReturn("Капитан Альфа");

        captain2 = mock(SpaceCaptain.class);
        when(captain2.getName()).thenReturn("Капитан Бета");

        adventure = new Adventure(2) {
            @Override
            public Action executeStrategy() {
                return Action.SEEK_GLORY;
            }
        };
    }

    @AfterEach
    public void cleanUp() {
        Mockito.reset(captain1, captain2);
    }

    @Test
    @DisplayName("Капитан добавляется в приключение, если оно не завершено")
    void testAddParticipantWhenNotCompleted() {
        adventure.addParticipant(captain1);
        assertEquals(1, adventure.getParticipants().size());
        assertEquals(captain1, adventure.getParticipants().get(0));
    }

    @Test
    @DisplayName("Капитан не добавляется в завершённое приключение")
    void testAddParticipantWhenCompleted() {
        adventure.completeAdventure();
        adventure.addParticipant(captain2);

        assertEquals(0, adventure.getParticipants().size());
    }

    @Test
    @DisplayName("При достаточном числе участников приключение завершается успехом")
    void testCompleteAdventureSuccess() {
        adventure.addParticipant(captain1);
        adventure.addParticipant(captain2);

        Action result = adventure.completeAdventure();
        assertEquals(Action.SEEK_GLORY, result);
    }

    @Test
    @DisplayName("Если участников недостаточно, приключение завершается неудачей")
    void testCompleteAdventureFailure() {
        adventure.addParticipant(captain1);

        Logger logger = Logger.getLogger(Adventure.class.getName());
        Handler mockHandler = mock(Handler.class);
        logger.addHandler(mockHandler);

        Action result = adventure.completeAdventure();
        assertEquals(Action.FAILURE, result);

        verify(mockHandler, atLeastOnce()).publish(argThat((LogRecord lr) ->
                lr.getLevel().getName().equals("WARNING") &&
                        lr.getMessage().contains("Приключение завершилось провалом")));

        logger.removeHandler(mockHandler);
    }

    @Test
    @DisplayName("При завершении вызывается все участники приключения завершают текущее")
    void testEndAdventureCalledOnAllParticipants() {
        SpaceCaptain realCaptain = mock(SpaceCaptain.class);
        when(realCaptain.getName()).thenReturn("Капитан Гамма");

        adventure.addParticipant(realCaptain);
        adventure.addParticipant(captain1);

        adventure.completeAdventure();

        verify(realCaptain).endAdventure();
        verify(captain1).endAdventure();
        verify(captain2, never()).endAdventure();
    }
}

