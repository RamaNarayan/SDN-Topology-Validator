package org;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;

public class TopologyValidator {

	public String getTopologyFromController() {
		try {
			Properties serverProps = new Properties();
			serverProps.load(new FileInputStream("server_config.properties"));
			String user = serverProps.getProperty("user");
		    String password = serverProps.getProperty("password");
		    String baseURL = serverProps.getProperty("url");
			URL url = new URL(baseURL);
			String authStr = user + ":" + password;
	        String encodedAuthStr = Base64.encodeBase64String(authStr.getBytes());
	        
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", "Basic " + encodedAuthStr);
			con.setRequestProperty("Accept", "application/json");
	        
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			return content.toString();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getTopologyFromFile() {
		return null;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hello World");
		TopologyValidator topo = new TopologyValidator();
		System.out.println("getTopology " + topo.getTopologyFromController());

	}

}
