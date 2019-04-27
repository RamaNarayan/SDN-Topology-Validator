package org;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Link {
	@JsonProperty("link-id")
	private String id;
	@JsonProperty("source")
	private SourceLinkNode src;
	@JsonProperty("destination")
	private DestinationLinkNode dst;
}
