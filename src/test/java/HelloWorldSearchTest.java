import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class HelloWorldSearchTest {

    private WebDriver driver;
    private final By findInputSelector = By.xpath("//input[@aria-label='Запрос']");
    private final By findButtonSelector = By.xpath("/html/body/main/div[2]/form/div[4]/button");

    @BeforeMethod
    public void initConfig() {
        ConfigTest.initializeProfileDirectory();
        ChromeOptions options = ConfigTest.createChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void testSearch() throws InterruptedException {
        driver.get("https://ya.ru");
        WebElement inputElement = driver.findElement(findInputSelector);
        inputElement.sendKeys("руддщ цкщдв");
        Thread.sleep(1000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement buttonSearch = wait.until(ExpectedConditions.elementToBeClickable((findButtonSelector)));
        buttonSearch.click();

        WebElement searchInputAfter = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@aria-label='Запрос']")));
        String searchFieldValue = searchInputAfter.getAttribute("value");

        assertEquals(searchFieldValue, "hello world", "Поле поиска должно содержать 'hello world'");

        String pageTitle = driver.getTitle();
        assertTrue(pageTitle.contains("hello world"), "Заголовок страницы должен содержать 'hello world'");
    }

    @AfterMethod
    public void quiteDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}