package org.example.core;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class Navigable implements SearchContext {

    private static final Duration defaultWaitDuration = Duration.ofSeconds(10L);

    private final WebDriver webDriver;

    private final WebDriverWait webDriverWait;

    public Navigable(final WebDriver webDriver) {
        this.webDriver = webDriver;
        this.webDriverWait = new WebDriverWait(webDriver, defaultWaitDuration);
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    protected <V> V waitFor(final Function<? super WebDriver, V> isTrue) {
        return webDriverWait.until(isTrue);
    }

    @Override
    public WebElement findElement(final By by) {
        return getWebDriver().findElement(by);
    }

    @Override
    public List<WebElement> findElements(final By by) {
        return getWebDriver().findElements(by);
    }
}

