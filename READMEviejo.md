##  Procesamiento y visualización de archivos SVG con Java

Proyecto didáctico que muestra cómo leer documentos **SVG (Scalable Vector Graphics)** mediante el analizador XML **SAX**,
describir sus elementos, calcular propiedades geométricas y dibujar algunas figuras en una ventana **Swing/Java 2D**.

Los gráficos vectoriales escalables o gráficos vectoriales redimensionables (del inglés: Scalable Vector Graphics (**SVG**) 
es un formato de gráficos vectoriales bidimensionales, tanto estáticos como animados, en formato de lenguaje de marcado 
extensible XML (Extensible Markup Language), es decir que se compone por código y cuya especificación es un estándar 
abierto desarrollado por el W3C desde 1999 ([Wikipedia](https://es.wikipedia.org/wiki/Gr%C3%A1ficos_vectoriales_escalables)).

Para conocer más sobre el formato SVG, puedes visitar:
- La página oficial del W3C: [SVG 1.1 Specification](https://www.w3.org/TR/SVG11/).
- La documentación de Mozilla Developer Network (MDN): [SVG - MDN Web Docs](https://developer.mozilla.org/es/docs/Web/SVG).
- La documentación de W3Schools: [SVG Tutorial](https://www.w3schools.com/graphics/svg_intro.asp).

> **Alcance real:** el proyecto incluye tres programas orientados a SVG (`DescribeSVG`, `Geometric` y `DisplayDiagram`) y una clase `Main` de demostración inicial. El visualizador gráfico **solo implementa el dibujo de elementos `<line>`**; no renderiza SVG completo.

## Contenido

- [Tecnologías y requisitos](#tecnologías-y-requisitos)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Compilación](#compilación)
- [Programas y ejemplos de ejecución](#programas-y-ejemplos-de-ejecución)
- [Documentación de las clases](#documentación-de-las-clases)
- [Archivos SVG incluidos](#archivos-svg-incluidos)
- [Limitaciones y observaciones técnicas](#limitaciones-y-observaciones-técnicas)
- [Posibles mejoras](#posibles-mejoras)
- [Referencias](#referencias)

## Tecnologías y requisitos

- **Lenguaje:** Java.
- **Construcción:** Gradle, con *wrapper* configurado para **Gradle 9.8.0**.
- **Interfaz gráfica:** Java Swing (`JFrame`, `JComponent`) y AWT/Java 2D (`Graphics2D`, `BasicStroke`, `Color`).
- **Procesamiento XML:** JAXP (`SAXParser`, `SAXParserFactory`) y SAX (`DefaultHandler`).
- **Pruebas:** JUnit Jupiter, declarado como dependencia de prueba en `build.gradle` (BOM 5.10.0). No se incluyen archivos de pruebas en el ZIP examinado.
- **Dependencias de producción:** no se declaran bibliotecas externas.

Se necesita un **JDK compatible con Gradle 9.8.0** (por ejemplo, JDK 17 o posterior) y un entorno gráfico de escritorio para ejecutar el visualizador. El repositorio no fija una versión de Java mediante `toolchain` ni `sourceCompatibility`.

## Estructura del proyecto

```text
java_svg_files/
├── build.gradle
├── settings.gradle
├── gradlew
├── gradlew.bat
├── gradle/wrapper/
├── src/main/java/mx/unison/
│   ├── Main.java
│   └── svg/
│       ├── data/
│       │   ├── DescribeSVG.java
│       │   ├── Geometric.java
│       │   └── SVGDocumentHandler.java
│       ├── display/
│       │   ├── DisplayDiagram.java
│       │   ├── SVGDiagram.java
│       │   └── TagsHandler.java
│       └── util/
│           ├── Circle.java
│           ├── Line.java
│           └── Rect.java
└── svg/
    ├── ejemplo.svg
    ├── figuras.svg
    └── simple.svg
```

El ZIP también contiene `.git/`, `build/` y un archivo `.iml`: son metadatos de Git, resultados de compilación y configuración del IDE, respectivamente; no forman parte del código fuente necesario para comprender los ejemplos.

## Compilación

Desde el directorio raíz del proyecto:

**macOS / Linux**

```bash
./gradlew classes
```

**Windows (PowerShell)**

```powershell
.\gradlew.bat classes
```

La compilación coloca las clases en `build/classes/java/main`. Para ejecutar clases con `main`, puede utilizarse `java -cp` después de compilar; el proyecto **no aplica el plugin `application`**, por lo que no define una tarea `run` ni una clase principal predeterminada.

Los comandos siguientes se ejecutan **desde la raíz del proyecto**. En Windows, sustituya `:` por `;` en el separador de rutas del classpath.

## Programas y ejemplos de ejecución

### 1. Descripción estructural de SVG — `DescribeSVG`

Lee un archivo SVG con SAX y escribe en consola atributos de etiquetas seleccionadas. El procesamiento se delega a `SVGDocumentHandler`.

```bash
java -cp build/classes/java/main mx.unison.svg.data.DescribeSVG svg/simple.svg
```

**Entrada:** un argumento obligatorio: ruta del archivo SVG.

**Salida:** mensajes con dimensiones del documento, grupos y propiedades de círculos, rectángulos, elipses y texto, cuando estos elementos están presentes. **No dibuja figuras ni modifica el archivo**. El programa finaliza con código 1 si no recibe exactamente un argumento o no puede crear el analizador; utiliza código 2 si falla el análisis.

### 2. Cálculos geométricos — `Geometric`

Analiza etiquetas SVG y realiza cálculos para las figuras que reconoce:

- `<line>`: longitud y pendiente.
- `<circle>`: área y perímetro a partir de `r`.
- `<rect>`: área y perímetro a partir de `width` y `height`.

```bash
java -cp build/classes/java/main mx.unison.svg.data.Geometric svg/figuras.svg
```

**Entrada:** ruta SVG como primer argumento. **Salida:** cálculos geométricos impresos en consola. Se requiere al menos un argumento, pero el programa no rechaza argumentos adicionales.

**Advertencia:** la clase `Line` recibe parámetros en el orden `(x1, x2, y1, y2)`, mientras que `Geometric` le pasa `(x1, y1, x2, y2)`. Esto puede producir longitudes y pendientes incorrectas; consulte [Limitaciones](#limitaciones-y-observaciones-técnicas). Asimismo, la fábrica SAX activa validación sin aportar esquema o DTD explícitos, por lo que pueden aparecer diagnósticos de validación al procesar los SVG de ejemplo.

### 3. Visualización gráfica — `DisplayDiagram`

Abre una ventana Swing titulada **Dibujo** y añade un componente `SVGDiagram` que lee el archivo SVG y dibuja **líneas** con `Graphics2D`.

```bash
java -cp build/classes/java/main mx.unison.svg.display.DisplayDiagram svg/figuras.svg
```

**Entrada:** exactamente una ruta a un SVG. **Salida:** una ventana no redimensionable con el dibujo de los elementos `<line>` que se hayan procesado. El cierre de la ventana termina la aplicación.

El programa no dibuja `<circle>`, `<rect>`, `<polyline>` ni otros elementos aunque estén presentes en el archivo. Las dimensiones preferidas del componente se obtienen de los atributos `width` y `height` de la raíz `<svg>` cuando son enteros válidos.

### 4. Demostración básica — `Main`

Clase independiente, generada como ejemplo inicial de Java. Muestra `Hello and welcome!` y los valores de `i` del 1 al 5; **no procesa SVG**.

```bash
java -cp build/classes/java/main mx.unison.Main
```

## Documentación de las clases

### Paquete `mx.unison.svg.data`

#### `DescribeSVG.java`

**Responsabilidad:** punto de entrada para inspeccionar la estructura de un documento SVG.

- `main(String[] args)`: valida el número de argumentos, crea una `SAXParserFactory` con reconocimiento de espacios de nombres y sin validación, instancia `SVGDocumentHandler` y procesa el archivo indicado.
- Emplea `java.util.logging.Logger` para reportar errores.
- No genera archivos de salida.

#### `SVGDocumentHandler.java`

**Responsabilidad:** receptor de eventos SAX que describe elementos SVG en consola; extiende `DefaultHandler`.

- `startElement(...)`: reconoce `svg` (`width`, `height`), `g` (`opacity`), `circle` (`cx`, `cy`, `r`), `rect` (`x`, `y`, `width`, `height`), `ellipse` (`cx`, `cy`, `rx`, `ry`) y `text` (`x`, `y`).
- `characters(...)`: imprime fragmentos no vacíos del contenido de texto cuando se encuentra dentro de `<text>`.
- `endElement(...)`: desactiva el indicador de texto al cerrar `</text>`.
- `startDocument()` y `endDocument()`: invocan el comportamiento heredado.

No almacena un modelo del documento. Los eventos `characters()` pueden dividir un mismo texto en varios fragmentos.

#### `Geometric.java`

**Responsabilidad:** programa de consola y manejador SAX para calcular medidas geométricas; extiende `DefaultHandler`.

- `main(String[] args)`: configura SAX con reconocimiento de espacios de nombres y validación habilitada; procesa el primer archivo indicado.
- `startElement(...)`: lee atributos numéricos de `line`, `circle` y `rect`, crea objetos `Line`, `Circle` o `Rect` e imprime sus medidas.
- `endElement(...)`: delega al método heredado.

Los atributos faltantes o no numéricos pueden provocar excepciones sin tratamiento específico. El cálculo de pendiente de líneas verticales lanza `ArithmeticException` desde `Line.m()`.

### Paquete `mx.unison.svg.display`

#### `DisplayDiagram.java`

**Responsabilidad:** punto de entrada de la interfaz gráfica.

- `main(String[] args)`: exige una ruta, crea un `JFrame`, añade `SVGDiagram`, desactiva el redimensionamiento y ajusta el tamaño mediante `pack()`.
- El marco se hace visible antes de añadir el componente y de llamar a `pack()`.
- No inicia explícitamente la interfaz desde el *Event Dispatch Thread* (EDT).

#### `SVGDiagram.java`

**Responsabilidad:** componente Swing encargado de cargar datos SVG y pintar las líneas reconocidas; extiende `JComponent`.

- `SVGDiagram(File file)`: crea un analizador SAX; hace una primera pasada para leer `width` y `height` de `<svg>` y una segunda con `TagsHandler` para recuperar líneas.
- `getPreferredSize()`: devuelve las dimensiones extraídas del documento.
- `paintComponent(Graphics g)`: recorre las figuras recopiladas y dibuja las de tipo `line` con `Graphics2D.drawLine(...)`.
- `parseColor(String colorStr)`: reconoce un conjunto limitado de nombres de color (`black`, `white`, `red`, `green`, `blue`, `yellow`, `cyan`, `magenta`, `gray`). También se intenta usar `Color.decode(...)` para colores como `#000`.

El componente mantiene `strokeColor`, `fillColor` y `currentStroke`; `fillColor` está inicializado pero no se usa para dibujar. Aunque el pintado busca la propiedad `stroke-width`, el manejador actual no la almacena, por lo que el ancho indicado en el SVG no se aplica mediante este flujo.

#### `TagsHandler.java`

**Responsabilidad:** transformar eventos SAX de líneas SVG en una lista de propiedades.

- `TagsHandler()`: inicializa `ArrayList<Properties>`.
- `startElement(...)`: únicamente cuando encuentra `<line>`, almacena `type`, `x1`, `y1`, `x2`, `y2` y `stroke`.
- `getShapes()`: devuelve la lista de figuras recopiladas.
- `endElement(...)`: pone `currentShape` en `null`.

No almacena `stroke-width`, grupos, transformaciones, círculos, rectángulos ni polilíneas. Otros métodos SAX sobrescritos no añaden comportamiento.

### Paquete `mx.unison.svg.util`

#### `Circle.java`

Modelo de círculo basado en su radio (`radio`, tipo `double`).

- `Circle(double radio)`: constructor.
- `area()`: calcula `π × radio²`.
- `perimeter()`: calcula `2 × π × radio`.
- `getRadio()` / `setRadio(double)`: acceso y modificación del radio.

No valida radios negativos.

#### `Rect.java`

Modelo de rectángulo definido por `width` y `height` (`double`).

- `Rect(double width, double height)`: constructor.
- `area()`: calcula `width × height`.
- `perimeter()`: calcula `2 × width + 2 × height`.
- Métodos `getWidth()`, `setWidth(double)`, `getHeight()` y `setHeight(double)`.

No valida dimensiones negativas.

#### `Line.java`

Modelo de segmento definido por cuatro coordenadas (`double`).

- `Line()`: constructor sin argumentos.
- `Line(double x1, double x2, double y1, double y2)`: constructor parametrizado **en este orden exacto**.
- `lenght()`: calcula la distancia euclidiana entre los extremos; el nombre del método contiene una errata (`lenght`, no `length`).
- `m()`: calcula `(y2 - y1) / (x2 - x1)` y lanza `ArithmeticException` cuando `x2 == x1`.
- Métodos de acceso: `getX1()`, `setX1(double)`, `getY1()`, `setY1(double)`, `getY2()` y `setY2(double)`.

No dispone de `getX2()` ni `setX2(double)`.

### Clase `mx.unison.Main`

- `main(String[] args)`: imprime un mensaje de bienvenida y ejecuta un bucle de cinco iteraciones.
- Es independiente de los paquetes `svg.data`, `svg.display` y `svg.util`.

## Flujo de procesamiento

```text
Archivo SVG (XML)
       |
       +--> DescribeSVG --> SAXParser --> SVGDocumentHandler --> Descripción en consola
       |
       +--> Geometric (DefaultHandler) --> Line / Circle / Rect --> Cálculos en consola
       |
       +--> DisplayDiagram --> SVGDiagram
                                 |--> SAXParser (dimensiones del SVG)
                                 |--> TagsHandler (recopila líneas)
                                 `--> paintComponent / Graphics2D --> Ventana Swing
```
