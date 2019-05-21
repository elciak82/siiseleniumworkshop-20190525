package pages;

import org.openqa.selenium.WebDriver;

public class GenericPage {
    protected WebDriver driver;

    public GenericPage(WebDriver driver) {
        this.driver = driver;
    }
}
