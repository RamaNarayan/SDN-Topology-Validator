package org;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TerminationPoint {
	@JsonProperty("tp-id")
	private String id;
	@JsonProperty("opendaylight-topology-inventory:inventory-node-connector-ref")
	private String inventoryNodeConnectorRef;
}
