package po.kinomorrigan.seleniumTests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TicketSaleTests {
    @Test
    public void when_BookAndSaleTicketsForScreening_SeatsNoLongerAvailable() {
        WebDriver driver = new ChromeDriver();

        String homeUrl = "http://127.0.0.1:8080/home";

        driver.get(homeUrl);
        assertEquals("Home", driver.getTitle());

        WebElement salesNavElem = driver.findElement(By.id("sales"));
        salesNavElem.click();
        //Screening list page
        assertEquals("Screenings", driver.getTitle());
        WebElement chooseScreeningButton = driver.findElement(By.cssSelector(".edit_button:first-of-type"));
        chooseScreeningButton.click();

        //Seat choice page
        assertEquals("Seat choice", driver.getTitle());
        WebElement confirmSeatsButton = driver.findElement(By.id("confirm_seats"));
        assertFalse(confirmSeatsButton.isEnabled());
        WebElement takenSeat = driver.findElement(By.cssSelector(".seat_checkbox:first-of-type"));
        assertFalse(takenSeat.isEnabled());
        WebElement freeSeat = driver.findElement(By.cssSelector(".seat_checkbox:nth-of-type(2)"));
        WebElement secFreeSeat = driver.findElement(By.cssSelector(".seat_checkbox:nth-of-type(3)"));
        freeSeat.click();
        secFreeSeat.click();
        assertTrue(confirmSeatsButton.isEnabled());
        confirmSeatsButton.click();

        //Ticket choice page
        assertEquals("Ticket choice", driver.getTitle());
        List<WebElement> inputs = driver.findElements(By.cssSelector(".input"));
        inputs.forEach(input -> assertEquals(input.getText(), "0"));
        WebElement buttonPlus1 = driver.findElement(By.id("normal_increase_1"));
        buttonPlus1.click();
        WebElement buttonPlus2 = driver.findElement(By.id("normal_increase_2"));
        buttonPlus2.click();
        WebElement confirmButton = driver.findElement(By.id("confirm_tickets_button"));
        confirmButton.click();

        //Payment
        assertEquals("Payment", driver.getTitle());
        WebElement paymentMethod = driver.findElement(By.cssSelector(".payment_method:nth-of-type(2)"));
        paymentMethod.click();
        WebElement confirmPaymentButton = driver.findElement(By.cssSelector(".purple_button:nth-of-type(2)"));
        confirmPaymentButton.click();
        WebElement alertDiv = driver.findElement(By.cssSelector(".alert"));
        assertEquals(alertDiv.getText(), "Sprzedaż zakończona pomyślnie");

        //Confirm seats no longer available
        salesNavElem = driver.findElement(By.id("sales"));
        salesNavElem.click();
        chooseScreeningButton = driver.findElement(By.cssSelector(".edit_button:first-of-type"));
        chooseScreeningButton.click();
        freeSeat = driver.findElement(By.cssSelector(".seat_checkbox:nth-of-type(2)"));
        secFreeSeat = driver.findElement(By.cssSelector(".seat_checkbox:nth-of-type(3)"));
        assertFalse(freeSeat.isEnabled());
        assertFalse(secFreeSeat.isEnabled());

        driver.quit();
    }
}
