#  Procesamiento y visualización de archivos SVG con Java
## Angel Israel Gonzalez Torres

## 7. Entregables

1. **Código fuente:** proyecto Java completo con las modificaciones realizadas. ✔️
2. **Archivo SVG de pruebas:** `test_shapes.svg`, que permita verificar las funcionalidades implementadas. ✔️
3. **Diagrama de clases:** representación de las clases nuevas o modificadas y sus relaciones. ✔️
   <img width="2155" height="1500" alt="Diagrama sin título-Página-5 drawio" src="https://github.com/user-attachments/assets/2ff5845f-416c-4aad-8eab-6a430433ba74" />
   
5. **Evidencias de ejecución:** capturas de pantalla que muestren el funcionamiento de la aplicación y su comparación con un navegador. ✔️
   Ejecucion
   <img width="1190" height="975" alt="image" src="https://github.com/user-attachments/assets/de407146-763a-4a73-9690-061abf6130d4" />
   Navegador
   <img width="986" height="341" alt="image" src="https://github.com/user-attachments/assets/2df26e4f-6b08-468f-88a2-7910d644c9dd" />
   
6. **Reporte técnico breve:** explicación de las decisiones de diseño, dificultades encontradas, pruebas realizadas y limitaciones de la solución.
Proyecto didáctico que muestra cómo leer documentos **SVG (Scalable Vector Graphics)** mediante el analizador XML **SAX**,
describir sus elementos, calcular propiedades geométricas y dibujar algunas figuras en una ventana **Swing/Java 2D**.

### Decisiones de diseño
La aplicación original solo soportaba los elementos ``line`` y ``circle``. Se implementó el soporte para:
``polyline``
``rect``
``ellipse``
``text``

Se decidió mantener la arquitectura original del proyecto, basada en:

``TagsHandler``: responsable de leer el archivo SVG con SAX y almacenar los atributos de cada elemento en objetos ``Properties``.
``SVGDiagram``: responsable de recorrer la lista de elementos y dibujarlos usando Graphics2D.

Cada nuevo elemento se agregó siguiendo el mismo patrón:

Reconocerlo en ``TagsHandler.startElement()``
Dibujarlo en ``SVGDiagram.paintComponent()`` según su tipo

Para el elemento ``text`` fue necesario manejar el evento ``characters()`` de SAX, ya que el contenido del texto no viene en atributos, sino entre las etiquetas.

### Dificultades encontradas
El contenido del texto no se obtenía - Solucion: Se implementó el método ``characters()`` y una bandera ``insideText``
Algunos colores no se reconocían - Solucion: Se reutilizó el método ``parseColor()`` existente y se manejó el caso de ``Color.decode()``
Coordenadas con decimales - Soulcion: Se usó Double.parseDouble() y se convirtió a int

### Pruebas realizadas
Se creó el archivo ``svg/test_shapes.svg`` que contiene:
1 polilinea
1 rectangulo
1 elipse 
1 texto
<img width="740" height="730" alt="image" src="https://github.com/user-attachments/assets/52cfb91b-5e3f-4745-9e05-d118f1b3ad53" />

## 9. Preguntas de reflexión
1. ¿Qué ventajas ofrece utilizar SAX para procesar documentos SVG?
   Es eficiente en memoria porque no carga todo el documento en un árbol como DOM. Procesa el archivo de forma secuencial mediante eventos, lo que lo hace adecuado para archivos grandes. Además, es parte de la API de java JAXP, por lo que no requiere bibliotecas externas.
    
2. ¿Qué diferencias existen entre interpretar la información geométrica de un elemento y representarlo gráficamente?
   Interpretar eses leer los atributos del elemento como las coordenadas, colores, tamaños, etc. Mientras que representar es traducir esa informacion a llamadas de la API gráfica como drawLine, fillRect, drawString.
   
3. ¿Cómo contribuye el polimorfismo a simplificar la representación de distintos elementos gráficos?
   El polimorfismo permite que cada figura se dibuje a sí misma, en vez de que un solo método tenga que saber cómo dibujar todas.
   
5. ¿Por qué es importante conservar el orden de los elementos dentro de un documento SVG?
   El orden de aparición determina el orden de apilamiento. Los elementos que se aparecen despues se dibujan encima de los anteriores.
   
6. ¿Qué modificaciones serían necesarias para incorporar nuevos elementos como `circle`, `polygon` o `path`?
   En ``TagsHandler``: agregar la detección del nuevo elemento y guardar sus atributos.
   En ``SVGDiagram``: agregar la lógica de dibujo correspondiente.
   Para ``polygon`` el proceso sería muy similar a ``polyline``, solo que se debe cerrar la figura automáticamente.
