package org.springframework.samples.petclinic.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.samples.petclinic.util.Sleep;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PossitiveEditRoomOwnerUITest {
	
	// Default
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	// Params
	private final String username = "owner1";
	private final String pass = "1";
	private final Double price = 200.0;
	private final Integer tamCloset = 20;

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
	public void testEditPropertyUITest() throws Exception {
		driver.get("http://localhost:"+port);

		loginAsOwner();

		editARoom();

		assertElements();

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

	public void editARoom() throws Exception {

		driver.findElement(By.xpath("//a[contains(@href, '/properties')]")).click();
		driver.findElement(By.xpath("//a[contains(@href, '/properties/1/show')]")).click();
		driver.findElement(By.xpath("//a[contains(@href, '/properties/1/rooms')]")).click();
		driver.findElement(By.xpath("//a[contains(@href, '/properties/1/rooms/1')]")).click();
		driver.findElement(By.xpath("//a[contains(@href, '1/edit')]")).click();
		driver.findElement(By.id("price")).click();
		driver.findElement(By.id("price")).clear();
		driver.findElement(By.id("price")).sendKeys(price.toString());
		driver.findElement(By.id("TamCloset")).click();
		driver.findElement(By.id("TamCloset")).clear();
		driver.findElement(By.id("TamCloset")).sendKeys(tamCloset.toString());
		driver.findElement(By.xpath("//button[@type='submit']")).click();

	}

	public void assertElements() throws Exception {

		Sleep.sleep(500);

		assertEquals(price.toString(), driver.findElement(By.xpath("//tr[3]/td")).getText());

		assertEquals(tamCloset.toString(), driver.findElement(By.xpath("//tr[5]/td")).getText());
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