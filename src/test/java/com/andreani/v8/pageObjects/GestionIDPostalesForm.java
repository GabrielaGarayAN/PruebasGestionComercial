package com.andreani.v8.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.andreani.v8.utilities.Utilidades;

public class GestionIDPostalesForm 
{
	WebDriver ldriver;
	
	@FindBy(className="modal-title")
	@CacheLookup
	WebElement titulo_formulario;
	
	@FindBy(xpath="/html/body/div[1]/div/div/div[2]/form/div[1]/div/div[1]/div")
	@CacheLookup
	WebElement idsNoAsignadosSorteable;
	
	@FindBy(xpath="/html/body/div[1]/div/div/div[2]/form/div[1]/div/div[2]/div")
	@CacheLookup
	WebElement idsAsignadosSorteable;
	
	@FindBy(xpath="//*[@id=\"formaAtencion\"]/tbody/tr[1]/td[2]/input")
	@CacheLookup
	WebElement atecionSucursalLunes;
	
	@FindBy(xpath="//*[@id=\"formaAtencion\"]/tbody/tr[1]/td[3]/input")
	@CacheLookup
	WebElement atecionSucursalMartes;
	
	@FindBy(xpath="//*[@id=\"formaAtencion\"]/tbody/tr[1]/td[4]/input")
	@CacheLookup
	WebElement atecionSucursalMiercoles;
	
	@FindBy(xpath="//*[@id=\"formaAtencion\"]/tbody/tr[1]/td[5]/input")
	@CacheLookup
	WebElement atecionSucursalJueves;
	
	@FindBy(xpath="//*[@id=\"formaAtencion\"]/tbody/tr[1]/td[6]/input")
	@CacheLookup
	WebElement atecionSucursalViernes;
	
	@FindBy(xpath="//*[@id=\"formaAtencion\"]/tbody/tr[1]/td[7]/input")
	@CacheLookup
	WebElement atecionSucursalSabado;
	
	@FindBy(xpath="//*[@id=\"formaAtencion\"]/tbody/tr[1]/td[8]/input")
	@CacheLookup
	WebElement atecionSucursalDomingo;
	
	@FindBy(xpath="//*[@id=\"formaAtencion\"]/tbody/tr[2]/td[2]/input")
	@CacheLookup
	WebElement atecionConcesionarioLunes;

	@FindBy(xpath="//*[@id=\"formaAtencion\"]/tbody/tr[2]/td[3]/input")
	@CacheLookup
	WebElement atecionConcesionarioMartes;
	
	@FindBy(xpath="//*[@id=\"formaAtencion\"]/tbody/tr[2]/td[4]/input")
	@CacheLookup
	WebElement atecionConcesionarioMiercoles;
	
	@FindBy(xpath="//*[@id=\"formaAtencion\"]/tbody/tr[2]/td[5]/input")
	@CacheLookup
	WebElement atecionConcesionarioJueves;
	
	@FindBy(xpath="//*[@id=\"formaAtencion\"]/tbody/tr[2]/td[6]/input")
	@CacheLookup
	WebElement atecionConcesionarioViernes;
		
	@FindBy(xpath="//*[@id=\"formaAtencion\"]/tbody/tr[2]/td[7]/input")
	@CacheLookup
	WebElement atecionConcesionarioSabado;
	
	@FindBy(xpath="//*[@id=\"formaAtencion\"]/tbody/tr[2]/td[8]/input")
	@CacheLookup
	WebElement atecionConcesionarioDomingo;
	
	@FindBy(className="btn-success")
	@CacheLookup
	WebElement guardar;
	
	@FindBy(className="btn-default")
	@CacheLookup
	WebElement cancelar;
	
	public GestionIDPostalesForm (WebDriver driver) throws Exception
	{
		this.ldriver = driver;
		PageFactory.initElements(driver, this);
		Utilidades.esperarElementoVisible(titulo_formulario);
	}

	public void setAtecionSucursalLunes() {
		this.atecionSucursalLunes.click();
	}

	public void setAtecionSucursalMartes() {
		this.atecionSucursalMartes.click();
	}

