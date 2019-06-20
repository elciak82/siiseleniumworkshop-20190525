package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.MenuPage;
import pages.ProfilePage;

import static helpers.Configuration.getConfiguration;
import static helpers.Driver.initializeWebDriver;
import static org.junit.Assert.assertEquals;
import static tests.ProfileTestData.*;

public class ProfileTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = initializeWebDriver();
        driver.get(getConfiguration().getSiteURL());
    }

    //Fluent intercafe w każdej metodzie zwracamy page, potem metody bedą po kropkach
    @Test
    public void updateProfileTest() {
        MenuPage menuPage = new MenuPage(driver);
        ProfilePage profilePage = new ProfilePage(driver);
        menuPage.openProfilePage()
                .updateName(NAME) //czejnowanie requestów
                .updateStreet(STREET)
                .updateCity(CITY)
                .updatePostalCode(POSTAL_CODE)
                .updateEmail(EMAIL)
                .updateCountry(COUNTRY)
                .selectGenderType(ProfilePage.GENDER_TYPE.FEMALE)
                .deselectNewsletter()
                .updateAdditionalMessage(ADDITIONAL_MESSAGE)
                .sendButton();

        assertEquals("Name is not correct.", NAME, profilePage.getPersonalData("Name"));
        assertEquals("Street is not correct.", STREET, profilePage.getPersonalData("Street"));
        assertEquals("Name is not correct.", CITY, profilePage.getPersonalData("City"));
        assertEquals("Name is not correct.", POSTAL_CODE, profilePage.getPersonalData("Postal code"));
        assertEquals("Name is not correct.", EMAIL, profilePage.getPersonalData("Email"));
        assertEquals("Name is not correct.", COUNTRY, profilePage.getPersonalData("Country"));
//        assertEquals("Name is not correct.", ProfilePage.GENDER_TYPE.FEMALE.toString().toLowerCase(), profilePage.getPersonalData("Gender"));
        assertEquals("Name is not correct.", ADDITIONAL_MESSAGE, profilePage.getPersonalData("Note"));
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}

