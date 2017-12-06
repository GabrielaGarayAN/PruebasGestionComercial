package com.andreani.v8.step_Definitions;

import static org.testng.Assert.assertEquals;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import com.andreani.v8.base.Base;
import com.andreani.v8.pageObjects.CentroOperativoForm;
import com.andreani.v8.pageObjects.CentroOperativoHomePage;
import com.andreani.v8.utilities.ConsultaBD;
import com.andreani.v8.utilities.HttpConnection;
import com.andreani.v8.utilities.Utilidades;
import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Dado;
import cucumber.api.java.es.Entonces;

public class CentroOperativo extends Base {
	private String nombreCO;
	private String ultimoEnvio;
	private Map<String,String> datosCO;
	private HttpConnection httpConnection;
	private CentroOperativoForm centroOperativoForm;
	
	public CentroOperativo () {
		httpConnection = HttpConnection.getHttpConnection();
	}
	
	@Dado("^que quiero crear la sucursal \"([^\"]*)\"$")
	public void iniciarCreacionCentroOperativo(String nombreCO) throws InterruptedException {
		this.nombreCO = nombreCO;
		home = new CentroOperativoHomePage(driver);
		Utilidades.waitLoadingContent();
		centroOperativoForm = home.abrirFormNuevoCO();
	}

	@Dado("^ingreso los siguientes datos y confirmo$")
	public void ingresarDatos(DataTable datos) throws InterruptedException
	{
		datosCO = datos.asMap(String.class, String.class); 
		centroOperativoForm.completarFormNuevoCO(datosCO.get("Tipo"), datosCO.get("Nombre CO"), 
				datosCO.get("Responsable"),datosCO.get("Calle"),
				datosCO.get("Provincia"),datosCO.get("Localidad"),
				datosCO.get("Telefono"), datosCO.get("Mail"),
				datosCO.get("Horarios Atencion"), datosCO.get("Region"),
				datosCO.get("Atencion al Cliente"),	datosCO.get("Usa PAD") );
		Thread.sleep(200);
		home = centroOperativoForm.guardarForm();
	}

	@Entonces("^la sucursal se crea correctamente$")
	public void sucursalCreadaCorrectamente() throws ClassNotFoundException, SQLException{
	
		assertEquals(home.existeCO(nombreCO),true);
		
		HashMap<String, String> datosSucursal = ConsultaBD.obtenerDatosCO(nombreCO);
		assertEquals(datosSucursal.get("nombre").toUpperCase(), nombreCO.toUpperCase());
		assertEquals(datosSucursal.get("nomenclatura"), nombreCO.substring(0, 3).toUpperCase());
		assertEquals(datosSucursal.get("region"), datosCO.get("Region"));
		assertEquals(datosSucursal.get("horarioAtencion"), datosCO.get("Horarios Atencion"));
		assertEquals(datosSucursal.get("telefono"), datosCO.get("Telefono"));
		assertEquals(datosSucursal.get("usaPad"),
				traducirUsaPad (datosCO.get("Usa PAD")));
	}

	@Entonces("^puedo dar de alta envíos en la misma$")
	public void altaYAdmisionEnvio() throws Exception{
		String nroEnvio = altaEnvio();
		System.out.println("Envio: " + nroEnvio + " Alta");
		admitirEnvio ("TUR",nroEnvio);
		System.out.println("Envio: " + nroEnvio + " Adminito");
		assertEquals(ConsultaBD.existeEnvio(nroEnvio), true);
		assertEquals(ConsultaBD.sucursal_Actual_Envio(nroEnvio), "TUR");
	}
	
	@Cuando("^doy de alta un envío con ID Postal destino 1834 BUENOS AIRES TEMPERLEY$")
	public void altaEnvioTEM() throws Exception
	{
		ultimoEnvio = altaEnvio();
		assertEquals(ConsultaBD.existeEnvio(ultimoEnvio), true);
	}
	
	@Entonces("^la sucursal responsable de distribuir el envío es \"([^\"]*)\"$")
	public void assertSucursalDistribucionDeUltimoEnvio(String sucursal) throws SQLException
	{
		String sucursalDistActual = ConsultaBD.getSucursalDistribucion(ultimoEnvio);
		assertEquals(sucursalDistActual.toUpperCase(), sucursal.toUpperCase());
	}
	
	@Entonces("^se va a dar de alta el número Andreani$")
	public void assertAltaEnvio() throws SQLException
	{
		assertEquals(ConsultaBD.existeEnvio(ultimoEnvio), true);
	}

	@Dado("^que quiero cambiar el responsable de la sucursal \"([^\"]*)\"$")
	public void iniciarCambioResponsable(String nombreCO){
		this.nombreCO = nombreCO;
		home = new CentroOperativoHomePage(driver);
		centroOperativoForm = home.abrirFormEdicionCO(nombreCO);
	}

	@Cuando("^cambio el responsable a \"([^\"]*)\"$")
	public void cambiarResponsable(String responsable){
		centroOperativoForm.setNombreResponsable(responsable);
		centroOperativoForm.guardarForm();
	}

	@Entonces("^el responsable de la sucursal pasa a ser \"([^\"]*)\"$")
	public void assertResponsable(String responsable) throws ClassNotFoundException, SQLException{
		HashMap<String, String> datosSucursal = ConsultaBD.obtenerDatosCO(nombreCO);
		assertEquals(datosSucursal.get("responsable"), responsable);
	}

