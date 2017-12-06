package com.andreani.v8.step_Definitions;

import static org.testng.Assert.assertEquals;
import com.andreani.v8.base.Base;
import com.andreani.v8.pageObjects.LoginPage;

import cucumber.api.java.es.Dado;

public class Usuario extends Base
{
	private LoginPage loginPage;

	@Dado ("^que estoy logueado$")
	public void autenticarUsuarioCorrecto() throws InterruptedException
	{
		String user = config.getProperty("user");
		String pass = config.getProperty("pass");
		
		loginPage = new LoginPage(driver);
		
		loginPage.setUser(user);
		loginPage.setPass(pass);
		loginPage.ingresar();
		Thread.sleep(300);
		assertEquals(loginPage.getMensajeBienvenida(), "Bienvenido a V8");
		home = loginPage.empezar();
	}	
}
