package third.models;

import config.TimingRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.ifmo.third.model.Action;
import ru.ifmo.third.model.Empire;
import ru.ifmo.third.model.alive.LiveEntity;
import ru.ifmo.third.model.reactions.Reaction;
import ru.ifmo.third.model.triggers.Trigger;
import ru.ifmo.third.model.triggers.TriggerHandler;

import java.lang.reflect.Field;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(TimingRules.class)
@DisplayName("Обработчик событий")
class TriggerHandlerTests {

    private TriggerHandler handler;
    private LiveEntity mockEntity;
    private Reaction mockReaction;
    private Empire spyEmpire;

    @BeforeEach
    void setup() throws NoSuchFieldException, IllegalAccessException {
        handler = new TriggerHandler();
        mockEntity = mock(LiveEntity.class);
        when(mockEntity.getName()).thenReturn("Zorgon");
        Field instanceField = Empire.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        spyEmpire = spy(Empire.getInstance());
        instanceField.set(null, spyEmpire);
        mockReaction = mock(Reaction.class);
    }

    @Test
    @DisplayName("Приступ героизма вызывает подъем духа")
    void testHeroicActReaction() {
        handler.handleTrigger(Trigger.HEROIC_ACT, mockEntity);

        verify(spyEmpire, times(1)).influenceBy(Action.SEEK_GLORY);
    }

    @Test
    @DisplayName("Климат планеты вызывает смущение")
    void testStrangePlanetReaction() {
        handler.handleTrigger(Trigger.STRANGE_PLANET, mockEntity);

        verify(spyEmpire, times(1)).influenceBy(Action.IGNORE);
    }

    @Test
    @DisplayName("Страх неизвестного вызывает смущение")
    void testUnknownTriggerUsesDefaultReaction() {
        handler.handleTrigger(Trigger.UNKNOWN, mockEntity);

        verify(spyEmpire, times(1)).influenceBy(Action.PANIC);
    }

    @Test
    @DisplayName("Триггер вызывает respond")
    void testReactionRespondIsCalled() {
        when(mockReaction.respond(any(), any())).thenReturn(Action.SEEK_GLORY);
        TriggerHandler handler = new TriggerHandler(Map.of(Trigger.HEROIC_ACT, mockReaction));

        handler.handleTrigger(Trigger.HEROIC_ACT, mockEntity);

        verify(mockReaction, times(1)).respond(Trigger.HEROIC_ACT, mockEntity);
    }

    @Test
    @DisplayName("Триггер вызывает influenceBy")
    void testEmpireInfluenceByIsCalled() {
        when(mockReaction.respond(any(), any())).thenReturn(Action.SEEK_GLORY);
        TriggerHandler handler = new TriggerHandler(Map.of(Trigger.HEROIC_ACT, mockReaction));

        handler.handleTrigger(Trigger.HEROIC_ACT, mockEntity);

        verify(spyEmpire, times(1)).influenceBy(Action.SEEK_GLORY);
    }
}

