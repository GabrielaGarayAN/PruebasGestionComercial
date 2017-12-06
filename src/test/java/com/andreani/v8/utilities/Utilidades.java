package com.andreani.v8.utilities;

import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.andreani.v8.base.Base;

public class Utilidades extends Base {
	public static final String HttpConnection = null;
	public static final long timeOut = 10l;
	static WebElement dropdown;
	
	public static void waitLoadingContent() throws InterruptedException {
		System.out.println("	- Empieza Wait Loading Content");
		Thread.sleep(500);
		List<WebElement> elements = driver.findElements(By.xpath("//*[contains(text(),'Aguarde un momento :)')]"));
		WebElement espereAviso = null;
		for (WebElement e : elements) {
			if (e.isDisplayed()){
				espereAviso = e;
			}
		}
		if (espereAviso != null){
			while (espereAviso.isDisplayed()){
				System.out.println("Esperando " + espereAviso);
				Thread.sleep(500);
			}
		}
		Thread.sleep(500);
		System.out.println("	- Termina Wait Loading Content");
	}

	public static void esperarElementoVisible (WebElement element) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public static void esperarElementoClickable( WebElement element) throws Exception {
	
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		wait.until(ExpectedConditions.elementToBeClickable(element));
		Thread.sleep(300);
	}
	
	public static void select(WebElement dropdown, String value) {
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
	}
	
	
	public static boolean existeElemento(int intentos, String xpathElemento) 
	{
		boolean existeElemento = true;
		int intentosHechos = 0;
		System.out.println("buscando elemento " + xpathElemento);
		while (driver.findElements(By.xpath(xpathElemento)).isEmpty() && intentosHechos < intentos)
		{
			try 
			{
				System.out.println("esperando 500 ms " );
				Thread.sleep(500);
				intentosHechos++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
		if (driver.findElements(By.xpath(xpathElemento)).isEmpty())
			existeElemento = false;
		return existeElemento;	
	}
	
	public static boolean existeElemento(WebElement element) 
	{
		int intentosHechos = 0;
		boolean existeElemento = true;
		System.out.println("buscando elemento " + element);
		while (!element.isDisplayed() && intentosHechos < 6)
		{
			try 
			{
				System.out.println("esperando 500 ms " );
				Thread.sleep(500);
				intentosHechos++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
		if (!element.isDisplayed())
			existeElemento = false;
		return existeElemento;	
	}
	
	public static void selectEspecial(String xPathLocator, String valor) throws Exception {
		System.out.println(xPathLocator + "/div[1]/span");

		WebElement cuadrado = driver.findElement(By.xpath(xPathLocator + "/div[1]/span"));
		WebElement input = driver.findElement(By.xpath(xPathLocator + "/input[1]"));
		WebElement opcion = driver.findElement(By.xpath(xPathLocator + "/ul/li/div[3]/span"));
			
		cuadrado.click();
		Thread.sleep(50);
		input.sendKeys(valor);
		Thread.sleep(50);
		esperarElementoClickable(opcion);
		opcion.click();
		Thread.sleep(100);
	}

	public void selectOptionWithText(By selector, String textToSelect) {
		System.out.println("Entro al selector");
		try {
			Thread.sleep(3000L);
			WebElement autoOptions = driver.findElement(selector);

			List<WebElement> optionsToSelect = autoOptions.findElements(By.tagName("li"));
			System.out.println("Ready List");
			for (int i = 0; i < optionsToSelect.size(); i++) {

				System.out.println(optionsToSelect.get(i).getText().toString());
			}

			for (WebElement option : optionsToSelect) {
				if (option.getText().toLowerCase().contains(textToSelect.toLowerCase())) {

					System.out.println("Seleccionando : " + textToSelect);
					option.click();
					break;
				}
			}

		}
		catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
	}
	
	public static void acceptAlert() 
	{
          Alert alert = driver.switchTo().alert();
          alert.accept();
    }

	public static void dobleClick(WebElement elemento) {
		Actions builder = new Actions(driver);
		builder.doubleClick(elemento);
		builder.perform();
	}
	
	public static void asignarEnSelect (String opcion, WebElement elementoSelect) throws InterruptedException
	{
		boolean existe = false;
		Select select = new Select(elementoSelect);
		for (WebElement  e : select.getOptions()) {
			 if (e.getText().equals(opcion))
				 existe = true;
		}
		if (existe == true)
		{
			select.selectByVisibleText(opcion);
			Utilidades.dobleClick(select.getFirstSelectedOption());
		}
	}
	
	public static void dragAndDrop (WebElement dragElement,WebElement dropElement)
	{
		Actions builder = new Actions(driver);
		builder.dragAndDrop(dragElement, dropElement).build().perform();
	}
	
	public static List<WebElement> getHijosElemento(WebElement elemento)
	{
		return elemento.findElements(By.xpath(".//*"));
	}
	
	public static void tomarScreenshot ()
	 { 
		byte[] screenshot = ((ChromeDriver) driver).getScreenshotAs(OutputType.BYTES);
		screenshots.addElement(screenshot);
	 }
}
