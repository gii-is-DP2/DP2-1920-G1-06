package org.springframework.samples.petclinic.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.samples.petclinic.util.Sleep;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NegativeListPropertyUITest {
	
	//Default
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	// Params
	private final String ownerUsername = "owner1";
	private final String username = "student1";
	private final String pass = "1";
	private Integer numOfRequest;

	@LocalServerPort
	private int port;
	
	@BeforeEach
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", System.getenv("webdriver.chrome.driver"));
		driver = new ChromeDriver();
		baseUrl = "https://www.google.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testRegisterOwnerUI() throws Exception {
		driver.get("http://localhost:"+port);

		
		loginAsStudent();
		
		ListProperties();

		

	}


	
	public void loginAsStudent() throws Exception {
		Sleep.sleep(500);
		driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		Sleep.sleep(500);
		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).click();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(pass);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Sleep.sleep(500);
		assertEquals(username.toUpperCase(),
				driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).getText());

	}

	public void ListProperties() throws Exception {

		driver.get("http://localhost:" + port + "/properties");
		      
		assertEquals("Forbidden", driver.findElement(By.xpath("//div[3]")).getText());		    
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
