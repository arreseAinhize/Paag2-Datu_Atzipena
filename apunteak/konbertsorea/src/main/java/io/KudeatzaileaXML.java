package main.java.io;

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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import main.java.App;
import main.java.utils.*; // Filtroak, Gehigarriak
import main.java.error.*; // ErroreenKudeaketa
import main.java.model.*; // Objetuak (ikaslea)

public class KudeatzaileaXML {

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
                            App.main(null);
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
    public static void xmlFitxategiaSortu() {
        String fileName, path;
        Scanner sc = Gehigarriak.in;

        do {
            Gehigarriak.kontsolaGarbitu();
            System.out.print(
                    Gehigarriak.Horia + "Sartu sortu nahi duzun fitxategiaren izena sartu: " + Gehigarriak.RESET);
            fileName = sc.next();
            fileName = Filtroak.removeSpaces(fileName);
            path = "apunteak\\konbertsorea\\src\\main\\resources\\XML\\" + fileName + ".xml";

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
                System.out.println(Gehigarriak.Gorria + "Errorea XML fitxategia sortzean: " + e.getMessage() + Gehigarriak.RESET);
            }
            break;

        } while (true);
    }

    // XML fitxategiak irakurri
    public static void xmlFitxategiaIrakurri() {
        String fileName, path;

        xmlFitxategiakBistaratu();

        System.out
                .print(Gehigarriak.Horia + "Sartu irakurri nahi duzun fitxategiaren izena sartu: " + Gehigarriak.RESET);
        fileName = Gehigarriak.in.next();
        fileName = Filtroak.removeSpaces(fileName);
        path = "apunteak\\konbertsorea\\src\\main\\resources\\XML\\" + fileName + ".xml";

        try {
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            System.out.println(Gehigarriak.Urdina + "Elementu erroa: " + doc.getDocumentElement().getNodeName());
            System.out.println(Gehigarriak.Cyan + "================================" + Gehigarriak.RESET);

            NodeList nList = doc.getElementsByTagName("ikaslea");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println(Gehigarriak.Berdea + "Ikaslea #" + (temp + 1) + ":" + Gehigarriak.RESET);
                    
                    // Leer todos los campos disponibles
                    if (eElement.getElementsByTagName("nan").getLength() > 0) {
                        System.out.println("NAN: " + eElement.getElementsByTagName("nan").item(0).getTextContent());
                    }
                    if (eElement.getElementsByTagName("izena").getLength() > 0) {
                        System.out.println("Izena: " + eElement.getElementsByTagName("izena").item(0).getTextContent());
                    }
                    if (eElement.getElementsByTagName("abizena").getLength() > 0) {
                        System.out.println("Abizena: " + eElement.getElementsByTagName("abizena").item(0).getTextContent());
                    }
                    if (eElement.getElementsByTagName("adina").getLength() > 0) {
                        System.out.println("Adina: " + eElement.getElementsByTagName("adina").item(0).getTextContent());
                    }
                    if (eElement.getElementsByTagName("helbidea").getLength() > 0) {
                        System.out.println("Helbidea: " + eElement.getElementsByTagName("helbidea").item(0).getTextContent());
                    }
                    System.out.println("--------------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println(Gehigarriak.Gorria + "Errorea XML fitxategia irakurtzean: " + e.getMessage() + Gehigarriak.RESET);
        }
    }

    // XML fitxategian datuak gehitu
    public static void xmlFitxategianDatuakGehitu() {
        String fileName, path;
        Scanner sc = Gehigarriak.in;

        System.out.println("XML fitxategian datuak gehitu");
        xmlFitxategiakBistaratu();

        System.out.print(
                Gehigarriak.Horia + "Zein fitxategiri datuak gehitu nahi dizkiozu? Sartu izena: " + Gehigarriak.RESET);
        fileName = sc.next();
        fileName = Filtroak.removeSpaces(fileName);
        path = "apunteak\\konbertsorea\\src\\main\\resources\\XML\\" + fileName + ".xml";

        sc.nextLine(); // Scanner buffer garbitu

        // Determinar el formato del XML existente
        int luzeera = determinarFormatoXML(path);

        String nan, izena = "", abizena = "", adinaStr = "", helbidea = "";
        int adina = 0;

        // NAN balidazioa
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

        if (luzeera == 5) {
            // Izena balidazioa
            do {
                System.out.print("Izena: ");
                izena = sc.nextLine();
                if (!Filtroak.isIzena(izena)) {
                    System.out.println(
                            Gehigarriak.Gorria + "Izena okerra. Letra bakarrik sartu behar da." + Gehigarriak.RESET);
                }
            } while (!Filtroak.isIzena(izena));

            // Abizena balidazioa
            do {
                System.out.print("Abizena: ");
                abizena = sc.nextLine();
                if (!Filtroak.isIzena(abizena)) {
                    System.out.println(
                            Gehigarriak.Gorria + "Abizena okerra. Letra bakarrik sartu behar da." + Gehigarriak.RESET);
                }
            } while (!Filtroak.isIzena(abizena));

            // Adina balidazioa
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

        // Helbidea (siempre se pide)
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

            // Crear nuevo elemento ikaslea
            Element ikaslea = doc.createElement("ikaslea");

            // Añadir campos según el formato
            Element nanElement = doc.createElement("nan");
            nanElement.appendChild(doc.createTextNode(nan));
            ikaslea.appendChild(nanElement);

            Element helbideaElement = doc.createElement("helbidea");
            helbideaElement.appendChild(doc.createTextNode(helbidea));
            ikaslea.appendChild(helbideaElement);

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

            rootElement.appendChild(ikaslea);

            // Guardar los cambios
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(path));
            transformer.transform(source, result);

            System.out.println(Gehigarriak.Berdea + "Datuak ondo gehitu dira XML fitxategian." + Gehigarriak.RESET);

        } catch (Exception e) {
            System.out.println(Gehigarriak.Gorria + "Errorea: Datuak ezin izan dira gehitu XML fitxategian." + Gehigarriak.RESET);
            System.err.println(e.getMessage());
        }
    }

    // XML fitxategia eguneratu
    public static void xmlFitxategianDatuakEguneratu() {
        String fileName, path;
        Scanner sc = Gehigarriak.in;

        System.out.println("XML fitxategian datuak eguneratu");
        xmlFitxategiakBistaratu();

        System.out.print(Gehigarriak.Horia + "Zein fitxategi eguneratu nahi duzu? Sartu izena: " + Gehigarriak.RESET);
        fileName = sc.next();
        fileName = Filtroak.removeSpaces(fileName);
        path = "apunteak\\konbertsorea\\src\\main\\resources\\XML\\" + fileName + ".xml";

        sc.nextLine(); // Scanner buffer garbitu

        // Determinar el formato
        int luzeera = determinarFormatoXML(path);

        // NAN aukeratu eguneratzeko
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

        // Datu berriak sartzeko
        String izena = "", abizena = "", adinaStr = "", helbidea = "";
        int adina = 0;

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

        // Helbidea (siempre se actualiza)
        do {
            System.out.print("Helbidea: ");
            helbidea = sc.nextLine();
            if (!Filtroak.isHelbidea(helbidea)) {
                System.out.println("Helbidea okerra da.");
                continue;
            }
        } while (!Filtroak.isHelbidea(helbidea));
        helbidea = Filtroak.removeSpaces(helbidea);

        // Actualizar datos en XML
        try {
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("ikaslea");
            boolean encontrado = false;

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String currentNan = eElement.getElementsByTagName("nan").item(0).getTextContent();

                    if (currentNan.equals(nan)) {
                        // Actualizar helbidea (siempre)
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

            if (encontrado) {
                // Guardar los cambios
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(path));
                transformer.transform(source, result);

                System.out.println(Gehigarriak.Berdea + "Datuak ondo eguneratu dira XML fitxategian." + Gehigarriak.RESET);
            } else {
                System.out.println(Gehigarriak.Gorria + "Ez da aurkitu NAN hori fitxategian." + Gehigarriak.RESET);
            }

        } catch (Exception e) {
            System.out.println(Gehigarriak.Gorria + "Errorea: Datuak ezin izan dira eguneratu XML fitxategian." + Gehigarriak.RESET);
            System.err.println(e.getMessage());
        }
    }

    // XML fitxategian datuak ezabatu
    public static void xmlFitxategianDatuakEzabatu() {
        String fileName, path;
        Scanner sc = Gehigarriak.in;

        System.out.println("XML fitxategitik datuak ezabatu");
        xmlFitxategiakBistaratu();

        System.out.print(
                Gehigarriak.Horia + "Zein fitxategitik datuak ezabatu nahi dituzu? Sartu izena: " + Gehigarriak.RESET);
        fileName = sc.next();
        fileName = Filtroak.removeSpaces(fileName);
        path = "apunteak\\konbertsorea\\src\\main\\resources\\XML\\" + fileName + ".xml";

        sc.nextLine(); // Scanner buffer garbitu

        // NAN balidazioa
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
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("ikaslea");
            boolean encontrado = false;

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String currentNan = eElement.getElementsByTagName("nan").item(0).getTextContent();

                    if (currentNan.equals(nan)) {
                        nNode.getParentNode().removeChild(nNode);
                        encontrado = true;
                        break;
                    }
                }
            }

            if (encontrado) {
                // Guardar los cambios
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(path));
                transformer.transform(source, result);

                System.out.println(Gehigarriak.Berdea + "Datuak ondo ezabatu dira XML fitxategitik." + Gehigarriak.RESET);
            } else {
                System.out.println(Gehigarriak.Horia + "Ez da aurkitu NAN hori fitxategian." + Gehigarriak.RESET);
            }

        } catch (Exception e) {
            System.out.println(Gehigarriak.Gorria + "Errorea: Datuak ezin izan dira ezabatu XML fitxategitik." + Gehigarriak.RESET);
            System.err.println(e.getMessage());
        }
    }

    // XML fitxategia CSV formatura bihurtu
    public static void xmlFitxategiaCSVraBihurtu() {
        String fileName, path, csvPath;
        Scanner sc = Gehigarriak.in;

        System.out.println("XML fitxategia CSV formatura bihurtu");
        xmlFitxategiakBistaratu();

        System.out.print(Gehigarriak.Horia + "Zein fitxategi bihurtu nahi duzu? Sartu izena: " + Gehigarriak.RESET);
        fileName = sc.next();
        fileName = Filtroak.removeSpaces(fileName);
        path = "apunteak\\konbertsorea\\src\\main\\resources\\XML\\" + fileName + ".xml";
        csvPath = "apunteak\\konbertsorea\\src\\main\\resources\\CSV\\" + fileName + "_xml-csv.csv";

        try {
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("ikaslea");

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvPath))) {
                // CSV-ren kabezalak
                bw.write("NAN;Izena;Abizena;Adina;Helbidea");
                bw.newLine();

                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;

                        String nan = eElement.getElementsByTagName("nan").item(0).getTextContent();
                        String helbidea = eElement.getElementsByTagName("helbidea").item(0).getTextContent();
                        
                        // Verificar si tiene campos adicionales (formato completo)
                        String izena = "";
                        String abizena = "";
                        String adina = "";

                        if (eElement.getElementsByTagName("izena").getLength() > 0) {
                            izena = eElement.getElementsByTagName("izena").item(0).getTextContent();
                        }
                        if (eElement.getElementsByTagName("abizena").getLength() > 0) {
                            abizena = eElement.getElementsByTagName("abizena").item(0).getTextContent();
                        }
                        if (eElement.getElementsByTagName("adina").getLength() > 0) {
                            adina = eElement.getElementsByTagName("adina").item(0).getTextContent();
                        }

                        String csvLerroa = nan + ";" + izena + ";" + abizena + ";" + adina + ";" + helbidea;
                        bw.write(csvLerroa);
                        bw.newLine();
                    }
                }

                System.out.println(Gehigarriak.Berdea + "XML fitxategia ondo bihurtu da CSV-ra: " + csvPath + Gehigarriak.RESET);
            }

        } catch (Exception e) {
            System.out.println(Gehigarriak.Gorria + "Errorea XML fitxategia CSV-ra bihurtzean: " + e.getMessage() + Gehigarriak.RESET);
        }
    }

    // XML fitxategiak bistaratu
    public static void xmlFitxategiakBistaratu() {
        System.out.println(Gehigarriak.Cyan + "================================");
        System.out.println(Gehigarriak.Berdea + "Fitxategi .xml-ak:" + Gehigarriak.Urdina);
        try {
            java.nio.file.Files.list(java.nio.file.Paths.get("apunteak\\konbertsorea\\src\\main\\resources\\XML\\"))
                    .filter(p -> p.toString().endsWith(".xml"))
                    .forEach(p -> System.out.println(p.getFileName().toString().replaceFirst("\\.xml$", "")));
        } catch (java.io.IOException e) {
            System.out.println(Gehigarriak.Gorria + "Errorea: Fitxategiak ezin izan dira erakutsi.");
            System.err.println(e.getMessage() + Gehigarriak.RESET);
        }
        System.out.println(Gehigarriak.Cyan + "================================");
    }

    // Método auxiliar para determinar el formato del XML
    private static int determinarFormatoXML(String path) {
        try {
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("ikaslea");
            if (nList.getLength() > 0) {
                Node firstNode = nList.item(0);
                if (firstNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) firstNode;
                    // Si tiene izena, abizena y adina, es formato completo (5 campos)
                    if (eElement.getElementsByTagName("izena").getLength() > 0 &&
                        eElement.getElementsByTagName("abizena").getLength() > 0 &&
                        eElement.getElementsByTagName("adina").getLength() > 0) {
                        return 5;
                    }
                }
            }
            // Por defecto, formato reducido (2 campos: nan y helbidea)
            return 2;
        } catch (Exception e) {
            // En caso de error, asumimos formato reducido
            return 2;
        }
    }
}