package com.nvfredy.testapp.util;

import com.nvfredy.testapp.entity.History;
import org.springframework.util.ObjectUtils;

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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HistoryXMLParser {

    private static boolean dataAvailable = true;

    public static List<History> parseFile(File fileName) {
        List<History> historiesList = new ArrayList<>();
        History history = null;
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(fileName));

            while (reader.hasNext()) {
                XMLEvent xmlEvent = reader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    if (startElement.getName().getLocalPart().equals("row")) {
                        history = new History();

                        Attribute tradedateAttr = startElement.getAttributeByName(new QName("TRADEDATE"));
                        if (tradedateAttr != null) {
                            history.setTradeDate(LocalDate.parse(tradedateAttr.getValue()));
                        }

                        Attribute secIdAttr = startElement.getAttributeByName(new QName("SECID"));
                        if (secIdAttr != null) {
                            history.setSecId(secIdAttr.getValue());
                        }

                        Attribute numTradesAttr = startElement.getAttributeByName(new QName("NUMTRADES"));
                        if (numTradesAttr != null) {
                            history.setNumTrades(Double.valueOf(numTradesAttr.getValue()));
                        }
                        Attribute openAttr = startElement.getAttributeByName(new QName("OPEN"));
                        if (!ObjectUtils.isEmpty(openAttr) && !openAttr.getValue().isEmpty()) {
                            history.setOpen(Double.valueOf(openAttr.getValue()));
                        }
                        Attribute closeAttr = startElement.getAttributeByName(new QName("CLOSE"));
                        if (!ObjectUtils.isEmpty(closeAttr) && !closeAttr.getValue().isEmpty()) {
                            history.setClose(Double.valueOf(closeAttr.getValue()));
                        }
                    }
                }
                if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("row") && dataAvailable) {
                        historiesList.add(history);
                    }
                }
                if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("data")) {
                        dataAvailable = false;
                    }
                }
            }

        } catch (FileNotFoundException | XMLStreamException exc) {
            exc.printStackTrace();
        }
        return historiesList;
    }

}
