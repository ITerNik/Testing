import { Page, expect } from '@playwright/test';
import {FilterComponent} from "../../components/filter";

export class ProjectsPage {
    private page: Page;
    private filterComponent: FilterComponent;
    private noProjectsMessage: string;

    constructor(page: Page) {
        this.page = page;
        this.filterComponent = new FilterComponent(page);
        this.noProjectsMessage = 'Проектов не найдено';
    }

    async navigate() {
        await this.page.goto('https://www.fl.ru/projects/');
    }

    get filters() {
        return this.filterComponent;
    }

    async expectNoProjectsMessage() {
        await expect(this.page.locator(`text="${this.noProjectsMessage}"`)).toBeVisible();
    }

    async expectValidationError(message: string) {
        await expect(this.page.locator(`text="${message}"`)).toBeVisible();
    }

    async expectFilterRequest(withFilters: boolean = true) {
        const expectedUrl = withFilters
            ? /https:\/\/www\.fl\.ru\/projects\/session\/filter\/.*/
            : 'https://www.fl.ru/projects/session/filter/';

        await this.page.waitForRequest(expectedUrl);
    }

    async expectProfessionsRequest() {
        await this.page.waitForRequest('https://www.fl.ru/prof_groups/professions/');
    }
}