package org.example.todo.components;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfNestedElementLocatedBy;

import org.example.core.Component;
import org.example.core.Page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public final class TodoItemComponent<P extends Page> extends Component<P> {

    private static final By toggleInput = By.className("toggle");

    private static final By contentLabel = By.tagName("label");

    private static final By destroyButton = By.tagName("button");

    public TodoItemComponent(final P page, final WebElement rootElement) {
        super(page, rootElement);
    }

    public TodoItemComponent<P> toggle() {
        waitFor(presenceOfNestedElementLocatedBy(getRootElement(), toggleInput)).click();

        return this;
    }

    public String getContent() {
        return waitFor(presenceOfNestedElementLocatedBy(getRootElement(), contentLabel)).getText();
    }

    public P destroy() {
        new Actions(getWebDriver())
                .moveToElement(getRootElement())
                .click(waitFor(presenceOfNestedElementLocatedBy(getRootElement(), destroyButton)))
                .build()
                .perform();

        return getPage();
    }
}

