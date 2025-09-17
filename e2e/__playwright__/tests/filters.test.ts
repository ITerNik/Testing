// tests/filters.spec.ts
import { test, expect } from '@playwright/test';
import {ProjectsPage} from "../pages/projects";

test.describe('Фильтры проектов', () => {
    let projectsPage: ProjectsPage;

    test.beforeEach(async ({ page }) => {
        projectsPage = new ProjectsPage(page);
        await projectsPage.navigate();
    });

    test('#124 [Работа] Пустой список', async () => {
        await projectsPage.filters.enterKeywords('Abracadabra');
        await projectsPage.filters.clickApplyFilters();
        await projectsPage.expectNoProjectsMessage();
    });

    test('#129 [Работа] Нельзя выбрать специализацию без категории', async () => {
        // Проверяем, что специализация заблокирована
        expect(await projectsPage.filters.isSpecializationSelectDisabled()).toBe(true);

        // Выбираем категорию
        await projectsPage.filters.selectCategory('Сайты');
        await projectsPage.expectProfessionsRequest();

        // Теперь специализация должна быть доступна
        expect(await projectsPage.filters.isSpecializationSelectDisabled()).toBe(false);

        const options = await projectsPage.filters.getSpecializationDropdownOptions();
        expect(options.length).toBeGreaterThan(0);
    });

    test('#126 [Работа] Сброс фильтров', async () => {
        // Устанавливаем фильтры
        await projectsPage.filters.selectCategory('Сайты');
        await projectsPage.filters.selectSpecialization('Дизайн');
        await projectsPage.filters.enterBudgetFrom('1000');
        await projectsPage.filters.enterCity('Москва');
        await projectsPage.filters.clickApplyFilters();

        await projectsPage.expectFilterRequest(true);

        // Сбрасываем фильтры
        await projectsPage.filters.clickReset();
        await projectsPage.filters.clickApplyFilters();

        await projectsPage.expectFilterRequest(false);
    });

    test('#127 [Работа] Валидация ввода', async ({ page }) => {
        // Тест валидации категории
        await page.locator('[data-testid="category-select"]').fill('Abracadabra');
        await projectsPage.expectValidationError('Sorry, no matching options');

        await page.locator('body').click(); // Расфокусировка
        expect(await page.locator('[data-testid="category-select"]').inputValue()).toBe('');


        // Тест валидации бюджета
        await projectsPage.filters.enterBudgetFrom('1000000');
        await projectsPage.expectValidationError('Максимальная длина поля "cost min.amount" - 999999 символа');
        await projectsPage.filters.clickApplyFilters();

        await page.waitForRequest(async (request) =>
            request.url().includes('https://www.fl.ru/projects/session/filter/') &&
            !(await request.response())?.ok
        );
    });

    test('#125 [Работа] Мобильная версия', async ({ page }) => {
        await page.setViewportSize({ width: 375, height: 667 }); // Mobile viewport

        await projectsPage.filters.clickMobileFilters();
        await expect(page.locator('[data-testid="filters-modal"]')).toBeVisible();

        await projectsPage.filters.selectCategory('Сайты');
        await projectsPage.expectFilterRequest();

        await projectsPage.filters.enterBudgetFrom('1000');
        await projectsPage.expectFilterRequest();
    });
});