import { Page, expect, Locator } from '@playwright/test';

export interface ScreenshotOptions {
    path?: string;
    animations?: 'disabled' | 'allow';
    caret?: 'hide' | 'initial';
    mask?: Locator[];
    clip?: { x: number; y: number; width: number; height: number };
    threshold?: number;
    maxDiffPixels?: number;
    timeout?: number;
}

export class ScreenshotHelper {
    private page: Page;

    constructor(page: Page) {
        this.page = page;
    }

    async takeFullPageScreenshot(name: string, options: ScreenshotOptions = {}) {
        const defaultOptions = {
            fullPage: true,
            animations: 'disabled' as const,
            caret: 'hide' as const,
            path: `screenshots/${name}.png`,
            ...options
        };

        return await this.page.screenshot(defaultOptions);
    }

    async expectFullPageScreenshotMatch(name: string, options: ScreenshotOptions = {}) {
        const defaultOptions = {
            fullPage: true,
            animations: 'disabled' as const,
            caret: 'hide' as const,
            threshold: 0.2,
            maxDiffPixels: 100,
            ...options
        };

        await expect(this.page).toHaveScreenshot(`${name}.png`, defaultOptions);
    }

    async takeElementScreenshot(
        selector: string | Locator,
        name: string,
        options: ScreenshotOptions = {}
    ) {
        const element = typeof selector === 'string' ? this.page.locator(selector) : selector;

        const defaultOptions = {
            animations: 'disabled' as const,
            caret: 'hide' as const,
            path: `screenshots/elements/${name}.png`,
            ...options
        };

        return await element.screenshot(defaultOptions);
    }

    async waitForImagesAndTakeScreenshot(name: string, timeout: number = 10000) {
        // Ожидаем загрузки всех сетевых ресурсов
        await this.page.waitForLoadState('networkidle');

        try {
            // Проверяем, что все изображения загружены
            await this.page.waitForFunction(
                () => {
                    const images = Array.from(document.querySelectorAll('img'));
                    return images.every(img => {
                        // Проверяем, что изображение загружено полностью
                        return img.complete && img.naturalHeight !== 0;
                    });
                },
                { timeout }
            );
        } catch (error) {
            console.warn(`Не удалось дождаться загрузки всех изображений за ${timeout}мс, продолжаем...`);
        }

        // Дополнительная задержка для стабилизации рендеринга
        await this.page.waitForTimeout(500);

        return await this.takeFullPageScreenshot(name);
    }

    async scrollAndTakeFullScreenshot(name: string) {
        // Скроллим до конца страницы для загрузки lazy-контента
        await this.page.evaluate(() => {
            return new Promise<void>((resolve) => {
                let totalHeight = 0;
                const distance = 100;
                const timer = setInterval(() => {
                    const scrollHeight = document.body.scrollHeight;
                    window.scrollBy(0, distance);
                    totalHeight += distance;

                    if (totalHeight >= scrollHeight) {
                        clearInterval(timer);
                        window.scrollTo(0, 0); // Возвращаемся наверх
                        setTimeout(resolve, 1000); // Даем время на рендеринг
                    }
                }, 100);
            });
        });

        return await this.takeFullPageScreenshot(name);
    }

    async takeModalScreenshot(modalSelector: string, name: string) {
        // Ожидаем появления модального окна
        await this.page.waitForSelector(modalSelector, { state: 'visible' });

        // Ожидаем завершения анимации появления
        await this.page.waitForTimeout(300);

        return await this.takeElementScreenshot(modalSelector, name);
    }

    async takeResponsiveScreenshots(
        name: string,
        resolutions: Array<{ width: number; height: number; suffix: string }>
    ) {
        const screenshots: string[] = [];

        for (const resolution of resolutions) {
            await this.page.setViewportSize({
                width: resolution.width,
                height: resolution.height
            });

            // Ждем перестроения layout
            await this.page.waitForTimeout(1000);

            const fileName = `${name}-${resolution.suffix}`;
            await this.takeFullPageScreenshot(fileName);
            screenshots.push(fileName);
        }

        return screenshots;
    }

    async expectElementScreenshotMatch(
        selector: string | Locator,
        name: string,
        options: ScreenshotOptions = {}
    ) {
        const element = typeof selector === 'string' ? this.page.locator(selector) : selector;

        const defaultOptions = {
            animations: 'disabled' as const,
            caret: 'hide' as const,
            threshold: 0.2,
            maxDiffPixels: 50,
            ...options
        };

        await expect(element).toHaveScreenshot(`${name}.png`, defaultOptions);
    }

    async takeHoverScreenshot(selector: string, name: string) {
        await this.page.hover(selector);
        await this.page.waitForTimeout(300); // Ждем завершения hover анимации
        return await this.takeFullPageScreenshot(name);
    }

    async takeScreenshotWithMasks(name: string, maskSelectors: string[]) {
        const masks = maskSelectors.map(selector => this.page.locator(selector));

        return await this.takeFullPageScreenshot(name, { mask: masks });
    }

    async takeScreenshotAfterLoading(name: string, loaderSelectors: string[] = []) {
        const defaultLoaders = [
            '.loader',
            '.spinner',
            '.loading',
            '[data-testid="loading"]',
            '.skeleton'
        ];

        const allLoaders = [...defaultLoaders, ...loaderSelectors];

        // Ожидаем исчезновения всех загрузчиков
        for (const loader of allLoaders) {
            try {
                await this.page.waitForSelector(loader, { state: 'hidden', timeout: 5000 });
            } catch (error) {
                // Игнорируем, если загрузчик не найден
            }
        }

        return await this.takeFullPageScreenshot(name);
    }
}