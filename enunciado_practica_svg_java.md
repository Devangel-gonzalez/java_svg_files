# Práctica de laboratorio: Implementación de elementos gráficos SVG en Java


## 1. Propósito de la práctica

Ampliar la funcionalidad de una aplicación Java que procesa y visualiza archivos SVG (*Scalable Vector Graphics*), mediante la implementación de mecanismos para interpretar y dibujar los elementos gráficos `polyline`, `rect`, `ellipse` y `text`.

Deberás aplicar los principios de programación orientada a objetos, procesamiento de documentos XML y programación gráfica con Java 2D, reutilizando y extendiendo la arquitectura del proyecto proporcionado.

## 2. Objetivo de aprendizaje

Al finalizar la práctica, serás capaz de implementar componentes de software que interpreten atributos de elementos SVG y generen su representación gráfica utilizando la API Java 2D, aplicando principios de modularidad, encapsulamiento y reutilización de código.

## 3. Contexto

Se proporciona un proyecto Java que permite procesar documentos SVG mediante un analizador SAX y visualizar elementos gráficos en una interfaz desarrollada con Swing.

Actualmente, la aplicación implementa la representación gráfica de líneas (`line`) y circulos (`circle`). Sin embargo, los documentos SVG pueden contener otros elementos que requieren interpretar diferentes atributos y aplicar distintas operaciones de dibujo.

El reto consiste en **extender la aplicación existente sin sustituir su arquitectura**, incorporando soporte para cuatro elementos SVG adicionales.

La solución deberá permitir cargar un archivo `.svg` que contenga distintos tipos de elementos gráficos y representarlos correctamente dentro de una misma ventana.

## 4. Actividades de desarrollo

### Actividad 1. Análisis del código fuente

Antes de modificar el proyecto, examine las clases que intervienen en el procesamiento de documentos SVG y su representación gráfica.

Identifique:

1. Cómo se carga y procesa un archivo SVG.
2. Qué responsabilidades tiene el analizador SAX.
3. Cómo se identifican los elementos y sus atributos.
4. Cómo se almacenan los datos geométricos.
5. Cómo se realiza el dibujo utilizando Java 2D.
6. Qué clases deben modificarse o extenderse para incorporar nuevos elementos.

### Actividad 2. Implementación del elemento `polyline`

Incorpore la funcionalidad necesaria para interpretar y representar el elemento SVG `polyline`, que define una secuencia de segmentos de línea conectados.

Ejemplo:

```xml
<polyline
    points="50,50 100,100 150,50 200,100"
    fill="none"
    stroke="blue"
    stroke-width="3"/>
```

**Requerimientos funcionales:**

- Interpretar el atributo `points` y obtener las coordenadas de los vértices.
- Construir una secuencia de segmentos conectados.
- Representar la figura utilizando `Graphics2D`.
- Aplicar los atributos `stroke`, `stroke-width` y `fill`.
- Permitir diferentes cantidades de vértices.
- Mantener abierta la figura, salvo que el relleno requiera cerrar implícitamente el contorno.

### Actividad 3. Implementación del elemento `rect`

Incorpore la representación de rectángulos SVG.

Ejemplo:

```xml
<rect
    x="50"
    y="50"
    width="200"
    height="100"
    fill="yellow"
    stroke="black"
    stroke-width="2"/>
```

**Requerimientos funcionales:**

- Interpretar los atributos `x`, `y`, `width` y `height`.
- Representar el contorno del rectángulo.
- Aplicar el color de relleno definido por `fill`.
- Aplicar el color y grosor del contorno.
- Validar las dimensiones de la figura.
- Incorporar opcionalmente esquinas redondeadas mediante `rx` y `ry`.

### Actividad 4. Implementación del elemento `ellipse`

Extienda la aplicación para interpretar y dibujar elipses.

Ejemplo:

```xml
<ellipse
    cx="200"
    cy="150"
    rx="100"
    ry="50"
    fill="lightblue"
    stroke="navy"
    stroke-width="3"/>
```

**Requerimientos funcionales:**

- Interpretar los atributos `cx`, `cy`, `rx` y `ry`.
- Calcular la región rectangular que delimita la elipse.
- Representar la figura mediante Java 2D.
- Aplicar los atributos de relleno y contorno.
- Validar los valores de los radios.
- Verificar que la elipse se dibuje correctamente cuando sus radios sean diferentes.

### Actividad 5. Implementación del elemento `text`

Incorpore soporte para representar texto contenido en documentos SVG.

Ejemplo:

```xml
<text
    x="100"
    y="250"
    font-family="Arial"
    font-size="24"
    fill="black">
    Programación gráfica con Java
</text>
```

**Requerimientos funcionales:**

