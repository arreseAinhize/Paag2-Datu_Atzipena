# Proyecto: konbertsore-apunteak

## Resumen breve
Este repositorio es una pequeña aplicación de consola en Java para gestionar/fabricar archivos (.TXT, .XML, .JSON, .CSV) con validaciones básicas y menú interactivo.

## Estructura de carpetas y archivos (explicación breve)
- [.vscode/settings.json](.vscode/settings.json)  
  Configuración del proyecto Java para VSCode (rutas de source, salida bin y librerías referenciadas).

- src/ (código fuente Java)
  - [src/main/App.java](src/main/App.java) — Clase principal y menú principal. Ejecuta los submenús de cada tipo de archivo. Referencia: [`main.App`](src/main/App.java).
  - [src/main/error/ErroreenKudeaketa.java](src/main/error/ErroreenKudeaketa.java) — Utilidades para comprobar/crear/leer/escribir/ eliminar archivos y comprobar existencia de NAN en CSV/XML. Referencia: [`main.error.ErroreenKudeaketa`](src/main/error/ErroreenKudeaketa.java).
  - [src/main/io/KudeatzaileaTXT.java](src/main/io/KudeatzaileaTXT.java) — Menú y operaciones previstas para .TXT. Referencia: [`main.io.KudeatzaileaTXT`](src/main/io/KudeatzaileaTXT.java).
  - [src/main/io/KudeatzaileaXML.java](src/main/io/KudeatzaileaXML.java) — Menú y operaciones previstas para .XML. Referencia: [`main.io.KudeatzaileaXML`](src/main/io/KudeatzaileaXML.java).
  - [src/main/io/KudeatzaileaJSON.java](src/main/io/KudeatzaileaJSON.java) — Menú y operaciones previstas para .JSON. Referencia: [`main.io.KudeatzaileaJSON`](src/main/io/KudeatzaileaJSON.java).
  - [src/main/io/KudeatzaileaCSV.java](src/main/io/KudeatzaileaCSV.java) — Menú y operaciones previstas para .CSV. Referencia: [`main.io.KudeatzaileaCSV`](src/main/io/KudeatzaileaCSV.java).
  - [src/main/model/Ikaslea.java](src/main/model/Ikaslea.java) — Modelo de datos para un alumno (NAN, nombre, apellidos, edad, dirección) con validaciones en el constructor. Referencia: [`main.model.Ikaslea`](src/main/model/Ikaslea.java).
  - [src/main/utils/Filtroak.java](src/main/utils/Filtroak.java) — Funciones de validación reutilizables (isnumeric, isDNI, isIzena, isAdina, isHelbidea, etc.). Referencia: [`main.utils.Filtroak`](src/main/utils/Filtroak.java).
  - [src/main/utils/Gehigarriak.java](src/main/utils/Gehigarriak.java) — Utilidades de consola (scanner compartido, colores, limpiar pantalla, pausa). Referencia: [`main.utils.Gehigarriak`](src/main/utils/Gehigarriak.java).

- resources/ y src/resources/ (plantillas/ejemplos)
  - resources/csv/, resources/json/, resources/txt/, resources/xml/ — carpetas para almacenar ejemplos o ficheros de entrada/salida. (No hay archivos concretos listados en lib adjunto.)

- bin/ (salida compilada)
  - Estructura preparada para clases compiladas (bin/main/...). VSCode/Java coloca aquí los .class.

- lib/ (dependencias)
  - Carpeta para jars externos si fueran necesarios (actualmente vacía en el repositorio adjuntado).

## Qué hace cada parte (resumen funcional)
- Menús (.TXT/.XML/.JSON/.CSV): los gestores en [src/main/io](src/main/io) presentan opciones (crear, listar, leer, añadir, actualizar, borrar, convertir). En el código actual las operaciones específicas están marcadas como TODO o impresas como mensajes; la estructura del menú está lista para implementar la lógica concreta.
- Validaciones: [`main.utils.Filtroak`](src/main/utils/Filtroak.java) valida formatos (DNI/NAN, nombres, edades, direcciones).
- Modelo: [`main.model.Ikaslea`](src/main/model/Ikaslea.java) encapsula un registro de alumno y lanza IllegalArgumentException si los campos no pasan validación.
- Errores y I/O: [`main.error.ErroreenKudeaketa`](src/main/error/ErroreenKudeaketa.java) contiene funciones para comprobar existencia de archivos, leerlos/escribirlos, crear/eliminar y buscar NAN en CSV/XML.

## Cómo compilar y ejecutar (desde la raíz del proyecto)
- Compilar con javac (coloca los .class en bin):
  - Windows / PowerShell:
    - javac -d bin -sourcepath src (find . -name "*.java" -print)  (ajustar comando según shell)
  - Alternativa directa:
    - mkdir bin
    - javac -d bin src/main/**/*.java
- Ejecutar:
  - java -cp bin main.App
- En VSCode: usar la extensión Java; la configuración está en [.vscode/settings.json](.vscode/settings.json).

## Notas y próximos pasos recomendados
- Implementar la lógica concreta de lectura/escritura y conversión en las clases de [src/main/io](src/main/io) (ahora mayormente menús).
- Añadir pruebas unitarias (no incluidas).
- Añadir ejemplos en resources/ para facilitar pruebas manuales.

## Lista rápida de archivos (links)
- [.vscode/settings.json](.vscode/settings.json)  
- [src/main/App.java](src/main/App.java) — [`main.App`](src/main/App.java)  
- [src/main/error/ErroreenKudeaketa.java](src/main/error/ErroreenKudeaketa.java) — [`main.error.ErroreenKudeaketa`](src/main/error/ErroreenKudeaketa.java)  
- [src/main/io/KudeatzaileaTXT.java](src/main/io/KudeatzaileaTXT.java) — [`main.io.KudeatzaileaTXT`](src/main/io/KudeatzaileaTXT.java)  
- [src/main/io/KudeatzaileaXML.java](src/main/io/KudeatzaileaXML.java) — [`main.io.KudeatzaileaXML`](src/main/io/KudeatzaileaXML.java)  
- [src/main/io/KudeatzaileaJSON.java](src/main/io/KudeatzaileaJSON.java) — [`main.io.KudeatzaileaJSON`](src/main/io/KudeatzaileaJSON.java)  
- [src/main/io/KudeatzaileaCSV.java](src/main/io/KudeatzaileaCSV.java) — [`main.io.KudeatzaileaCSV`](src/main/io/KudeatzaileaCSV.java)  
- [src/main/model/Ikaslea.java](src/main/model/Ikaslea.java) — [`main.model.Ikaslea`](src/main/model/Ikaslea.java)  
- [src/main/utils/Filtroak.java](src/main/utils/Filtroak.java) — [`main.utils.Filtroak`](src/main/utils/Filtroak.java)  
- [src/main/utils/Gehigarriak.java](src/main/utils/Gehigarriak.java) — [`main.utils.Gehigarriak`](src/main/utils/Gehigarriak.java)

