package ru.ifmo.testing.utils;

import org.junit.jupiter.api.Test;

public class ProxyTest extends BaseTest {
    @Override
    protected boolean useSelenide() {
        return false;
    }

    @Override
    public void acceptCookies() {
    }

    @Test
    public void test() throws InterruptedException {
        driver.get("https://httpbin.io/ip");
        Thread.sleep(2000);
    }
}
