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
public class Addresses {
	@XmlElement(name="id")
	private String id;
	@XmlElement(name="mac")
	private String mac;
	@XmlElement(name="first-seen")
	private String firstSeen;
	@XmlElement(name="last-seen")
	private String lastSeen;
	@XmlElement(name="ip")
	private String ip;
}
