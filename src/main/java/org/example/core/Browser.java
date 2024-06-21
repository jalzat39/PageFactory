package org.example.core;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public final class Browser extends Navigable implements WebDriver {

    private static final Supplier<WebDriver> defaultWebDriverFactory = () -> {
        final ChromeDriver webDriver = new ChromeDriver();

        webDriver.manage().window().maximize();

        return webDriver;
    };

    private Browser(final WebDriver webDriver) {
        super(webDriver);
    }

    public static void navigate(
            final Supplier<WebDriver> webDriverFactory,
            final Consumer<Browser> context) {
        final WebDriver webDriver = webDriverFactory.get();

        try {
            context.accept(new Browser(webDriver));
        } catch (final Exception exception) {
            throw exception;
        } finally {
            if (webDriver != null && !webDriver.toString().contains("null")) {
                webDriver.quit();
            }
        }
    }

    public static void navigate(final Consumer<Browser> context) {
        navigate(defaultWebDriverFactory, context);
    }

    public <P extends Page> P goTo(final Function<WebDriver, P> pageFactory, final String url) {
        getWebDriver().get(url);

        return at(pageFactory);
    }

    public <P extends Page> P at(final Function<WebDriver, P> pageFactory) {
        return pageFactory.apply(getWebDriver());
    }

    @Override
    public void get(final String url) {
        getWebDriver().get(url);
    }

    @Override
    public String getCurrentUrl() {
        return getWebDriver().getCurrentUrl();
    }

    @Override
    public String getPageSource() {
        return getWebDriver().getPageSource();
    }

    @Override
    public String getTitle() {
        return getWebDriver().getTitle();
    }

    @Override
    public String getWindowHandle() {
        return getWebDriver().getWindowHandle();
    }

    @Override
    public Set<String> getWindowHandles() {
        return getWebDriver().getWindowHandles();
    }

    @Override
    public Options manage() {
        return getWebDriver().manage();
    }

    @Override
    public Navigation navigate() {
        return getWebDriver().navigate();
    }

    @Override
    public TargetLocator switchTo() {
        return getWebDriver().switchTo();
    }

    @Override
    public void quit() {
        final WebDriver webDriver = getWebDriver();

        if (webDriver != null && !webDriver.toString().contains("null")) {
            webDriver.quit();
        }
    }

    @Override
    public void close() {
        final WebDriver webDriver = getWebDriver();

        if (webDriver != null && !webDriver.toString().contains("null")) {
            webDriver.close();
        }
    }
}