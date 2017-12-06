package com.andreani.v8.step_Definitions;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import com.andreani.v8.base.Base;
import com.andreani.v8.pageObjects.CentroOperativoHomePage;
import com.andreani.v8.pageObjects.GestionIDPostalesForm;
import com.andreani.v8.utilities.ConsultaBD;
import com.andreani.v8.utilities.Utilidades;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Dado;
import cucumber.api.java.es.Entonces;
import cucumber.api.java.es.Y;

public class IDPostal extends Base
{
	private GestionIDPostalesForm gestionIDPostalesForm;
	private String idPostalPendienteAsignacion;
	private String idPostalAsignado;
	private String nombreCO;

	@Dado("^que quiero asignar el ID Postal \"([^\"]*)\" existente a la sucursal \"([^\"]*)\"$")
	public void iniciarAsignacionCPExistente (String idPostal, String sucursal) throws Exception
	{
		this.nombreCO = sucursal;
		home = new CentroOperativoHomePage(driver);
		gestionIDPostalesForm = home.abrirFormGestionIdPostales(sucursal);
		this.idPostalPendienteAsignacion = idPostal;
	}
	
	@Dado("^que quiero asignar un ID Postal no asignado a la sucursal \"([^\"]*)\"$")
	public void asignarPrimerIDPostalNoAsignado(String sucursal) throws Exception
	{
		this.nombreCO = sucursal;
		home = new CentroOperativoHomePage(driver);
		gestionIDPostalesForm = home.abrirFormGestionIdPostales(sucursal);
		if (!gestionIDPostalesForm.getIDsNoAsignados().isEmpty())
			this.idPostalPendienteAsignacion = gestionIDPostalesForm.getIDsNoAsignados().get(0).getText();
		else
			throw new PendingException("No hay ID Postales sin asignación");
	}
	
	@Cuando("^se lo asigno a \"([^\"]*)\" con forma de atencion por sucursal$")
	public void asignarIDPostalPendientePorSucursal(String sucursal) throws InterruptedException
	{
		gestionIDPostalesForm.asignarIDPostal(idPostalPendienteAsignacion);
		gestionIDPostalesForm.setAtecionSucursal(idPostalPendienteAsignacion);
		gestionIDPostalesForm.guardar();
		idPostalAsignado = idPostalPendienteAsignacion;
		idPostalPendienteAsignacion = null;
	}
	
	@Entonces("^el ID Postal deja de estar pendiente de asignación$")
	public void assertIDPostalAsignadoCorrectamente() throws Exception
	{
		gestionIDPostalesForm = home.abrirFormGestionIdPostales(nombreCO);
		Boolean SinAsignacion = gestionIDPostalesForm.estaSinAsignacion(idPostalAsignado);
		assertFalse(SinAsignacion, "El ID Postal: " + idPostalAsignado + "No está asignado o no existe");
	}
	
	@Dado("^que el ID Postal \"([^\"]*)\" esta asignado a la sucursal de distribucion \"([^\"]*)\"$")
	public void assertIDPostalYaAsignadoEnBD(String idPostal, String sucursal) throws SQLException
	{
		
		String provincia = obtenerProvinciaIdPostal(idPostal);
		String ciudad = obtenerCiudadIdPostal(idPostal);
		String cp = obtenerCPIdPostal(idPostal);
		boolean cpAsignado = ConsultaBD.estaIDPostalAsignado(sucursal.toUpperCase(),provincia,ciudad,cp);	
		assertTrue(cpAsignado , "El ID Postal: " + idPostal + "no está asignado");
		idPostalAsignado = idPostal;
	}
	
	@Entonces("^el ID Postal \"([^\"]*)\" no permite ser asignado a otra sucursal$")
	public void assertNoPermiteAsignacion(String idPostal)
	{
		boolean permiteAsignacion = gestionIDPostalesForm.estaSinAsignacion(idPostal);
		assertFalse(permiteAsignacion, "El ID Postal permite la asignación");
	}
	
