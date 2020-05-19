package org.springframework.samples.petclinic.ui;


import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class PossitiveListPropertiesOwner {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeEach
  public void setUp() throws Exception {
//    String pathToGeckoDriver = "C:\\Users\\enriq\\OneDrive\\Escritorio";
    System.setProperty("webdriver.chrome.driver",System.getenv("webdriver.chrome.driver"));
    driver = new ChromeDriver();
   
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }
  
  @Test
  public void testNegativeCreatePropertyUI() throws Exception {
    driver.get("http://localhost:8090/");
    driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
    driver.findElement(By.id("username")).click();
    driver.findElement(By.id("username")).clear();
    try {
        Thread.sleep(500);
    } catch (InterruptedException e) {
    }
    driver.findElement(By.id("username")).sendKeys("owner1");
    driver.findElement(By.id("password")).clear();
    try {
        Thread.sleep(500);
    } catch (InterruptedException e) {
    }
    driver.findElement(By.id("password")).sendKeys("1");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[2]/a/span[2]")).click();
    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
    }
  }

  @AfterEach
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }

}