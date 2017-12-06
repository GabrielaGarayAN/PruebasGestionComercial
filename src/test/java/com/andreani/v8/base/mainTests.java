package com.andreani.v8.base;

import java.sql.SQLException;

import com.andreani.v8.utilities.ConsultaBD;

public class mainTests {

	public static void main(String[] args) throws SQLException {
		Base b = new Base();
		b.openBrowser();
		assertIDPostalYaAsignadoEnBD("(1834) BUENOS AIRES,TEMPERLEY","Temperley");
	}

	private static void assertIDPostalYaAsignadoEnBD(String idPostal, String centroOperativo) throws SQLException {
				String provincia = idPostal.substring(7, idPostal.indexOf(","));
				String ciudad = idPostal.substring(idPostal.indexOf(",")+1);
				String cp = idPostal.substring(1, 5);
				System.out.println(cp);
				System.out.println(ciudad);
				System.out.println(provincia);
				
				System.out.println(ConsultaBD.estaIDPostalAsignado
						(centroOperativo.toUpperCase(),provincia,ciudad,cp));	
	}

}
