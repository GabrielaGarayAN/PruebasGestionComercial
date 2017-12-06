package com.andreani.v8.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.Calendar;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import com.andreani.v8.pageObjects.CentroOperativoHomePage;
import com.andreani.v8.reportConfig.ExtentReporter;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
		strict = true,
		monochrome = true, 
		features = "src/test/resources/features/",
		glue = {"com/andreani/v8/step_Definitions","com/andreani/v8/hooks"},
		plugin = {"com.andreani.v8.reportConfig.ExtentFormatter:report/report.html","json:report/cucumber.json"},
		tags={"@TestngScenario"}
		)
public class Base extends AbstractTestNGCucumberTests {
	public static WebDriver driver;
	public static Properties config=new Properties();
	public static Properties config_log=new Properties();
	public static Properties bd=new Properties();
	public static FileInputStream fis;	
	public static Logger log=Logger.getLogger("devpinoyLogger");
	public static boolean result;
	protected CentroOperativoHomePage home;
	protected static Vector<byte[]> screenshots;

	public void openBrowser()
	{
		System.out.println("Open Browser");
		log.debug("Inicia la Prueba");
		
		if(driver==null){
			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\config.properties");
			log.debug("Loaded Config properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				config.load(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}
					
			try {

				fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\bd.properties");
				log.debug("Loaded BD properties");

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}	
			try {
				bd.load(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(config.getProperty("browser").equals("firefox")){
				System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\drivers\\geckodriver.exe");
				driver=new FirefoxDriver();
				
			}
			else if(config.getProperty("browser").equals("chrome")){
				System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\drivers\\chromedriver.exe");
				driver=new ChromeDriver();
			
			}
			
			else if(config.getProperty("browser").equals("ie")){
				System.setProperty("webdriver.ie.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\drivers\\IEDriverServer.exe");
				driver=new InternetExplorerDriver();
			}
		}	
	}
	
	public void maximizeWindow() {
		driver.manage().window().maximize();
	}

	public void implicitWait(int time) {
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}

	public void pageLoad(int time) {
		driver.manage().timeouts().pageLoadTimeout(time, TimeUnit.SECONDS);
	}

	public void deleteAllCookies() {
		driver.manage().deleteAllCookies();
	}

	public void setEnv() {
		driver.get(config.getProperty("url"));
	}


	public static String currentDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String date = dateFormat.format(cal.getTime());
		return date;
	}
	
	@AfterClass(alwaysRun = true)
	public void report() throws IOException {
		ExtentReporter.setConfig(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\Extent-Config.xml");
        ExtentReporter.setSystemInfo("user", System.getProperty("user.name"));
        ExtentReporter.setSystemInfo("os", System.getProperty("os.name"));
	}
	
}
