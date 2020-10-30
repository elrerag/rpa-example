package rpa;

import java.net.URL;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.windows.WindowsDriver;

public class NotepadTest {
	private static WindowsDriver notepadSession = null;

	public static String getDate() {
		LocalDate date = LocalDate.now();
		return date.toString();
	}
// "C:\Program Files (x86)\Windows Application Driver\WinAppDriver.exe" 192.168.0.11 4723
	@BeforeClass
	public static void setUp() {
		try {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("app", "C:\\Windows\\System32\\notepad.exe");
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("deviceName", "WindowsPC");
//			notepadSession = new WindowsDriver(new URL("http://10.2.0.15:4723"), capabilities);
//			notepadSession = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
			notepadSession = new WindowsDriver(new URL("http://192.168.0.11:4723/"), capabilities);
			notepadSession.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterMethod
	public void cleanApp() {
		notepadSession.quit();
		setUp();
	}

	@AfterSuite
	public void tearDown() {
		notepadSession.quit();
	}

	@Test
	public void checkAboutWindow() {
		notepadSession.findElementByName("Help").click();
		notepadSession.findElementByName("About Notepad").click();
		notepadSession.findElementByName("OK").click();
	}

	@Test
	public void sendTestText() {
		notepadSession.findElementByClassName("Edit").sendKeys(getDate());
		notepadSession.findElementByClassName("Edit").clear();
	}

	@Test()
	public void pressTimeAndDateButton() {
		notepadSession.findElementByName("Edit").click();
		notepadSession.findElementByAccessibilityId("26").click();
		Assert.assertNotNull(notepadSession.findElementByClassName("Edit"));
		notepadSession.findElementByClassName("Edit").clear();
	}
}
