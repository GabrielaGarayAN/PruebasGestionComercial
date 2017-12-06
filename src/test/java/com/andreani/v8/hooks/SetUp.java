package com.andreani.v8.hooks;

import java.util.Vector;

import org.openqa.selenium.WebDriverException;

import com.andreani.v8.base.Base;
import com.andreani.v8.utilities.Utilidades;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class SetUp extends Base{
	
	@After 
	public void embedScreenshot (Scenario scenario)
	{
		Utilidades.tomarScreenshot();
		for (byte[] screenshot : screenshots) {
			scenario.embed(screenshot, "image/png");
		}
		if(scenario.isFailed()){
			try{
				scenario.write("La página actual es: " + driver.getCurrentUrl());
			    } catch (WebDriverException somePlatformsDontSupportScreenshots) {
			    	System.out.println((somePlatformsDontSupportScreenshots.getMessage()));
			    }catch (ClassCastException cce) {
			    	cce.printStackTrace(); 
			    }
		 }
		driver.quit();
		driver = null;
	 }
	
	@Before 
	public void init() throws Throwable 
	{
		System.out.println("Entro al BeforeSuite");
		screenshots = new Vector<byte[]>();
		openBrowser();
		maximizeWindow();
		implicitWait(15);
		deleteAllCookies();
		setEnv();
	}
	
}
