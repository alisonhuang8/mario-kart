package com.ltub.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Serializer {

	private XStream xstream;
	
	public Serializer() {
		xstream = new XStream(new DomDriver());
	}
	
	// also write to file and read from file. also stream. 
	
	public String serialize(Object obj) {
		return xstream.toXML(obj);
	}
	
	public Object deserialize(String xml) {
		return xstream.fromXMLs(xml);
	}
	
}
