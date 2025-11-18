package paagbi.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import paagbi.Main;
import paagbi.utils.*; // Filtroak, Gehigarriak

/**
 * XML fitxategien kudeatzailea
 * 
 * @author Ainhzie Arrese
 */
public class KudeatzaileaXML {

    /**
     * XML fitxategien menua
     * 
     * @return void
     * @throws Exception Erroreak kudeatzeko
     */
    public static void menua() {
        String aukera;
        do {
            Gehigarriak.kontsolaGarbitu();
            System.out.println(Gehigarriak.Urdina + "Zer egin nahi duzu?");
            System.out.println(Gehigarriak.Cyan + "================================");
            System.out.println(Gehigarriak.Berdea + "1. XML fitxategia sortu.");
            System.out.println("2. XML fitxategia irakurri.");
            System.out.println("3. XML fitxategian datuak gehitu");
            System.out.println("4. XML fitxategia eguneratu.");
            System.out.println("5. XML fitxategia ezabatu.");
            System.out.println("6. XML fitxategia CSV formatura bihurtu.");
            System.out.println(Gehigarriak.Urdina + "7. Irten");
            System.out.println(Gehigarriak.Cyan + "================================");
            System.out.print(Gehigarriak.Horia + "Aukera: " + Gehigarriak.RESET);
            aukera = Gehigarriak.in.next();
            if (Filtroak.isnumeric(aukera) == true) {
                switch (aukera) {
                    case "1":
                        Gehigarriak.kontsolaGarbitu();
                        System.out.println("XML fitxategia sortu");
                        xmlFitxategiaSortu();
                        break;
                    case "2":
                        Gehigarriak.kontsolaGarbitu();
                        xmlFitxategiaIrakurri();
                        Gehigarriak.aurreraJarraitu();
                        break;
                    case "3":
                        Gehigarriak.kontsolaGarbitu();
                        xmlFitxategianDatuakGehitu();
                        break;
                    case "4":
                        Gehigarriak.kontsolaGarbitu();
                        xmlFitxategianDatuakEguneratu();
                        break;
                    case "5":
                        Gehigarriak.kontsolaGarbitu();
                        xmlFitxategianDatuakEzabatu();
                        break;
                    case "6":
                        Gehigarriak.kontsolaGarbitu();
                        xmlFitxategiaCSVraBihurtu();
                        break;
                    case "7":
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
                        Gehigarriak.kontsolaGarbitu();
                        System.out.println(Gehigarriak.Gorria + "Aukera okerra, saiatu berriro.");
                        System.out.print(Gehigarriak.Horia + "Aukera: " + Gehigarriak.RESET);
                        aukera = Gehigarriak.in.next();
                        break;
                }
            } else {
                Gehigarriak.kontsolaGarbitu();
                System.out.print(Gehigarriak.Gorria + "Zenbaki bat sartu behar duzu!" + Gehigarriak.RESET);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while (!aukera.equals("7"));
    }

    // XML fitxategia sortu
    /**
     * XML fitxategia sortu
     * 
     * @param fileName Sortu nahi den fitxategiaren izena
     * @return void
     * @throws Exception Erroreak kudeatzeko
     */
    public static void xmlFitxategiaSortu() {
        String fileName, path;
        Scanner sc = Gehigarriak.in;

        do {
            Gehigarriak.kontsolaGarbitu();
            System.out.print(
                    Gehigarriak.Horia + "Sartu sortu nahi duzun fitxategiaren izena sartu: " + Gehigarriak.RESET);
            fileName = sc.next();
            fileName = Filtroak.removeSpaces(fileName);
            path = "apunteak\\konbertsorea2\\src\\main\\resources\\XML\\" + fileName + ".xml";

            File fitx = new File(path);
            if (fitx.exists()) {
                System.out.println(Gehigarriak.Gorria + "Jada fitxategi batek izen hori du, zehiatu beste batekin."
                        + Gehigarriak.RESET);
                continue;
            }

            try {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.newDocument();

                // Elemento raíz
                Element rootElement = doc.createElement("ikasleak");
                doc.appendChild(rootElement);

                // Guardar el documento
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(path));

                transformer.transform(source, result);

                System.out.println(Gehigarriak.Berdea + "XML fitxategia ondo sortu da: " + path + Gehigarriak.RESET);
            } catch (Exception e) {
                System.out.println(
                        Gehigarriak.Gorria + "Errorea XML fitxategia sortzean: " + e.getMessage() + Gehigarriak.RESET);
            }
            break;

        } while (true);
    }

    // Método para leer un XML y mostrar su contenido
    public static void xmlFitxategiaIrakurri() {
        String fileName, path;

        // Mostrar lista de XML disponibles
        xmlFitxategiakBistaratu();

        // Pedir al usuario el nombre del archivo a leer
        System.out
                .print(Gehigarriak.Horia + "Sartu irakurri nahi duzun fitxategiaren izena sartu: " + Gehigarriak.RESET);
        fileName = Gehigarriak.in.next();
        fileName = Filtroak.removeSpaces(fileName); // Eliminar espacios del nombre
        path = "apunteak\\konbertsorea2\\src\\main\\resources\\XML\\" + fileName + ".xml"; // Ruta completa del archivo

        try {
            // Preparar el parser de XML
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize(); // Normalizar el documento

            // Mostrar el nodo raíz del XML
            System.out.println(Gehigarriak.Urdina + "Elementu erroa: " + doc.getDocumentElement().getNodeName());
            System.out.println(Gehigarriak.Cyan + "================================" + Gehigarriak.RESET);

            // Obtener todos los elementos <ikaslea>
            NodeList nList = doc.getElementsByTagName("ikaslea");

            // Recorrer cada <ikaslea>
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println(Gehigarriak.Berdea + "Ikaslea #" + (temp + 1) + ":" + Gehigarriak.RESET);

                    // Mostrar cada campo si existe
                    if (eElement.getElementsByTagName("nan").getLength() > 0) {
                        System.out.println("NAN: " + eElement.getElementsByTagName("nan").item(0).getTextContent());
                    }
                    if (eElement.getElementsByTagName("izena").getLength() > 0) {
                        System.out.println("Izena: " + eElement.getElementsByTagName("izena").item(0).getTextContent());
                    }
                    if (eElement.getElementsByTagName("abizena").getLength() > 0) {
                        System.out.println(
                                "Abizena: " + eElement.getElementsByTagName("abizena").item(0).getTextContent());
                    }
                    if (eElement.getElementsByTagName("adina").getLength() > 0) {
                        System.out.println("Adina: " + eElement.getElementsByTagName("adina").item(0).getTextContent());
                    }
                    if (eElement.getElementsByTagName("helbidea").getLength() > 0) {
                        System.out.println(
                                "Helbidea: " + eElement.getElementsByTagName("helbidea").item(0).getTextContent());
                    }
                    System.out.println("--------------------------------");
                }
            }
        } catch (Exception e) {
            // Si ocurre un error al leer el XML
            System.out.println(
                    Gehigarriak.Gorria + "Errorea XML fitxategia irakurtzean: " + e.getMessage() + Gehigarriak.RESET);
        }
    }

    // Método para añadir datos a un XML existente
    public static void xmlFitxategianDatuakGehitu() {
        String fileName, path;
        Scanner sc = Gehigarriak.in;

        System.out.println("XML fitxategian datuak gehitu");
        xmlFitxategiakBistaratu(); // Mostrar lista de XML disponibles

        // Pedir al usuario el archivo donde añadir datos
        System.out.print(
                Gehigarriak.Horia + "Zein fitxategiri datuak gehitu nahi dizkiozu? Sartu izena: " + Gehigarriak.RESET);
        fileName = sc.next();
        fileName = Filtroak.removeSpaces(fileName);
        path = "apunteak\\konbertsorea2\\src\\main\\resources\\XML\\" + fileName + ".xml";

        sc.nextLine(); // Limpiar buffer del Scanner

        // Determinar el formato del XML (número de campos esperados)
        int luzeera = determinarFormatoXML(path);

        String nan, izena = "", abizena = "", adinaStr = "", helbidea = "";
        int adina = 0;

        // Validar el NAN (DNI)
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

        // Si el formato requiere 5 campos, pedir también nombre, apellido y edad
        if (luzeera == 5) {
            // Validar nombre
            do {
                System.out.print("Izena: ");
                izena = sc.nextLine();
                if (!Filtroak.isIzena(izena)) {
                    System.out.println(
                            Gehigarriak.Gorria + "Izena okerra. Letra bakarrik sartu behar da." + Gehigarriak.RESET);
                }
            } while (!Filtroak.isIzena(izena));

            // Validar apellido
            do {
                System.out.print("Abizena: ");
                abizena = sc.nextLine();
                if (!Filtroak.isIzena(abizena)) {
                    System.out.println(
                            Gehigarriak.Gorria + "Abizena okerra. Letra bakarrik sartu behar da." + Gehigarriak.RESET);
                }
            } while (!Filtroak.isIzena(abizena));

            // Validar edad
            do {
                System.out.print("Adina: ");
                adinaStr = sc.nextLine();
                if (!Filtroak.isAdina(adinaStr)) {
                    System.out.println(
                            Gehigarriak.Gorria + "Adina okerra. Zenbaki bakarrik sartu behar da." + Gehigarriak.RESET);
                    continue;
                }
                adina = Integer.parseInt(adinaStr);
                break;
            } while (true);
        }

        // Pedir dirección (siempre obligatoria)
        System.out.print("Helbidea: ");
        helbidea = sc.nextLine();
        helbidea = Filtroak.removeSpaces(helbidea);

        // Añadir datos al XML
        try {
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            Element rootElement = doc.getDocumentElement();

            // Crear nuevo elemento <ikaslea>
            Element ikaslea = doc.createElement("ikaslea");

            // Añadir siempre NAN y dirección
            Element nanElement = doc.createElement("nan");
            nanElement.appendChild(doc.createTextNode(nan));
            ikaslea.appendChild(nanElement);

            Element helbideaElement = doc.createElement("helbidea");
            helbideaElement.appendChild(doc.createTextNode(helbidea));
            ikaslea.appendChild(helbideaElement);

            // Si el formato es completo (5 campos), añadir también nombre, apellido y edad
            if (luzeera == 5) {
                Element izenaElement = doc.createElement("izena");
                izenaElement.appendChild(doc.createTextNode(izena));
                ikaslea.appendChild(izenaElement);

                Element abizenaElement = doc.createElement("abizena");
                abizenaElement.appendChild(doc.createTextNode(abizena));
                ikaslea.appendChild(abizenaElement);

                Element adinaElement = doc.createElement("adina");
                adinaElement.appendChild(doc.createTextNode(String.valueOf(adina)));
                ikaslea.appendChild(adinaElement);
            }

            // Insertar el nuevo <ikaslea> en el documento
            rootElement.appendChild(ikaslea);

            // Guardar los cambios en el archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // Indentar el XML para que quede legible
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(path));
            transformer.transform(source, result);

            System.out.println(Gehigarriak.Berdea + "Datuak ondo gehitu dira XML fitxategian." + Gehigarriak.RESET);

        } catch (Exception e) {
            // Si ocurre un error al añadir datos
            System.out.println(
                    Gehigarriak.Gorria + "Errorea: Datuak ezin izan dira gehitu XML fitxategian." + Gehigarriak.RESET);
            System.err.println(e.getMessage());
        }
    }

    // Método para actualizar datos en un XML existente
    public static void xmlFitxategianDatuakEguneratu() {
        String fileName, path;
        Scanner sc = Gehigarriak.in;

        System.out.println("XML fitxategian datuak eguneratu");
        xmlFitxategiakBistaratu(); // Mostrar lista de XML disponibles

        // Pedir al usuario el archivo que quiere actualizar
        System.out.print(Gehigarriak.Horia + "Zein fitxategi eguneratu nahi duzu? Sartu izena: " + Gehigarriak.RESET);
        fileName = sc.next();
        fileName = Filtroak.removeSpaces(fileName); // Eliminar espacios del nombre
        path = "apunteak\\konbertsorea2\\src\\main\\resources\\XML\\" + fileName + ".xml"; // Ruta completa

        sc.nextLine(); // Limpiar buffer del Scanner

        // Determinar el formato del XML (número de campos esperados)
        int luzeera = determinarFormatoXML(path);

        // Mostrar todos los NAN (DNI) que existen en el archivo para que el usuario
        // elija
        try {
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            System.out.println(Gehigarriak.Urdina + "Fitxategiko NAN-ak:");
            System.out.println(Gehigarriak.Cyan + "================================" + Gehigarriak.RESET);

            NodeList nList = doc.getElementsByTagName("ikaslea");

            // Recorrer cada <ikaslea> y mostrar su NAN
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if (eElement.getElementsByTagName("nan").getLength() > 0) {
                        System.out.println("NAN: " + eElement.getElementsByTagName("nan").item(0).getTextContent());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(
                    Gehigarriak.Gorria + "Errorea XML fitxategia irakurtzean: " + e.getMessage() + Gehigarriak.RESET);
        }

        // Pedir al usuario el NAN de la persona que quiere actualizar
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

        // Variables para los nuevos datos
        String izena = "", abizena = "", adinaStr = "", helbidea = "";
        int adina = 0;

        // Si el formato es completo (5 campos), pedir también nombre, apellido y edad
        if (luzeera == 5) {
            do {
                System.out.print("Izena: ");
                izena = sc.nextLine();
                if (!Filtroak.isIzena(izena)) {
                    System.out.println("Izena okerra. Letra bakarrik sartu.");
                }
            } while (!Filtroak.isIzena(izena));

            do {
                System.out.print("Abizena: ");
                abizena = sc.nextLine();
                if (!Filtroak.isIzena(abizena)) {
                    System.out.println("Abizena okerra. Letra bakarrik sartu.");
                }
            } while (!Filtroak.isIzena(abizena));

            do {
                System.out.print("Adina: ");
                adinaStr = sc.nextLine();
                if (!Filtroak.isAdina(adinaStr)) {
                    System.out.println("Adina okerra. Zenbaki bakarrik sartu.");
                }
            } while (!Filtroak.isAdina(adinaStr));
            adina = Integer.parseInt(adinaStr);
        }

        // Pedir dirección (siempre obligatoria)
        do {
            System.out.print("Helbidea: ");
            helbidea = sc.nextLine();
            if (!Filtroak.isHelbidea(helbidea)) {
                System.out.println("Helbidea okerra da.");
                continue;
            }
        } while (!Filtroak.isHelbidea(helbidea));
        helbidea = Filtroak.removeSpaces(helbidea);

        // Actualizar los datos en el XML
        try {
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("ikaslea");
            boolean encontrado = false;

            // Buscar el <ikaslea> cuyo NAN coincide con el introducido
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String currentNan = eElement.getElementsByTagName("nan").item(0).getTextContent();

                    if (currentNan.equals(nan)) {
                        // Actualizar dirección (siempre)
                        eElement.getElementsByTagName("helbidea").item(0).setTextContent(helbidea);

                        // Actualizar otros campos si es formato completo
                        if (luzeera == 5) {
                            eElement.getElementsByTagName("izena").item(0).setTextContent(izena);
                            eElement.getElementsByTagName("abizena").item(0).setTextContent(abizena);
                            eElement.getElementsByTagName("adina").item(0).setTextContent(String.valueOf(adina));
                        }

                        encontrado = true;
                        break;
                    }
                }
            }

            // Guardar cambios si se encontró el NAN
            if (encontrado) {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // Indentar el XML
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(path));
                transformer.transform(source, result);

                System.out.println(
                        Gehigarriak.Berdea + "Datuak ondo eguneratu dira XML fitxategian." + Gehigarriak.RESET);
            } else {
                System.out.println(Gehigarriak.Gorria + "Ez da aurkitu NAN hori fitxategian." + Gehigarriak.RESET);
            }

        } catch (Exception e) {
            System.out.println(Gehigarriak.Gorria + "Errorea: Datuak ezin izan dira eguneratu XML fitxategian."
                    + Gehigarriak.RESET);
            System.err.println(e.getMessage());
        }
    }

    // Método para eliminar datos de un XML existente
    public static void xmlFitxategianDatuakEzabatu() {
        String fileName, path;
        Scanner sc = Gehigarriak.in;

        System.out.println("XML fitxategitik datuak ezabatu");
        xmlFitxategiakBistaratu(); // Mostrar lista de XML disponibles

        // Pedir al usuario el archivo donde quiere eliminar datos
        System.out.print(
                Gehigarriak.Horia + "Zein fitxategitik datuak ezabatu nahi dituzu? Sartu izena: " + Gehigarriak.RESET);
        fileName = sc.next();
        fileName = Filtroak.removeSpaces(fileName); // Eliminar espacios del nombre
        path = "apunteak\\konbertsorea2\\src\\main\\resources\\XML\\" + fileName + ".xml"; // Ruta completa del archivo

        sc.nextLine(); // Limpiar buffer del Scanner

        // Mostrar todos los NAN que contiene el archivo para que el usuario elija
        try {
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            System.out.println(Gehigarriak.Urdina + "Fitxategiko NAN-ak:");
            System.out.println(Gehigarriak.Cyan + "================================" + Gehigarriak.RESET);

            NodeList nList = doc.getElementsByTagName("ikaslea");

            // Recorrer cada <ikaslea> y mostrar su NAN
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if (eElement.getElementsByTagName("nan").getLength() > 0) {
                        System.out.println("NAN: " + eElement.getElementsByTagName("nan").item(0).getTextContent());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(
                    Gehigarriak.Gorria + "Errorea XML fitxategia irakurtzean: " + e.getMessage() + Gehigarriak.RESET);
        }

        // Pedir al usuario el NAN que quiere eliminar
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

        // Intentar eliminar el registro correspondiente
        try {
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("ikaslea");
            boolean encontrado = false;

            // Buscar el <ikaslea> cuyo NAN coincide con el introducido
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String currentNan = eElement.getElementsByTagName("nan").item(0).getTextContent();

                    if (currentNan.equals(nan)) {
                        // Eliminar el nodo completo <ikaslea>
                        nNode.getParentNode().removeChild(nNode);
                        encontrado = true;
                        break;
                    }
                }
            }

            // Guardar cambios si se encontró el NAN
            if (encontrado) {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // Indentar el XML para que quede legible
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(path));
                transformer.transform(source, result);

                System.out
                        .println(Gehigarriak.Berdea + "Datuak ondo ezabatu dira XML fitxategitik." + Gehigarriak.RESET);
            } else {
                System.out.println(Gehigarriak.Horia + "Ez da aurkitu NAN hori fitxategian." + Gehigarriak.RESET);
            }

        } catch (Exception e) {
            System.out.println(Gehigarriak.Gorria + "Errorea: Datuak ezin izan dira ezabatu XML fitxategitik."
                    + Gehigarriak.RESET);
            System.err.println(e.getMessage());
        }
    }

    // Método para convertir un archivo XML a formato CSV
    public static void xmlFitxategiaCSVraBihurtu() {
        String fileName, path, csvPath;
        Scanner sc = Gehigarriak.in;

        System.out.println("XML fitxategia CSV formatura bihurtu");
        xmlFitxategiakBistaratu(); // Mostrar lista de XML disponibles

        // Pedir al usuario el archivo que quiere convertir
        System.out.print(Gehigarriak.Horia + "Zein fitxategi bihurtu nahi duzu? Sartu izena: " + Gehigarriak.RESET);
        fileName = sc.next();
        fileName = Filtroak.removeSpaces(fileName); // Eliminar espacios del nombre
        path = "apunteak\\konbertsorea2\\src\\main\\resources\\XML\\" + fileName + ".xml"; // Ruta del XML
        csvPath = "apunteak\\konbertsorea2\\src\\main\\resources\\CSV\\" + fileName + "_xml-csv.csv"; // Ruta del CSV
                                                                                                      // destino

        try {
            // Preparar parser de XML
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // Obtener todos los elementos <ikaslea>
            NodeList nList = doc.getElementsByTagName("ikaslea");

            // Crear el archivo CSV con cabeceras
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvPath))) {
                bw.write("NAN;Izena;Abizena;Adina;Helbidea"); // Cabecera CSV
                bw.newLine();

                // Recorrer cada <ikaslea> y extraer sus datos
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;

                        // Campos obligatorios
                        String nan = eElement.getElementsByTagName("nan").item(0).getTextContent();
                        String helbidea = eElement.getElementsByTagName("helbidea").item(0).getTextContent();

                        // Campos opcionales (formato completo)
                        String izena = "";
                        String abizena = "";
                        int adina = 0;

                        if (eElement.getElementsByTagName("izena").getLength() > 0) {
                            izena = eElement.getElementsByTagName("izena").item(0).getTextContent();
                        }
                        if (eElement.getElementsByTagName("abizena").getLength() > 0) {
                            abizena = eElement.getElementsByTagName("abizena").item(0).getTextContent();
                        }
                        if (eElement.getElementsByTagName("adina").getLength() > 0) {
                            try {
                                adina = Integer
                                        .parseInt(eElement.getElementsByTagName("adina").item(0).getTextContent());
                            } catch (NumberFormatException ex) {
                                adina = 0; // Si no es número válido, poner 0
                            }
                        }

                        // Construir la línea CSV con los datos
                        String csvLerroa = nan + ";" + izena + ";" + abizena + ";" + adina + ";" + helbidea;
                        bw.write(csvLerroa);
                        bw.newLine();
                    }
                }

                // Mensaje de éxito
                System.out.println(
                        Gehigarriak.Berdea + "XML fitxategia ondo bihurtu da CSV-ra: " + csvPath + Gehigarriak.RESET);
            }

        } catch (Exception e) {
            // Si ocurre un error en la conversión
            System.out.println(Gehigarriak.Gorria + "Errorea XML fitxategia CSV-ra bihurtzean: " + e.getMessage()
                    + Gehigarriak.RESET);
        }
    }

    // Método para mostrar todos los archivos XML disponibles en la carpeta
    public static void xmlFitxategiakBistaratu() {
        System.out.println(Gehigarriak.Cyan + "================================");
        System.out.println(Gehigarriak.Berdea + "Fitxategi .xml-ak:" + Gehigarriak.Urdina);
        try {
            // Listar todos los archivos .xml en la carpeta indicada
            java.nio.file.Files.list(java.nio.file.Paths.get("apunteak\\konbertsorea2\\src\\main\\resources\\XML\\"))
                    .filter(p -> p.toString().endsWith(".xml")) // Filtrar solo los que terminan en .xml
                    .forEach(p -> System.out.println(p.getFileName().toString().replaceFirst("\\.xml$", ""))); // Mostrar
                                                                                                               // sin
                                                                                                               // extensión
        } catch (java.io.IOException e) {
            // Si ocurre un error al listar los archivos
            System.out.println(Gehigarriak.Gorria + "Errorea: Fitxategiak ezin izan dira erakutsi.");
            System.err.println(e.getMessage() + Gehigarriak.RESET);
        }
        System.out.println(Gehigarriak.Cyan + "================================");
    }

    // Método auxiliar para determinar el formato del XML
    private static int determinarFormatoXML(String path) {
        try {
            // Abrir el archivo XML indicado por la ruta
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize(); // Normalizar el documento

            // Obtener todos los elementos <ikaslea>
            NodeList nList = doc.getElementsByTagName("ikaslea");

            // Si hay al menos un <ikaslea>, analizamos el primero
            if (nList.getLength() > 0) {
                Node firstNode = nList.item(0);
                if (firstNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) firstNode;

                    // Si el primer <ikaslea> contiene los campos izena, abizena y adina,
                    // significa que el XML está en formato completo (5 campos)
                    if (eElement.getElementsByTagName("izena").getLength() > 0 &&
                            eElement.getElementsByTagName("abizena").getLength() > 0 &&
                            eElement.getElementsByTagName("adina").getLength() > 0) {
                        return 5; // Formato completo: nan, izena, abizena, adina, helbidea
                    }
                }
            }

            // Si no cumple las condiciones anteriores, asumimos formato reducido
            // (solo contiene nan y helbidea)
            return 2;

        } catch (Exception e) {
            // En caso de error al leer o analizar el XML, devolvemos formato reducido por
            // defecto
            return 2;
        }
    }
}