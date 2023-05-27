package po.kinomorrigan.seleniumTests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class HallEditorTests
{
    @Test
    public void when_AddNewMovie_CorrectInputData() {
        WebDriver driver = new ChromeDriver();

        String homeUrl = "http://127.0.0.1:8080/home";

        driver.get(homeUrl);
        assertEquals("Home", driver.getTitle());

        WebElement salesNavElem = driver.findElement(By.id("halls"));
        salesNavElem.click();
        //Hall list page
        assertEquals("Halls", driver.getTitle());
        WebElement chooseScreeningButton = driver.findElement(By.cssSelector(".edit_button:first-of-type"));
        chooseScreeningButton.click();

        //Edit hall details
        assertEquals("Hall details", driver.getTitle());
        driver.findElement(By.name("newSeatsNumber")).sendKeys("35");

        WebElement editHallButton = driver.findElement(By.id("confirm_seats"));
        editHallButton.click();

        driver.quit();
    }
}