	@Cuando("^reasigno el ID Postal \"([^\"]*)\" de \"([^\"]*)\" a \"([^\"]*)\"$")
	public void reasignarIDPostal(String idPostal, String sucursalOrigen, String sucursalDestino) throws Exception
	{
		desasignarIDPostal(idPostal, sucursalOrigen);
		Utilidades.waitLoadingContent();
		Thread.sleep(300);
		Utilidades.waitLoadingContent();
		iniciarAsignacionCPExistente(idPostal, sucursalDestino);
		asignarIDPostalPendientePorSucursal(sucursalDestino);
	}
	
	@Cuando("^desasigno el ID Postal \"([^\"]*)\" de \"([^\"]*)\"$")
	public void desasignarIDPostal(String idPostal, String sucursal) throws Exception
	{
		this.nombreCO = sucursal;
		home = new CentroOperativoHomePage(driver);
		gestionIDPostalesForm = home.abrirFormGestionIdPostales(sucursal);
		gestionIDPostalesForm.desasignarIDPostal(idPostal);
	}
	
	@Entonces("^puedo asignar el ID Postal \"([^\"]*)\" a \"([^\"]*)\"$")
	public void assertPuedoAsignarElIDPostal(String idPostal, String sucursal)
	{
		assertIDPostalNoAsignado(idPostal);
	}
	
	@Dado("^que el ID Postal \"([^\"]*)\" no esta asignado a una sucursal de distribucion$")
	public void assertIDPostalNoAsignado(String idPostal)
	{
		idPostalPendienteAsignacion = idPostal;
		Boolean SinAsignacion = gestionIDPostalesForm.estaSinAsignacion(idPostal);
		assertTrue(SinAsignacion, "El ID Postal: " + idPostal + "ya está asignado o no existe");
	}

	@Y("por lo tanto no esta definida la relación ID Postal - Sucursal de distribución")
	public void assertRelacionIDPostalSucursalNoDefinida() throws SQLException
	{
		String provincia = obtenerProvinciaIdPostal(idPostalPendienteAsignacion);
		String ciudad = obtenerCiudadIdPostal(idPostalPendienteAsignacion);
		String cp = obtenerCPIdPostal(idPostalPendienteAsignacion);
		boolean tieneRelacionConAlgunaSucursal =  ConsultaBD.IdPostaltieneRelacionConAlgunaSucursal(provincia,ciudad,cp);
		assertFalse(tieneRelacionConAlgunaSucursal, "El Id Postal " + idPostalPendienteAsignacion + " tiene una relación en la BD");
	}

	@Y("por lo tanto esta definida la relación ID Postal - Sucursal de distribución")
	public void assertRelacionIDPostalSucursalDefinida() throws SQLException
	{
		String provincia = obtenerProvinciaIdPostal(idPostalPendienteAsignacion);
		String ciudad = obtenerCiudadIdPostal(idPostalPendienteAsignacion);
		String cp = obtenerCPIdPostal(idPostalPendienteAsignacion);
		boolean tieneRelacionConAlgunaSucursal =  ConsultaBD.IdPostaltieneRelacionConAlgunaSucursal(provincia,ciudad,cp);
		assertTrue(tieneRelacionConAlgunaSucursal, "El Id Postal " + idPostalPendienteAsignacion + " no tiene una relación en la BD");
	}

	
	
	public String obtenerCiudadIdPostal (String idPostal)
	{
		return idPostal.substring(idPostal.indexOf(",")+1);
	}
	public String obtenerCPIdPostal (String idPostal)
	{
		return idPostal.substring(1, 5);
	}
	public String obtenerProvinciaIdPostal (String idPostal)
	{
		return idPostal.substring(7, idPostal.indexOf(","));
	}
	
	
	
	@After("@asignacionIDPostal")
	public void desasignarIDPostalAfter() throws Exception 
	{
		desasignarIDPostal(idPostalAsignado,nombreCO);	
	}

}
