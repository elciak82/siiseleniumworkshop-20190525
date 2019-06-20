package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.*;

public class TransfersPage extends GenericPage {

    private static final String TRANSFER_ACCOUNT = "transfer-account";
    private static final String TRANSFER_TITLE = "transfer-title";
    private static final String TRANSFER_RECIPIENT = "transfer-recipent";
    private static final String TRANSFER_AMOUNT = "transfer-amount";
    private static final String SEND_TRANSFER_BUTTON_CSS = "input[value='Send']";
    private static final String PENDING_TRANSFER_AMOUNT = "//*[@id='transfer-pending-table']/tbody/tr[1]/td[3]"; //kropkami idziemy w górę, sleshami idziemy w dół. Od findElement (i to bedzie np drzewko) mozna zrobic sobie kolejny findElement, który bedzie wyszukiwał to co mamy w wcześniej wyszikanum drzewku
    private static final String TRANSFER_TYPE_STANDARD_CSS = "label[for='transfer-type-standard']";
    private static final String TRANSFER_TYPE_INSTANT_CSS = "label[for='transfer-type-instant']";
    private static final String TRANSFER_TYPE_FAST_CSS = "label[for='transfer-type-fast']";
    public static final int LAST_TRANSFER_AMOUNT = 2;
    public static final int PENDING_TRANSFER_TABLE_SIZE = 3;

    public enum TRANSFER_TYPE {
        STANDARD, FAST, INSTANT
    }

    public enum PENDING_TRANSFER_HEADERS {
        DATE, TITLE, AMOUNT
    }

//    @FindBy(id = "transfer-account")
//    WebElement accountNumber;

//    @FindBy(id = "transfer-recipent")
//    WebElement transferRecipient;

    public TransfersPage(WebDriver driver) {
        super(driver);
    }

    public TransfersPage fillAccountNumber(String accNumber) {
        findElementById(TRANSFER_ACCOUNT).sendKeys(accNumber);
        return this;
    }

    public TransfersPage fillTitle(String title) { //TAK NIE ROBIMY ZAMIAST TEGO @FindBy
        findElementById(TRANSFER_TITLE).sendKeys(title);
        return this;
    }

    public TransfersPage fillRecipient(String recipient) {
        findElementById(TRANSFER_RECIPIENT).sendKeys(recipient);
        return this;
    }

    public TransfersPage fillAmount(String amount) {
        findElementById(TRANSFER_AMOUNT).sendKeys(amount);
        return this;
    }

    public String getAccountNumberValue() {
        return findElementById(TRANSFER_ACCOUNT).getAttribute("value");
    }

    public TransfersPage sendTransfer() {
        waitUntilElementIsVisibleByCss(SEND_TRANSFER_BUTTON_CSS);
        findElementByCss(SEND_TRANSFER_BUTTON_CSS).click();
        return this;
    }

    public String getPendingTransferAmount() {
        return findElementByXpath(PENDING_TRANSFER_AMOUNT).getText(); //wymusza granulat, testy składają się z sekcji, najpierw cos ustawiamy potem getem robimy assercję, get juz nie zwraca obiektu strony
    }

    public TransfersPage selectTransferType(TRANSFER_TYPE transfer_type) {
        switch (transfer_type) {
            case STANDARD:
                findElementByCss(TRANSFER_TYPE_STANDARD_CSS).click();
                return this;
            case FAST:
                findElementByCss(TRANSFER_TYPE_FAST_CSS).click();
                return this;
            case INSTANT:
                findElementByCss(TRANSFER_TYPE_INSTANT_CSS).click();
                return this;
        }
        return this;
    }

    public List<String> getAllTransfers(){
        List<String> transfersStringList = new ArrayList<>(); //deklarujemy listę stringów
        List<WebElement> transferPendingWebElementsList = new ArrayList<>(); //dekladujemy listę webelementów
        transferPendingWebElementsList.addAll(driver.findElement(By.id("transfer-pending-table")).findElements(By.tagName("td"))); //UZYJ EWALUATE (NOTATKI) dodajemy wszystkie wyszukane wartości do listy
        for (WebElement element : transferPendingWebElementsList) { //dla kazdego elementu z listy webelementów iteracja
            transfersStringList.add(element.getText()); //pobierz jego text i dodaj do do listy stringów
        }
        return transfersStringList; //na końcu ją zwróć. To jest cała lista. lista jest ok dla list, dla tebael lepsza jest mapa
    }

    public String getLastTransferAmount(){
        return getAllTransfers().get(LAST_TRANSFER_AMOUNT);
    }

    public Map<PENDING_TRANSFER_HEADERS, String> getLastTransferMap() { //PENDING_TRANSFER_HEADERS bo musi być odpowiedni tym danych
        Map<PENDING_TRANSFER_HEADERS, String> lastPendingTransfer = new HashMap<>();//inicjalizacja mapy. ENUM  mniejsza szansa na pomyłkę
        lastPendingTransfer.put(PENDING_TRANSFER_HEADERS.DATE, getAllTransfers().get(0)); //wsadziliśmy do mapy klucz - enum, a potem wyciągamy z listy getAllTranfers i putem wrzucamy do mapy
        lastPendingTransfer.put(PENDING_TRANSFER_HEADERS.TITLE, getAllTransfers().get(1));
        lastPendingTransfer.put(PENDING_TRANSFER_HEADERS.AMOUNT, getAllTransfers().get(2));
        return lastPendingTransfer;
    }

    public Map<Integer, Map<PENDING_TRANSFER_HEADERS, String>> getAllTransferMap() {
        Map<Integer, Map<PENDING_TRANSFER_HEADERS, String>> allTransfersMap = new HashMap<>(); //inicjalizacja głównej mapy
        List<String> allPendingTransfers = getAllTransfers(); //bieżemy wszystkie wartości i wrzucamy je do listy
        if(allPendingTransfers.size() >0) {
            for (int i = 0; i <allPendingTransfers.size(); i =+PENDING_TRANSFER_TABLE_SIZE){
                Map<PENDING_TRANSFER_HEADERS, String> rowTransferMap = new HashMap<>();
                rowTransferMap.put(PENDING_TRANSFER_HEADERS.DATE, getAllTransfers().get(i));
                rowTransferMap.put(PENDING_TRANSFER_HEADERS.TITLE, getAllTransfers().get(i + 1));
                rowTransferMap.put(PENDING_TRANSFER_HEADERS.AMOUNT, getAllTransfers().get(i + 2));
                allTransfersMap.put(i/PENDING_TRANSFER_TABLE_SIZE, rowTransferMap); //do wyjaśnienia!!!!
            }
        }
        return allTransfersMap;
    }
    public String getPendingTransfersData (Integer pendingTransferRow, PENDING_TRANSFER_HEADERS pendingTransferColumn) {
        return getAllTransferMap().get(pendingTransferRow).get(pendingTransferColumn);
    }
}
