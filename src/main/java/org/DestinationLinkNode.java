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
public class DestinationLinkNode {
	@XmlElement(name="dest-tp")
	private String terminationPoint;
	@XmlElement(name="dest-node")
	private String node;
}
