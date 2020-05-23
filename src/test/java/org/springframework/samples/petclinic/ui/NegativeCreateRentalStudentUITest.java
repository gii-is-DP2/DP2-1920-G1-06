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
public class NegativeCreateRentalStudentUITest {
	
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
		
		createRequest();

		

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

	public void createRequest() throws Exception {

		driver.findElement(By.xpath("//a[contains(@href, '/properties/find')]")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.xpath("(//a[contains(text(),'Show property')])[2]")).click();
	    driver.findElement(By.xpath("//a[contains(@href, '/properties/2/rooms')]")).click();
	    driver.findElement(By.xpath("//table[@id='roomsTable']/tbody/tr/td[4]/a")).click();
	    driver.findElement(By.xpath("//a[contains(@href, '5/rental/new')]")).click();
	    driver.findElement(By.id("startDate")).click();
	    driver.findElement(By.id("startDate")).clear();
	    driver.findElement(By.id("startDate")).sendKeys("11-11-2020");
	    driver.findElement(By.id("endDate")).click();
	    driver.findElement(By.id("endDate")).clear();
	    driver.findElement(By.id("endDate")).sendKeys("12-12-2020");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    
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
