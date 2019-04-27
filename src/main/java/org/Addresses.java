package org;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Addresses {
	@JsonProperty("id")
	private String id;
	@JsonProperty("mac")
	private String mac;
	@JsonProperty("first-seen")
	private String firstSeen;
	@JsonProperty("last-seen")
	private String lastSeen;
	@JsonProperty("ip")
	private String ip;
}
