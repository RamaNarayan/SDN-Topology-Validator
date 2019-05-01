package org;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBException;

import org.apache.commons.codec.binary.Base64;
import org.model.Topology;
import org.model.TopologyRoot;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TopologyValidator {

	public static boolean isDiff;

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

	public static TopologyRoot getTopologyFromJSON(String json)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		TopologyRoot topology = objectMapper.readValue(json, TopologyRoot.class);
		return topology;
	}

	public static TopologyRoot getTopologyFromJSON(File jsonFile)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		TopologyRoot topology = objectMapper.readValue(jsonFile, TopologyRoot.class);
		return topology;
	}

	public static void printDifference(TopologyRoot master, TopologyRoot current, StringBuilder sb) {
		if (master == null || master.getTopology() == null)
			return;
		if (current == null || current.getTopology() == null) {
			System.out.println("No current Topology");
			sb.append("No current Topology\n");
		}

		Map<String, Topology> masterMap = master.getTopology().stream()
				.collect(Collectors.toMap(Topology::getId, topology -> topology));

		for (Topology currentTopology : current.getTopology()) {
			Topology masterTopology = masterMap.get(currentTopology.getId());

			if (masterTopology == null) {
				System.out.println("new flow " + currentTopology.getId());
				sb.append("new flow " + currentTopology.getId() + "\n");
			} else {
				System.out.println("difference in flow " + currentTopology.getId());
				sb.append("difference in flow " + currentTopology.getId() + "\n");
				isDiff = false;
				printDifferenceInNodesCount(currentTopology, masterTopology, sb);
				printDifferenceInLinksCount(currentTopology, masterTopology, sb);
				if (!isDiff) {
					System.out.println("No difference");
					sb.append("No difference\n");
				}

			}

		}

	}

	public static void printDifferenceInNodesCount(Topology currentTopology, Topology masterTopology,
			StringBuilder sb) {
		if (currentTopology.getNodes() != null && masterTopology.getNodes() != null) {
			if (currentTopology.getNodes().size() != masterTopology.getNodes().size()) {
				isDiff = true;
				int difference = currentTopology.getNodes().size() - masterTopology.getNodes().size();
				if (difference > 0) {
					System.out.println("New nodes: " + difference);
					sb.append("New nodes: " + difference + "\n");
				} else {
					System.out.println("Missing Nodes: " + Math.abs(difference));
					sb.append("Missing Nodes: " + Math.abs(difference) + "\n");
				}
			}
		}

	}

	public static void printDifferenceInLinksCount(Topology currentTopology, Topology masterTopology,
			StringBuilder sb) {
		if (currentTopology.getLinks() != null && masterTopology.getLinks() != null) {
			if (currentTopology.getLinks().size() != masterTopology.getLinks().size()) {
				isDiff = true;
				int difference = currentTopology.getLinks().size() - masterTopology.getLinks().size();
				if (difference > 0) {
					System.out.println("New Links: " + difference);
					sb.append("New Links: " + difference + "\n");
				} else {
					System.out.println("Missing Links: " + Math.abs(difference));
					sb.append("Missing Links: " + Math.abs(difference));
				}
			}
		}

	}

	private static void writeToFile(String str) throws FileNotFoundException {
		Date date = new Date();
		long time = date.getTime();
		PrintWriter writer = new PrintWriter("results_" + time + ".txt");
		writer.println(str);
		writer.flush();
		writer.close();
	}

	public static void main(String[] args) throws JAXBException, IOException {

		if (args.length > 0) {
			TopologyValidator topo = new TopologyValidator();

			// get current topology
			String currentTopologyString = topo.getTopologyFromController();
			TopologyRoot currentTopology = getTopologyFromJSON(currentTopologyString);

			// get master topology
			File file = new File(args[0]);
			TopologyRoot masterTopology = getTopologyFromJSON(file);

			StringBuilder sb = new StringBuilder();
			printDifference(masterTopology, currentTopology, sb);
			writeToFile(sb.toString());
		}

	}

}
