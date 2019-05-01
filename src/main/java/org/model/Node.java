package org.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Node {
	@JsonProperty("node-id")
	private String nodeId;
	@JsonProperty("opendaylight-topology-inventory:inventory-node-ref")
	private String inventoryNodeReference;
	@JsonProperty("host-tracker-service:attachment-points")
	private List<AttachmentPoints> attachmentPoints;
	@JsonProperty("host-tracker-service:id")
	private String hostTrackerServiceId;
	@JsonProperty("termination-point")
	private List<TerminationPoint> terminationPoint;
	@JsonProperty("host-tracker-service:addresses")
	private List<Addresses> addresses;
}
