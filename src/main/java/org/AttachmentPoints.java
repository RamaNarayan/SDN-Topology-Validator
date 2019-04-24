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
public class AttachmentPoints {
	@XmlElement(name="tp-id")
	private String id;
	@XmlElement(name="corresponding-tp")
	private String correspondingTerminationPoint;
	//change it to boolean
	@XmlElement(name="active")
	private String isActive;
}
