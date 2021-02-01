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

public class appiumTest extends capability{
	AndroidDriver<AndroidElement> driver;
	@BeforeMethod
	public void LunchDevice() throws IOException, InterruptedException {
		service=startServer();
		driver =capabilities(appPacckage, appActivity, deviceName, platformName, chromeExecutable);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	

	@Test
	public void  TestCart() throws InterruptedException {
		
		System.out.println("General Store Launched");
		driver.findElement(By.xpath("//*[@text='Female']")).click();
driver.findElement(By.xpath("//*[@text='Enter name here']")).sendKeys("Anjal");

		driver.findElement(By.xpath("//*[@resource-id=\"com.androidsample.generalstore:id/spinnerCountry\"]")).click();
		driver.findElementByAndroidUIAutomator("new UiScrollable( new UiSelector()).scrollIntoView(text(\"Austria\"))").click();
		driver.findElementByClassName("android.widget.Button").click();
		Thread.sleep(3000);
		//driver.findElements(By.xpath("//*[@text=\"Add To CART\"]")).get(0).click();
		//driver.findElements(By.xpath("//*[@text=\"Add To CART\"]")).get(0).click();
		
		driver.findElements(By.xpath("//*[@resource-id=\"com.androidsample.generalstore:id/productAddCart\"]")).get(0).click();
		driver.findElements(By.xpath("//*[@resource-id=\"com.androidsample.generalstore:id/productAddCart\"]")).get(1).click();
		driver.findElement(By.xpath("//*[@resource-id=\"com.androidsample.generalstore:id/appbar_btn_cart\"]")).click();
		Thread.sleep(3000);
		String amount1=driver.findElements(By.xpath("//*[@resource-id=\"com.androidsample.generalstore:id/productPrice\"]")).get(0).getText();
		String amount2=driver.findElements(By.xpath("//*[@resource-id=\"com.androidsample.generalstore:id/productPrice\"]")).get(1).getText();
		
		String am1=amount1.substring(1);
		Double am1val=Double.parseDouble(am1);
		System.out.println(am1val);
		String am2=amount2.substring(1);
		Double am2val=Double.parseDouble(am2);
		System.out.println(am2val);
		
		
	

	String Total=	driver.findElement(By.id("com.androidsample.generalstore:id/totalAmountLbl")).getText();
	String tot=Total.substring(1);
	Double finaltot=Double.parseDouble(tot);
	//Assert.assertEquals(finaltot, am1val+am2val);
	
	TouchAction t=new TouchAction(driver);
	WebElement checkBoxtap=driver.findElement(By.className("android.widget.CheckBox"));
	t.tap(tapOptions().withElement(element(checkBoxtap))).perform();
	WebElement LP= driver.findElement(By.xpath("//*[@text=\"Please read our terms of conditions\"]"));
	

	t.longPress(longPressOptions().withElement(element(LP)).withDuration(ofSeconds(3))).release().perform();
	Thread.sleep(3000);
	driver.findElement(By.xpath("//*[@text=\"CLOSE\"]")).click();
	driver.findElement(By.xpath("//*[@text=\"Visit to the website to complete purchase\"]")).click();
	Thread.sleep(4000);
	Set<String> contextNames = driver.getContextHandles();
	for (String contextName : contextNames) {
	    System.out.println(contextName); //prints out something like NATIVE_APP \n WEBVIEW_1
	}
driver.context("WEBVIEW_com.androidsample.generalstore");
Thread.sleep(2000);
	driver.findElement(By.xpath("//*[@name=\"q\"]")).sendKeys("IBM");
	driver.findElement(By.xpath("//*[@name=\"q\"]")).sendKeys(Keys.ENTER);
	// set context to WEBVIEW_1

	//do some web testing
//	String myText = driver.findElement(By.cssSelector(".green_button")).click();
Thread.sleep(2000);
driver.pressKey(new KeyEvent(AndroidKey.BACK));
	driver.context("NATIVE_APP");
	service.stop();
	}
	
	@AfterTest
	public void afterExecution() throws IOException, InterruptedException {
		stopEmulator();
		service.stop();
	}
}