- Interpretar los atributos `x` y `y`.
- Recuperar el contenido textual del elemento mediante SAX.
- Interpretar `font-family` y `font-size`.
- Aplicar el color especificado en `fill`.
- Representar el texto utilizando `Graphics2D`.
- Considerar que las coordenadas SVG del texto establecen, por defecto, la posición de su línea base.

### Actividad 6. Integración de los elementos gráficos

Integre las funcionalidades desarrolladas dentro del visualizador SVG.

La aplicación deberá:

1. Procesar documentos que contengan diferentes tipos de elementos gráficos.
2. Almacenar la información de cada elemento en objetos apropiados.
3. Dibujar los elementos respetando el orden en que aparecen en el documento SVG.
4. Permitir que varios tipos de figuras coexistan en una misma representación.
5. Manejar atributos ausentes mediante valores predeterminados apropiados.
6. Evitar que un elemento con datos incorrectos provoque la terminación inesperada de la aplicación.

Se recomienda utilizar una abstracción común para representar los elementos gráficos, de manera que cada clase implemente su propia lógica de dibujo.

## 5. Pruebas de funcionamiento

Construya un archivo denominado `test_shapes.svg` que contenga, como mínimo:

- Dos polilíneas con diferentes cantidades de vértices.
- Dos rectángulos con distintos tamaños y colores.
- Dos elipses con diferentes radios.
- Dos elementos de texto con diferentes tamaños y colores.
- Al menos una línea que permita verificar que la funcionalidad original continúa operando.

El documento deberá ser XML válido y contener un elemento raíz `<svg>` con su espacio de nombres correspondiente.

Abra el documento en un navegador compatible con SVG y compare su representación con la obtenida mediante la aplicación Java.

Registre las diferencias observadas y explique sus posibles causas.

## 6. Requisitos técnicos y restricciones

- Utilizar Java y las bibliotecas estándar para procesamiento XML y gráficos 2D.
- Mantener el mecanismo de procesamiento SAX utilizado en el proyecto.
- Evitar bibliotecas externas que implementen automáticamente la representación SVG.
- Utilizar encapsulamiento y clases con responsabilidades claramente definidas.
- Reutilizar la lógica de interpretación de colores y atributos cuando sea posible.
- No utilizar valores geométricos fijos para sustituir los atributos obtenidos del archivo SVG.
- Documentar los métodos públicos incorporados.

**Alcance:** No se requiere implementar el estándar SVG completo. Se evaluarán los cuatro elementos solicitados, sus atributos especificados y su integración con la funcionalidad existente. No se exige soporte para CSS, transformaciones, gradientes, rutas `path` ni unidades de medida distintas de valores numéricos simples.

## 7. Entregables

Cada estudiante o equipo deberá entregar:

1. **Código fuente:** proyecto Java completo con las modificaciones realizadas.
2. **Archivo SVG de pruebas:** `test_shapes.svg`, que permita verificar las funcionalidades implementadas.
3. **Diagrama de clases:** representación de las clases nuevas o modificadas y sus relaciones.
4. **Evidencias de ejecución:** capturas de pantalla que muestren el funcionamiento de la aplicación y su comparación con un navegador.
5. **Reporte técnico breve:** explicación de las decisiones de diseño, dificultades encontradas, pruebas realizadas y limitaciones de la solución.

## 8. Criterios de evaluación

| Criterio | Ponderación |
|---|---:|
| Implementación de `polyline` | 15% |
| Implementación de `rect` | 15% |
| Implementación de `ellipse` | 15% |
| Implementación de `text` | 15% |
| Integración y funcionamiento conjunto | 15% |
| Diseño orientado a objetos y calidad del código | 15% |
| Pruebas, documentación y evidencias | 10% |
| **Total** | **100%** |

Para evaluar cada elemento gráfico se considerará la interpretación correcta de sus atributos, la precisión de la representación y el manejo de casos límite dentro del alcance establecido.

## 9. Preguntas de reflexión

Incluya en el reporte una respuesta fundamentada a las siguientes preguntas:

1. ¿Qué ventajas ofrece utilizar SAX para procesar documentos SVG?
2. ¿Qué diferencias existen entre interpretar la información geométrica de un elemento y representarlo gráficamente?
3. ¿Cómo contribuye el polimorfismo a simplificar la representación de distintos elementos gráficos?
4. ¿Por qué es importante conservar el orden de los elementos dentro de un documento SVG?
5. ¿Qué modificaciones serían necesarias para incorporar nuevos elementos como `circle`, `polygon` o `path`?

## 10. Resultado esperado

Al concluir la práctica, la aplicación deberá ser capaz de procesar un documento SVG que contenga líneas, polilíneas, rectángulos, elipses y texto, generando una representación gráfica coherente con los atributos definidos en el archivo.

