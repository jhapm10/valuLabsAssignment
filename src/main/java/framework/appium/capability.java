package framework.appium;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class capability {

	protected static String appPacckage;
	protected static String appActivity;
	protected static String deviceName;
	protected static String platformName;
	protected static String chromeExecutable;

	public AppiumDriverLocalService service;

	public AppiumDriverLocalService startServer() {
		boolean flag = checkServerIsRunning(4723);
		if(!flag) {
		
		service = AppiumDriverLocalService.buildDefaultService();
		// service=AppiumDriverLocalService.buildService(new
		// AppiumServiceBuilder().usingDriverExecutable(new File("‪C://Program
		// Files//nodejs//node.exe")).withAppiumJS(new
		// File("C://Users//AnjaliJha//AppData//Roaming//npm//node_modules//appium//build//lib//main.js")).withIPAddress("127.0.0.1").usingPort(4723));
		service.start();
		}

		return service;
	}
	//to check port availibility else it will kill and sart fresh
	public static boolean checkServerIsRunning(int port) {
		boolean isServerRunning=false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket();
			serverSocket.close();
		} catch(IOException e) {
			 isServerRunning=true;
		}
	finally {
		serverSocket = null;
	}
	return isServerRunning;	
	}

	public AppiumDriverLocalService stopServer() {
		service = AppiumDriverLocalService.buildDefaultService();
		service.stop();
		return service;
	}

	public static void startEmulator() throws IOException, InterruptedException {
		Runtime.getRuntime().exec(System.getProperty("user.dir") + "//src//main//java//emulatot.bat");
		Thread.sleep(8000);
	}
	public static void stopEmulator() throws IOException, InterruptedException {
		Thread.sleep(4000);
		Runtime.getRuntime().exec(System.getProperty("user.dir") + "//src//main//java//killEmalutaor.bat");
		
	}

	public static AndroidDriver<AndroidElement> capabilities(String appPacckage, String appActivity, String deviceName,
			String platformName, String chromeExecutable) throws IOException, InterruptedException {
		FileReader fis = new FileReader(System.getProperty("user.dir") + "//src//main//java//global.properties");
		Properties prop = new Properties();
		prop.load(fis);
		appPacckage = prop.getProperty("appPacckage");
		appActivity = prop.getProperty("appActivity");
		deviceName = prop.getProperty("deviceName");
		platformName = prop.getProperty("platformName");
		chromeExecutable = prop.getProperty("chromeExecutable");

		if (deviceName.contains("AJSDET")) {
			startEmulator();
		}
		DesiredCapabilities cap = new DesiredCapabilities();

		cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
		// cap.setCapability(MobileCapabilityType.AUTOMATION_NAME,
		// AutomationName.ANDROID_UIAUTOMATOR2);

		cap.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE, chromeExecutable);
		cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, appPacckage);
		cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, appActivity);
		AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(
				new URL("http://127.0.0.1:4723/wd/hub"), cap);

		return driver;
	}

}
