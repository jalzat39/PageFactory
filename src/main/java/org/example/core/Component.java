package org.example.core;

import org.openqa.selenium.WebElement;

public abstract class Component<P extends Page> extends Navigable {

    private final P page;

    private final WebElement rootElement;

    public Component(final P page, final WebElement rootElement) {
        super(page.getWebDriver());
        this.page = page;
        this.rootElement = rootElement;
    }

    public P getPage() {
        return page;
    }

    protected WebElement getRootElement() {
        return rootElement;
    }
}

