package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfilePage extends GenericPage {

    public static final String TAG_NAME_TD = "td";
    public static final String TAG_NAME_TH = "th";

    public ProfilePage(WebDriver driver) {
        super(driver); //wywoływany jest konstruktor, loadowanie tych elementów, które potem są wykorzystywane (tworzenie referencji do tych elementów)
        PageFactory.initElements(driver, this); // "this" na tej stronie, Konstruktor - inicjacja elementu !!!!!@FindBY bez tego nie zadziała!!!
    }

    @FindBy(id = "update-name")
    WebElement updateName;

    @FindBy(id = "update-country") //referencja do obiektu na stronie
            WebElement updateCountry;

    @FindBy(id = "update-street")
    WebElement updateStreet;

    @FindBy(id = "update-city")
    WebElement updateCity;

    @FindBy(id = "update-postalcode")
    WebElement updatePostalCode;

    @FindBy(id = "update-email")
    WebElement updateEmail;

    @FindBy(css = "label[for='update-gender-male']")
    WebElement updateGenderMale;

    @FindBy(css = "label[for='update-gender-female']")
    WebElement updateGenderFemale;

    @FindBy(css = "label[for='update-gender-dont']")
    WebElement updateGenderDont;

    @FindBy(css = "label[for='update-newsletter']")
    WebElement updateNewsletter;

    @FindBy(id = "update-newsletter")
    WebElement updateNewsletterState;

    @FindBy(id = "update-additional")
    WebElement updateAdditionalMessage;

    @FindBy(className = "button")
    WebElement sendButton;

    public enum GENDER_TYPE {
        MALE, FEMALE, DONT
    }

    public enum PROFILE_DATA {
        NAME, STREET, CITY, POSTAL_CODE, EMAIL, COUNTRY, GENDER, NEWSLETTER, NOTE
    }


    public ProfilePage updateName(String newProfileName) {
        updateName.sendKeys(newProfileName);
        return this; //zwraca ProfilePage! tak z kazda metodą która działa na tej stronie
    }

    public ProfilePage updateStreet(String newStreet) {
        updateStreet.sendKeys(newStreet);
        return this;
    }

    public ProfilePage updateCity(String newCity) {
        updateCity.sendKeys(newCity);
        return this;
    }

    public ProfilePage updatePostalCode(String newPostalCode) {
        updatePostalCode.sendKeys(newPostalCode);
        return this;
    }

    public ProfilePage updateEmail(String newEmail) {
        updateEmail.sendKeys(newEmail);
        return this;
    }

//    public TransfersPage updateEmail(String newEmail){
//        updateEmail.sendKeys(newEmail);
//        return new TransfersPage(driver); ///przechodzimy na zupełnie nową stronę wiec nowy driver!
//    }

    public ProfilePage updateCountry(String dropdownCountry) { //z biblioteki selenium-support. Page Factory. Select to nazwa na dropdown na stronie
        Select countryDropdown = new Select(updateCountry);//tworzymy zmienną, nowy obiekt. musimy dodac do niego webelement (dropdown)
        countryDropdown.selectByVisibleText(dropdownCountry);//i z tego dropdowna pobiera odpowiednią/przekazaną w metodzie wartość
        return this;
    }

    public ProfilePage selectGenderType(GENDER_TYPE gender_type) {
        switch (gender_type) {
            case MALE:
                updateGenderMale.click();
                return this;
            case FEMALE:
                updateGenderFemale.click();
                return this;
            case DONT:
                updateGenderDont.click();
                return this;
        }
        return this;
    }

    public ProfilePage deselectNewsletter() {
        if (updateNewsletterState.isSelected()) ;
        updateNewsletter.click();
        return this;
    }

    public ProfilePage selectNewsletter() {
        if (!updateNewsletterState.isSelected()) ;
        updateNewsletter.click();
        return this;
    }

    public ProfilePage updateAdditionalMessage(String newAdditionalMessage) {
        updateAdditionalMessage.sendKeys(newAdditionalMessage);
        return this;
    }

    public ProfilePage sendButton() {
        waitUntilElementIsVisible(sendButton);
        sendButton.click();
        return this;
    }

    public List<String> getAllProfileData(String tagName) {
        List<String> profileStringList = new ArrayList<>(); //deklarujemy listę stringów
        List<WebElement> profileWebElementsList = new ArrayList<>(); //dekladujemy listę webelementów
        profileWebElementsList.addAll(driver.findElement(By.id("personal-data-table")).findElements(By.tagName(tagName))); //UZYJ EWALUATE (NOTATKI) dodajemy wszystkie wyszukane wartości do listy
        for (WebElement element : profileWebElementsList) { //dla kazdego elementu z listy webelementów iteracja
            profileStringList.add(element.getText()); //pobierz jego text i dodaj do do listy stringów
        }
        return profileStringList; //na końcu ją zwróć. To jest cała lista. lista jest ok dla list, dla tebael lepsza jest mapa
    }

    public Map<String, String> getProfileData() {
        Map<String, String> personalData = new HashMap<>(); //zainicjowanie mapy
        List<String> headersList = new ArrayList<>(); //zainicjowanie listy z headerami
        List<String> profileValueList = new ArrayList<>(); //zainicjowanie listy z danymi personalnymi
        headersList.addAll(getAllProfileData(TAG_NAME_TH)); //dodanie do listy wartości ze strony pod tagiem "th" - headers
        profileValueList.addAll(getAllProfileData(TAG_NAME_TD)); //dodanie do listy wartości ze strony pod tagiem "td" - dane personalne
        if (profileValueList.size()>0)
            for(int i = 0; i<profileValueList.size(); i++) {
                personalData.put(headersList.get(i), profileValueList.get(i)); //iteracja przypisuj do headerów dane personalne
        }
//        System.out.println(personalData); print for test
        return personalData;
    }

    public String getPersonalData(String personalData) {
        return getProfileData().get(personalData); //Wrapper żeby ładniej to wyglądało w teście. Wykorzystuje mapę. Tworzy mapę i pobiera z niej wartość dla klucza, który podajemy jako parametr
    }

}
