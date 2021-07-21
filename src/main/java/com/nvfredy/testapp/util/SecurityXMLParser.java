package com.nvfredy.testapp.util;

import com.nvfredy.testapp.entity.Security;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class SecurityXMLParser {

    public static List<Security> parseFile(File fileName) {
        List<Security> securitiesList = new ArrayList<>();
        Security security = null;
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(fileName));

            while (reader.hasNext()) {
                XMLEvent xmlEvent = reader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    if (startElement.getName().getLocalPart().equals("row")) {
                        security = new Security();
                        Attribute idAttr = startElement.getAttributeByName(new QName("id"));
                        if (idAttr != null) {
                            security.setId(Long.parseLong(idAttr.getValue()));
                        }
                        Attribute secIdAttr = startElement.getAttributeByName(new QName("secid"));
                        if (secIdAttr != null) {
                            security.setSecId(secIdAttr.getValue());
                        }
                        Attribute nameAttr = startElement.getAttributeByName(new QName("name"));
                        if (nameAttr != null) {
                            security.setName(nameAttr.getValue());
                        }
                        Attribute emitentTitleAttr = startElement.getAttributeByName(new QName("emitent_title"));
                        if (emitentTitleAttr != null) {
                            security.setEmitentTitle(emitentTitleAttr.getValue());
                        }
                        Attribute regnumberAttr = startElement.getAttributeByName(new QName("regnumber"));
                        if (regnumberAttr != null) {
                            security.setRegNumber(regnumberAttr.getValue());
                        }
                    }
                }
                if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("row")) {
                        securitiesList.add(security);
                    }
                }
            }

        } catch (FileNotFoundException | XMLStreamException exc) {
            exc.printStackTrace();
        }
        return securitiesList;
    }

}
