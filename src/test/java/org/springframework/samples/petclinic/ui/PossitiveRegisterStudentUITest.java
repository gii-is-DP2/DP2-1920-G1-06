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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.samples.petclinic.util.Sleep;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PossitiveRegisterStudentUITest {

	// Default
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	// Params
	private final String username = "student999";
	private final String pass = "999";

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
	public void testRegisterStudentUI() throws Exception {
		driver.get("http://localhost:"+port);

		registerStudentUI();

		loginAsStudent();

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

	public void registerStudentUI() throws Exception {

		driver.findElement(By.xpath("//a[contains(@href, '/users/new')]")).click();
		driver.findElement(By.xpath("//a[contains(@href, '/users/new/student')]")).click();
		driver.findElement(By.id("firstName")).click();
		driver.findElement(By.id("firstName")).clear();
		driver.findElement(By.id("firstName")).sendKeys("Alberto");
		driver.findElement(By.id("lastName")).clear();
		driver.findElement(By.id("lastName")).sendKeys("Garcia");
		driver.findElement(By.id("dni")).clear();
		driver.findElement(By.id("dni")).sendKeys("77898780O");
		driver.findElement(By.id("birthDate")).clear();
		driver.findElement(By.id("birthDate")).sendKeys("02-10-1980");
		driver.findElement(By.name("gender")).click();
		driver.findElement(By.name("gender")).click();
		driver.findElement(By.id("email")).click();
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("soymupro@gmail.com");
		driver.findElement(By.id("telephone")).clear();
		driver.findElement(By.id("telephone")).sendKeys("666666666");
		driver.findElement(By.id("user.username")).clear();
		driver.findElement(By.id("user.username")).sendKeys(username);
		driver.findElement(By.id("user.password")).clear();
		driver.findElement(By.id("user.password")).sendKeys(pass);
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		assertEquals("Welcome", driver.findElement(By.xpath("//h2")).getText());
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
