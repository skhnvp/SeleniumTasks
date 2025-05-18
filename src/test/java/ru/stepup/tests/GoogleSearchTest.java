package ru.stepup.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stepup.services.WebDriverFactory;

@Deprecated
public class GoogleSearchTest {
    WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = WebDriverFactory.createChromeDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void testGoogleSearch() {
        driver.get("https://www.google.com/");
        driver.manage().window().maximize();

        Assertions.assertEquals("https://www.google.com/", driver.getCurrentUrl());
        Assertions.assertEquals("Google", driver.getTitle());

        WebElement searchBox = driver.findElement(By.cssSelector("[id=\"APjFqb\"]"));
        searchBox.click();
        searchBox.sendKeys("Очень странные дела");
        searchBox.sendKeys(Keys.ENTER);

        Assertions.assertEquals("Очень странные дела - Поиск в Google", driver.getTitle());
        Assertions.assertTrue(driver.findElement(By.cssSelector("[class=\"logo\"]")).isDisplayed());
        Assertions.assertEquals(driver.findElement(By.cssSelector("[id=\"APjFqb\"]")).getText(), "Очень странные дела");
    }
}
