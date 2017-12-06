package com.andreani.v8.utilities;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import com.andreani.v8.base.Base;

public class ConsultaBD extends Base
{
	

	public static HashMap<String,String> obtenerDatosCO(String centroOperativo) throws ClassNotFoundException, SQLException
	{
		HashMap<String, String> branch = new HashMap<String,String>();
		Statement statement = DbManager.getConnection().createStatement();
		String queryString = " " +
				" SELECT b.description as nombre,b.number as nomenclatura,r.description as region, "+
				" businessHour as horarioAtencion, b.telephone, " +
				" b.usaPad, b.centroEmisor, b.mailResponsableSucursal, b.habilitada, " +
				" b.latitude, b.longitude, b.customerService, b.nombreResponsableSucursal, " + 
				" b.apellidoResponsableSucursal "+
				" FROM branch b JOIN region r ON b.region = r.region" +
				" WHERE habilitada = 1 AND b.description = '" + centroOperativo +"'";
		ResultSet rs = statement.executeQuery(queryString);
		if(rs.next())
	    {
			branch.put("nombre",rs.getString(1));
			branch.put("nomenclatura",rs.getString(2));
			branch.put("region",rs.getString(3));
			branch.put("horarioAtencion",rs.getString(4));
			branch.put("telefono",rs.getString(5));
			branch.put("usaPad",rs.getString(6));
			branch.put("centroEmisor",rs.getString(7));
			branch.put("mailResponsableSucursal",rs.getString(8));
			branch.put("habilitada",rs.getString(9));
			branch.put("latitud",rs.getString(10));
			branch.put("longitud",rs.getString(11));
			branch.put("servicioCliente",rs.getString(12));
			branch.put("responsable",rs.getString(13) + " " + rs.getString(14) );
	    }	
		DbManager.closeConnection();
		return branch;	
	}
	
	public static boolean centroOperativoHabilitado(String centroOperativo) throws ClassNotFoundException, SQLException
	{
		boolean existeCO = false;
		Statement statement = DbManager.getConnection().createStatement();
		String queryString = " " +
				" SELECT b.description as nombre " +
				" FROM branch b " +
				" WHERE habilitada = 1 AND b.description = '" + centroOperativo +"'";
		
		ResultSet rs = statement.executeQuery(queryString);
		if(rs.next())
			existeCO = true;
		DbManager.closeConnection();	
		return existeCO;
	}
	
	public static String codigoCentroOperativo (String nombreCO) throws SQLException, ClassNotFoundException
	{
		String codigoCO = null;
		Statement statement = DbManager.getConnection().createStatement();
		String queryString = " " +
				" SELECT top 1 branch " +
				" FROM branch " +
				" WHERE description = '" + nombreCO +"' AND habilitada = 1 ";	
		ResultSet rs = statement.executeQuery(queryString);
		if(rs.next())
			codigoCO =  rs.getString(1);
		DbManager.closeConnection();
		return codigoCO;
	}
	
	public static String ultimoEnvioWS (String documento) throws SQLException, ClassNotFoundException 
	{
		String codigoEnvio = null;
		Statement statement = DbManager.getConnection().createStatement();
		String queryString = " " +
				" SELECT top 1 numbercode " +
				" FROM entityNumber en " +
				" JOIN piece p " +
				" ON en.entityCode = piece " + 
				" WHERE en._op = 2302 AND p.recipientDocumentNumber = '"+ documento +"'" +
				" ORDER BY indate desc ";

		ResultSet rs = statement.executeQuery(queryString);
		if(rs.next())
			codigoEnvio =  rs.getString(1);
		DbManager.closeConnection();
		return codigoEnvio;
	}
	
	public static String sucursal_Actual_Envio (String nroEnvio) throws SQLException
	{
		String nomenclaturaSucursal = "";
		Statement statement = DbManager.getConnection().createStatement();
		String queryString = " " +
				"SELECT number FROM entitynumber " + 
				"JOIN branch " + 
				"ON inbranch = branch " +
				"WHERE numberCode = '" + nroEnvio +"'";
		ResultSet rs = statement.executeQuery(queryString);
		if(rs.next())
			nomenclaturaSucursal =  rs.getString(1);
		DbManager.closeConnection();
		return nomenclaturaSucursal;
	}
	
	public static boolean existeEnvio (String nroEnvio) throws SQLException
	{
		boolean existe = false;
		Statement statement = DbManager.getConnection().createStatement();
		String queryString = " " +
		"SELECT numberCode FROM entitynumber " + 
		"WHERE numberCode = '" + nroEnvio +"'";
		ResultSet rs = statement.executeQuery(queryString);
		if(rs.next())
			existe = true;
		DbManager.closeConnection();
		return existe;
	}
	
	public static boolean estaIDPostalAsignado (String sucursal,
			String provincia, String ciudad, String codigoPostal) throws SQLException
	{
		boolean estaIDPostalAsignado = false;
		Statement statement = DbManager.getConnection().createStatement();
		String queryString = " " +
				"select b.description " +
				"FROM branchAddressPostalCode branchCP " +
				"JOIN branch b " +
				"ON branchCP.branch = b.branch " +
				"JOIN addressState provincia " +
				"ON provincia.addressState = branchCP.addressState " +
				"JOIN addressCity ciudad " +
				"ON ciudad.addressCity = branchCP.addressCity " +
				"WHERE b.description = '" + sucursal + "' " +
				"AND addressPostalCode = " + codigoPostal +
				"AND provincia.description = '" + provincia + "' " +
				"AND ciudad.description = '" + ciudad +"' ";
		ResultSet rs = statement.executeQuery(queryString);
		if(rs.next())
			estaIDPostalAsignado = true;
		DbManager.closeConnection();
		return estaIDPostalAsignado;
	}
	
	public static boolean IdPostaltieneRelacionConAlgunaSucursal (String provincia, String ciudad,
			String codigoPostal) throws SQLException
	{
		boolean hayRelacion = false;
		Statement statement = DbManager.getConnection().createStatement();
		String queryString = " " +
				"SELECT branchCP.* " +
				"FROM branchAddressPostalCode branchCP " +
				"JOIN addressState provincia " +
				"ON provincia.addressState = branchCP.addressState " +
				"JOIN addressCity ciudad " +
				"ON ciudad.addressCity = branchCP.addressCity " +
				"WHERE addressPostalCode = " + codigoPostal +
				"AND provincia.description = '" + provincia + "' " +
				"AND ciudad.description = '" + ciudad +"' ";
		ResultSet rs = statement.executeQuery(queryString);
		if(rs.next())
			hayRelacion = true;
		DbManager.closeConnection();
		return hayRelacion;
	}
	
	public static String getSucursalDistribucion (String envio) throws SQLException
	{
		String sucursalDistribucion = "";
		Statement statement = DbManager.getConnection().createStatement();
		String queryString = " " +
				"SELECT b.description " +
				"FROM entityNumber en " +
				"JOIN branch b " +
				"ON en.toBranch = b.branch " +
				"WHERE numberCode = '" + envio +"'";
		ResultSet rs = statement.executeQuery(queryString);
		if(rs.next())
			sucursalDistribucion = rs.getString(1);
		DbManager.closeConnection();
		return sucursalDistribucion;
	}
	
	
}
