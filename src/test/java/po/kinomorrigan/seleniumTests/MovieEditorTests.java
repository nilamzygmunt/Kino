package po.kinomorrigan.seleniumTests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MovieEditorTests {
    @Test
    public void when_AddNewMovie_CorrectInputData() {
        WebDriver driver = new ChromeDriver();

        String homeUrl = "http://127.0.0.1:8080/home";

        driver.get(homeUrl);
        assertEquals("Home", driver.getTitle());

        WebElement salesNavElem = driver.findElement(By.id("repertoire"));
        salesNavElem.click();
        //Repertoire list page
        assertEquals("Repertoire", driver.getTitle());
        WebElement chooseScreeningButton = driver.findElement(By.id("addMovie"));
        chooseScreeningButton.click();

        //Add movie page
        assertEquals("Add movie", driver.getTitle());
        driver.findElement(By.name("title")).sendKeys("title");
        driver.findElement(By.name("ageCategory")).sendKeys("12+");
        driver.findElement(By.name("duration")).sendKeys("120");
        driver.findElement(By.name("premiere")).sendKeys("2022-12-12");
        driver.findElement(By.name("genre")).sendKeys("Comedy");

        WebElement addNewFilmButton = driver.findElement(By.id("addFilmButton"));
        addNewFilmButton.click();

        //Return to repertoire list page
        assertEquals("Repertoire", driver.getTitle());

        driver.quit();
    }
}
