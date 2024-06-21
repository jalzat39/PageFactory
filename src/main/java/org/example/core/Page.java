package org.example.core;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class Page extends Navigable {

    public Page(final WebDriver webDriver) {
        super(webDriver);
    }

    @SuppressWarnings("unchecked")
    protected <P extends Page, C extends Component<P>> C hasA(
            final BiFunction<P, WebElement, C> componentFactory,
            final WebElement rootElement) {
        return componentFactory.apply((P) this, rootElement);
    }

    protected <P extends Page, C extends Component<P>> List<C> hasMany(
            final BiFunction<P, WebElement, C> componentFactory,
            final List<WebElement> rootElements) {
        if (rootElements != null) {
            return rootElements.stream()
                    .map(element -> hasA(componentFactory, element))
                    .toList();
        }

        return Collections.emptyList();
    }
}

