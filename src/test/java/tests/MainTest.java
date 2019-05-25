package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static helpers.Configuration.getConfiguration;
import static helpers.Driver.initializeWebDriver;
import static org.junit.Assert.assertEquals;
import static tests.MainTestData.*;

public class MainTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = initializeWebDriver();
    }

    @Test
    public void MainPageTest() {
        driver.get(getConfiguration().getSiteURL());
        assertEquals("Title is correct", MAIN_PAGE_TITLE, driver.getTitle());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
