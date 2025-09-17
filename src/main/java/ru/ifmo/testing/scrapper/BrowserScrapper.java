package ru.ifmo.testing.scrapper;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.AbstractDriverOptions;
import ru.ifmo.testing.utils.PropertyLoader;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class BrowserScrapper {
    public static void proxify(AbstractDriverOptions<?> options) {
        List<String> proxyList = PropertyLoader.returnConfigValues("proxy.addresses");

        System.out.println(proxyList);
        Random rand = new Random(new Date().getTime());
        String proxyAddress = proxyList.get(rand.nextInt(proxyList.size()));

        Proxy proxy = new Proxy();
        proxy.setHttpProxy(proxyAddress);
        proxy.setSslProxy(proxyAddress);

        options.setProxy(proxy);
    }
}
