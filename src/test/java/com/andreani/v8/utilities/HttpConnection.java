package com.andreani.v8.utilities;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.net.HttpURLConnection;

public class HttpConnection 
{
	private static HttpConnection httpConnection = null;
	
	public static HttpConnection getHttpConnection()
	{
		if (httpConnection == null)
			httpConnection = new HttpConnection();
		return httpConnection;
	}

	public void sendGet(String url) throws Exception 
	{
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) 
			response.append(inputLine);
		in.close();

		//print result
		System.out.println(response.toString());
	}
		
	public void sendPost(String url,String rawBody, String contentType) throws Exception 
	{
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type", contentType);
	
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(rawBody);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		String responseMessage = con.getResponseMessage();
		System.out.println("\nEnviando 'POST' request a: " + url);
		System.out.println("Post body : " + rawBody);
		System.out.println("Response Code : " + responseCode);
		System.out.println("Response Message : " + responseMessage);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		System.out.println(response.toString());
	}
	
	public void sendDelete(String url,String parametro, String contentType) throws Exception 
	{
		
		URL obj = new URL(url + parametro);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	
		con.setRequestMethod("DELETE");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type", contentType);
		
		con.setDoOutput(true);
		  	
		int responseCode = con.getResponseCode();
		String responseMessage = con.getResponseMessage();
		System.out.println("\nEnviando 'DELETE' request a: " + url + parametro);
		System.out.println("Response Code : " + responseCode);
		System.out.println("Response Message : " + responseMessage);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		System.out.println(response.toString());
	}
	
	public void sendWSCall(String url,String xmlFileUrl, String contentType,
			String soapAction) throws Exception 
	{
		String content = new String(Files.readAllBytes(
				Paths.get(System.getProperty("user.dir")+ xmlFileUrl)));
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	
		con.setRequestMethod("POST");
		con.setRequestProperty("Accept-Encoding", "gzip,deflate");
		con.setRequestProperty("Content-Type", contentType);
		con.setRequestProperty("SOAPAction", "\"" + soapAction + "\"");
		con.setRequestProperty("User-Agent", "Apache-HttpClient/4.1.1 (java 1.5)");	

		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(content);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		String responseMessage = con.getResponseMessage();
		System.out.println("\nEnviando 'POST' request a: " + url);
		System.out.println("Post body : " + content);
		System.out.println("Response Code : " + responseCode);
		System.out.println("Response Message : " + responseMessage);
	}
}
