package org;

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
public class TerminationPoint {
	@XmlElement(name="tp-id")
	private String id;
	@XmlElement(name="inventory-node-connector-ref")
	private String inventoryNodeConnectorRef;
}
