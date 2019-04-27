package org;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DestinationLinkNode {
	@JsonProperty("dest-tp")
	private String terminationPoint;
	@JsonProperty("dest-node")
	private String node;
}
