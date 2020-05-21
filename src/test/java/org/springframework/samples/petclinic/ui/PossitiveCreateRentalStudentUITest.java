package org.springframework.samples.petclinic.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.samples.petclinic.util.Sleep;

public class PossitiveCreateRentalStudentUITest {
	
	//Default
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	// Params
	private final String ownerUsername = "owner1";
	private final String username = "student1";
	private final String pass = "1";
	private Integer numOfRequest;

	@BeforeEach
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", System.getenv("webdriver.chrome.driver"));
		driver = new ChromeDriver();
		baseUrl = "https://www.google.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testRegisterOwnerUI() throws Exception {
		driver.get("http://localhost:8090/");

		loginAsOwner();
		
		showRequests();
		
		logout();
		
		loginAsStudent();
		
		createRequest();
		
		logout();
		
		loginAsOwner();
		
		assertExistANewRequest();
		
		
		
		

	}

	private void assertExistANewRequest() {
		Sleep.sleep(500);
		driver.findElement(By.xpath("//a[contains(@href, '/request')]")).click();
		assertEquals(numOfRequest + 1,countRequest());
	}

	private void showRequests() {
		Sleep.sleep(500);
		driver.findElement(By.xpath("//a[contains(@href, '/request')]")).click();
		numOfRequest = countRequest();
	}

	public void loginAsOwner() throws Exception {
		Sleep.sleep(500);
		driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		Sleep.sleep(500);
		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(ownerUsername);
		driver.findElement(By.id("password")).click();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(pass);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Sleep.sleep(500);
		assertEquals(ownerUsername.toUpperCase(),
				driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).getText());

	}
	public void logout() throws Exception {
		Sleep.sleep(500);
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).click();
	    driver.findElement(By.xpath("//a[contains(@href, '/logout')]")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
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

	private int countRequest() {
		WebElement tp = this.driver.findElement(By.id("rentalsTable"));
		List<WebElement> tpList = tp.findElements(By.tagName("tr"));
		return tpList.size();
		
		
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
