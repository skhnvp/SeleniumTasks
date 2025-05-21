package ru.stepup.tests;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stepup.services.WebDriverFactory;

import java.time.Duration;
import java.util.List;

public class PobedaTest {
    WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = WebDriverFactory.createChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @SneakyThrows
    @Test
    void testPobeda()  {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        driver.get("https://www.google.com/");
        driver.manage().window().setSize(new Dimension(1920, 1080));

        WebElement searchBox = driver.findElement(By.cssSelector("[id=\"APjFqb\"]"));
        searchBox.click();
        searchBox.sendKeys("Сайт компании Победа" + Keys.ENTER);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("rso"))); //ждем появления страницы с результатами
        List<WebElement> allGoogleSearchResults = driver.findElements(By.cssSelector("div#rso div.MjjYud"));
        allGoogleSearchResults.get(0).findElement(By.tagName("a")).click();
        Assertions.assertEquals("https://www.flypobeda.ru/", driver.getCurrentUrl());

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.dp-1tviork-root-container"))); //жду загрузки блока картинок
        List<WebElement> bestDeals = driver.findElements(By.cssSelector("div.dp-1tviork-root-container button.dp-1mt82sv-root"));
        Assertions.assertEquals("Самарканд", bestDeals.get(2).findElement(By.cssSelector("div.dp-z7hrww-root")).getText()); //проверяю что 3й элемент о городе Самарканд

        driver.findElement(By.cssSelector("div.dp-khrveb-root button")).click();
        driver.findElements(By.cssSelector("div.dp-1loxy1b-root button.dp-203da9-root-root")).get(1).click();
        Assertions.assertEquals("Ticket search", driver.findElement(By.xpath("//div[contains(text(), 'Ticket search')]")).getText());
        Assertions.assertEquals("Online check-in", driver.findElement(By.xpath("//div[contains(text(), 'Online check-in')]")).getText());
        Assertions.assertEquals("Manage my booking", driver.findElement(By.xpath("//div[contains(text(), 'Manage my booking')]")).getText());
    }
}
