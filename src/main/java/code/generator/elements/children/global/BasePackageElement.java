package code.generator.elements.children.global;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.ToString;

@XmlAccessorType (XmlAccessType.FIELD)
@Getter
@ToString
public class BasePackageElement {
	
    @XmlAttribute
    private String controller;
    
    @XmlAttribute
    private String service;
    
    @XmlAttribute
    private String repository;
    
    @XmlAttribute
    private String model;
    
    @XmlAttribute
    private String client;
    
}