package org;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Topology {
	@JsonProperty("topology-id")
	private String id;
	@JsonProperty("node")
	private List<Node> nodes;
	@JsonProperty("link")
	private List<Link> links;	
}
