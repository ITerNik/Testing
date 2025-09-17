import { Locator, Page } from '@playwright/test';

export class NavigationComponent {
    private moreMenuButton: Locator;
    private profileAvatar: Locator;
    private chatsLink: Locator;
    private responsesLink: Locator;

    constructor(page: Page) {
        this.moreMenuButton = page.locator('#navbarLeftDropdown');
        this.profileAvatar = page.getByTestId('qa-head-profile');
        this.chatsLink = page.getByTestId('qa-head-messages');
        this.responsesLink = page.getByTestId('qa-head-my-offers');
    }

    async clickMoreMenu() {
        await this.moreMenuButton.click();
    }

    async clickProfileAvatar() {
        await this.profileAvatar.click();
    }

    async isChatsVisible(): Promise<boolean> {
        return await this.chatsLink.isVisible();
    }

    async isResponsesVisible(): Promise<boolean> {
        return await this.responsesLink.isVisible();
    }
}