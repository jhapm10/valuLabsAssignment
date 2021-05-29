package framework.appium;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static java.time.Duration.ofSeconds;
import static org.testng.Assert.assertEquals;

public class appiumTest extends capability {

	AndroidDriver<AndroidElement> driver;

	@BeforeMethod
	public void LunchDevice() throws IOException, InterruptedException {
		service = startServer();
		driver = capabilities(appPacckage, appActivity, deviceName, platformName, chromeExecutable);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void PositiveCheckoutFlow() throws InterruptedException {

		System.out.println("ValuLabsApp Launched");
		driver.findElement(By.xpath("//*[@text=\"Username\"]")).sendKeys("standard_user");
		driver.findElement(By.xpath("//*[@text=\"Password\"]")).sendKeys("secret_sauce");
		driver.findElement(By.xpath("//*[@text=\"LOGIN\"]")).click();
		driver.findElements(By.xpath("//*[@content-desc=\"test-ADD TO CART\"]")).get(0).click();
		driver.findElements(By.xpath("//*[@content-desc=\"test-ADD TO CART\"]")).get(0).click();
		driver.findElementByAndroidUIAutomator(
				"new UiScrollable( new UiSelector()).scrollIntoView(text(\"Sauce Labs Onesie\"))");
		// .click();
		driver.findElements(By.xpath("//*[@content-desc=\"test-ADD TO CART\"]")).get(0).click();
		driver.findElements(By.xpath("//*[@content-desc=\"test-ADD TO CART\"]")).get(0).click();

		driver.findElements(By.xpath(
				"//android.view.ViewGroup[@content-desc=\"test-Cart\"]/android.view.ViewGroup/android.widget.ImageView"))
				.get(0).click();

		driver.findElements(By.xpath("//*[@content-desc=\"test-REMOVE\"]")).get(0).click();
		driver.findElements(By.xpath("//*[@content-desc=\"test-REMOVE\"]")).get(0).click();
		driver.findElementByAndroidUIAutomator("new UiScrollable( new UiSelector()).scrollIntoView(text(\"CHECKOUT\"))")
				.click();
		driver.findElement(By.xpath("//*[@text=\"First Name\"]")).sendKeys("Anjali");
		driver.findElement(By.xpath("//*[@text=\"Last Name\"]")).sendKeys("Jha");
		driver.findElement(By.xpath("//*[@text=\"Zip/Postal Code\"]")).sendKeys("12345");
		driver.findElements(By.xpath("//*[@content-desc=\"test-CONTINUE\"]")).get(0).click();
		driver.findElement(By.xpath("//*[@text=\"CHECKOUT: OVERVIEW\"]")).isDisplayed();

		driver.findElementByAndroidUIAutomator("new UiScrollable( new UiSelector()).scrollIntoView(text(\"FINISH\"))")
				.click();
		driver.findElement(By.xpath("//*[@text=\"CHECKOUT: COMPLETE!\"]")).isDisplayed();

		Thread.sleep(3000);
	}

	@Test
	public void NegativeFlowCheckoutWithoutFirstName() throws InterruptedException {

		System.out.println("ValuLabsApp Launched");
		driver.findElement(By.xpath("//*[@text=\"Username\"]")).sendKeys("standard_user");
		driver.findElement(By.xpath("//*[@text=\"Password\"]")).sendKeys("secret_sauce");
		driver.findElement(By.xpath("//*[@text=\"LOGIN\"]")).click();
		driver.findElements(By.xpath("//*[@content-desc=\"test-ADD TO CART\"]")).get(0).click();
		driver.findElements(By.xpath("//*[@content-desc=\"test-ADD TO CART\"]")).get(0).click();
		driver.findElementByAndroidUIAutomator(
				"new UiScrollable( new UiSelector()).scrollIntoView(text(\"Sauce Labs Onesie\"))");
		// .click();
		driver.findElements(By.xpath("//*[@content-desc=\"test-ADD TO CART\"]")).get(0).click();
		driver.findElements(By.xpath("//*[@content-desc=\"test-ADD TO CART\"]")).get(0).click();

		driver.findElements(By.xpath(
				"//android.view.ViewGroup[@content-desc=\"test-Cart\"]/android.view.ViewGroup/android.widget.ImageView"))
				.get(0).click();

		driver.findElements(By.xpath("//*[@content-desc=\"test-REMOVE\"]")).get(0).click();
		driver.findElements(By.xpath("//*[@content-desc=\"test-REMOVE\"]")).get(0).click();
		driver.findElementByAndroidUIAutomator("new UiScrollable( new UiSelector()).scrollIntoView(text(\"CHECKOUT\"))")
				.click();
		driver.findElement(By.xpath("//*[@text=\"Last Name\"]")).sendKeys("Jha");
		driver.findElement(By.xpath("//*[@text=\"Zip/Postal Code\"]")).sendKeys("12345");
		driver.findElements(By.xpath("//*[@content-desc=\"test-CONTINUE\"]")).get(0).click();
		driver.findElement(By.xpath("//*[@content-desc=\"test-Error message\"]")).isDisplayed();

		Thread.sleep(3000);

	}

	@AfterTest
	public void afterExecution() throws IOException, InterruptedException {
		stopEmulator();
		service.stop();
	}
}