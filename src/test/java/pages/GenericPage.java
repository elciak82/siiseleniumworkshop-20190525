package pages;

import org.omg.CORBA.TIMEOUT;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class GenericPage {
    WebDriver driver;

    GenericPage(WebDriver driver) {
        this.driver = driver;
    }

    WebElement findElementByTextLink(String elementLinkText){
        return driver.findElement(By.linkText(elementLinkText));
    }

    WebElement findElementByClassName (String className){
        return driver.findElement(By.className(className));
    }

    WebElement findElementById (String elementId){
        return driver.findElement(By.id(elementId));
    }

    WebElement findElementByCss (String elementCss){
        return driver.findElement(By.cssSelector(elementCss));
    }

    WebElement findElementByXpath (String elementXpath){
        return driver.findElement(By.xpath(elementXpath));
    }

    public void fluentWaitForElementDisplayed(WebElement elementToBeDisplayed) { //czeka az jakiś element bedzie wydoczny. 10 sek co 100 milisekund konkretnym monencie a nie przez całe życie drivera
        new FluentWait<>(elementToBeDisplayed) //element MUSI byc zainicjalizowany Page Factory
                .withTimeout(10, TimeUnit.SECONDS)
                .pollingEvery(100, TimeUnit.MILLISECONDS)
                .until(WebElement::isDisplayed);
    }

    public void waitUntilElementIsVisibleById(String idOfElement){
        WebDriverWait wait = new WebDriverWait(driver, 10); //tutaj element NIE MUSI byc zainicjalizowany
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(idOfElement))); //poczeka az element o konkretnym ID bedzie widoczny
    }

    public void waitUntilElementIsVisibleByCss(String cssOfElement){
        WebDriverWait wait = new WebDriverWait(driver, 10);//tutaj element NIE MUSI byc zainicjalizowany
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssOfElement))); //poczeka az element o konkretnym CSS bedzie widoczny
    }

    public void waitUntilElementIsVisible(WebElement element){ //!!!!INNA METODA DLA WEBELEMENTS
        WebDriverWait wait = new WebDriverWait(driver, 10); //tutaj element NIE MUSI byc zainicjalizowany
        wait.until(ExpectedConditions.visibilityOf(element)); //poczeka az element o konkretnym CSS bedzie widoczny
    }
}
