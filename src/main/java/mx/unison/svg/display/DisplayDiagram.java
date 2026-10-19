package mx.unison.svg.display;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class DisplayDiagram {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java DisplayDiagram <svg-file>");
            System.exit(1);
        }

        File file = new File(args[0]);

        JFrame frame = new JFrame("Dibujo");

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new SVGDiagram(file));
        frame.setResizable(false);
        frame.pack();

    }
}
