package mx.unison.svg.display;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

public class TagsHandler  extends DefaultHandler {
    public static final Logger LOG = Logger.getLogger( TagsHandler.class.getName() );


    private ArrayList<Properties> shapes = null;
    private Properties currentShape = null;

    public TagsHandler() {
        super();
        shapes = new ArrayList<>();
    }

    public ArrayList<Properties> getShapes() {
        return shapes;
    }


    @Override
    public void setDocumentLocator(Locator locator) {
        super.setDocumentLocator(locator);
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
            super.startPrefixMapping(prefix, uri);
    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
        super.endPrefixMapping(prefix);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        String tagName = null;

        if(localName == null || localName.isEmpty()) {
            tagName = qName.toLowerCase();
        } else {
            tagName = localName.toLowerCase();
        }

        if  (tagName.equals("line") ) {
            LOG.info("Starting element " + tagName);
            currentShape = new Properties();
            currentShape.put("type", "line");
            currentShape.put("x1", atts.getValue("x1"));
            currentShape.put("y1", atts.getValue("y1"));
            currentShape.put("x2", atts.getValue("x2"));
            currentShape.put("y2", atts.getValue("y2"));
            currentShape.put("stroke", atts.getValue("stroke"));
            currentShape.put("stroke-width", atts.getValue("stroke-width"));
            shapes.add(currentShape);
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        currentShape = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {

    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {

    }

    @Override
    public void skippedEntity(String name) throws SAXException {

    }
}
