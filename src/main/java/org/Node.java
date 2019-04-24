package org;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class Node {
	@XmlElement(name="node-id")
	private String nodeId;
	@XmlElement(name="inventory-node-ref")
	private String inventoryNodeReference;
	@XmlElement(name="attachment-points",type=AttachmentPoints.class)
	private AttachmentPoints attachmentPoints;
	@XmlElement(name="id")
	private String id;
	@XmlElement(name="termination-point",type=TerminationPoint.class)
	private List<TerminationPoint> terminationPoint;
	@XmlElement(name="addresses",type=Addresses.class)
	private Addresses addresses;
}
