package com.andreani.v8.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	WebDriver ldriver;
	
	@FindBy(id="username")
	@CacheLookup
	WebElement user_textbox;
	
	@FindBy(xpath="//input[@ng-model='password']")
	@CacheLookup
	WebElement pass_textbox;
	
	@FindBy(xpath="//button[@ng-click='login()']")
	@CacheLookup
	WebElement ingresar_boton;
	
	@FindBy(xpath="/html/body/div[1]/div/section/ui-view/div/div/h4")
	@CacheLookup
	WebElement mensajeBienvenida;
	
	@FindBy(xpath="/html/body/div[1]/div/section/ui-view/div/div/a")
	@CacheLookup
	WebElement empezar_boton;
	

	public LoginPage(WebDriver driver) {
		this.ldriver=driver;
		try {
			Thread.sleep(300);
			PageFactory.initElements(driver,this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setUser(String user) {
		this.user_textbox.sendKeys(user);
	}

	public void setPass(String pass) {
		this.pass_textbox.sendKeys(pass);
	}
	
	public void ingresar () throws InterruptedException
	{
		ingresar_boton.click();
		Thread.sleep(200);
	}

	public String getMensajeBienvenida() {
		return mensajeBienvenida.getText();
	}
	
	public CentroOperativoHomePage empezar() throws InterruptedException
	{
		empezar_boton.click();
		Thread.sleep(200);
		return new CentroOperativoHomePage(ldriver);
	}

}
