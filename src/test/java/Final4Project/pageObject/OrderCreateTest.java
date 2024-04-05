package Final4Project.pageObject;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static Final4Project.pageObject.constants.CreateOrderButton.DOWN_BUTTON;
import static Final4Project.pageObject.constants.CreateOrderButton.UP_BUTTON;
import static Final4Project.pageObject.constants.RentDurationConstants.*;
import static Final4Project.pageObject.constants.ScooterColours.*;

@RunWith(Parameterized.class)
public class OrderCreateTest {
    private WebDriver driver;
    private final String site = "https://qa-scooter.praktikum-services.ru/";
    private final String name;
    private final String surname;
    private final String address;
    private final int stateMetroNumber;
    private final String telephoneNumber;
    private final String date;
    private final String duration;
    private final Enum colour;
    private final String comment;
    private final String expectedHeader = "Заказ оформлен";
    private final Enum button;

    public OrderCreateTest(Enum button, String name, String surname, String address, int stateMetroNumber, String telephoneNumber,
                           String date, String duration, Enum colour, String comment) {
        this.button = button;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.stateMetroNumber = stateMetroNumber;
        this.telephoneNumber = telephoneNumber;
        this.date = date;
        this.duration = duration;
        this.colour = colour;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Object[][] getParameters() {
        return new Object[][]{
                {UP_BUTTON, "Петр", "Петров", "Иванов переулок", 1, "79261234567", "03.04.2024", THREE_DAYS, BLACK, "Три дня"},
                {UP_BUTTON, "Иван", "Иванов", "улица Сергеева", 2, "79261234568", "04.04.2024", TWO_DAYS, BLACK, "Два дня"},
                {UP_BUTTON, "Сергей", "Сергеев", "Проспект Ильича", 3, "79261234569", "03.04.2024", FIVE_DAYS, BLACK, "Пять дней"},
                {DOWN_BUTTON, "Федр", "Федоров", "Бобров переулок", 4, "79261234560", "05.04.2024", ONE_DAY, GREY, "Один день"},
                {DOWN_BUTTON, "Зульфия", "Боброва", "Сергеев Проезд", 5, "79261234561", "03.04.2024", TWO_DAYS, GREY, "Два дня"},
                {DOWN_BUTTON, "Юля", "Юлина", "улица Кикабидзе", 6, "79261234562", "08.04.2024", FOUR_DAYS, GREY, "Четыре дня"},
        };
    }

    @Before
    public void startUp() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.get(site);
    }

    @After
    public void teardown() {
        driver.quit();
    }

    @Test
    public void testCreateOrderWithUpButton() {
        new HomePage(driver)
                .waitForLoadHomePage()
                .clickCreateOrderButton(button);

        new Renters(driver)
                .waitForLoadOrderPage()
                .inputName(name)
                .inputSurname(surname)
                .inputAddress(address)
                .changeStateMetro(stateMetroNumber)
                .inputTelephone(telephoneNumber)
                .clickNextButton();

        new Scooters(driver)
                .waitAboutRentHeader()
                .inputDate(date)
                .inputDuration(duration)
                .changeColour(colour)
                .inputComment(comment)
                .clickButtonCreateOrder();

        PopUp popUpWindow = new PopUp(driver);
                popUpWindow.clickButtonYes();

        assertTrue(popUpWindow.getHeaderAfterCreateOrder().contains(expectedHeader));
    }
}
