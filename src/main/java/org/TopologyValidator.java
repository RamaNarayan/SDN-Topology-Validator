package org;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

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
	
	public static Topology getTopologyObject(File file) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Topology.class);
	        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	        Topology topology = (Topology) unmarshaller.unmarshal(file);
	        return topology;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
		
	}
	
	public static Topology getTopologyObject(String xmlString) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Topology.class);
	        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	        StringReader reader = new StringReader(xmlString);
	        Topology topology = (Topology) unmarshaller.unmarshal(reader);
	        return topology;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
		
	}
	
	public static void main(String[] args) throws JAXBException, IOException {
		// TODO Auto-generated method stub
		TopologyValidator topo = new TopologyValidator();
		
		//get current topology
		String currentTopologyString = topo.getTopologyFromController();
		Topology currentTopology = getTopologyObject(currentTopologyString);
		System.out.println(currentTopology);
		
//		//test string topo
//		String currentTopologyString = new String ( Files.readAllBytes( Paths.get("topology_test.xml") ) );
//		Topology currentTopology = getTopologyObject(currentTopologyString);
//		System.out.println(currentTopology);
		
		//get master topology
		File file = new File("topology_standard.xml");
		Topology masterTopology = getTopologyObject(file);
        System.out.println(masterTopology);
        
        //print difference
        if(currentTopology!=null && masterTopology!=null) {
        	if(currentTopology.getNodes()!=null && masterTopology.getNodes()!=null) {
        		if(currentTopology.getNodes().size()!=masterTopology.getNodes().size()) {
        			int difference = currentTopology.getNodes().size() - masterTopology.getNodes().size();
        			if(difference > 0 ) {
        				System.out.println("New nodes: "+difference);
        			}
        			else {
        				System.out.println("Missing Nodes: "+Math.abs(difference));
        			}
        		}
        	}
        }

	}

}
