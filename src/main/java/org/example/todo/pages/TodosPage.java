package org.example.todo.pages;

import org.example.todo.components.TodoItemComponent;
import org.example.core.Page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public final class TodosPage extends Page {

    private static final By selectAllLabel = By.cssSelector("label[for='toggle-all']");

    private static final By newTodoInbox = By.className("new-todo");

    private static final By todoItems = By.className("todo-list");

    private static final By todoElement = By.className("view");

    private static final By clearCompletedButton = By.className("clear-completed");

    private static final By todoCountSpan = By.className("todo-count");

    public enum Filter {
        ALL(By.linkText("All")),
        ACTIVE(By.linkText("Active")),
        COMPLETED(By.linkText("Completed"));

        private final By selector;

        private Filter(final By selector) {
            this.selector = selector;
        }
    }

    public TodosPage(final WebDriver webDriver) {
        super(webDriver);
    }

    public TodosPage toggleSelectAll() {
        waitFor(elementToBeClickable(selectAllLabel)).click();

        return this;
    }

    public TodosPage createNewTodo(final String whatNeedsToBeDone) {
        waitFor(presenceOfElementLocated(newTodoInbox)).sendKeys(whatNeedsToBeDone, Keys.ENTER);

        return this;
    }

    public List<TodoItemComponent<TodosPage>> getTodoItems() {
        return hasMany(TodoItemComponent::new, waitFor(presenceOfNestedElementsLocatedBy(todoItems, todoElement)));
    }

    public TodosPage filterBy(final Filter filter) {
        waitFor(elementToBeClickable(filter.selector)).click();

        return this;
    }

    public TodosPage clearCompleted() {
        waitFor(elementToBeClickable(clearCompletedButton)).click();

        return this;
    }
}

