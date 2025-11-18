package paagbi.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import paagbi.Main;
import paagbi.utils.*; // Filtroak, Gehigarriak
import paagbi.model.*; // Objetuak (ikaslea)

/**
 * Clase principal para gestionar operaciones con archivos JSON
 * Permite crear, leer, modificar, eliminar y convertir archivos JSON
 */
public class KudeatzaileaJSON {

    // Objeto Gson para conversión a/desde JSON con formato bonito
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Menú principal de gestión de archivos JSON
     * Muestra las opciones disponibles y gestiona la navegación
     */
    public static void menua() {
        String aukera;
        do {
            // Limpiar consola y mostrar menú
            Gehigarriak.kontsolaGarbitu();
            System.out.println(Gehigarriak.Urdina + "Zer egin nahi duzu?");
            System.out.println(Gehigarriak.Cyan + "================================");
            System.out.println(Gehigarriak.Berdea + "1. JSON fitxategia sortu.");
            System.out.println("2. JSON fitxategia irakurri.");
            System.out.println("3. JSON fitxategian datuak gehitu");
            System.out.println("4. JSON fitxategia eguneratu.");
            System.out.println("5. JSON fitxategia ezabatu.");
            System.out.println("6. JSON fitxategia CSV formatura bihurtu.");
            System.out.println(Gehigarriak.Urdina + "7. Irten");
            System.out.println(Gehigarriak.Cyan + "================================");
            System.out.print(Gehigarriak.Horia + "Aukera: " + Gehigarriak.RESET);
            aukera = Gehigarriak.in.next();
            
            // Validar que la opción sea numérica
            if (Filtroak.isnumeric(aukera) == true) {
                switch (aukera) {
                    case "1":
                        // Crear nuevo archivo JSON
                        Gehigarriak.kontsolaGarbitu();
                        System.out.println("JSON fitxategia sortu");
                        jsonFitxategiaSortu();
                        break;
                    case "2":
                        // Leer y mostrar contenido de archivo JSON
                        Gehigarriak.kontsolaGarbitu();
                        jsonFitxategiaIrakurri();
                        Gehigarriak.aurreraJarraitu();
                        break;
                    case "3":
                        // Añadir datos a archivo JSON existente
                        Gehigarriak.kontsolaGarbitu();
                        jsonFitxategianDatuakGehitu();
                        break;
                    case "4":
                        // Actualizar datos existentes en archivo JSON
                        Gehigarriak.kontsolaGarbitu();
                        jsonFitxategianDatuakEguneratu();
                        break;
                    case "5":
                        // Eliminar datos de archivo JSON
                        Gehigarriak.kontsolaGarbitu();
                        jsonFitxategianDatuakEzabatu();
                        break;
                    case "6":
                        // Convertir JSON a formato CSV
                        Gehigarriak.kontsolaGarbitu();
                        jsonFitxategiaCSVraBihurtu();
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
     * Crea un nuevo archivo JSON vacío
     * Verifica que no exista un archivo con el mismo nombre
     */
    public static void jsonFitxategiaSortu() {
        String fileName, path;
        Scanner sc = Gehigarriak.in;

        do {
            Gehigarriak.kontsolaGarbitu();
            // Solicitar nombre del nuevo archivo
            System.out.print(
                    Gehigarriak.Horia + "Sartu sortu nahi duzun fitxategiaren izena sartu: " + Gehigarriak.RESET);
            fileName = sc.next();
            fileName = Filtroak.removeSpaces(fileName); // Eliminar espacios del nombre
            path = "apunteak\\konbertsorea2\\src\\main\\resources\\JSON\\" + fileName + ".json";

            // Verificar si el archivo ya existe
            File fitx = new File(path);
            if (fitx.exists()) {
                System.out.println(Gehigarriak.Gorria + "Jada fitxategi batek izen hori du, zehiatu beste batekin."
                        + Gehigarriak.RESET);
                continue; // Repetir si el archivo existe
            }

            try {
                // Crear lista vacía de estudiantes
                List<Ikaslea> ikasleak = new ArrayList<>();

                // Guardar la lista vacía en el archivo JSON
                try (FileWriter writer = new FileWriter(path)) {
                    gson.toJson(ikasleak, writer);
                }

                System.out.println(Gehigarriak.Berdea + "JSON fitxategia ondo sortu da: " + path + Gehigarriak.RESET);
            } catch (Exception e) {
                System.out.println(
                        Gehigarriak.Gorria + "Errorea JSON fitxategia sortzean: " + e.getMessage() + Gehigarriak.RESET);
            }
            break;

        } while (true); // Repetir hasta que se cree un archivo válido
    }

    /**
     * Lee y muestra el contenido de un archivo JSON específico
     * Muestra todos los estudiantes almacenados en el archivo
     */
    public static void jsonFitxategiaIrakurri() {
        String fileName, path;

        // Mostrar archivos JSON disponibles
        jsonFitxategiakBistaratu();

        // Solicitar nombre del archivo a leer
        System.out
                .print(Gehigarriak.Horia + "Sartu irakurri nahi duzun fitxategiaren izena sartu: " + Gehigarriak.RESET);
        fileName = Gehigarriak.in.next();
        fileName = Filtroak.removeSpaces(fileName);
        path = "apunteak\\konbertsorea2\\src\\main\\resources\\JSON\\" + fileName + ".json";

        try {
            File inputFile = new File(path);

            // Verificar que el archivo existe
            if (!inputFile.exists()) {
                System.out.println(Gehigarriak.Gorria + "Fitxategia ez da existitzen." + Gehigarriak.RESET);
                return;
            }

            // Leer el archivo JSON
            try (FileReader reader = new FileReader(path)) {
                // Convertir JSON a lista de objetos Ikaslea
                List<Ikaslea> ikasleak = gson.fromJson(reader, new TypeToken<List<Ikaslea>>() {
                }.getType());

                // Verificar si el archivo está vacío
                if (ikasleak == null || ikasleak.isEmpty()) {
                    System.out.println(Gehigarriak.Horia + "Fitxategia hutsik dago." + Gehigarriak.RESET);
                    return;
                }

                // Mostrar contenido del archivo
                System.out.println(Gehigarriak.Urdina + "JSON fitxategiaren edukia:");
                System.out.println(Gehigarriak.Cyan + "================================" + Gehigarriak.RESET);

                for (int i = 0; i < ikasleak.size(); i++) {
                    Ikaslea ikaslea = ikasleak.get(i);
                    System.out.println(Gehigarriak.Berdea + "Ikaslea #" + (i + 1) + ":" + Gehigarriak.RESET);
                    System.out.println("NAN: " + ikaslea.getNan());
                    System.out.println("Izena: " + ikaslea.getIzena());
                    System.out.println("Abizena: " + ikaslea.getAbizena());
                    System.out.println("--------------------------------");
                }
            }

        } catch (Exception e) {
            System.out.println(
                    Gehigarriak.Gorria + "Errorea JSON fitxategia irakurtzean: " + e.getMessage() + Gehigarriak.RESET);
        }
    }

    /**
     * Añade nuevos datos (estudiantes) a un archivo JSON existente
     * Valida los datos de entrada y verifica duplicados por NAN
     */
    public static void jsonFitxategianDatuakGehitu() {
        String fileName, path;
        Scanner sc = Gehigarriak.in;

        System.out.println("JSON fitxategian datuak gehitu");
        // Mostrar archivos disponibles
        jsonFitxategiakBistaratu();

        // Solicitar archivo destino
        System.out.print(
                Gehigarriak.Horia + "Zein fitxategiri datuak gehitu nahi dizkiozu? Sartu izena: " + Gehigarriak.RESET);
        fileName = sc.next();
        fileName = Filtroak.removeSpaces(fileName);
        path = "apunteak\\konbertsorea2\\src\\main\\resources\\JSON\\" + fileName + ".json";

        sc.nextLine(); // Limpiar buffer del Scanner

        String nan, izena, abizena;

        // Validación de NAN (DNI)
        do {
            System.out.print("NAN (8 zenbaki + 1 letra): ");
            nan = sc.nextLine();
            if (!Filtroak.isDNI(nan)) {
                System.out.println(
                        Gehigarriak.Gorria + "NAN okerra. 8 zenbaki eta 1 letra izan behar ditu." + Gehigarriak.RESET);
                continue;
            }
            break;
        } while (true);

        // Validación de nombre (solo letras)
        do {
            System.out.print("Izena: ");
            izena = sc.nextLine();
            if (!Filtroak.isIzena(izena)) {
                System.out.println(
                        Gehigarriak.Gorria + "Izena okerra. Letra bakarrik sartu behar da." + Gehigarriak.RESET);
            }
        } while (!Filtroak.isIzena(izena));

        // Validación de apellido (solo letras)
        do {
            System.out.print("Abizena: ");
            abizena = sc.nextLine();
            if (!Filtroak.isIzena(abizena)) {
                System.out.println(
                        Gehigarriak.Gorria + "Abizena okerra. Letra bakarrik sartu behar da." + Gehigarriak.RESET);
            }
        } while (!Filtroak.isIzena(abizena));

        // Crear nuevo objeto estudiante
        Ikaslea nuevoIkaslea = new Ikaslea(nan, izena, abizena);

        try {
            File inputFile = new File(path);
            List<Ikaslea> ikasleak;

            // Si el archivo existe y no está vacío, leer datos existentes
            if (inputFile.exists() && inputFile.length() > 0) {
                try (FileReader reader = new FileReader(path)) {
                    ikasleak = gson.fromJson(reader, new TypeToken<List<Ikaslea>>() {
                    }.getType());
                    if (ikasleak == null) {
                        ikasleak = new ArrayList<>(); // Crear nueva lista si es null
                    }
                }
            } else {
                // Si no existe, crear nueva lista
                ikasleak = new ArrayList<>();
            }

            // Verificar si el NAN ya existe en el archivo
            boolean nanExistente = false;
            for (Ikaslea ikaslea : ikasleak) {
                if (ikaslea.getNan().equalsIgnoreCase(nan)) {
                    nanExistente = true;
                    break;
                }
            }

            if (nanExistente) {
                System.out.println(Gehigarriak.Gorria + "NAN hori jada existitzen da fitxategian." + Gehigarriak.RESET);
                return;
            }

            // Añadir nuevo estudiante a la lista
            ikasleak.add(nuevoIkaslea);

            // Guardar la lista actualizada en el archivo JSON
            try (FileWriter writer = new FileWriter(path)) {
                gson.toJson(ikasleak, writer);
            }

            System.out.println(Gehigarriak.Berdea + "Datuak ondo gehitu dira JSON fitxategian." + Gehigarriak.RESET);

        } catch (Exception e) {
            System.out.println(
                    Gehigarriak.Gorria + "Errorea: Datuak ezin izan dira gehitu JSON fitxategian." + Gehigarriak.RESET);
            System.err.println(e.getMessage());
        }
    }

    /**
     * Actualiza datos existentes en un archivo JSON
     * Busca por NAN y actualiza nombre y apellido
     */
    public static void jsonFitxategianDatuakEguneratu() {
        String fileName, path;
        Scanner sc = Gehigarriak.in;

        System.out.println("JSON fitxategian datuak eguneratu");
        // Mostrar archivos disponibles
        jsonFitxategiakBistaratu();

        // Solicitar archivo a actualizar
        System.out.print(Gehigarriak.Horia + "Zein fitxategi eguneratu nahi duzu? Sartu izena: " + Gehigarriak.RESET);
        fileName = sc.next();
        fileName = Filtroak.removeSpaces(fileName);
        path = "apunteak\\konbertsorea2\\src\\main\\resources\\JSON\\" + fileName + ".json";

        sc.nextLine(); // Limpiar buffer del Scanner

        // Mostrar NANs disponibles en el archivo
        try {
            try (FileReader reader = new FileReader(path)) {
                // Leer lista de estudiantes desde JSON
                List<Ikaslea> ikasleak = gson.fromJson(reader, new TypeToken<List<Ikaslea>>() {
                }.getType());

                if (ikasleak == null || ikasleak.isEmpty()) {
                    System.out.println(Gehigarriak.Horia + "Fitxategia hutsik dago." + Gehigarriak.RESET);
                    return;
                }

                // Mostrar todos los NANs disponibles
                System.out.println(Gehigarriak.Urdina + "Fitxategiko NAN-ak:");
                System.out.println(Gehigarriak.Cyan + "================================" + Gehigarriak.RESET);

                for (int i = 0; i < ikasleak.size(); i++) {
                    Ikaslea ikaslea = ikasleak.get(i);
                    System.out.println("NAN: " + ikaslea.getNan());
                }
            }

        } catch (Exception e) {
            System.out.println(
                    Gehigarriak.Gorria + "Errorea JSON fitxategia irakurtzean: " + e.getMessage() + Gehigarriak.RESET);
        }

        // Solicitar NAN a actualizar
        String nan;
        do {
            System.out.print(Gehigarriak.Horia + "Eguneratu nahi duzun pertsonaren NAN-a sartu: " + Gehigarriak.RESET);
            nan = sc.nextLine();
            if (!Filtroak.isDNI(nan)) {
                System.out.println(
                        Gehigarriak.Gorria + "NAN okerra. 8 zenbaki + 1 letra izan behar du." + Gehigarriak.RESET);
                continue;
            }
            break;
        } while (true);

        // Solicitar nuevos datos
        String izena, abizena;

        // Validación de nuevo nombre
        do {
            System.out.print("Izena: ");
            izena = sc.nextLine();
            if (!Filtroak.isIzena(izena)) {
                System.out.println("Izena okerra. Letra bakarrik sartu.");
            }
        } while (!Filtroak.isIzena(izena));

        // Validación de nuevo apellido
        do {
            System.out.print("Abizena: ");
            abizena = sc.nextLine();
            if (!Filtroak.isIzena(abizena)) {
                System.out.println("Abizena okerra. Letra bakarrik sartu.");
            }
        } while (!Filtroak.isIzena(abizena));

        try {
            File inputFile = new File(path);

            // Verificar que el archivo existe
            if (!inputFile.exists()) {
                System.out.println(Gehigarriak.Gorria + "Fitxategia ez da existitzen." + Gehigarriak.RESET);
                return;
            }

            // Leer datos existentes
            List<Ikaslea> ikasleak;
            try (FileReader reader = new FileReader(path)) {
                ikasleak = gson.fromJson(reader, new TypeToken<List<Ikaslea>>() {
                }.getType());
            }

            if (ikasleak == null || ikasleak.isEmpty()) {
                System.out.println(Gehigarriak.Gorria + "Fitxategia hutsik dago." + Gehigarriak.RESET);
                return;
            }

            // Buscar y actualizar estudiante
            boolean encontrado = false;
            for (Ikaslea ikaslea : ikasleak) {
                if (ikaslea.getNan().equalsIgnoreCase(nan)) {
                    // Actualizar datos del estudiante
                    ikaslea.setIzena(izena);
                    ikaslea.setAbizena(abizena);
                    encontrado = true;
                    break;
                }
            }

            if (encontrado) {
                // Guardar los cambios en el archivo
                try (FileWriter writer = new FileWriter(path)) {
                    gson.toJson(ikasleak, writer);
                }

                System.out.println(
                        Gehigarriak.Berdea + "Datuak ondo eguneratu dira JSON fitxategian." + Gehigarriak.RESET);
            } else {
                System.out.println(Gehigarriak.Gorria + "Ez da aurkitu NAN hori fitxategian." + Gehigarriak.RESET);
            }

        } catch (Exception e) {
            System.out.println(Gehigarriak.Gorria + "Errorea: Datuak ezin izan dira eguneratu JSON fitxategian."
                    + Gehigarriak.RESET);
            System.err.println(e.getMessage());
        }
    }

    /**
     * Elimina datos específicos de un archivo JSON
     * Busca por NAN y elimina el estudiante correspondiente
     */
    public static void jsonFitxategianDatuakEzabatu() {
        String fileName, path;
        Scanner sc = Gehigarriak.in;

        System.out.println("JSON fitxategitik datuak ezabatu");
        // Mostrar archivos disponibles
        jsonFitxategiakBistaratu();

        // Solicitar archivo del que eliminar datos
        System.out.print(
                Gehigarriak.Horia + "Zein fitxategitik datuak ezabatu nahi dituzu? Sartu izena: " + Gehigarriak.RESET);
        fileName = sc.next();
        fileName = Filtroak.removeSpaces(fileName);
        path = "apunteak\\konbertsorea2\\src\\main\\resources\\JSON\\" + fileName + ".json";

        sc.nextLine(); // Limpiar buffer del Scanner

        // Mostrar NANs disponibles en el archivo
        try {
            try (FileReader reader = new FileReader(path)) {
                // Leer lista de estudiantes desde JSON
                List<Ikaslea> ikasleak = gson.fromJson(reader, new TypeToken<List<Ikaslea>>() {
                }.getType());

                if (ikasleak == null || ikasleak.isEmpty()) {
                    System.out.println(Gehigarriak.Horia + "Fitxategia hutsik dago." + Gehigarriak.RESET);
                    return;
                }

                // Mostrar todos los NANs disponibles
                System.out.println(Gehigarriak.Urdina + "Fitxategiko NAN-ak:");
                System.out.println(Gehigarriak.Cyan + "================================" + Gehigarriak.RESET);

                for (int i = 0; i < ikasleak.size(); i++) {
                    Ikaslea ikaslea = ikasleak.get(i);
                    System.out.println("NAN: " + ikaslea.getNan());
                }
            }

        } catch (Exception e) {
            System.out.println(
                    Gehigarriak.Gorria + "Errorea JSON fitxategia irakurtzean: " + e.getMessage() + Gehigarriak.RESET);
        }

        // Solicitar NAN a eliminar
        String nan;
        do {
            System.out.print("Ezabatu nahi duzun NAN (8 zenbaki + 1 letra): ");
            nan = sc.nextLine();
            if (!Filtroak.isDNI(nan)) {
                System.out.println(
                        Gehigarriak.Gorria + "NAN okerra. 8 zenbaki eta 1 letra izan behar ditu." + Gehigarriak.RESET);
                continue;
            }
            break;
        } while (true);

        try {
            File inputFile = new File(path);

            // Verificar que el archivo existe
            if (!inputFile.exists()) {
                System.out.println(Gehigarriak.Gorria + "Fitxategia ez da existitzen." + Gehigarriak.RESET);
                return;
            }

            // Leer datos existentes
            List<Ikaslea> ikasleak;
            try (FileReader reader = new FileReader(path)) {
                ikasleak = gson.fromJson(reader, new TypeToken<List<Ikaslea>>() {
                }.getType());
            }

            if (ikasleak == null || ikasleak.isEmpty()) {
                System.out.println(Gehigarriak.Gorria + "Fitxategia hutsik dago." + Gehigarriak.RESET);
                return;
            }

            // Filtrar lista excluyendo el NAN a eliminar
            boolean encontrado = false;
            List<Ikaslea> ikasleakActualizados = new ArrayList<>();

            for (Ikaslea ikaslea : ikasleak) {
                if (!ikaslea.getNan().equalsIgnoreCase(nan)) {
                    ikasleakActualizados.add(ikaslea); // Mantener estudiantes que no coinciden
                } else {
                    encontrado = true; // Marcar que se encontró el NAN
                }
            }

            if (encontrado) {
                // Guardar la lista actualizada (sin el estudiante eliminado)
                try (FileWriter writer = new FileWriter(path)) {
                    gson.toJson(ikasleakActualizados, writer);
                }

                System.out.println(
                        Gehigarriak.Berdea + "Datuak ondo ezabatu dira JSON fitxategitik." + Gehigarriak.RESET);
            } else {
                System.out.println(Gehigarriak.Horia + "Ez da aurkitu NAN hori fitxategian." + Gehigarriak.RESET);
            }

        } catch (Exception e) {
            System.out.println(Gehigarriak.Gorria + "Errorea: Datuak ezin izan dira ezabatu JSON fitxategitik."
                    + Gehigarriak.RESET);
            System.err.println(e.getMessage());
        }
    }

    /**
     * Convierte un archivo JSON a formato CSV
     * Utiliza el método toCSV() de la clase Ikaslea para la conversión
     */
    public static void jsonFitxategiaCSVraBihurtu() {
        String fileName, path, csvPath;
        Scanner sc = Gehigarriak.in;

        System.out.println("JSON fitxategia CSV formatura bihurtu");
        // Mostrar archivos disponibles
        jsonFitxategiakBistaratu();

        // Solicitar archivo a convertir
        System.out.print(Gehigarriak.Horia + "Zein fitxategi bihurtu nahi duzu? Sartu izena: " + Gehigarriak.RESET);
        fileName = sc.next();
        fileName = Filtroak.removeSpaces(fileName);
        path = "apunteak\\konbertsorea2\\src\\main\\resources\\JSON\\" + fileName + ".json";
        csvPath = "apunteak\\konbertsorea2\\src\\main\\resources\\CSV\\" + fileName + "_json-csv.csv";

        try {
            File inputFile = new File(path);

            // Verificar que el archivo existe
            if (!inputFile.exists()) {
                System.out.println(Gehigarriak.Gorria + "Fitxategia ez da existitzen." + Gehigarriak.RESET);
                return;
            }

            // Leer datos JSON
            List<Ikaslea> ikasleak;
            try (FileReader reader = new FileReader(path)) {
                ikasleak = gson.fromJson(reader, new TypeToken<List<Ikaslea>>() {
                }.getType());
            }

            if (ikasleak == null || ikasleak.isEmpty()) {
                System.out.println(Gehigarriak.Gorria + "Fitxategia hutsik dago." + Gehigarriak.RESET);
                return;
            }

            // Escribir archivo CSV
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvPath))) {
                // Escribir cabecera del CSV
                bw.write("NAN;Izena;Abizena;Adina;Helbidea");
                bw.newLine();

                // Escribir cada estudiante en formato CSV
                for (Ikaslea ikaslea : ikasleak) {
                    String csvLerroa = ikaslea.toCSV(); // Usar método de conversión de la clase Ikaslea
                    bw.write(csvLerroa);
                    bw.newLine();
                }

                System.out.println(
                        Gehigarriak.Berdea + "JSON fitxategia ondo bihurtu da CSV-ra: " + csvPath + Gehigarriak.RESET);
            }

        } catch (Exception e) {
            System.out.println(Gehigarriak.Gorria + "Errorea JSON fitxategia CSV-ra bihurtzean: " + e.getMessage()
                    + Gehigarriak.RESET);
        }
    }

    /**
     * Muestra todos los archivos JSON disponibles en el directorio
     * Lista los archivos con extensión .json en la carpeta específica
     */
    public static void jsonFitxategiakBistaratu() {
        System.out.println(Gehigarriak.Cyan + "================================");
        System.out.println(Gehigarriak.Berdea + "Fitxategi .json-ak:" + Gehigarriak.Urdina);
        try {
            // Listar todos los archivos .json en el directorio específico
            java.nio.file.Files.list(java.nio.file.Paths.get("apunteak\\konbertsorea2\\src\\main\\resources\\JSON\\"))
                    .filter(p -> p.toString().endsWith(".json")) // Filtrar por extensión .json
                    .forEach(p -> System.out.println(p.getFileName().toString().replaceFirst("\\.json$", ""))); // Mostrar sin extensión
        } catch (java.io.IOException e) {
            System.out.println(Gehigarriak.Gorria + "Errorea: Fitxategiak ezin izan dira erakutsi.");
            System.err.println(e.getMessage() + Gehigarriak.RESET);
        }
        System.out.println(Gehigarriak.Cyan + "================================");
    }
}