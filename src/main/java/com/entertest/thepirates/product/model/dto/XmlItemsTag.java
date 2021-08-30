package com.entertest.thepirates.product.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "items")
@Getter
@Setter
@ToString
public class XmlItemsTag {
    @XmlElement(name = "item")
    private Holidays[] Holidays;
}
