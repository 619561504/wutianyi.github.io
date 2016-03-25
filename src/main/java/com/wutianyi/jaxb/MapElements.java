package com.wutianyi.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by hanjiewu on 2016/3/22.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class MapElements {

	public String key;
	public String value;

	public MapElements() {

	}

	public MapElements(String key, String value) {
		this.key = key;
		this.value = value;
	}
}
