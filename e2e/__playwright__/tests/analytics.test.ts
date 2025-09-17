import { test } from '@playwright/test';
import {MainPage} from "../pages/main";
import {ProjectsPage} from "../pages/projects";

test.describe('Аналитика', () => {
    test('#80 Проверка разметки событий страницы', async ({ page }) => {
        const mainPage = new MainPage(page);
        await mainPage.navigate();

        await Promise.all([mainPage.expectYandexMetricaRequest('pointer-click='), mainPage.clickEmptySpace()]);
        await Promise.all([mainPage.expectYandexMetricaRequest('pageUrl=goal://www.fl.ru/click_header_more_button_menu'), mainPage.nav.clickMoreMenu()]);
        await Promise.all([mainPage.expectYandexMetricaRequest('pageUrl=goal://www.fl.ru/click_right_menu_avatar'), mainPage.nav.clickProfileAvatar()]);
        await Promise.all([mainPage.expectYandexMetricaRequest('pageUrl=goal://www.fl.ru/click-best-freelancers'), mainPage.clickFirstFreelancerTile()]);
    });

    test('#123 [Работа] Смена фильтров', async ({ page }) => {
        const projectsPage = new ProjectsPage(page);
        await projectsPage.navigate();

        await projectsPage.filters.selectCategory('Сайты');
        await projectsPage.expectFilterRequest();

        await projectsPage.filters.enterBudgetFrom('1000');
        await projectsPage.expectFilterRequest();
    });
});