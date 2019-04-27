package org;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SourceLinkNode {
	@JsonProperty("source-tp")
	private String terminationPoint;
	@JsonProperty("source-node")
	private String node;
}
