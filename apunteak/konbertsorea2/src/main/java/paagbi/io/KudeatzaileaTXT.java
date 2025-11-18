// Declaración del paquete y nombre de la clase
package paagbi.io;

// Importaciones de clases necesarias para entrada/salida y manipulación de datos
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Importaciones de clases propias del proyecto
import paagbi.Main;
import paagbi.utils.*; // Filtroak, Gehigarriak - Utilidades y filtros
import paagbi.error.*; // ErroreenKudeaketa - Manejo de errores
import paagbi.model.*; // Objetuak (ikaslea) - Modelo de datos (estudiante)

/**
 * Clase para gestionar operaciones con archivos TXT
 * Proporciona funcionalidades CRUD (Crear, Leer, Actualizar, Eliminar) para archivos de texto
 */
public class KudeatzaileaTXT {

    /**
     * Menú principal para operaciones con archivos TXT
     * Muestra un menú interactivo con diferentes opciones
     */
    public static void menua() {
        String aukera; // Variable para almacenar la opción del usuario
        
        // Bucle do-while para mantener el menú activo
        do {
            Gehigarriak.kontsolaGarbitu(); // Limpiar la consola
            System.out.println(Gehigarriak.Urdina + "¿Qué quieres hacer?");
            System.out.println(Gehigarriak.Cyan + "================================");
            System.out.println(Gehigarriak.Berdea + "1. Crear archivo TXT.");
            System.out.println("2. Leer archivo TXT.");
            System.out.println("3. Añadir datos a archivo TXT");
            System.out.println("4. Actualizar archivo TXT.");
            System.out.println("5. Eliminar archivo TXT.");
            System.out.println("6. Convertir archivo TXT a formato CSV.");
            System.out.println(Gehigarriak.Urdina + "7. Salir");
            System.out.println(Gehigarriak.Cyan + "================================");
            System.out.print(Gehigarriak.Horia + "Opción: " + Gehigarriak.RESET);
            aukera = Gehigarriak.in.next();
            
            // Validar si la entrada es numérica
            if (Filtroak.isnumeric(aukera) == true) {
                switch (aukera) {
                    case "1":
                        Gehigarriak.kontsolaGarbitu();
                        System.out.println("Crear archivo TXT");
                        txtFitxategiaSortu(); // Llamar al método para crear archivo
                        break;
                    case "2":
                        Gehigarriak.kontsolaGarbitu();
                        txtFitxategiaIrakurri(); // Llamar al método para leer archivo
                        Gehigarriak.aurreraJarraitu(); // Esperar que usuario presione Enter
                        break;
                    case "3":
                        Gehigarriak.kontsolaGarbitu();
                        txtFitxategianDatuakGehitu(); // Añadir datos al archivo
                        break;
                    case "4":
                        Gehigarriak.kontsolaGarbitu();
                        txtFitxategianDatuakEguneratu(); // Actualizar datos del archivo
                        break;
                    case "5":
                        Gehigarriak.kontsolaGarbitu();
                        txtFitxategianDatuakEzabatu(); // Eliminar datos del archivo
                        break;
                    case "6":
                        Gehigarriak.kontsolaGarbitu();
                        txtFitxategiaCSVraBihurtu(); // Convertir a CSV
                        break;
                    case "7":
                        Gehigarriak.kontsolaGarbitu();
                        System.out.println(Gehigarriak.Gorria + "¡Menú Principal!" + Gehigarriak.RESET);
                        try {
                            Main.main(null); // Volver al menú principal
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println(Gehigarriak.Gorria + "Error al iniciar la aplicación: " + e.getMessage());
                        }
                        return; // Salir del método
                    default:
                        Gehigarriak.kontsolaGarbitu();
                        System.out.println(Gehigarriak.Gorria + "Opción incorrecta, intenta de nuevo.");
                        System.out.print(Gehigarriak.Horia + "Opción: " + Gehigarriak.RESET);
                        aukera = Gehigarriak.in.next();
                        break;
                }
            } else {
                // Si no es numérico, mostrar error
                Gehigarriak.kontsolaGarbitu();
                System.out.print(Gehigarriak.Gorria + "¡Debes introducir un número!" + Gehigarriak.RESET);
                try {
                    Thread.sleep(2000); // Esperar 2 segundos
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while (!aukera.equals("7")); // Continuar hasta que se seleccione salir
    }

    /**
     * Método para crear un nuevo archivo TXT
     * Solicita al usuario el nombre del archivo y lo crea en la ruta especificada
     */
    public static void txtFitxategiaSortu() {
        String fileName, path;
        Scanner sc = Gehigarriak.in;

        do {
            Gehigarriak.kontsolaGarbitu();
            System.out.print(Gehigarriak.Horia + "Introduce el nombre del archivo que quieres crear: " + Gehigarriak.RESET);
            fileName = sc.next();
            fileName = Filtroak.removeSpaces(fileName); // Eliminar espacios
            path = "apunteak\\konbertsorea2\\src\\main\\resources\\TXT\\" + fileName + ".txt";

            File fitx = new File(path);
            // Verificar si el archivo ya existe
            if (fitx.exists()) {
                System.out.println(Gehigarriak.Gorria + "Ya existe un archivo con ese nombre, prueba con otro." + Gehigarriak.RESET);
                continue; // Pedir otro nombre
            }

            // Intentar crear el archivo
            if (ErroreenKudeaketa.fitxategiaSortu(path)) {
                System.out.println(Gehigarriak.Berdea + "Archivo creado correctamente: " + path + Gehigarriak.RESET);
            } else {
                System.out.println(Gehigarriak.Gorria + "El archivo no se ha creado: " + path + Gehigarriak.RESET);
                ErroreenKudeaketa.fitxategiaSortu(path);
            }
            break; // Salir del bucle si el archivo se creó correctamente

        } while (true);
    }

    /**
     * Método para leer y mostrar el contenido de un archivo TXT
     * Muestra primero la lista de archivos disponibles
     */
    public static void txtFitxategiaIrakurri() {
        String fileName, path;

        // Mostrar archivos TXT disponibles
        txtFitxategiakBistaratu();

        // Solicitar el archivo a leer
        System.out.print(Gehigarriak.Horia + "Introduce el nombre del archivo que quieres leer: " + Gehigarriak.RESET);
        fileName = Gehigarriak.in.next();
        Filtroak.removeSpaces(fileName);
        path = "apunteak\\konbertsorea2\\src\\main\\resources\\TXT\\" + fileName + ".txt";
        
        // Verificar si se puede leer el archivo
        if (ErroreenKudeaketa.fitxategiaIrakurri(path) == true) {
            try {
                // Leer y mostrar todas las líneas del archivo
                java.util.List<String> lerroak = java.nio.file.Files.readAllLines(java.nio.file.Paths.get(path));
                for (String lerroa : lerroak) {
                    System.out.println(lerroa);
                }
            } catch (java.io.IOException e) {
                System.out.println(Gehigarriak.Gorria + "Error: No se puede leer el archivo.");
                System.err.println(e.getMessage() + Gehigarriak.RESET);
            }
        } else {
            // Mostrar error si no se puede leer
            System.out.println(Gehigarriak.Gorria + "El archivo no se ha leído: " + path + Gehigarriak.RESET);
            ErroreenKudeaketa.fitxategiaIrakurri(path);
        }
    }

    /**
     * Método para añadir datos de un estudiante a un archivo TXT
     * Valida el DNI y la edad antes de añadir los datos
     */
    public static void txtFitxategianDatuakGehitu() {
        String fileName, path;
        Scanner sc = Gehigarriak.in;

        System.out.println("Añadir datos a archivo TXT");
        txtFitxategiakBistaratu(); // Mostrar archivos disponibles

        // Seleccionar archivo
        System.out.print(Gehigarriak.Horia + "¿A qué archivo quieres añadir datos? Introduce nombre: " + Gehigarriak.RESET);
        fileName = sc.next();
        fileName = Filtroak.removeSpaces(fileName);
        path = "apunteak\\konbertsorea2\\src\\main\\resources\\TXT\\" + fileName + ".txt";

        sc.nextLine(); // Limpiar buffer del Scanner
        String nan, adinaStr;
        int adina;

        // Validación del DNI (Número de Identificación)
        do {
            System.out.print("DNI (8 números + 1 letra): ");
            nan = sc.nextLine();
            if (!Filtroak.isDNI(nan)) {
                System.out.println(Gehigarriak.Gorria + "DNI incorrecto. Debe tener 8 números y 1 letra." + Gehigarriak.RESET);
                continue;
            }
            // Verificar si el DNI ya existe en el archivo
            if (ErroreenKudeaketa.ifExistsNan(nan, path)) {
                continue; // Pedir otro DNI
            }
            break; // DNI válido y no existe en el archivo
        } while (true);

        // Validación de la edad
        do {
            System.out.print("Edad: ");
            adinaStr = sc.nextLine();
            if (!Filtroak.isAdina(adinaStr)) {
                System.out.println(Gehigarriak.Gorria + "Edad incorrecta. Solo debe contener números." + Gehigarriak.RESET);
                continue;
            }
            adina = Integer.parseInt(adinaStr);
            break;
        } while (true);

        // Crear objeto Estudiante
        Ikaslea p = new Ikaslea(nan, adina);

        // Añadir al archivo
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            bw.write(p.toTXT()); // Escribir en formato TXT
            bw.newLine();
            System.out.println(Gehigarriak.Berdea + "Datos añadidos correctamente al archivo." + Gehigarriak.RESET);
        } catch (IOException e) {
            System.out.println(Gehigarriak.Gorria + "Error: No se pueden añadir datos al archivo." + Gehigarriak.RESET);
            System.err.println(e.getMessage());
        }
    }

    /**
     * Método para actualizar datos de un estudiante en un archivo TXT
     * Permite modificar todos los campos de un registro existente
     */
    public static void txtFitxategianDatuakEguneratu() {
        String fileName, path;
        List<Ikaslea> ikasleak = new ArrayList<>(); // Lista para almacenar estudiantes
        Scanner sc = Gehigarriak.in;

        System.out.println("Actualizar datos en archivo TXT");
        txtFitxategiakBistaratu(); // Mostrar archivos disponibles

        // Seleccionar archivo
        System.out.print(Gehigarriak.Horia + "¿Qué archivo quieres actualizar? Introduce nombre: " + Gehigarriak.RESET);
        fileName = sc.next();
        fileName = Filtroak.removeSpaces(fileName);
        path = "apunteak\\konbertsorea2\\src\\main\\resources\\TXT\\" + fileName + ".txt";

        // Leer archivo y crear objetos Estudiante
        int luzeera = 2; // Longitud por defecto (formato corto)
        String banatzailea = ","; // Separador

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String lerroa;
            System.out.println("Datos en el archivo:");
            while ((lerroa = br.readLine()) != null) {
                System.out.println(lerroa);
                String[] zatiak = lerroa.split(banatzailea);
                if (zatiak.length == 2) { 
                    // Formato corto: DNI, Edad
                    luzeera = 2;
                    Ikaslea p = new Ikaslea(zatiak[0], Integer.parseInt(zatiak[1]));
                    ikasleak.add(p);
                } else if (zatiak.length == 5) { 
                    // Formato completo: DNI, Nombre, Apellido, Edad, Dirección
                    luzeera = 5;
                    Ikaslea p = new Ikaslea(zatiak[0], zatiak[1], zatiak[2], Integer.parseInt(zatiak[3]), zatiak[4]);
                    ikasleak.add(p);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado.");
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        sc.nextLine(); // Limpiar buffer del Scanner

        // Mostrar DNI disponibles en el archivo
        System.out.println("DNIs en el archivo:");
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String lerroa;
            while ((lerroa = br.readLine()) != null) {
                String[] zatiak = lerroa.split(",");
                System.out.println(zatiak[0]); // Mostrar solo el DNI
            }
        } catch (IOException e) {
            System.out.println(Gehigarriak.Gorria + "Error: No se puede leer el archivo." + Gehigarriak.RESET);
            System.err.println(e.getMessage());
        }

        // Seleccionar DNI para actualizar
        String nan;
        Ikaslea ikasleaEguneratu = null;
        do {
            System.out.print(Gehigarriak.Horia + "Introduce el DNI de la persona a actualizar: " + Gehigarriak.RESET);
            nan = sc.nextLine();
            if (!Filtroak.isDNI(nan)) {
                System.out.println(Gehigarriak.Gorria + "DNI incorrecto. Debe tener 8 números + 1 letra." + Gehigarriak.RESET);
                continue;
            }
            // Buscar estudiante por DNI
            for (Ikaslea p : ikasleak) {
                if (p.getNan().equalsIgnoreCase(nan)) {
                    ikasleaEguneratu = p;
                    break;
                }
            }
            if (ikasleaEguneratu == null) {
                System.out.println(Gehigarriak.Gorria + "No se encontró ese DNI." + Gehigarriak.RESET);
            }
        } while (ikasleaEguneratu == null);

        // Solicitar nuevos datos con validación
        String izena = "", abizena = "", adinaStr = "", helbidea = "";
        int adina = 0;

        // Si es formato completo, pedir nombre y apellido
        if (luzeera == 5) {
            do {
                System.out.print("Nombre: ");
                izena = sc.nextLine();
                if (!Filtroak.isIzena(izena)) {
                    System.out.println("Nombre incorrecto. Solo introduce letras.");
                }
            } while (!Filtroak.isIzena(izena));

            do {
                System.out.print("Apellido: ");
                abizena = sc.nextLine();
                if (!Filtroak.isIzena(abizena)) {
                    System.out.println("Apellido incorrecto. Solo introduce letras.");
                }
            } while (!Filtroak.isIzena(abizena));
        }

        // Validar edad
        do {
            System.out.print("Edad: ");
            adinaStr = sc.nextLine();
            if (!Filtroak.isAdina(adinaStr)) {
                System.out.println("Edad incorrecta. Solo introduce números.");
            }
        } while (!Filtroak.isAdina(adinaStr));
        adina = Integer.parseInt(adinaStr);

        // Si es formato completo, pedir dirección
        if (luzeera == 5) {
            do {
                System.out.print("Dirección: ");
                helbidea = sc.nextLine();
                if (!Filtroak.isHelbidea(helbidea)) {
                    System.out.println("Dirección incorrecta.");
                    continue;
                }
            } while (!Filtroak.isHelbidea(helbidea));
            helbidea = Filtroak.removeSpaces(helbidea);
        }

        // Actualizar datos del estudiante
        if (luzeera == 5) {
            ikasleaEguneratu.setIzena(izena);
            ikasleaEguneratu.setAbizena(abizena);
            ikasleaEguneratu.setAdina(adina);
            ikasleaEguneratu.setHelbidea(helbidea);
        } else if (luzeera == 2) {
            ikasleaEguneratu.setAdina(adina); // Solo actualizar edad en formato corto
        }

        // Reescribir completamente el archivo con los datos actualizados
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (Ikaslea p : ikasleak) {
                if (luzeera == 5) {
                    bw.write(p.toCSV()); // Escribir en formato CSV
                } else {
                    bw.write(p.toTXT()); // Escribir en formato TXT corto
                }
                bw.newLine();
            }
            System.out.println(Gehigarriak.Berdea + "Datos actualizados correctamente en el archivo." + Gehigarriak.RESET);
        } catch (IOException e) {
            System.out.println(Gehigarriak.Gorria + "Error: No se pueden actualizar datos en el archivo." + Gehigarriak.RESET);
            System.err.println(e.getMessage());
        }
    }

    /**
     * Método para eliminar datos de un estudiante de un archivo TXT
     * Elimina un registro basado en el DNI
     */
    public static void txtFitxategianDatuakEzabatu() {
        String fileName, path;
        Scanner sc = Gehigarriak.in;

        System.out.println("Eliminar datos de archivo TXT");
        txtFitxategiakBistaratu(); // Mostrar archivos disponibles

        // Seleccionar archivo
        System.out.print(Gehigarriak.Horia + "¿De qué archivo quieres eliminar datos? Introduce nombre: " + Gehigarriak.RESET);
        fileName = sc.next();
        fileName = Filtroak.removeSpaces(fileName);
        path = "apunteak\\konbertsorea2\\src\\main\\resources\\TXT\\" + fileName + ".txt";

        sc.nextLine(); // Limpiar buffer del Scanner

        // Mostrar DNI disponibles
        System.out.println("DNIs en el archivo:");
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String lerroa;
            while ((lerroa = br.readLine()) != null) {
                String[] zatiak = lerroa.split(",");
                System.out.println(zatiak[0]); // Mostrar solo el DNI
            }
        } catch (IOException e) {
            System.out.println(Gehigarriak.Gorria + "Error: No se puede leer el archivo." + Gehigarriak.RESET);
            System.err.println(e.getMessage());
        }

        // Validar DNI a eliminar
        String nan;
        do {
            System.out.print("DNI a eliminar (8 números + 1 letra): ");
            nan = sc.nextLine();
            if (!Filtroak.isDNI(nan)) {
                System.out.println(Gehigarriak.Gorria + "DNI incorrecto. Debe tener 8 números y 1 letra." + Gehigarriak.RESET);
                continue;
            }
            break;
        } while (true);

        // Eliminar el dato
        try {
            ezabatuDatua(nan, path);
        } catch (IOException e) {
            System.out.println(Gehigarriak.Gorria + "Error: No se pueden eliminar datos del archivo." + Gehigarriak.RESET);
            System.err.println(e.getMessage());
        }
    }

    /**
     * Método auxiliar para eliminar un dato específico por DNI
     * @param nan DNI a eliminar
     * @param path Ruta del archivo
     * @throws IOException Si hay error de entrada/salida
     */
    public static void ezabatuDatua(String nan, String path) throws IOException {
        List<String> lines = new ArrayList<>();
        boolean found = false;

        // Leer todas las líneas del archivo
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Si la línea empieza con el DNI, no añadirla (eliminarla)
                if (line.startsWith(nan + ",")) {
                    found = true;
                    continue;
                }
                lines.add(line);
            }
        }

