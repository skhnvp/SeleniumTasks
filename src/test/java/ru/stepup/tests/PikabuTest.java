package ru.stepup.tests;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import ru.stepup.services.WebDriverFactory;

import java.time.Duration;

public class PikabuTest {
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
    void testPikabu()  {
        driver.get("https://pikabu.ru/");
        driver.manage().window().setSize(new Dimension(1920, 1080));

        Assertions.assertEquals("https://pikabu.ru/", driver.getCurrentUrl());

        Assertions.assertEquals("Горячее – самые интересные и обсуждаемые посты | Пикабу", driver.getTitle());
        WebElement loginButton = driver.findElement(By.cssSelector("div[class=\"header-right-menu\"] button[class=\"pkb-normal-btn header-right-menu__login-button\"]"));
        loginButton.click();

        WebElement authModal = driver.findElement(By.cssSelector("div[class=\"auth-modal\"]"));
        Assertions.assertTrue(authModal.isDisplayed());

        WebElement modalLogin = authModal.findElement(By.cssSelector("div[class=\"auth-modal\"] input[placeholder=\"Логин\"][name=\"username\"]"));
        WebElement modalPassword = authModal.findElement(By.cssSelector("div[class=\"auth-modal\"] input[placeholder=\"Пароль\"][name=\"password\"]"));
        WebElement modalLoginButton = authModal.findElement(By.cssSelector("div[class=\"auth-modal\"] button[type=\"submit\"] span"));

        Assertions.assertTrue(modalLogin.isDisplayed());
        Assertions.assertTrue(modalPassword.isDisplayed());
        Assertions.assertTrue(modalLoginButton.isDisplayed());

        modalLogin.sendKeys("Qwerty");
        modalPassword.sendKeys("Qwerty");
        modalLoginButton.click();

        WebElement errorLoginMessage = driver.findElement(By.cssSelector("div[class=\"popup__container\"] span[class=\"auth__error auth__error_top\"]"));

        Assertions.assertTrue(errorLoginMessage.isDisplayed());
        Assertions.assertEquals("Ошибка. Вы ввели неверные данные авторизации", errorLoginMessage.getText());
    }
}
