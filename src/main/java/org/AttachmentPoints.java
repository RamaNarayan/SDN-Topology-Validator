package org;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttachmentPoints {
	@JsonProperty("tp-id")
	private String id;
	@JsonProperty("corresponding-tp")
	private String correspondingTerminationPoint;
	//change it to boolean
	@JsonProperty("active")
	private String isActive;
}
