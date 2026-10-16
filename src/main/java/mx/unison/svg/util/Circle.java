package mx.unison.svg.util;

public class Circle {
    double radio;

    public Circle(double radio) {
        this.radio = radio;
    }

    public double area() {
        return Math.PI * Math.pow(radio, 2.0);
    }

    public double perimeter() {
        return 2.0 * Math.PI * radio;
    }

    public double getRadio() {
        return radio;
    }

    public void setRadio(double radio) {
        this.radio = radio;
    }
}
