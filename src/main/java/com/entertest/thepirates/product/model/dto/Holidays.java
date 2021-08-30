package com.entertest.thepirates.product.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "item")
@Getter
@Setter
@ToString
public class Holidays {
    @XmlElement(name = "locdate")
    private String locdate;

    @XmlElement(name = "isHoliday")
    private String isHoliday;

}
