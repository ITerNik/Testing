package third.models;


import config.TimingRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.ifmo.third.model.Action;
import ru.ifmo.third.model.Empire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(TimingRules.class)
@DisplayName("Сущность империи")
class EmpireTests {

    @BeforeEach
    void resetEmpire() {
        Empire.reset();
    }

    @Test
    @DisplayName("Империя создается с базовыми характеристиками")
    void testInitialValues() {
        Empire empire = Empire.getInstance();
        assertEquals(100, empire.getGlory());
        assertEquals(100, empire.getStability());
    }

    @Test
    @DisplayName("Изменение характеристик империи после действия")
    void testInfluenceByAction() {
        Empire empire = Empire.getInstance();
        Action action = Action.FAILURE;

        empire.influenceBy(action);

        assertEquals(90, empire.getGlory());
        assertEquals(90, empire.getStability());
    }

    @Test
    @DisplayName("Несколько вызовов накапливаются")
    void testMultipleInfluences() {
        Empire empire = Empire.getInstance();
        empire.influenceBy(Action.FAILURE);
        empire.influenceBy(Action.PANIC);

        assertEquals(90, empire.getGlory());
        assertEquals(70, empire.getStability());
    }

    @Test
    @DisplayName("Проверка Singleton поведения")
    void testSingletonConsistency() {
        Empire e1 = Empire.getInstance();
        Empire e2 = Empire.getInstance();
        assertSame(e1, e2, "Обе ссылки должны указывать на один экземпляр");
    }
}
