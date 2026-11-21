# Post-contenido | Unidad 2

Proyecto de Patrones de Diseño de Software para la exportación de actas académicas.


## Cómo ejecutar

Requiere JDK 17 o superior y Maven 3.8 o superior. Desde la raíz:

```sh
cd exportador-reportes
mvn compile
mvn exec:java
```

La demostración imprime encabezado, cuerpo y pie de PDF, Excel y HTML como representaciones de texto con marcadores, tal como solicita el laboratorio. Los datos de calificaciones son ficticios. También compara los valores predeterminados con una configuración personalizada, pagina el ejemplo de Excel en bloques de dos filas y muestra el rechazo de la compresión sin destino y de un formato desconocido. El idioma determina el formato numérico. El tamaño, la orientación, el logo y la marca se representan como metadatos en la simulación. Si se indica `outputPath`, se guarda la salida como texto UTF-8, con GZIP cuando se solicita compresión.

## Decisiones de diseño

### Decisión 1. Elegir Abstract Factory

1. Cada formato requiere dos productos relacionados: el cuerpo y el encabezado/pie.
2. CSV incorporará una familia propia con su cuerpo y sus metadatos, más el registro de su fábrica en la configuración de arranque.
3. El riesgo principal es ensamblar productos incompatibles. El servicio obtiene ambos de una única fábrica concreta y no acepta piezas sueltas.

Factory Method habría sido adecuado si la unidad de variación fuera un único documento opaco. Con dos contratos independientes, una fábrica por familia expresa su relación y permite conservar un ensamblador común.

### Decisión 2. Resolver formatos mediante un registro

Un `Map<String, Supplier<ReportFormatFactory>>` enlaza claves de formato con proveedores registrados en `Main`. El servicio resuelve por clave y desconoce las clases concretas. Agregar un proveedor requiere una entrada nueva en la configuración de quien lo usa. Un `switch` obligaría a editar la lógica de resolución para cada formato. Las claves se normalizan y se rechazan tanto los duplicados como los formatos desconocidos.

### Decisión 3. Construir ExportConfig con Builder

El constructor del Builder recibe el formato. Los ocho parámetros opcionales tienen un valor por defecto y pueden indicarse por nombre. El constructor de nueve argumentos haría fácil intercambiar tamaño e idioma o los dos booleanos sin error de tipos. La sobrecarga exigiría numerosos constructores que repiten reglas y algunas combinaciones de argumentos del mismo tipo serían indistinguibles. Los setters sobre el objeto final lo dejarían temporalmente incompleto y exigirían que cada consumidor recordara cuándo validarlo. En cambio, `build()` comprueba la ruta cuando se pide compresión, el límite de filas, el formato, la orientación y el idioma antes de crear una configuración inmutable.

### Decisión 4. ¿El registro necesita Singleton?

**No.** El servicio utiliza el registro por clave y no necesita pasar una instancia como argumento ni consultar su identidad. Inicializar el mapa y registrar tres proveedores cuesta poco. El campo estático ya comparte una fuente de verdad entre los consumidores que usan la misma clase cargada en la JVM. Un método `getInstance()` no resolvería un problema adicional. Si surgiera un requisito confirmado de catálogos separados por universidad, se usarían instancias inyectadas por institución para aislar los formatos.

## Herramientas

Java con destino de compilación 17, Maven y Git.

## Conclusiones

Abstract Factory expresa la compatibilidad entre piezas y mantiene estable el servicio que las combina. El registro permite introducir otra familia sin modificar la resolución por clave. Builder concentra las reglas de una configuración con varios valores opcionales. La decisión sobre Singleton depende de la necesidad de identidad y del alcance del estado compartido.
