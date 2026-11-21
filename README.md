# Post-contenido | Unidad 2

Proyecto de Patrones de Diseño de Software para la exportación de actas académicas.

## Decisiones de diseño

### Decisión 1. Elegir Abstract Factory

1. Cada formato requiere dos productos relacionados: el cuerpo y el encabezado/pie.
2. CSV incorporará una familia propia con su cuerpo y sus metadatos, más el registro de su fábrica en la configuración de arranque.
3. El riesgo principal es ensamblar productos incompatibles. El servicio obtiene ambos de una única fábrica concreta y no acepta piezas sueltas.

Factory Method habría sido adecuado si la unidad de variación fuera un único documento opaco. Con dos contratos independientes, una fábrica por familia expresa su relación y permite conservar un ensamblador común.

### Decisión 2. Resolver formatos mediante un registro

Un `Map<String, Supplier<ReportFormatFactory>>` enlaza claves de formato con proveedores registrados en `Main`. El servicio resuelve por clave y desconoce las clases concretas. Agregar un proveedor requiere una entrada nueva en la configuración de quien lo usa. Un `switch` obligaría a editar la lógica de resolución para cada formato. Las claves se normalizan y se rechazan tanto los duplicados como los formatos desconocidos.
