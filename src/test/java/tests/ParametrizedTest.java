package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import pages.MenuPage;


import static helpers.Configuration.getConfiguration;
import static helpers.Driver.initializeWebDriver;
//import static org.junit.Assert.assertEquals;

public class ParametrizedTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = initializeWebDriver();
        driver.get(getConfiguration().getSiteURL());
    }


    @ParameterizedTest
    @ValueSource(strings = {"12345", "text", "%^$#@!)"})
    public void parametrizedTest(String parameter) {
        MenuPage menuPage = new MenuPage(driver);
        menuPage.openProfilePage()
                .updateName(parameter);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}

