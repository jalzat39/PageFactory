import static org.assertj.core.api.Assertions.assertThat;

import org.example.core.Browser;
import org.example.todo.components.TodoItemComponent;
import org.example.todo.pages.TodosPage;
import org.example.todo.pages.TodosPage.Filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
/* ************************************************************************************************/

/* ************************************************************************************************/
@TestInstance(Lifecycle.PER_CLASS)   // Having a single instance of the class for all tests
@Execution(ExecutionMode.CONCURRENT) // If we try to run the test concurrently
public class ReiWayTest {

    private static final Logger log = LogManager.getLogger();

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void teardown() {
        if (driver != null && !driver.toString().contains("null")) {
            driver.quit();
        }
    }

    @Test
    void newTodoItemCreation(final TestInfo testInfo) {
        final EntryMessage entryMessage = log.traceEntry("Running test {}", testInfo.getDisplayName());

        Browser.navigate(() -> driver, browser -> {
            // Given I navigate to the todos page
            final TodosPage page = browser.goTo(TodosPage::new, "https://todomvc.com/examples/vue/dist/#/");

            // When I create a new todo
            page.createNewTodo("Prepare the session for the guys and gals");

            // Then there should be only one todo item with the expected text
            assertThat(page.getTodoItems())
                    .describedAs("Only one todo item must exists and it should have the expected text")
                    .hasSize(1)
                    .first()
                    .extracting(TodoItemComponent::getContent)
                    .isEqualTo("Prepare the session for the guys and gals");

            pause();
        });

        log.traceExit(entryMessage);
    }

    @Test
    void newTodoItemCreationAndFilterByActive(final TestInfo testInfo) {
        final EntryMessage entryMessage = log.traceEntry("Running test {}", testInfo.getDisplayName());

        Browser.navigate(() -> driver, browser -> {
            // Given I navigate to the todos page
            final TodosPage page = browser.goTo(TodosPage::new, "https://todomvc.com/examples/vue/dist/#/");

            // When I create a new todo and filter by Active
            page.createNewTodo("Prepare the session for the guys and gals")
                    .filterBy(Filter.ACTIVE);

            // Then there should be only one todo item with the expected text
            assertThat(page.getTodoItems())
                    .describedAs("Only one todo item must exists and it should have the expected text")
                    .hasSize(1)
                    .first()
                    .extracting(TodoItemComponent::getContent)
                    .isEqualTo("Prepare the session for the guys and gals");

            pause();
        });

        log.traceExit(entryMessage);
    }

    @Test
    void complexInteraction(final TestInfo testInfo) {
        final EntryMessage entryMessage = log.traceEntry("Running test {}", testInfo.getDisplayName());

        Browser.navigate(() -> driver, browser -> {
            // Given I navigate to the todos page
            final TodosPage page = browser.goTo(TodosPage::new, "https://todomvc.com/examples/vue/dist/#/");

            // When I get creative
            page.createNewTodo("Active todo")
                    .createNewTodo("Completed todo")
                    .getTodoItems()
                    .get(1)
                    .toggle()
                    .getPage()
                    .filterBy(Filter.COMPLETED)
                    .getTodoItems()
                    .get(0)
                    .destroy()
                    .filterBy(Filter.ACTIVE);

            // Then there should be only one todo item with the expected text
            assertThat(page.getTodoItems())
                    .describedAs("Only one todo item must exists and it should have the expected text")
                    .hasSize(1)
                    .first()
                    .extracting(TodoItemComponent::getContent)
                    .isEqualTo("Active todo");

            pause();
        });

        log.traceExit(entryMessage);
    }

    private static void pause() {
        try {
            Thread.sleep(5000L);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }
}

