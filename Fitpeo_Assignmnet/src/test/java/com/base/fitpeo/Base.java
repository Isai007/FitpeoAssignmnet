package com.base.fitpeo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.config.WebDriverManagerException;

public class Base {
static protected WebDriver driver; //initiate driver
	
public static void setup() {
	try {
	String browser = "edge"; // Change this to "chrome", "firefox", or "edge" as needed
	switch (browser.toLowerCase()) {
    case "chrome":
        System.setProperty("webdriver.chrome.driver", "E:\\Selenium\\Chrome_driver\\chromedriver.exe");
        driver = new ChromeDriver();
        break;
    case "firefox":
        System.setProperty("webdriver.gecko.driver", "E:\\Selenium\\Chrome_driver\\geckodriver.exe");
        driver = new FirefoxDriver();
        break;
    case "edge":
        System.setProperty("webdriver.edge.driver", "E:\\Selenium\\Chrome_driver\\msedgedriver.exe");
        driver = new EdgeDriver();
        break;
    default:
        System.out.println("Invalid browser choice! Please select chrome, firefox, or edge.");
}}catch(WebDriverManagerException e) {
	System.out.println(e.getMessage());
}
	driver.get("https://www.fitpeo.com/");
	driver.manage().window().maximize();
}
	
}
