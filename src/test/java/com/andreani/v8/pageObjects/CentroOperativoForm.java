package com.andreani.v8.pageObjects;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.andreani.v8.utilities.Utilidades;

public class CentroOperativoForm {
	
	WebDriver ldriver;
	
	@FindBy (xpath="//h4[contains(text(),'Crear Sucursal / Planta')"
			+ " or contains(text(),'Editar Sucursal / Planta')]")
	@CacheLookup
	WebElement tituloForm;
	
	@FindBy(xpath="/html/body/div[1]/div/div/div[2]/form/div[1]/div[1]/div/div/select")
	@CacheLookup
	WebElement tipoSucursal_select;
	
	@FindBy(xpath="/html/body/div[1]/div/div/div[2]/form/div[1]/div[1]/div/input")
	@CacheLookup
	WebElement nombreCO_textBox;
	
	@FindBy(xpath="//*[@id=\"ui-select-choices-row-1-0\"]/span/span")
	@CacheLookup
	WebElement calleCO_Option_textBox; 
			
	@FindBy(xpath=".//*[@id='ui-select-choices-row-0-0']/span")
	@CacheLookup
	WebElement nombreResponsableCOOptions;
			
	@FindBy(xpath="/html/body/div[1]/div/div/div[2]/form/div[1]/div[2]/input")
	@CacheLookup
	WebElement nomenclaturaCO_textBox;
	
	@FindBy(xpath="/html/body/div[1]/div/div/div[2]/form/div[2]/div/div")
	@CacheLookup
	WebElement nombreResponsableCO_textBox;
	
	@FindBy(xpath="/html/body/div[1]/div/div/div[2]/form/div[2]/div/input[1]")
	@CacheLookup
	WebElement nombreResponsableCOInput_textBox;
	
	@FindBy(xpath="html/body/div[1]/div/div/div[2]/form/div[3]/div[1]/div/span")
	WebElement calleCO_textBox; 
	
	@FindBy(xpath="//html/body/div[1]/div/div/div[2]/form/div[3]/div[1]/input[1]")
	@CacheLookup
	WebElement calleCO_textBox_input; 
	
	@FindBy(xpath="html/body/div[1]/div/div/div[2]/form/div[4]/div[1]/div/div[1]/span")
	@CacheLookup
	WebElement provinciaCO_textBox;
	
	@FindBy(xpath="html/body/div[1]/div/div/div[2]/form/div[4]/div[2]/div/div[1]/span")
	@CacheLookup
	WebElement localidadCO_textBox;
	
	@FindBy(xpath="html/body/div[1]/div/div/div[2]/form/div[5]/div/input")
	@CacheLookup
	WebElement telefonoCO_textbox;
	
	@FindBy(xpath="/html/body/div[1]/div/div/div[2]/form/div[6]/div[1]/div/input")
	@CacheLookup
	WebElement mailCO_textbox;
	
	@FindBy(xpath="/html/body/div[1]/div/div/div[2]/form/div[7]/input")
	@CacheLookup
	WebElement horarioAtencionCO_textbox;
	
	@FindBy(xpath="//input[@ng-model='seHaceAtencionAlCliente']")
	@CacheLookup
	WebElement atencionClienteCO_checkbox;
	
	@FindBy(xpath="//input[@ng-model='usaPad']")
	@CacheLookup
	WebElement usaPadCO_checkbox;
	
	@FindBy(xpath="/html/body/div[1]/div/div/div[2]/form/div[4]/div[1]/div/input[1]")
	@CacheLookup
	WebElement pronviaCOInput_textbox;
	
	@FindBy(xpath="html/body/div[1]/div/div/div[2]/form/div[4]/div[2]/div/input[1]")
	@CacheLookup
	WebElement localidadCOInput_textbox;
	
	@FindBy(xpath="/html/body/div[1]/div/div/div[2]/form/div[8]/div[1]/div/div[1]/span")
	@CacheLookup
	WebElement regionCO_textbox;
	
	@FindBy(xpath="html/body/div[1]/div/div/div[2]/form/div[8]/div[1]/div[1]/input[1]")
	@CacheLookup
	WebElement regionCOInput_textbox;
	
	@FindBy(xpath="//button[@ng-click='ok(formEditarSucursal)']")
	@CacheLookup
	WebElement guardarFormSucursal_btn;
		
	@FindBy(xpath="//*[contains(text(), 'Uno o mas campos del formulario son inválidos.')]")
	@CacheLookup
	WebElement msgError;
	
	@FindBy(xpath="//button[@ng-click='cancelar()' and @class='btn btn-default']")
	@CacheLookup
	WebElement cancelar_btn;
	
	public CentroOperativoForm(WebDriver driver) throws Exception{
		this.ldriver=driver;		
		PageFactory.initElements(driver, this);
		Utilidades.esperarElementoClickable(nombreResponsableCO_textBox);
	}
	
