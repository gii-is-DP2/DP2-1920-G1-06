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
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.samples.petclinic.util.Sleep;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PossitiveCreatePropertyUITest {

	//Default
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	// Params
	private final String username = "owner1";
	private final String pass = "1";
	private Integer numOfProperties;

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
	 public void testPossitiveCreatePropertyUI() throws Exception {
		driver.get("http://localhost:"+port);

		loginAsOwner();
		
		createAProperty();

		
	}
  

	
	private void createAProperty() {
		
		driver.findElement(By.xpath("//a[contains(@href, '/properties')]")).click();
		numOfProperties = countProperties();
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
	    
	    assertEquals(numOfProperties + 1,countProperties());

		
	}

	private int countProperties() {
		WebElement tp = this.driver.findElement(By.id("propertiesTable"));
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