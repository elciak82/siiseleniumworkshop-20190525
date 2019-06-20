package pages;

import org.openqa.selenium.WebDriver;

public class MenuPage extends GenericPage{

    public MenuPage(WebDriver driver) {
        super(driver);
    }

    public TransfersPage openTransferPage(){
        findElementByTextLink("TRANSFER").click();
        return new TransfersPage(driver); //metoda zwraca obiekt klasy TransferPage! W teście nie trzeba jej już inicjalizować (TransferPage tp = new TransferPage(driver))
    }

    public ProfilePage openProfilePage(){
        findElementByTextLink("PROFILE").click();
        return new ProfilePage(driver); //metoda zwraca obiekt klasy TransferPage! W teście nie trzeba jej już inicjalizować (TransferPage tp = new TransferPage(driver))
//        return this; // to nie może tu być, musi byc driver
    }
}
