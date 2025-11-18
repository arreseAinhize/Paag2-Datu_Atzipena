package paagbi.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import paagbi.Main;
import paagbi.utils.*; // Filtroak, Gehigarriak
import paagbi.model.*; // Objetuak (ikaslea)

/**
 * Clase principal para gestionar operaciones con archivos CSV
 * Permite visualizar, leer, combinar y convertir archivos CSV a otros formatos
 */
public class KudeatzaileaCSV {

    // Objeto Gson para conversión a JSON con formato bonito
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Menú principal de gestión de archivos CSV
     * Muestra las opciones disponibles y gestiona la navegación
     */
    public static void menua() {
        String aukera;
        do {
            // Limpiar consola y mostrar menú
            Gehigarriak.kontsolaGarbitu();
            System.out.println(Gehigarriak.Urdina + "Zer egin nahi duzu?");
            System.out.println(Gehigarriak.Cyan + "================================");
            System.out.println(Gehigarriak.Berdea + "1. CSV fitxategiak bistaratu.");
            System.out.println("2. CSV fitxategia irakurri.");
            System.out.println("3. CSV fitxategiak bateratu");
            System.out.println("4. CSV fitxategia TXT formatura bihurtu.");
            System.out.println("5. CSV fitxategia XML formatura bihurtu.");
            System.out.println("6. CSV fitxategia JSON formatura bihurtu.");
            System.out.println(Gehigarriak.Urdina + "7. Irten");
            System.out.println(Gehigarriak.Cyan + "================================");
            System.out.print(Gehigarriak.Horia + "Aukera: " + Gehigarriak.RESET);
            aukera = Gehigarriak.in.next();
            
            // Validar que la opción sea numérica
            if (Filtroak.isnumeric(aukera) == true) {
                switch (aukera) {
                    case "1":
                        // Mostrar archivos CSV disponibles
                        Gehigarriak.kontsolaGarbitu();
                        csvFitxategiakBistaratu();
                        Gehigarriak.aurreraJarraitu();
                        break;
                    case "2":
                        // Leer y mostrar contenido de archivo CSV
                        Gehigarriak.kontsolaGarbitu();
                        csvFitxategiaIrakurri();
                        Gehigarriak.aurreraJarraitu();
                        break;
                    case "3":
                        // Combinar múltiples archivos CSV en uno
                        Gehigarriak.kontsolaGarbitu();
                        csvFitxategiakBateratu();
                        break;
                    case "4":
                        // Convertir CSV a formato TXT
                        Gehigarriak.kontsolaGarbitu();
                        csvFitxategiaTXTraBihurtu();
                        break;
                    case "5":
                        // Convertir CSV a formato XML
                        Gehigarriak.kontsolaGarbitu();
                        csvFitxategiaXMLraBihurtu();
                        break;
                    case "6":
                        // Convertir CSV a formato JSON
                        Gehigarriak.kontsolaGarbitu();
                        csvFitxategiaJSONraBihurtu();
                        break;
                    case "7":
                        // Volver al menú principal
                        Gehigarriak.kontsolaGarbitu();
                        System.out.println(Gehigarriak.Gorria + "Menu Naguzia!" + Gehigarriak.RESET);
                        try {
                            Main.main(null);
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out
                                    .println(Gehigarriak.Gorria + "Errorea aplikazioa abiarazteko: " + e.getMessage());
                        }
                        return;
                    default:
                        // Opción no válida
                        Gehigarriak.kontsolaGarbitu();
                        System.out.println(Gehigarriak.Gorria + "Aukera okerra, saiatu berriro.");
                        System.out.print(Gehigarriak.Horia + "Aukera: " + Gehigarriak.RESET);
                        aukera = Gehigarriak.in.next();
                        break;
                }
            } else {
                // Entrada no numérica
                Gehigarriak.kontsolaGarbitu();
                System.out.print(Gehigarriak.Gorria + "Zenbaki bat sartu behar duzu!" + Gehigarriak.RESET);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while (!aukera.equals("7")); // Repetir hasta que se seleccione salir
    }

    /**
     * Muestra todos los archivos CSV disponibles en el directorio
     * Lista los archivos con extensión .csv en la carpeta específica
     */
    public static void csvFitxategiakBistaratu() {
        System.out.println(Gehigarriak.Cyan + "================================");
        System.out.println(Gehigarriak.Berdea + "Fitxategi .csv-ak:" + Gehigarriak.Urdina);
        try {
            // Listar todos los archivos .csv en el directorio específico
            java.nio.file.Files.list(java.nio.file.Paths.get("apunteak\\konbertsorea2\\src\\main\\resources\\CSV\\"))
                    .filter(p -> p.toString().endsWith(".csv")) // Filtrar por extensión .csv
                    .forEach(p -> System.out.println(p.getFileName().toString())); // Mostrar nombre de archivo
        } catch (java.io.IOException e) {
            System.out.println(Gehigarriak.Gorria + "Errorea: Fitxategiak ezin izan dira erakutsi.");
            System.err.println(e.getMessage() + Gehigarriak.RESET);
        }
        System.out.println(Gehigarriak.Cyan + "================================");
    }

    /**
     * Lee y muestra el contenido de un archivo CSV específico
     * Muestra todas las líneas del archivo seleccionado
     */
    public static void csvFitxategiaIrakurri() {
        String fileName, path;

        // Mostrar archivos disponibles primero
        csvFitxategiakBistaratu();

        // Solicitar nombre del archivo a leer
        System.out
                .print(Gehigarriak.Horia + "Sartu irakurri nahi duzun fitxategiaren izena sartu: " + Gehigarriak.RESET);
        fileName = Gehigarriak.in.next();
        path = "apunteak\\konbertsorea2\\src\\main\\resources\\CSV\\" + fileName;

        try {
            // Leer todas las líneas del archivo
            List<String> lerroak = java.nio.file.Files.readAllLines(java.nio.file.Paths.get(path));
            System.out.println(Gehigarriak.Urdina + "CSV fitxategiaren edukia:");
            System.out.println(Gehigarriak.Cyan + "================================" + Gehigarriak.RESET);

            // Mostrar cada línea del archivo
            for (String lerroa : lerroak) {
                System.out.println(lerroa);
            }

        } catch (java.io.IOException e) {
            System.out.println(Gehigarriak.Gorria + "Errorea: Fitxategia ezin izan da irakurri.");
            System.err.println(e.getMessage() + Gehigarriak.RESET);
        }
    }

    /**
     * Combina múltiples archivos CSV en un solo archivo
     * Elimina duplicados basándose en el campo NAN (ID único)
     * Prioriza datos no vacíos cuando hay conflictos
     */
    public static void csvFitxategiakBateratu() {
        String izenBerria, pathBerria;
        Scanner sc = Gehigarriak.in;

        System.out.println("CSV fitxategiak bateratu");
        // Mostrar archivos disponibles para combinar
        csvFitxategiakBistaratu();

        // Solicitar nombre para el nuevo archivo combinado
        System.out.print(Gehigarriak.Horia + "Sartu fitxategi berriaren izena: " + Gehigarriak.RESET);
        izenBerria = sc.next();
        izenBerria = Filtroak.removeSpaces(izenBerria); // Eliminar espacios del nombre
        pathBerria = "apunteak\\konbertsorea2\\src\\main\\resources\\CSV\\" + izenBerria + ".csv";

        try {
            // Usar un Map para guardar todos los datos por NAN (clave única)
            java.util.Map<String, String[]> datuMap = new java.util.HashMap<>();
            String[] goiburuak = null; // Cabeceras del CSV
            boolean lehenengoLerroa = true; // Control para primera línea (cabecera)

            // Obtener lista de todos los archivos CSV en el directorio
            File csvDir = new File("apunteak\\konbertsorea2\\src\\main\\resources\\CSV\\");
            File[] csvFiles = csvDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));

            // Verificar que hay archivos CSV
            if (csvFiles == null || csvFiles.length == 0) {
                System.out.println(Gehigarriak.Gorria + "Ez da CSV fitxategirik aurkitu." + Gehigarriak.RESET);
                return;
            }

            // Procesar cada archivo CSV
            for (File file : csvFiles) {
                // Saltar el archivo de destino si existe
                if (file.getName().equals(izenBerria + ".csv")) {
                    continue;
                }

                // Leer todas las líneas del archivo actual
                List<String> lerroak = java.nio.file.Files.readAllLines(file.toPath());

                // Procesar cada línea del archivo
                for (int i = 0; i < lerroak.size(); i++) {
                    String lerroa = lerroak.get(i);

                    // Si es la primera línea (cabecera), guardar una vez
                    if (i == 0) {
                        if (lehenengoLerroa) {
                            goiburuak = lerroa.split(";"); // Dividir cabecera por punto y coma
                            lehenengoLerroa = false;
                        }
                        continue; // Saltar al siguiente registro
                    }

                    // Procesar línea de datos
                    String[] datuak = lerroa.split(";");
                    if (datuak.length > 0 && !datuak[0].isEmpty()) {
                        String nan = datuak[0]; // NAN como clave única

                        // Si el NAN ya existe, combinar los datos
                        if (datuMap.containsKey(nan)) {
                            String[] datuExistente = datuMap.get(nan);
                            String[] datuBerriak = new String[goiburuak.length];

                            // Combinar datos: preferir datos no vacíos
                            for (int j = 0; j < goiburuak.length; j++) {
                                if (j < datuak.length && !datuak[j].isEmpty()) {
                                    datuBerriak[j] = datuak[j]; // Usar nuevo dato si no está vacío
                                } else if (j < datuExistente.length && datuExistente[j] != null
                                        && !datuExistente[j].isEmpty()) {
                                    datuBerriak[j] = datuExistente[j]; // Usar dato existente
                                } else {
                                    datuBerriak[j] = j < datuak.length ? datuak[j] : ""; // Valor por defecto
                                }
                            }
                            datuMap.put(nan, datuBerriak);
                        } else {
                            // NAN nuevo, añadir todos los datos
                            String[] datuOsoak = new String[goiburuak.length];
                            for (int j = 0; j < goiburuak.length; j++) {
                                datuOsoak[j] = j < datuak.length ? datuak[j] : ""; // Copiar dato o cadena vacía
                            }
                            datuMap.put(nan, datuOsoak);
                        }
                    }
                }
            }

            // Escribir el archivo unificado
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathBerria))) {
                // Escribir cabecera
                bw.write(String.join(";", goiburuak));
                bw.newLine();

                // Escribir datos de todos los registros únicos
                for (String[] datuak : datuMap.values()) {
                    bw.write(String.join(";", datuak));
                    bw.newLine();
                }
            }

            // Mostrar resultados
            System.out.println(
                    Gehigarriak.Berdea + "CSV fitxategiak ondo bateratu dira: " + pathBerria + Gehigarriak.RESET);
            System.out.println(
                    Gehigarriak.Horia + "Guztira " + datuMap.size() + " erregistro uniko." + Gehigarriak.RESET);

        } catch (Exception e) {
            System.out.println(
                    Gehigarriak.Gorria + "Errorea CSV fitxategiak bateratzean: " + e.getMessage() + Gehigarriak.RESET);
            e.printStackTrace();
        }
    }

    /**
     * Convierte un archivo CSV a formato TXT
     * Reemplaza los separadores ';' por ',' para formato TXT
     */
    public static void csvFitxategiaTXTraBihurtu() {
        String fileName, path, txtPath;
        Scanner sc = Gehigarriak.in;

        System.out.println("CSV fitxategia TXT formatura bihurtu");
        csvFitxategiakBistaratu();

        // Solicitar archivo a convertir
        System.out.print(Gehigarriak.Horia + "Zein fitxategi bihurtu nahi duzu? Sartu izena: " + Gehigarriak.RESET);
        fileName = sc.next();
        path = "apunteak\\konbertsorea2\\src\\main\\resources\\CSV\\" + fileName;
        txtPath = "apunteak\\konbertsorea2\\src\\main\\resources\\TXT\\" + fileName.replace(".csv", "") + ".txt";

        try {
            // Leer archivo CSV original
            List<String> lerroak = java.nio.file.Files.readAllLines(java.nio.file.Paths.get(path));

            // Escribir archivo TXT convertido
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(txtPath))) {
                for (String lerroa : lerroak) {
                    // Reemplazar ';' por ',' para formato TXT
                    String txtLerroa = lerroa.replace(";", ",");
                    bw.write(txtLerroa);
                    bw.newLine();
                }
            }

            System.out.println(
                    Gehigarriak.Berdea + "CSV fitxategia ondo bihurtu da TXT-ra: " + txtPath + Gehigarriak.RESET);

        } catch (Exception e) {
            System.out.println(Gehigarriak.Gorria + "Errorea CSV fitxategia TXT-ra bihurtzean: " + e.getMessage()
                    + Gehigarriak.RESET);
        }
    }

    /**
     * Convierte un archivo CSV a formato XML
     * Crea una estructura XML con elementos para cada campo del CSV
     */
    public static void csvFitxategiaXMLraBihurtu() {
        String fileName, path, xmlPath;
        Scanner sc = Gehigarriak.in;

        System.out.println("CSV fitxategia XML formatura bihurtu");
        csvFitxategiakBistaratu();

        // Solicitar archivo a convertir
        System.out.print(Gehigarriak.Horia + "Zein fitxategi bihurtu nahi duzu? Sartu izena: " + Gehigarriak.RESET);
        fileName = sc.next();
        path = "apunteak\\konbertsorea2\\src\\main\\resources\\CSV\\" + fileName;
        xmlPath = "apunteak\\konbertsorea2\\src\\main\\resources\\XML\\" + fileName.replace(".csv", "") + ".xml";

        try {
            // Leer archivo CSV
            List<String> lerroak = java.nio.file.Files.readAllLines(java.nio.file.Paths.get(path));

            // Verificar que el archivo no esté vacío
            if (lerroak.isEmpty()) {
                System.out.println(Gehigarriak.Gorria + "Fitxategia hutsik dago." + Gehigarriak.RESET);
                return;
            }

            // Crear documento XML
            javax.xml.parsers.DocumentBuilderFactory docFactory = javax.xml.parsers.DocumentBuilderFactory
                    .newInstance();
            javax.xml.parsers.DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = docBuilder.newDocument();

            // Elemento raíz
            org.w3c.dom.Element rootElement = doc.createElement("ikasleak");
            doc.appendChild(rootElement);

            // Leer cabecera y usar los nombres de campo como elementos XML
            String[] goiburuak = lerroak.get(0).split(";");

            // Procesar datos - CORREGIDO: procesar TODOS los registros
            for (int i = 1; i < lerroak.size(); i++) {
                String lerroa = lerroak.get(i);
                if (lerroa == null || lerroa.trim().isEmpty()) {
                    continue; // Saltar líneas vacías
                }

                String[] datuak = lerroa.split(";", -1); // Usar -1 para mantener campos vacíos

                // Crear elemento ikaslea para CADA registro
                org.w3c.dom.Element ikaslea = doc.createElement("ikaslea");

                // Crear elementos para cada campo del registro
                for (int j = 0; j < goiburuak.length; j++) {
                    String balioa = (j < datuak.length) ? datuak[j] : "";
                    // Si el valor está vacío, poner cadena vacía en lugar de omitir
                    org.w3c.dom.Element element = doc.createElement(goiburuak[j].toLowerCase());
                    element.appendChild(doc.createTextNode(balioa));
                    ikaslea.appendChild(element);
                }

                rootElement.appendChild(ikaslea);
            }

            // Guardar XML en archivo
            javax.xml.transform.TransformerFactory transformerFactory = javax.xml.transform.TransformerFactory
                    .newInstance();
            javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes"); // Formato indentado
            javax.xml.transform.dom.DOMSource source = new javax.xml.transform.dom.DOMSource(doc);
            javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(
                    new File(xmlPath));
            transformer.transform(source, result);

            System.out.println(
                    Gehigarriak.Berdea + "CSV fitxategia ondo bihurtu da XML-ra: " + xmlPath + Gehigarriak.RESET);

        } catch (Exception e) {
            System.out.println(Gehigarriak.Gorria + "Errorea CSV fitxategia XML-ra bihurtzean: " + e.getMessage()
                    + Gehigarriak.RESET);
            e.printStackTrace();
        }
    }

    /**
     * Convierte un archivo CSV a formato JSON
     * Utiliza la clase Ikaslea para mapear los datos y Gson para la serialización
     */
    public static void csvFitxategiaJSONraBihurtu() {
        String fileName, path, jsonPath;
        Scanner sc = Gehigarriak.in;

        System.out.println("CSV fitxategia JSON formatura bihurtu");
        csvFitxategiakBistaratu();

        // Solicitar archivo a convertir
        System.out.print(Gehigarriak.Horia + "Zein fitxategi bihurtu nahi duzu? Sartu izena: " + Gehigarriak.RESET);
        fileName = sc.next();
        path = "apunteak\\konbertsorea2\\src\\main\\resources\\CSV\\" + fileName;
        jsonPath = "apunteak\\konbertsorea2\\src\\main\\resources\\JSON\\" + fileName.replace(".csv", "") + ".json";

        try {
            // Leer archivo CSV
            List<String> lerroak = java.nio.file.Files.readAllLines(java.nio.file.Paths.get(path));

            // Verificar que el archivo no esté vacío
            if (lerroak.isEmpty()) {
                System.out.println(Gehigarriak.Gorria + "Fitxategia hutsik dago." + Gehigarriak.RESET);
                return;
            }

            // Lista para almacenar objetos Ikaslea
            List<Ikaslea> ikasleak = new ArrayList<>();

            // Procesar datos (empezar desde la línea 1, saltando la cabecera)
            for (int i = 1; i < lerroak.size(); i++) {
                String[] datuak = lerroak.get(i).split(";");

                // Verificar que hay datos mínimos (NAN, Izena, Abizena)
                if (datuak.length >= 3) {
                    String nan = datuak[0];
                    String izena = datuak.length > 1 ? datuak[1] : "";
                    String abizena = datuak.length > 2 ? datuak[2] : "";

                    // Crear objeto Ikaslea y añadir a la lista
                    Ikaslea ikaslea = new Ikaslea(nan, izena, abizena);
                    ikasleak.add(ikaslea);
                }
            }

            // Guardar JSON usando Gson
            try (FileWriter writer = new FileWriter(jsonPath)) {
                gson.toJson(ikasleak, writer);
            }

            System.out.println(
                    Gehigarriak.Berdea + "CSV fitxategia ondo bihurtu da JSON-ra: " + jsonPath + Gehigarriak.RESET);

        } catch (Exception e) {
            System.out.println(Gehigarriak.Gorria + "Errorea CSV fitxategia JSON-ra bihurtzean: " + e.getMessage()
                    + Gehigarriak.RESET);
        }
    }
}