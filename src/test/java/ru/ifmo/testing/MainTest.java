package ru.ifmo.testing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import ru.ifmo.testing.utils.BaseTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest extends BaseTest {
    private static final Logger LOGGER = LogManager.getLogger();

    @Test
    public void shouldValidateTitle() {
        String title = mainPage.getTitle().trim();
        mainPage.shouldBeVisibleByText("Разместить заказ");
        assertEquals(title.trim(), "Крупнейшая биржа фриланса с лучшими профессионалами рунета. Фриланс и удаленная работа - FL.ru");
        LOGGER.info("Title found: {}", title);
    }
}
