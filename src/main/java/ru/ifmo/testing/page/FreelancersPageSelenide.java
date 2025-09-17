package ru.ifmo.testing.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.ScrollIntoViewOptions;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class FreelancersPageSelenide {
    private final SelenideElement advancedSearchButton = $x("//a[@id='search-advanced-button']");
    private final ElementsCollection experienceFields = $$x("//input[@name='exp[]']");
    private final SelenideElement experienceFromField = experienceFields.get(0);
    private final SelenideElement experienceToField = experienceFields.get(1);
    @Getter
    private final SelenideElement acceptFiltersButton = $x("//button[@data-id='tpl-form-search-exp']");
    private final SelenideElement categoryDropdown = $x("//input[@id='profession']");
    private final SelenideElement websitesSpecialization = $x("//span[contains(text(), 'Сайты')]");
    private final SelenideElement costFromField = $x("//input[@name='from_cost']");
    private final ElementsCollection freelancerCards = $$x("//div[@class='cf-user-in']");
    private final SelenideElement firstFreelancerCard = freelancerCards.get(0);
    private final SelenideElement resetButton = $x("//a[text()='(сбросить настройки)']");


    public void openPage() {
        open("/freelancers/");
    }

    public void clickAdvancedSearch() {
        advancedSearchButton.click();
    }

    public void resetFilters() {
        resetButton.click();
        clickFindExecutor();
    }

    public void fillExperienceFrom(String experience) {
        experienceFromField.setValue(experience);
    }

    public void fillExperienceTo(String experience) {
        experienceToField.setValue(experience);
    }

    public void fillExperience(String from, String to) {
        fillExperienceFrom(from);
        fillExperienceTo(to);
    }


    public void clickFindExecutor() {
        acceptFiltersButton.scrollIntoView(new ScrollIntoViewOptions(
                ScrollIntoViewOptions.Behavior.instant,
                ScrollIntoViewOptions.Block.center,
                ScrollIntoViewOptions.Inline.center));
        acceptFiltersButton.click();
    }

    public void clickCategory() {
        categoryDropdown.click();
    }

    public void selectWebsitesSpecialization() {
        websitesSpecialization.click();
    }

    public void fillCostFrom(String cost) {
        costFromField.setValue(cost);
    }

    public void waitForFreelancerCard() {
        firstFreelancerCard.shouldBe(visible);
    }

    public String getFreelancerCardName() {
        return firstFreelancerCard.$x(".//a").getText();
    }

    public String getCurrentUrl() {
        return com.codeborne.selenide.WebDriverRunner.url();
    }

    public void searchWithEmptyResults() {
        clickAdvancedSearch();
        fillExperience("100", "100");
        clickFindExecutor();
    }

    public void searchWithSpecializationAndCost() {
        clickAdvancedSearch();
        clickCategory();
        selectWebsitesSpecialization();
        fillCostFrom("1000");
        clickFindExecutor();
    }

    public boolean hasExperienceParams() {
        String url = getCurrentUrl();
        return url.contains("experience_from") || url.contains("from_experience");
    }

    public boolean hasCostParams() {
        String url = getCurrentUrl();
        return url.contains("cost_from") || url.contains("from_cost");
    }

    public boolean hasSpecializationParam() {
        String url = getCurrentUrl();
        return url.contains("profession=") || url.contains("specialization=");
    }
}