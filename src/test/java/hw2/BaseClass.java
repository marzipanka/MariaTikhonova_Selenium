package hw2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public abstract class BaseClass {
    protected WebDriver driver;

    @BeforeSuite
    public void setUpDriverPath() {
        System.setProperty("webdriver.chrome.driver",
                Paths.get("src/test/resources/driver/chromedriver.exe")
                        .toAbsolutePath().toString());
    }

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // 1. Open test site by URL
        driver.get("https://epam.github.io/JDI/index.html");

        // 2. Assert Browser title
        assertBrowserTitle("Home Page");

        // 3. Perform login
        login("epam", "1234");

        // 4. Assert User name in the right-top side of screen that user is logged in
        assertEquals(driver.findElement(By.id("user-name")).getText(), "PITER CHAILOVSKII");
    }

    //Close Browser
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    protected void assertBrowserTitle(String title) {
        assertEquals(driver.getTitle(), title);
    }

    protected void login(String username, String password) {
        driver.findElement(By.id("user-icon")).click();
        driver.findElement(By.id("name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
    }

    protected void checkElementIsDisplayed(WebElement element) {
        assertTrue(element.isDisplayed());
    }

    protected void checkElementsAreDisplayed(List<WebElement> webElements) {
        SoftAssert softAssert = new SoftAssert();
        for (WebElement e : webElements) {
            softAssert.assertTrue(e.isDisplayed());
        }
        softAssert.assertAll();
    }

    protected void checkElementsHaveProperTexts(List<WebElement> webElements, List<String> expectedWebElements) {
        assertEquals(webElements.size(), expectedWebElements.size());

        SoftAssert softAssert = new SoftAssert();
        for (int i = 0; i < webElements.size(); i++) {
            softAssert.assertEquals(webElements.get(i).getText(), expectedWebElements.get(i));
        }
        softAssert.assertAll();

    }

}
