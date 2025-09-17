import { Page, expect } from '@playwright/test';
import {NavigationComponent} from "../../components/navigation";

export class MainPage {
    private page: Page;
    private readonly navigation: NavigationComponent;

    constructor(page: Page) {
        this.page = page;
        this.navigation = new NavigationComponent(page);
    }

    async navigate() {
        await this.page.goto('https://www.fl.ru/');
    }

    get nav() {
        return this.navigation;
    }

    async setViewportWidth(width: number) {
        await this.page.setViewportSize({ width, height: 800 });
    }

    async expectTilesInColumns(columns: number) {
        const tilesContainer = this.page.locator('.mb-66 > .row');
        await expect(tilesContainer).toHaveCSS('grid-template-columns', `repeat(${columns}, 1fr)`);
    }

    async expectSpecializationsInColumns(columns: number) {
        const specializationsContainer = this.page.locator('.fl-home-page__spec-list');
        await expect(specializationsContainer).toHaveCSS('grid-template-columns', `repeat(${columns}, 1fr)`);
    }

    async clickFirstFreelancerTile() {
        await this.page.locator('.fl-card-freelancer').first().click();
    }

    async clickEmptySpace() {
        await this.page.locator('body').click({ position: { x: 100, y: 100 } });
    }

    async expectYandexMetricaRequest(expectedUrl: string) {
        await this.page.waitForRequest(request =>
            request.url().includes('https://mc.yandex.ru/webvisor')
        );
    }
}
