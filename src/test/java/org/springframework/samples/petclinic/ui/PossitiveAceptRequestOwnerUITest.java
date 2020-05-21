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
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.samples.petclinic.util.Sleep;


public class PossitiveAceptRequestOwnerUITest {
	//Default
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	// Params
	private final String username = "owner1";
	private final String pass = "1";
	private Integer numOfRequest;
	private Integer numOfRentals;
	private String completeAddress;
	
	@BeforeEach
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", System.getenv("webdriver.chrome.driver"));
		driver = new ChromeDriver();
		baseUrl = "https://www.google.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	 public void testPossitiveAcceptRequestOwnerUI() throws Exception {
		driver.get("http://localhost:8090/");

		loginAsOwner();
		
		acceptARequest();


	}
  

	
	private void acceptARequest() {
		
		driver.findElement(By.xpath("//a[contains(@href, '/rentals')]")).click();
		numOfRentals = countRentals();
	    driver.findElement(By.xpath("//a[contains(@href, '/request')]")).click();
	    Sleep.sleep(500);
	    
	    completeAddress = driver.findElement(By.xpath("//table[@id='rentalsTable']/tbody/tr/td[3]")).getText();
	    numOfRequest = countRequest();
	    
	    driver.findElement(By.xpath("//table[@id='rentalsTable']/tbody/tr/td[4]/a")).click();
	    driver.findElement(By.xpath("//a[contains(@href, '1/accept')]")).click();
	    driver.findElement(By.xpath("//a[contains(@href, '/request')]")).click();
	    
	    Sleep.sleep(500);
	    assertEquals(numOfRequest - 1,countRequest());
	    
	    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/a/span[2]")).click();
	    
	    Sleep.sleep(500);
	    assertEquals(numOfRentals + 1,countRentals());
	    
	    driver.findElement(By.xpath("//a[contains(@href, '/rentals/1')]")).click();
	    Sleep.sleep(500);
	    assertEquals(completeAddress,driver.findElement(By.xpath("//tr[3]/td")).getText());
		
	}

	private int countRequest() {
		WebElement tp = this.driver.findElement(By.id("rentalsTable"));
		List<WebElement> tpList = tp.findElements(By.tagName("tr"));
		return tpList.size();
		
		
	}
	private int countRentals() {
		WebElement tp = this.driver.findElement(By.id("rentalsTable"));
		List<WebElement> tpList = tp.findElements(By.tagName("tr"));
		return tpList.size();
		
		
	}

	public void loginAsOwner() throws Exception {
		
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
 
 
	@AfterEach
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

}