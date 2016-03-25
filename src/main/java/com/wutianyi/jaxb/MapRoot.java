package com.wutianyi.jaxb;

import com.google.api.client.util.Maps;
import com.google.common.reflect.Reflection;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

/**
 * Created by hanjiewu on 2016/3/22.
 */
@XmlRootElement(name = "map")
@XmlAccessorType(XmlAccessType.FIELD)
public class MapRoot {

	@XmlJavaTypeAdapter(value = MapAdapter.class)
	public Map<String, String> mapProperties;


	public static void main(String[] args) throws JAXBException, IOException, SAXException {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		String xml = " <xml>\n" +
				" <ToUserName><![CDATA[toUser]]></ToUserName>\n" +
				" <FromUserName><![CDATA[fromUser]]></FromUserName> \n" +
				" <CreateTime>1348831860</CreateTime>\n" +
				" <MsgType><![CDATA[text]]></MsgType>\n" +
				" <Content><![CDATA[this is a test]]></Content>\n" +
				" <MsgId>1234567890123456</MsgId>\n" +
				" </xml>";
		try {
			DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
			NodeList nodeList = document.getElementsByTagName("xml").item(0).getChildNodes();
			int len = nodeList.getLength();
			for (int i = 0; i < len; i ++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					System.out.println(nodeList.item(i).getTextContent().trim());
				}
			}


		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
}