	public void completarFormNuevoCO(String tipo, String nombreCO, String responsable, String calle, String provincia,
			String localidad, String telefono, String mail, String horariosAtencion, String region,String atencionCliente, String usaPad) throws InterruptedException 
	{
		setTipoSucursal(tipo);
		setNombreCO(nombreCO);
		setNomenclaturaCO(nombreCO.substring(0, 3).toUpperCase());	
		setNombreResponsable(responsable);
		setCalle(calle);
		setProvinciaCO(provincia);
		setLocalidadCO(localidad);
		setTelefonoCO(telefono);
		setMailCO(mail);		
		setHorarioAtencionCO(horariosAtencion);		
		setRegionCO(region);	
		setAtencionClienteCO(atencionCliente);
		setUsaPadCO(usaPad);
	}
	

	public  CentroOperativoHomePage guardarForm()
	{		
		CentroOperativoHomePage result=null;
		try {
			Utilidades.esperarElementoClickable(guardarFormSucursal_btn);
			guardarFormSucursal_btn.click();
			Thread.sleep(200);
			Utilidades.waitLoadingContent();
			
			result=new CentroOperativoHomePage(ldriver);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public  boolean tieneDatosFaltantes() throws Exception {
		Thread.sleep(500);
		Utilidades.esperarElementoVisible(msgError);
		return msgError.isDisplayed();
	}
	
	public  CentroOperativoHomePage cerrarFormCO() 
	{
		cancelar_btn.click();
		CentroOperativoHomePage result=new CentroOperativoHomePage(ldriver);
		return result;
	}

	public void setTipoSucursal(String tipo)
	{
		try {
			Utilidades.esperarElementoClickable(tipoSucursal_select);
			Utilidades.select(tipoSucursal_select,tipo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setNombreCO(String nombreCO) {
		nombreCO_textBox.clear();
		nombreCO_textBox.sendKeys(nombreCO);
	}
	
	public void setNomenclaturaCO(String nomenclatura){
		nomenclaturaCO_textBox.clear();
		nomenclaturaCO_textBox.sendKeys(nomenclatura);
	}

	public void setNombreResponsable(String nombreResponsable){
		try {
			if( !nombreResponsable.equals(""))
			{
				nombreResponsableCO_textBox.click();
				nombreResponsableCOInput_textBox.clear();
				nombreResponsableCOInput_textBox.sendKeys(nombreResponsable);
					Thread.sleep(1500);
				Utilidades.waitLoadingContent();
				Utilidades.esperarElementoClickable(nombreResponsableCOOptions);
				nombreResponsableCOInput_textBox.sendKeys(Keys.ENTER);
			}
		} catch ( Throwable e) {
			e.printStackTrace();
		}
	}

	public void setCalle(String calle){
		try{
		calleCO_textBox.click();
		calleCO_textBox_input.clear();
		calleCO_textBox_input.sendKeys(calle);
		Thread.sleep(500);
		Utilidades.waitLoadingContent();
		Utilidades.esperarElementoClickable(calleCO_Option_textBox);
		calleCO_Option_textBox.click();
		} catch ( Throwable e) {
			e.printStackTrace();
		}
	}

	public void setProvinciaCO(String provincia){
		try{
		provinciaCO_textBox.click();
		pronviaCOInput_textbox.clear();
		pronviaCOInput_textbox.sendKeys(provincia);
		pronviaCOInput_textbox.sendKeys(Keys.ENTER);
		Thread.sleep(500);
		Utilidades.waitLoadingContent();
		} catch ( Throwable e) {
			e.printStackTrace();
		}
	}

	public void setLocalidadCO(String localidad){
		try{
		localidadCO_textBox.click();
		localidadCOInput_textbox.clear();
		localidadCOInput_textbox.sendKeys(localidad);
		localidadCOInput_textbox.sendKeys(Keys.ENTER);
		Thread.sleep(500);
		Utilidades.waitLoadingContent();
		} catch ( Throwable e) {
			e.printStackTrace();
		}
	}


	public void setTelefonoCO(String telefono) {
		telefonoCO_textbox.clear();
		telefonoCO_textbox.sendKeys(telefono);
	}

	public void setMailCO(String mail) {
		mailCO_textbox.clear();
		mailCO_textbox.sendKeys(mail);
	}

	public void setHorarioAtencionCO(String horario) {
		horarioAtencionCO_textbox.clear();
		horarioAtencionCO_textbox.sendKeys(horario);
	}

	public void setRegionCO(String region) throws InterruptedException {
		regionCO_textbox.click();
		regionCOInput_textbox.clear();
		regionCOInput_textbox.sendKeys(region);
		regionCOInput_textbox.sendKeys(Keys.ENTER);
		Utilidades.waitLoadingContent();
	}
	

	public void setAtencionClienteCO(String atencionCliente) {
		if (atencionCliente.equals("Si"))
			atencionClienteCO_checkbox.click();
	}

	public void setUsaPadCO(String usaPad) {
		if (usaPad.equals("Si"))
			usaPadCO_checkbox.click();
	}

	public String getTituloForm()
	{
		String titulo = "";
		if (tituloForm.isDisplayed())
			titulo = tituloForm.getText();
		return titulo;
	}
	
	public void Cancelar() {
		this.cancelar_btn.click();
	}

	public void modificarNombreYNomenclaturaCO(String nombreCO_nuevo, String nomenclaturaCO_nueva) {
		setNombreCO(nombreCO_nuevo);
		setNomenclaturaCO(nomenclaturaCO_nueva);
	}

	public void modificarResponsableCO(String responsable)
	{
		setNombreResponsable("Torres Carlos");
	}

	
}
