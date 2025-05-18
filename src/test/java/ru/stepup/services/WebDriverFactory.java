package ru.stepup.services;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.nio.file.Paths;

public class WebDriverFactory {
    public static WebDriver createChromeDriver() {
        // Путь к chromedriver
        String path = Paths.get("src/test/resources/driver/chromedriver.exe").toAbsolutePath().toString();
        System.setProperty("webdriver.chrome.driver", path);

        return new ChromeDriver();
    }
}
