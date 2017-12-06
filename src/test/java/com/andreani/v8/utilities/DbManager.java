package com.andreani.v8.utilities;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.andreani.v8.base.Base;

public class DbManager extends Base
{
	private static Connection con = null;
	private static Connection conn = null;

	private static void setDbConnection()
	{
		System.out.println("Seteando conexión SQL DB");
		System.out.println("Driver: " + bd.getProperty("SQL_driver"));
		System.out.println("Username: " + bd.getProperty("SQL_username"));
		try{
			Class.forName(bd.getProperty("SQL_driver"));
			con = DriverManager.getConnection(bd.getProperty("SQL_url"),bd.getProperty("SQL_username"), bd.getProperty("SQL_password"));
			if(!con.isClosed())
				System.out.println("Successfully connected to SQL server");	
		}catch(Exception e)
		{
			
		}	
	}
	
	public  void setMysqlDbConnection() 
    {
		try{
			Class.forName (bd.getProperty("MySQL_driver")).newInstance ();
			conn = DriverManager.getConnection ("MySQL_url", "MySQL_username", "MySQL_password");
			if(!conn.isClosed())
				System.out.println("Successfully connected to MySQL server");
			}
		catch (Exception e)
		{
			System.err.println ("Cannot connect to database server"); 
       // monitoringMail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject+" - (Script failed with Error, Datamart database used for reports, connection not established)", TestConfig.messageBody, TestConfig.attachmentPath, TestConfig.attachmentName);
		}
    }
	
	public static Connection getConnection() 
	{
		if(con == null)
			setDbConnection();
		return con;
	}
	
	public static void closeConnection ()
	{
		if(con != null)
		{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		con = null;
	}
	
	public static List<String> getQuery(String query) throws SQLException{
		Statement St = con.createStatement();
		ResultSet rs = St.executeQuery(query);
		List<String> values = new ArrayList<String>();
		while(rs.next()){
			values.add(rs.getString(1));	
		}
		return values;
	}
	
	public static List<String> getMysqlQuery(String query) throws SQLException{
		Statement St = conn.createStatement();
		ResultSet rs = St.executeQuery(query);
		List<String> values1 = new ArrayList<String>();
		while(rs.next()){
			values1.add(rs.getString(1));			
		}
		return values1;
	}
}
