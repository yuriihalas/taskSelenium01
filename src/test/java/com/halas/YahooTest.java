package com.halas;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertNotEquals;
import static org.testng.AssertJUnit.assertEquals;


public class YahooTest {
    private static final Logger LOG = LogManager.getLogger(YahooTest.class);
    private static final String HOME_PAGE = "https://www.yahoo.com/";
    private static final String PATH_TO_DRIVER = "src/main/resources/chromedriver.exe";
    private static final String WEB_DRIVER_NAME = "webdriver.chrome.driver";
    private WebDriver driver;

    @BeforeClass
    void initializeObjects() {
        System.setProperty(WEB_DRIVER_NAME, PATH_TO_DRIVER);
        driver = new ChromeDriver();
        driver.manage().timeouts()
                .implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(HOME_PAGE);
    }

    @Test
    void testGoToFoundElementByName() {
        LOG.info("Title: " + driver.getTitle());
        assertNotEquals(driver.getTitle(), "");
        WebElement searchElement = driver.findElement(By.name("p"));
        searchElement.sendKeys("Apple");
        searchElement.submit();
        WebElement imageElement = driver.findElement(By.cssSelector(".compList ul li:nth-child(2)>*"));
        imageElement.click();

        String textActualResult = driver.findElement(By.className("active")).getText();
        String textExpectedResult = driver.findElement(By.cssSelector("#pivot-tabs :nth-child(2)>*")).getText();

        assertEquals(textActualResult, textExpectedResult);
    }

    @AfterClass
    void closeResources() {
        driver.quit();
    }
}