package ru.ifmo.testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.ifmo.testing.utils.SelenideTests;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты списка фрилансеров (Selenide)")
public class FreelancerSelenideTests extends SelenideTests {
    @BeforeEach
    public void openPage() {
        flPage.openPage();
        acceptCookies();
    }

    @Test
    @DisplayName("[Фрилансеры] Пустой список")
    public void testEmptyFreelancersList() {
        flPage.searchWithEmptyResults();

        waitForText("Фрилансеров не найдено");
    }

    @Test
    @DisplayName("[Фрилансеры] Смена специализации и тегов")
    public void testSpecializationAndTagsChange() {
        flPage.clickAdvancedSearch();
        flPage.clickCategory();

        flPage.selectWebsitesSpecialization();
        pressKeyTab();

        flPage.clickFindExecutor();

        waitForUrl("/freelancers/saity/");

        flPage.fillCostFrom("1000");
        flPage.clickFindExecutor();

        waitForUrl("from_cost=1000");

        flPage.waitForFreelancerCard();
        assertEquals(flPage.getFreelancerCardName(), "Сергей Сивков",
                "Должна отображаться карточка 'Сергей Сивков'");
    }

    @Test
    @DisplayName("Сброс фильтров")
    public void testFiltersReset() {
        flPage.clickAdvancedSearch();
        flPage.fillExperience("2", "5");
        flPage.fillCostFrom("500");
        flPage.clickFindExecutor();

        waitForUrl("exp%5B0%5D=2&exp%5B1%5D=5");
        waitForUrl("from_cost=500");

        flPage.resetFilters();

        waitForUrl("exp%5B0%5D=&exp%5B1%5D=&");
        waitForUrl("from_cost=&");
    }
}