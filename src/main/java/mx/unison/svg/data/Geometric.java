package mx.unison.svg.data;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import mx.unison.svg.util.Line;
import mx.unison.svg.util.Circle;
import mx.unison.svg.util.Rect;


public class Geometric extends DefaultHandler {
    private static final String CLASS_NAME = Geometric.class.getSimpleName();

    private final static Logger LOG = Logger.getLogger(CLASS_NAME);

   public Geometric() {
        super();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if( localName.equals("line")) {
            double x1 = Double.parseDouble( attributes.getValue("x1") );
            double y1 = Double.parseDouble( attributes.getValue("y1") );
            double x2 = Double.parseDouble( attributes.getValue("x2") );
            double y2 = Double.parseDouble( attributes.getValue("y2") );
            Line line = new Line(x1,y1,x2,y2);
            System.out.printf("Linea\n\tLongitud: %6.2f , pendiente: %6.2f\n",
                    line.lenght(),
                    line.m());
        }

        if( localName.equals("circle") ) {
            double radio = Double.parseDouble( attributes.getValue("r") );
            Circle circle = new Circle(radio);
            System.out.printf("Circulo\n\tArea: %6.2f , perimetro: %6.2f\n",
                    circle.area(),
                    circle.perimeter());
        }

        if( localName.equals("rect")) {
            double width = Double.parseDouble(attributes.getValue("width") );
            double height = Double.parseDouble(attributes.getValue("height"));
            Rect rect = new Rect( width, height) ;
            System.out.printf("Rectangulo\n\tArea: %6.2f , perimetro: %6.2f\n",
                    rect.area(),
                    rect.perimeter());
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
    }

    public static void main(String args[]) {
       if (args.length == 0) {
            LOG.severe("Falta el nombre del documento SVG. Uso:" + "\njava Geometric <filename>");
            System.exit(1);
        }

        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(true);
        SAXParser saxParser = null;

        try {
            saxParser = factory.newSAXParser();
        } catch (ParserConfigurationException e) {
            LOG.severe( e.getMessage());
        } catch (SAXException e) {
            LOG.severe( e.getMessage());
        }

        File xmlFile = new File(args[0]);

        Geometric handler = new Geometric();

        try {
            saxParser.parse(xmlFile, handler);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            LOG.severe( e.getMessage());
        }
    }
}
