package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.MenuPage;
import pages.TransfersPage;

import java.util.concurrent.TimeUnit;

import static helpers.Configuration.getConfiguration;
import static helpers.Driver.initializeWebDriver;
import static org.junit.Assert.assertEquals;
import static tests.TransferTestData.*; //import interface z danymi

public class TransferTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = initializeWebDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //czeka czałe życie drivera
        driver.get(getConfiguration().getSiteURL());
    }

    @Test //transferPage nie chcemy tego wywyoływac tyle razy ItransferPage więc zwracamy ten obiekt w metodach
    public void newTransferPageTest() {
        MenuPage mainPage = new MenuPage(driver); //nowy obiekt
        mainPage.openTransferPage()
                .fillAccountNumber(TRANSFER_ACCOUNT_NUMBER)
                .fillTitle(TRANSFER_TITLE)
                .fillRecipient(TRANSFER_RECIPIENT)
                .fillAmount(TRANSFER_AMOUNT)
                .selectTransferType(TransfersPage.TRANSFER_TYPE.INSTANT)
                .sendTransfer();
        TransfersPage transfersPage = new TransfersPage(driver);
        assertEquals("Amount is not correct.", TRANSFER_AMOUNT, transfersPage.getPendingTransfersData(TRANSFER_TEST_PENDING_TRANSFER_ROW, TransfersPage.PENDING_TRANSFER_HEADERS.AMOUNT));
        assertEquals("Title is not correct.", TRANSFER_TITLE, transfersPage.getPendingTransfersData(TRANSFER_TEST_PENDING_TRANSFER_ROW, TransfersPage.PENDING_TRANSFER_HEADERS.TITLE)); //wykorzystanie mapy + WRAPPER (nowa metoda getPendingTransfersData zeby ładniej wygldało

//        assertEquals("Amount is not correct.", TRANSFER_AMOUNT,
//                transfersPage.getLastTransferMap().get(TransfersPage.PENDING_TRANSFER_HEADERS.AMOUNT)); //zastosowanie mapy. Podajemy wartośc klucza, do której jest przypisana jest wartość, którą sprawdzamy
//        assertEquals("Amount is not correct.", TRANSFER_TITLE,
//                transfersPage.getLastTransferMap().get(TransfersPage.PENDING_TRANSFER_HEADERS.TITLE));

//        assertEquals("Amount is not correct.", TRANSFER_AMOUNT, transfersPage.getLastTransferAmount()); zastosowanie listy
//        assertEquals("Account number is not correct.", TRANSFER_ACCOUNT_NUMBER, transfersPage.getAccountNumberValue());
//        assertEquals("Account number is not correct.", "199", transfersPage.getAccountNumberValue());
//        assertEquals("Title is not correct", TRANSFER_PAGE_TITLE, driver.getTitle());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