	public void setAtecionSucursalMiercoles() {
		this.atecionSucursalMiercoles.click();
	}

	public void setAtecionSucursalJueves() {
		this.atecionSucursalJueves.click();
	}

	public void setAtecionSucursalViernes() {
		this.atecionSucursalViernes.click();
	}
	
	public void setAtecionSucursalSabado() {
		this.atecionSucursalSabado.click();
	}
	
	public void setAtecionSucursalDomingo() {
		this.atecionSucursalDomingo.click();
	}

	public void setAtecionConcesionarioLunes() {
		this.atecionConcesionarioLunes.click();
	}

	public void setAtecionConcesionarioMartes() {
		this.atecionConcesionarioMartes.click();
	}

	public void setAtecionConcesionarioMiercoles() {
		this.atecionConcesionarioMiercoles.click();
	}

	public void setAtecionConcesionarioJueves() {
		this.atecionConcesionarioJueves.click();
	}

	public void setAtecionConcesionarioViernes() {
		this.atecionConcesionarioViernes.click();
	}

	public void setAtecionConcesionarioSabado() {
		this.atecionConcesionarioSabado.click();
	}
	public void setAtecionConcesionarioDomingo() {
		this.atecionConcesionarioDomingo.click();
	}
	public void guardar() {
		this.guardar.click();
	}

	public void cancelar() {
		this.cancelar.click();
	}
	
	public List<WebElement> getIDsNoAsignados()
	{
		List<WebElement> idsNoAsignados = idsNoAsignadosSorteable.findElements(By.xpath(".//*"));
		return idsNoAsignados;
	}
	
	public List<WebElement>  getIDsAsignados()
	{
		List<WebElement> idsAsignados = idsAsignadosSorteable.findElements(By.xpath(".//*"));
		return idsAsignados;
	}

	public Boolean estaSinAsignacion(String idPostal) {
		List<WebElement> idsNoAsignados = getIDsNoAsignados();
		boolean sinAsignacion = false;
		for (WebElement e : idsNoAsignados) {
			if(e.getText().equals(idPostal))
			{
				sinAsignacion = true;
				break;
			}
		}
		return sinAsignacion;
	}
	
	public Boolean estaAsignadoACO(String idPostal) {
		List<WebElement> idsAsignados = getIDsAsignados();
		boolean asignado = false;
		for (WebElement e : idsAsignados) {
			if(e.getText().equals(idPostal))
			{
				asignado = true;
				break;
			}
		}
		return asignado;
	}

	public void asignarIDPostal(String idPostalPendienteAsignacion) throws InterruptedException {
		WebElement idPostal = idsNoAsignadosSorteable.
				findElement(By.xpath(".//*[contains(text(),'" + idPostalPendienteAsignacion + "')]"));
		System.out.println("Asignando ID Postal: " + idPostalPendienteAsignacion);
		Utilidades.dragAndDrop(idPostal, idsAsignadosSorteable);
		System.out.println("ID Postal: " + idPostalPendienteAsignacion + " asignado.");
	}
	
	public void desasignarIDPostal(String idPostalPendienteDesasignacion) throws InterruptedException {
		WebElement idPostal = idsAsignadosSorteable.
				findElement(By.xpath(".//*[contains(text(),'" + idPostalPendienteDesasignacion + "')]"));
		System.out.println("Desasignando ID Postal: " + idPostalPendienteDesasignacion);
		Utilidades.dragAndDrop(idPostal, idsNoAsignadosSorteable);
		System.out.println("ID Postal: " + idPostalPendienteDesasignacion + " desasignado.");
	}

	public void setAtecionSucursal(String idPostal) 
	{
		WebElement idPostalElement = 
				ldriver.findElement(By.xpath(".//*[contains(text(),'" + idPostal + "')]"));
		idPostalElement.click();
		setAtecionSucursalLunes();
		setAtecionSucursalMartes();
		setAtecionSucursalMiercoles();
		setAtecionSucursalJueves();
		setAtecionSucursalViernes();
		setAtecionSucursalSabado();
		setAtecionSucursalDomingo();
		ldriver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/form/div[2]/div/div[3]/div/button[2]"));
	}
	
	

}
