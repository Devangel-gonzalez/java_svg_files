package mx.unison.svg.data;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.logging.Logger;

public class DescribeSVG {
    public static Logger logger = Logger.getLogger(DescribeSVG.class.getName());

    public static void main(String[] args) {
        System.out.println("Describing SVG file...");
        if (args.length != 1) {
            System.out.println("Usage: java DescribeSVG <svg-file>");
            System.exit(1);
        }

        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(false);

        File svgFilePath = new File(args[0]);

        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
        } catch (Exception e) {
            logger.severe("Error creating SAX parser: " +e.getMessage());
            System.exit(1);
        }

        SVGDocumentHandler handler = new SVGDocumentHandler();

        try {
            parser.parse(svgFilePath, handler);
        } catch (Exception e) {
            logger.severe("Error parsing SVG file: " +e.getMessage());
            System.exit(2);
        }
    }
}