	@Entonces("^veo un mensaje de indicando los datos faltantes$")
	public void assertMensajeDatosFaltantes() throws Exception {
		boolean faltanDatos = centroOperativoForm.tieneDatosFaltantes();
		assertEquals(faltanDatos, true);
		centroOperativoForm.cerrarFormCO();
	}

	@Entonces("^la sucursal no se da de alta$")
	public void la_sucursal_no_se_da_de_alta() throws Exception 
	{
		assertEquals(existeCO(), false);
	}

	@Entonces("^la sucursal deja de existir")
	public void la_sucursal_deja_de_existir() throws Exception{
		assertEquals(existeCO(), false);
	}

	@Dado("^que quiero dar de baja la sucursal \"([^\"]*)\"$")
	public void iniciarBajaCO(String nombreCO) {
		this.nombreCO = nombreCO;
		home = new CentroOperativoHomePage(driver);
		home.buscarCO(nombreCO);
	}

	@Cuando("^confirmo la baja de la sucursal$")
	public void bajaCO() throws InterruptedException {
		home.eliminarCO(nombreCO);
		Thread.sleep(500);
		Utilidades.acceptAlert();
	}

	@Dado("^que quiero cambiar el nombre y nomenclatura de la sucursal \"([^\"]*)\"$")
	public void iniciarModificacionCO(String nombreCO){
		this.nombreCO = nombreCO;
		home = new CentroOperativoHomePage(driver);
		centroOperativoForm = home.abrirFormEdicionCO(nombreCO);
	}

	@Cuando("^cambio el nombre de \"([^\"]*)\" por \"([^\"]*)\" y su nomenclatura de \"([^\"]*)\" a \"([^\"]*)\"$")
	public void modificarNombreYNomenclaturaCO(String nombreCO_original, String nombreCO_nuevo,
			String nomenclaturaCO_original, String nomenclaturaCO_nueva){
		centroOperativoForm.setNombreCO(nombreCO_nuevo);
		centroOperativoForm.setNomenclaturaCO(nomenclaturaCO_nueva);
		centroOperativoForm.guardarForm();
		this.nombreCO = nombreCO_nuevo;
	}

	@Entonces("^la sucursal \"([^\"]*)\" pasa a llamarse \"([^\"]*)\" y su nomenclatura es \"([^\"]*)\"$")
	public void verificarNombreNomenclatura(String nombreCO_original, String nombreCO_nuevo,
		String nomenclaturaCO_nueva) throws ClassNotFoundException, SQLException {
		assertEquals(true, home.existeCO(nombreCO_nuevo));
	
		HashMap<String, String> datosSucursal = ConsultaBD.obtenerDatosCO(nombreCO_nuevo);
		assertEquals(datosSucursal.get("nombre").toUpperCase(), nombreCO_nuevo.toUpperCase());
		assertEquals(datosSucursal.get("nomenclatura"), nomenclaturaCO_nueva);
	}	

	private String altaEnvio() throws Exception 
	{
		httpConnection.sendWSCall(
				"http://pfstestsrv:9732/ImposicionRemota.svc",
				"/src/test/resources/requests/altaEnvio.xml", 
				"text/xml;charset=UTF-8", 
				"http://www.andreani.com.ar/IConfirmacionCompra/ConfirmarCompraConRecibo");	
		return ConsultaBD.ultimoEnvioWS("37612000");
	}
	
	private void admitirEnvio(String nomenclaturaCentroOperativo, String nroEnvio) throws Exception
	{
		String request = ""
				+ "{ "
				+ "  \"codigoSucursal\":\"" + nomenclaturaCentroOperativo + "\","
				+ "  \"conRemito\":true, "
				+ "  \"numeroAndreani\":\"" + nroEnvio +"\""
				+ " } ";
		
		httpConnection.sendPost(
				"http://ICSDESADCSRV01:8731/api/envios/admitirEnvio?format=json",
				request,
				config.getProperty("API_contentType"));
	}

	private boolean existeCO() throws Exception {
		boolean existeCO_en_interfaz = home.existeCO(nombreCO);
		boolean CO_habilitada_en_bd = ConsultaBD.centroOperativoHabilitado(nombreCO);
		return (CO_habilitada_en_bd && existeCO_en_interfaz);
	}
	
	private String traducirUsaPad(String Usa_pad_interfaz) {
		String usa_pad_bd = "";
		if (Usa_pad_interfaz.equals("Si"))
			usa_pad_bd = "1";
		else
			usa_pad_bd = "0";
		return usa_pad_bd;
	}
	
	@After("@altaCO , @modificarCO")
	public void bajaLogica() throws Exception 
	{
		System.out.println("Empieza el after de AltaCO");
		System.out.println("Se da de baja la sucursal: " + this.nombreCO);
		String codigoCO = ConsultaBD.codigoCentroOperativo(this.nombreCO);
		if(codigoCO != null)
			httpConnection.sendDelete(config.getProperty("API_URL"),
				codigoCO,config.getProperty("API_contentType"));
	}
	
	@Before ("@bajaCO , @modificarCO")
	public void altaCO() throws Throwable 
	{
		System.out.println("Se ejecutó el before del BajaCO");
		String request = new String(Files.readAllBytes(
				Paths.get(System.getProperty("user.dir")+ 
						"/src/test/resources/requests/altaSucursal.json")));
		httpConnection.sendPost(config.getProperty("API_URL"),request,
				config.getProperty("API_contentType"));
	}	
}
