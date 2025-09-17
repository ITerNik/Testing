import { defineConfig, devices } from '@playwright/test';

export default defineConfig({
    testDir: './__playwright__/tests',
    timeout: 30 * 1000,
    expect: {
        timeout: 5000
    },
    workers: 4,
    fullyParallel: true,
    reporter: [
        ['html', { outputFolder: 'playwright-report' }],
        ['allure-playwright']
    ],
    use: {
        baseURL: 'https://www.fl.ru',
        testIdAttribute: 'data-id',
        actionTimeout: 0,
        trace: 'on-first-retry',
        video: 'retain-on-failure',
        screenshot: 'only-on-failure',
    },

    projects: [
        {
            name: 'chromium',
            use: { ...devices['Desktop Chrome'] },
        },
        {
            name: 'firefox',
            use: { ...devices['Desktop Firefox'] },
        },
        {
            name: 'webkit',
            use: { ...devices['Desktop Safari'] },
        },
        {
            name: 'Mobile Chrome',
            use: { ...devices['Pixel 5'] },
        },
        {
            name: 'Mobile Safari',
            use: { ...devices['iPhone 12'] },
        },
    ],
});