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
public class NegativeCreateRoomOwnerUITest {

	// Default
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	// Params
	private final String username = "owner1";
	private final String pass = "1";
	private Integer numOfRooms;

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
	public void testPossitiveCreateRoomOwnerUITest() throws Exception {
		driver.get("http://localhost:"+port);

		loginAsOwner();

		createARoom();

	}

	private void createARoom() {
		
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[2]/a/span[2]")).click();
	    driver.findElement(By.linkText("Show property")).click();
	    driver.findElement(By.linkText("Show rooms")).click();	    
	    driver.findElement(By.linkText("Add room")).click();
	    driver.findElement(By.id("roomNumber")).click();
	    driver.findElement(By.id("roomNumber")).clear();
	    driver.findElement(By.id("roomNumber")).sendKeys("6");
	    driver.findElement(By.id("surface")).click();
	    driver.findElement(By.id("surface")).clear();
	    driver.findElement(By.id("surface")).sendKeys("0");
	    driver.findElement(By.id("price")).click();
	    driver.findElement(By.id("price")).clear();
	    driver.findElement(By.id("price")).sendKeys("0");
	    driver.findElement(By.id("extWindow")).click();
	    driver.findElement(By.id("extWindow")).clear();
	    driver.findElement(By.id("extWindow")).sendKeys("1");
	    driver.findElement(By.id("TamCloset")).click();
	    driver.findElement(By.id("TamCloset")).clear();
	    driver.findElement(By.id("TamCloset")).sendKeys("4");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    
		assertEquals("tiene que estar entre 1 y 50", driver.findElement(By.xpath("//form[@id='add-room-form']/div/div[2]/div/span[2]")).getText());
		assertEquals("tiene que estar entre 1 y 9223372036854775807", driver.findElement(By.xpath("//form[@id='add-room-form']/div/div[3]/div/span[2]")).getText());


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