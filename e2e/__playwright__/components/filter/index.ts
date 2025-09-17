import { Locator, Page } from '@playwright/test';

export class FilterComponent {
    private page: Page;
    private categorySelect: Locator;
    private specializationSelect: Locator;
    private keywordsInput: Locator;
    private budgetFromInput: Locator;
    private countryInput: Locator;
    private applyFiltersButton: Locator;
    private resetButton: Locator;
    private mobileFiltersButton: Locator;

    constructor(page: Page) {
        this.page = page;
        this.categorySelect = page.getByTestId('qa-head-buy-pro');
        this.specializationSelect = page.getByTitle('Меню профиля');
        this.keywordsInput = page.locator('#navbarLeftDropdown');
        this.budgetFromInput = page.getByTestId('qa-ui-input-budgetFrom');
        this.countryInput = page.getByTestId('qa-select-new-filter-country');
        this.applyFiltersButton = page.getByText('Применить фильтр');
        this.resetButton = page.getByText('Сбросить');
        this.mobileFiltersButton = page.getByRole('button', { name: 'Фильтры' });
    }

    async selectCategory(category: string) {
        await this.categorySelect.click();
        await this.page.locator(`text="${category}"`).click();
    }

    async selectSpecialization(specialization: string) {
        await this.specializationSelect.click();
        await this.page.locator(`text="${specialization}"`).click();
    }

    async enterKeywords(keywords: string) {
        await this.keywordsInput.fill(keywords);
    }

    async enterBudgetFrom(amount: string) {
        await this.budgetFromInput.fill(amount);
    }

    async enterCity(city: string) {
        await this.countryInput.fill(city);
    }

    async clickApplyFilters() {
        await this.applyFiltersButton.click();
    }

    async clickReset() {
        await this.resetButton.click();
    }

    async clickMobileFilters() {
        await this.mobileFiltersButton.click();
    }

    async isCategorySelectDisabled(): Promise<boolean> {
        return await this.categorySelect.isDisabled();
    }

    async isSpecializationSelectDisabled(): Promise<boolean> {
        return await this.specializationSelect.isDisabled();
    }

    async getCategoryDropdownOptions(): Promise<string[]> {
        await this.categorySelect.click();
        const options = this.page.locator('.vs__dropdown_menu').locator('li');
        return await options.allTextContents();
    }

    async getSpecializationDropdownOptions(): Promise<string[]> {
        await this.specializationSelect.click();
        const options = this.page.locator('[data-testid="specialization-dropdown"] option');
        return await options.allTextContents();
    }
}