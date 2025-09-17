// tests/responsive.spec.ts
import { test, expect } from '@playwright/test';
import {FreelancersPage} from "../pages/freelancers";
import {MainPage} from "../pages/main";
import {COOKIE_VALUE, parseCookieString} from "../utils/cookie";

test.describe('Адаптивность', () => {
    test('#81 Верстка страницы фрилансеров на разных устройствах', async ({ page }) => {
        const freelancersPage = new FreelancersPage(page);
        await freelancersPage.navigate();

        await freelancersPage.setViewportWidth(1200);
        await freelancersPage.expectScreenshotMatch('freelancers-desktop');

        await freelancersPage.setViewportWidth(768);
        await freelancersPage.expectScreenshotMatch('freelancers-tablet');

        await freelancersPage.setViewportWidth(375);
        await freelancersPage.expectScreenshotMatch('freelancers-mobile');
    });

    test('#79 Адаптивность страницы общего каталога', async ({ page }) => {
        const mainPage = new MainPage(page);
        const cookies = parseCookieString(COOKIE_VALUE, "www.fl.ru");

        await page.context().addCookies(cookies);

        await mainPage.navigate();


        await mainPage.setViewportWidth(1200);
        expect(await mainPage.nav.isChatsVisible()).toBe(true);
        expect(await mainPage.nav.isResponsesVisible()).toBe(true);

        await mainPage.setViewportWidth(700);
        expect(await mainPage.nav.isChatsVisible()).toBe(false);
        expect(await mainPage.nav.isResponsesVisible()).toBe(false);

        await mainPage.setViewportWidth(375);
        expect(await mainPage.nav.isChatsVisible()).toBe(false);
        expect(await mainPage.nav.isResponsesVisible()).toBe(false);
    });
});
