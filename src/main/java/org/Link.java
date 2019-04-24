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
public class Link {
	@XmlElement(name="link-id")
	private String id;
	@XmlElement(name="source",type=SourceLinkNode.class)
	private SourceLinkNode src;
	@XmlElement(name="destination",type=DestinationLinkNode.class)
	private DestinationLinkNode dst;
}
