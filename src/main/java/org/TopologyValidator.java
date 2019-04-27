package org;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBException;

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	public static TopologyRoot getTopologyFromJSON(String json) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		TopologyRoot topology = objectMapper.readValue(json, TopologyRoot.class);
		return topology;
	}
	
	public static TopologyRoot getTopologyFromJSON(File jsonFile) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		TopologyRoot topology = objectMapper.readValue(jsonFile, TopologyRoot.class);
		return topology;
	}
	
	public static void printDifference(TopologyRoot master, TopologyRoot current) {
		if(master==null || master.getTopology()==null)
			return;
		if(current == null || current.getTopology()==null)
			System.out.println("No current Topology");
		Map<String,Topology> masterMap =master.getTopology().stream().collect(Collectors.toMap(Topology::getId,topology->topology));
		
		for(Topology currentTopology: current.getTopology()) {
			Topology masterTopology = masterMap.get(currentTopology.getId());
			
			if(masterTopology==null) {
				System.out.println("new flow "+currentTopology.getId());
			}
			else {				
				System.out.println("difference in flow "+currentTopology.getId());
				boolean isDiff = false;
				if(currentTopology.getNodes()!=null && masterTopology.getNodes()!=null) {					
					if(currentTopology.getNodes().size()!=masterTopology.getNodes().size()) {
						isDiff = true;
						int difference = currentTopology.getNodes().size() - masterTopology.getNodes().size();
	        			if(difference > 0 ) {
	        				System.out.println("New nodes: "+difference);
	        			}
	        			else {
	        				System.out.println("Missing Nodes: "+Math.abs(difference));
	        			}
	        		}
	        	}
				if(!isDiff)
					System.out.println("No difference");
			}
			
        }
			
		
		
	}
	
	public static void main(String[] args) throws JAXBException, IOException {
		// TODO Auto-generated method stub
		TopologyValidator topo = new TopologyValidator();
		
		//get current topology
		String currentTopologyString = topo.getTopologyFromController();
		System.out.println(currentTopologyString);
		TopologyRoot currentTopology = getTopologyFromJSON(currentTopologyString);
		System.out.println(currentTopology);
		
//		//test string topo
//		String currentTopologyString = new String ( Files.readAllBytes( Paths.get("C:\\Users\\raman\\eclipse-workspace\\topology-validator\\target\\topology_test.json") ) );
//		TopologyRoot currentTopology = getTopologyFromJSON(currentTopologyString);
//		System.out.println(currentTopology);
		
		//get master topology
		File file = new File("topology_standard.json");
		//File file = new File("C:\\Users\\raman\\eclipse-workspace\\topology-validator\\target\\topology_standard.json");
		TopologyRoot masterTopology = getTopologyFromJSON(file);
        System.out.println(masterTopology);
        
        printDifference(masterTopology,currentTopology);

	}

}
