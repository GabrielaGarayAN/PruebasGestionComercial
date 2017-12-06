package com.andreani.v8.pageObjects;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.andreani.v8.utilities.Utilidades;

import cucumber.api.PendingException;


public class CentroOperativoHomePage {
	WebDriver ldriver;
					
	@FindBy(name="table_search")
	@CacheLookup
	WebElement searchtextBox;
	
	@FindBy(xpath=".//*[@id='btn-agregar']")
	@CacheLookup
	WebElement agregar_boton;
	
	@FindBy(className=".btn-exportar")
	@CacheLookup
	WebElement exportar_boton;
				   
	@FindBy(xpath="/html/body/div[1]/div/section/ui-view/section/div/div[1]/div/div/div[2]/table/tbody")
	@CacheLookup
	WebElement primerItemCO_filaTabla;
	
	@FindBy(className="btn-nodos")
	@CacheLookup
	WebElement gestionarNodo_boton;
	
	@FindBy(className="btn-nodos")
	@CacheLookup
	List<WebElement> gestionarNodos;
	
	@FindBy(xpath="//a[@class=\"btn-editar\"]")
	@CacheLookup
	WebElement editarCO_boton;
	
	@FindBy(xpath="//a[@class=\"btn-editar\"]")
	@CacheLookup
	List<WebElement> editarCO_botons;
	
	@FindBy(xpath="//a[@class=\"btn-eliminar\"]")
	@CacheLookup
	WebElement eliminarCO_boton;
	
	@FindBy(xpath="//a[@class=\"btn-eliminar\"]")
	@CacheLookup
	List<WebElement> eliminarCO_botons;
	
	@FindBy(xpath="//a[@class=\"btn-postales\"]")
	@CacheLookup
	WebElement gestionarIdPostales_btn;

	@FindBy(xpath="//a[@class=\"btn-postales\"]")
	@CacheLookup
	List<WebElement> gestionarIdPostales_btns;
	
	@FindBy(xpath="html/body/div[1]/section/ui-view/section/div/div[1]/div/div/div[3]/ul/li[8]/a")
	@CacheLookup
	WebElement paginaSiguiente_btn;
	
	public  CentroOperativoHomePage(WebDriver driver) {
		this.ldriver=driver;
		try {
			Thread.sleep(300);
			PageFactory.initElements(driver,this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public  boolean existeCO(String CO){
		boolean existeCO = true;
		try {
			Utilidades.esperarElementoClickable(searchtextBox);
			searchtextBox.clear();
			searchtextBox.sendKeys(CO);
			Thread.sleep(1000);
			Utilidades.waitLoadingContent();
			existeCO  = Utilidades.existeElemento(primerItemCO_filaTabla);
		} catch (Exception e) {
			System.out.println("Centro Operativo no encontrado");
			existeCO = false;
		}
		searchtextBox.clear();
		return existeCO;
	}
		
	public  void buscarCO(String CO) {
		searchtextBox.clear();
		searchtextBox.sendKeys(CO);
		try {
			Thread.sleep(3000);
			Utilidades.esperarElementoVisible(primerItemCO_filaTabla);
		} catch (Exception e) {
			System.out.println("Centro Operativo no encontrado");
		}
	}
	
	public CentroOperativoForm abrirFormNuevoCO() {
		CentroOperativoForm result=null;
		try{
		Utilidades.waitLoadingContent();
		Utilidades.esperarElementoClickable(agregar_boton);
		Utilidades.waitLoadingContent();
		agregar_boton.click();
		Utilidades.waitLoadingContent();
		result=new CentroOperativoForm(ldriver);
		}catch (Throwable e) {
			e.printStackTrace();
		}
		return result;
	}

	public CentroOperativoForm abrirFormEdicionCO(String centroOperativo) {
		CentroOperativoForm result=null;
		try{
			Thread.sleep(500);
			Utilidades.waitLoadingContent();
			buscarCO(centroOperativo);
			Utilidades.esperarElementoClickable(editarCO_boton);
			if(editarCO_botons.size() > 0)
			{
				editarCO_botons.get(0).click(); 
				Utilidades.waitLoadingContent();
				result=new CentroOperativoForm(ldriver);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void eliminarCO(String centroOperativo) {
		try{
			Utilidades.waitLoadingContent();
			Utilidades.esperarElementoClickable(eliminarCO_boton);
			if(eliminarCO_botons.size() > 0)
				eliminarCO_botons.get(0).click();
			Thread.sleep(300);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public void paginaSiguiente() {
		try{
		if (paginaSiguiente_btn.isDisplayed()){
			paginaSiguiente_btn.click();
			Thread.sleep(200);
		}	
	} 	catch (Throwable e) {
		e.printStackTrace();
		}
	}
	
	public GestionNodosForm abrirFormGestionNodo(String centroOperativo) throws Exception
	{
		Utilidades.waitLoadingContent();
		buscarCO(centroOperativo);
		Utilidades.esperarElementoVisible(gestionarNodo_boton);
		if(gestionarNodos.size() > 0)
			gestionarNodos.get(0).click();
		GestionNodosForm gestionNodosForm = new GestionNodosForm(ldriver);
		return gestionNodosForm;
	}
	
	public GestionIDPostalesForm abrirFormGestionIdPostales(String centroOperativo) throws Exception {
		Utilidades.waitLoadingContent();
		buscarCO(centroOperativo);
		Utilidades.esperarElementoClickable(gestionarIdPostales_btn); 
		if(gestionarIdPostales_btns.size() > 0)
			gestionarIdPostales_btns.get(0).click();
		return new GestionIDPostalesForm(ldriver);
	}
	
}