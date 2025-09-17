package ru.ifmo.testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.ifmo.testing.page.SearchPage;
import ru.ifmo.testing.utils.SeleniumTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты поиска (Selenium)")
public class SearchSeleniumTests extends SeleniumTests {
    @BeforeEach
    public void setUp() {
        super.setUp();
        searchPage.open();
    }

    @Test
    @DisplayName("Поиск без ввода данных")
    public void testSearchWithoutInput() {
        SearchPage searchPage = new SearchPage(driver);
        String suggestion = searchPage.getSuggestionText();
        searchPage.searchEmpty();

        assertEquals(suggestion.replace("Например, ", ""), searchPage.getSearchText(),
                "Должно подставляться значение из рекомендаций");
    }

    @Test
    @DisplayName("Поиск по ключевому слову")
    public void testSearchByKeyword() {
        searchPage.search("joomshopping");

        assertTrue(searchPage.getFirstResultText().contains("Дмитрий Акиндинов"),
                "Должна отображаться карточка 'Дмитрий Акиндинов'");
    }

    @Test
    @DisplayName("Перезагрузка сохраняет запрос в поиске")
    public void testSearchOnReload() {
        searchPage.search("key");

        driver.navigate().refresh();

        assertEquals(searchPage.getSearchText(), "key", "Результат поиска должен сохраниться");
    }
}