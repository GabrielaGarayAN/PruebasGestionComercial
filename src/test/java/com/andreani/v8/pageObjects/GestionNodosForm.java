package com.andreani.v8.pageObjects;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.andreani.v8.utilities.Utilidades;

public class GestionNodosForm {

	WebDriver ldriver;
	
	@FindBy(xpath="html/body/div[1]/div/div/div[1]/h4")
	@CacheLookup
	WebElement titulo_formulario;
	
	@FindBy(xpath="//*[@id=\"nodosNoAsignados\"]")
	@CacheLookup 
	WebElement nodosNoAsignadosSelect;
	
	@FindBy(xpath="//*[@id=\"nodosAsignados\"]")
	@CacheLookup
	WebElement nodosAsignadosSelect;
	
	@FindBy(className="btn-success")
	@CacheLookup
	WebElement guardar;
	
	@FindBy(className="btn-default")
	@CacheLookup
	WebElement cancelar;
	
	public GestionNodosForm (WebDriver driver) throws Exception
	{
		this.ldriver = driver;
		PageFactory.initElements(driver,this);
		Utilidades.esperarElementoVisible(titulo_formulario);
	}
	
	public List<WebElement> getCentrosOperativosNoAsignados()
	{
		Select select = new Select(nodosNoAsignadosSelect);
		return select.getOptions();
	}
	
	public List<WebElement> getCentrosOperativosAsignados ()
	{
		Select select = new Select(nodosAsignadosSelect);
		return select.getOptions();
	}
	
	public void AsignarCentroOperativoNoAsignado (String CentroOperativo) throws InterruptedException
	{
		Utilidades.asignarEnSelect(CentroOperativo, nodosNoAsignadosSelect);
		guardar.click();
		Thread.sleep(200);
		Utilidades.waitLoadingContent();
	}
	
	public void DesasignarCentroOperativoAsignado (String CentroOperativo) throws InterruptedException
	{
		Utilidades.asignarEnSelect(CentroOperativo, nodosAsignadosSelect);
		guardar.click();
		Thread.sleep(200);
		Utilidades.waitLoadingContent();
	}
}
