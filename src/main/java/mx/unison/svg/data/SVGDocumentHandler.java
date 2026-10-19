package mx.unison.svg.data;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SVGDocumentHandler extends DefaultHandler {

    private boolean inTextElement = false;

    public SVGDocumentHandler() {
        super();
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        String tagName = null;

        if(localName == null || localName.isEmpty()) {
            tagName = qName;
        } else {
            tagName = localName;
        }

        if(tagName.equals("svg")) {
            String width = attributes.getValue("width");
            String height = attributes.getValue("height");
            System.out.printf("SVG Document\n\tWidth: %s , Height: %s\n", width, height);
        }
        if(tagName.equals("g")) {
            String opacity = attributes.getValue("opacity");
            System.out.printf("Group\n\tOpacity: %s\n", opacity);
        }
        if(tagName.equals("circle")) {
            String cx = attributes.getValue("cx");
            String cy = attributes.getValue("cy");
            String r = attributes.getValue("r");
            System.out.printf("Circle\n\tCenter: (%s, %s), Radius: %s\n", cx, cy, r);
        }
        if(tagName.equals("rect")) {
            String x = attributes.getValue("x");
            String y = attributes.getValue("y");
            String width = attributes.getValue("width");
            String height = attributes.getValue("height");
            System.out.printf("Rectangle\n\tPosition: (%s, %s), Width: %s, Height: %s\n", x, y, width, height);
        }
        if(tagName.equals("ellipse")) {
            String cx = attributes.getValue("cx");
            String cy = attributes.getValue("cy");
            String rx = attributes.getValue("rx");
            String ry = attributes.getValue("ry");
            System.out.printf("Ellipse\n\tCenter: (%s, %s), RadiusX: %s, RadiusY: %s\n", cx, cy, rx, ry);
        }
        if(tagName.equals("text")) {
            inTextElement = true;
            String x = attributes.getValue("x");
            String y = attributes.getValue("y");
            System.out.printf("Text\n\tPosition: (%s, %s)\n", x, y);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if(inTextElement) {
            String textContent = new String(ch, start, length).trim();
            if(!textContent.isEmpty()) {
                System.out.printf("\tText Content: %s\n", textContent);
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        String tagName = null;

        if(localName == null || localName.isEmpty()) {
            tagName = qName;
        } else {
            tagName = localName;
        }
          if(tagName.equals("text")) {
            inTextElement = false;
        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }
}
