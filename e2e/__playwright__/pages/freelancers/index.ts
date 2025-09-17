import { Page, expect } from '@playwright/test';
import path from 'path';

export class FreelancersPage {
    private page: Page;

    constructor(page: Page) {
        this.page = page;
    }

    async navigate() {
        await this.page.goto('https://www.fl.ru/freelancers');
    }

    async setViewportWidth(width: number) {
        await this.page.setViewportSize({ width, height: 800 });
    }

    async takeScreenshot(name: string) {
        return await this.page.screenshot({
            path: `${name}.png`,
        });
    }

    async expectScreenshotMatch(name: string) {
        await expect(this.page).toHaveScreenshot(`${name}.png`, {
            stylePath: path.join(__dirname, "..", "..", "screenshots.css"),
        });
    }
}