package org.springframework.samples.petclinic.ui;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class NegativeCreatePropertyUITest {

	//Default
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	// Params
	private final String username = "owner1";
	private final String pass = "1";

	@BeforeEach
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", System.getenv("webdriver.chrome.driver"));
		driver = new ChromeDriver();
		baseUrl = "https://www.google.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	 public void testNegativeCreatePropertyUI() throws Exception {
		driver.get("http://localhost:8090/");

		loginAsOwner();
		
		createAProperty();

	}
  

	
	private void createAProperty() {
		
		driver.findElement(By.xpath("//a[contains(@href, '/properties')]")).click();
	    driver.findElement(By.xpath("//a[contains(@href, '/properties/new')]")).click();
	    driver.findElement(By.id("address")).click();
	    driver.findElement(By.id("address")).clear();
	    driver.findElement(By.id("address")).sendKeys("Paseo de las delicias");
	    driver.findElement(By.id("city")).click();
	    driver.findElement(By.id("city")).clear();
	    driver.findElement(By.id("city")).sendKeys("Sevilla");
	    driver.findElement(By.id("description")).click();
	    driver.findElement(By.id("description")).clear();
	    driver.findElement(By.id("description")).sendKeys("Bonito adosado unifamiliar");
	    driver.findElement(By.id("surface")).click();
	    driver.findElement(By.id("surface")).clear();
	    driver.findElement(By.id("surface")).sendKeys("45");
	    driver.findElement(By.id("totalRooms")).click();
	    driver.findElement(By.id("totalRooms")).clear();
	    driver.findElement(By.id("totalRooms")).sendKeys("4");
	    driver.findElement(By.name("propertyType")).click();
	    driver.findElement(By.name("propertyType")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.linkText("Back to my properties")).click();
		
	}

	public void loginAsOwner() throws Exception {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).click();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(pass);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		assertEquals(username.toUpperCase(),
				driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).getText());

	}
 
	@AfterEach
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
   

}