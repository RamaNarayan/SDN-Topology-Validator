package org;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;

public class TopologyValidator {

	public static String getTopology() {
		try {
			String user = "admin";
		    String password = "admin";
		    String baseURL = "http://localhost:8181/restconf/operational/network-topology:network-topology/topology/flow:1/";
		    String containerName = "default";
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hello World");
		System.out.println("getTopology " + getTopology());

	}

}