        // Si se encontró el DNI, reescribir el archivo sin esa línea
        if (found) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
                for (String l : lines) {
                    bw.write(l);
                    bw.newLine();
                }
            }
            System.out.println(Gehigarriak.Berdea + "Datos eliminados correctamente del archivo." + Gehigarriak.RESET);
        } else {
            // Si no se encontró el DNI
            System.out.println(Gehigarriak.Horia + "No se encontró ese DNI en el archivo." + Gehigarriak.RESET);
        }
    }

    /**
     * Método para convertir un archivo TXT a formato CSV
     * Adapta diferentes formatos de TXT a estructura CSV estándar
     */
    public static void txtFitxategiaCSVraBihurtu() {
        String fileName, path, csvPath;
        Scanner sc = Gehigarriak.in;

        System.out.println("Convertir archivo TXT a formato CSV");
        txtFitxategiakBistaratu(); // Mostrar archivos disponibles

        // Seleccionar archivo
        System.out.print(Gehigarriak.Horia + "¿Qué archivo quieres convertir? Introduce nombre: " + Gehigarriak.RESET);
        fileName = sc.next();
        Filtroak.removeSpaces(fileName);
        path = "apunteak\\konbertsorea2\\src\\main\\resources\\TXT\\" + fileName + ".txt";
        csvPath = "apunteak\\konbertsorea2\\src\\main\\resources\\CSV\\" + fileName + "_txt-csv.csv";

        // Convertir de TXT a CSV
        try (BufferedReader br = new BufferedReader(new FileReader(path));
                BufferedWriter bw = new BufferedWriter(new FileWriter(csvPath))) {

            // Cabeceras del CSV
            String cabezalak = "DNI;Nombre;Apellido;Edad;Dirección";
            bw.write(cabezalak);
            bw.newLine();

            // Definir separadores
            final String TXT_BANATZAILEA = ","; // Separador en TXT
            final String CSV_BANATZAILEA = ";";  // Separador en CSV

            String lerroa;
            while ((lerroa = br.readLine()) != null) {

                String[] datuak = lerroa.split(TXT_BANATZAILEA);
                String csvLerroa = "";

                // Procesar según el formato detectado
                if (datuak.length == 2) {
                    // Formato Corto: DNI y Edad (2 columnas)
                    String nan = datuak[0];
                    String adina = datuak[1];

                    // Construir línea CSV: DNI ; Nombre ; Apellido ; Edad ; Dirección
                    csvLerroa = nan + CSV_BANATZAILEA +
                            "" + CSV_BANATZAILEA + // Nombre (vacío)
                            "" + CSV_BANATZAILEA + // Apellido (vacío)
                            adina + CSV_BANATZAILEA + // Edad
                            ""; // Dirección (vacía)

                } else if (datuak.length == 5) {
                    // Formato Completo: DNI, Nombre, Apellido, Edad, Dirección (5 columnas)
                    csvLerroa = String.join(CSV_BANATZAILEA, datuak);

                } else {
                    // Formato no reconocido
                    System.err.println("NOTA: La línea no tiene el formato esperado (2 o 5 columnas): " + lerroa);
                    continue; // Saltar a la siguiente línea
                }

                // Escribir en el archivo CSV
                bw.write(csvLerroa);
                bw.newLine();
            }

            System.out.println(Gehigarriak.Berdea + "Archivo convertido correctamente: " + csvPath + Gehigarriak.RESET);
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para mostrar la lista de archivos TXT disponibles
     * Lista todos los archivos .txt en el directorio correspondiente
     */
    public static void txtFitxategiakBistaratu() {
        System.out.println(Gehigarriak.Cyan + "================================");
        System.out.println(Gehigarriak.Berdea + "Archivos .txt:" + Gehigarriak.Urdina);
        try {
            // Listar todos los archivos .txt en el directorio
            java.nio.file.Files.list(java.nio.file.Paths.get("apunteak\\konbertsorea2\\src\\main\\resources\\TXT\\"))
                    .filter(p -> p.toString().endsWith(".txt"))
                    .forEach(p -> System.out.println(p.getFileName().toString().replaceFirst("\\.txt$", "")));
        } catch (java.io.IOException e) {
            System.out.println(Gehigarriak.Gorria + "Error: No se pueden mostrar los archivos.");
            System.err.println(e.getMessage() + Gehigarriak.RESET);
        }
        System.out.println(Gehigarriak.Cyan + "================================");
    }
}