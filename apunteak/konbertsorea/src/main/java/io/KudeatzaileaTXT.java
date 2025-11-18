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

import main.java.App;
import main.java.utils.*; // Filtroak, Gehigarriak
import main.java.error.*; // ErroreenKudeaketa
import main.java.model.*; // Objetuak (ikaslea)
import main.resources.*; // Fitxategiak (CSV, TXT, XML, JSON)

public class KudeatzaileaTXT {

    public static void menua() {
        String aukera;
        do {
            Gehigarriak.kontsolaGarbitu();
            System.out.println(Gehigarriak.Urdina + "Zer egin nahi duzu?");
            System.out.println(Gehigarriak.Cyan + "================================");
            System.out.println(Gehigarriak.Berdea + "1. TXT fitxategia sortu.");
            System.out.println("2. TXT fitxategia irakurri.");
            System.out.println("3. TXT fitxategian datuak gehitu");
            System.out.println("4. TXT fitxategia eguneratu.");
            System.out.println("5. TXT fitxategia ezabatu.");
            System.out.println("6. TXT fitxategia CSV formatura bihurtu.");
            System.out.println(Gehigarriak.Urdina + "7. Irten");
            System.out.println(Gehigarriak.Cyan + "================================");
            System.out.print(Gehigarriak.Horia + "Aukera: " + Gehigarriak.RESET);
            aukera = Gehigarriak.in.next();
            if (Filtroak.isnumeric(aukera) == true) {
                switch (aukera) {
                    case "1":
                        Gehigarriak.kontsolaGarbitu();
                        System.out.println("TXT fitxategia sortu");
                        txtFitxategiaSortu();
                        break;
                    case "2":
                        Gehigarriak.kontsolaGarbitu();
                        txtFitxategiaIrakurri();
                        Gehigarriak.aurreraJarraitu(); // erabiltzaileak enter sakatu aurrera joateko
                        break;
                    case "3":
                        Gehigarriak.kontsolaGarbitu();
                        txtFitxategianDatuakGehitu();
                        break;
                    case "4":
                        Gehigarriak.kontsolaGarbitu();
                        //System.out.println("TXT fitxategian datuak eguneratu");
                        txtFitxategianDatuakEguneratu();
                        break;
                    case "5":
                        Gehigarriak.kontsolaGarbitu();
                        // System.out.println("TXT fitxategia datuak ezabatu");
                        txtFitxategianDatuakEzabatu();
                        break;
                    case "6":
                        Gehigarriak.kontsolaGarbitu();
                        txtFitxategiaCSVraBihurtu();
                        //System.out.println("TXT fitxategia CSV formatura bihurtu");
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

    // TXT fitxategia sortu
    public static void txtFitxategiaSortu() {
        String fileName, path;
        Scanner sc = Gehigarriak.in;

        do {
            Gehigarriak.kontsolaGarbitu();
            System.out.print(
                    Gehigarriak.Horia + "Sartu sortu nahi duzun fitxategiaren izena sartu: " + Gehigarriak.RESET);
            fileName = sc.next();
            fileName = Filtroak.removeSpaces(fileName); // hutsuneak kendu
            path = "apunteak\\konbertsorea\\src\\main\\resources\\TXT\\" + fileName + ".txt";

            File fitx = new File(path);
            if (fitx.exists()) {
                System.out.println(Gehigarriak.Gorria + "Jada fitxategi batek izen hori du, zehiatu beste batekin."
                        + Gehigarriak.RESET);
                continue; // berriro galdetu
            }

            if (ErroreenKudeaketa.fitxategiaSortu(path)) {
                System.out.println(Gehigarriak.Berdea + "Fitxategia ondo sortu da: " + path + Gehigarriak.RESET);
            } else {
                System.out.println(Gehigarriak.Gorria + "Fitxategia ez da sortu: " + path + Gehigarriak.RESET);
                ErroreenKudeaketa.fitxategiaSortu(path);
            }
            break; // fitxategia ondo sortu bada, irten loop-etik

        } while (true);

    }

    // TXT fitxategiak irakurri
    public static void txtFitxategiaIrakurri() {
        String fileName, path;

        // .txt fitxategiak bistaratu:
        txtFitxategiakBistaratu();

        // fitxategi bat irakurri:
        System.out
                .print(Gehigarriak.Horia + "Sartu irakurri nahi duzun fitxategiaren izena sartu: " + Gehigarriak.RESET);
        fileName = Gehigarriak.in.next();
        Filtroak.removeSpaces(fileName);
        path = "apunteak\\konbertsorea\\src\\main\\resources\\TXT\\" + fileName + ".txt";
        if (ErroreenKudeaketa.fitxategiaIrakurri(path) == true) {
            // System.out.println(Gehigarriak.Berdea + "Fitxategia ondo irakurri da: " +
            // path + Gehigarriak.RESET);
            try {
                java.util.List<String> lerroak = java.nio.file.Files.readAllLines(java.nio.file.Paths.get(path));
                for (String lerroa : lerroak) {
                    System.out.println(lerroa);
                }
            } catch (java.io.IOException e) {
                System.out.println(Gehigarriak.Gorria + "Errorea: Fitxategia ezin izan da irakurri.");
                System.err.println(e.getMessage() + Gehigarriak.RESET);
            }
        } else {
            // Errorea bistaratu:
            System.out.println(Gehigarriak.Gorria + "Fitxategia ez da irakurri: " + path + Gehigarriak.RESET);
            ErroreenKudeaketa.fitxategiaIrakurri(path);
        }
    }

    // TXT fitxategian datuak gehitu
    public static void txtFitxategianDatuakGehitu() {
        String fileName, path;
        Scanner sc = Gehigarriak.in;

        System.out.println("TXT fitxategian datuak gehitu");
        txtFitxategiakBistaratu();

        // Fitxategia aukeratu
        System.out.print(
                Gehigarriak.Horia + "Zein fitxategiri datuak gehitu nahi dizkiozu? Sartu izena: " + Gehigarriak.RESET);
        fileName = sc.next();
        fileName = Filtroak.removeSpaces(fileName);
        path = "apunteak\\konbertsorea\\src\\main\\resources\\TXT\\" + fileName + ".txt";

        sc.nextLine(); // Scanner buffer garbitu
        String nan, izena, abizena, adinaStr, helbidea;
        int adina;

        // NAN balidazioa + existitzen den ala ez
        do {
            System.out.print("NAN (8 zenbaki + 1 letra): ");
            nan = sc.nextLine();
            if (!Filtroak.isDNI(nan)) {
                System.out.println(
                        Gehigarriak.Gorria + "NAN okerra. 8 zenbaki eta 1 letra izan behar ditu." + Gehigarriak.RESET);
                continue;
            }
            if (ErroreenKudeaketa.ifExistsNan(nan, path)) {
                // NAN existitzen da, beste bat sartu
                continue;
            }
            break; // NAN baliozkoa eta ez dago fitxategian
        } while (true);


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


        // Pertsona objektua sortu
        Ikaslea p = new Ikaslea(nan, adina);

        // Fitxategian gehitu
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            bw.write(p.toTXT());
            bw.newLine();
            System.out.println(Gehigarriak.Berdea + "Datuak ondo gehitu dira fitxategian." + Gehigarriak.RESET);
        } catch (IOException e) {
            System.out.println(
                    Gehigarriak.Gorria + "Errorea: Datuak ezin izan dira gehitu fitxategian." + Gehigarriak.RESET);
            System.err.println(e.getMessage());
        }
    }

    // TXT fitxategia eguneratu
public static void txtFitxategianDatuakEguneratu() {
    String fileName, path;
    List<Ikaslea> ikasleak = new ArrayList<>();
    Scanner sc = Gehigarriak.in;

    System.out.println("TXT fitxategian datuak eguneratu");
    txtFitxategiakBistaratu();

    // Fitxategia aukeratu
    System.out.print(Gehigarriak.Horia + "Zein fitxategi eguneratu nahi duzu? Sartu izena: " + Gehigarriak.RESET);
    fileName = sc.next();
    fileName = Filtroak.removeSpaces(fileName);
    path = "apunteak\\konbertsorea\\src\\main\\resources\\TXT\\" + fileName + ".txt";

    // Fitxategia irakurri eta Pertsona objektuak sortu
    int luzeera = 2;
    String banatzailea = ","; // CORREGIDO: Definir banatzailea

    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
        String lerroa;
        System.out.println("Datuak fitxategian:");
        while ((lerroa = br.readLine()) != null) {
            System.out.println(lerroa);
            String[] zatiak = lerroa.split(banatzailea);
            if (zatiak.length == 2) { // NAN, Adina <-- Txt formatu laburra
                luzeera = 2;
                Ikaslea p = new Ikaslea(zatiak[0], Integer.parseInt(zatiak[1]));
                ikasleak.add(p);
            } else if (zatiak.length == 5) { // NAN, Izena, Abizena, Adina, Helbidea
                luzeera = 5;
                Ikaslea p = new Ikaslea(zatiak[0], zatiak[1], zatiak[2], Integer.parseInt(zatiak[3]), zatiak[4]);
                ikasleak.add(p);
            }
        }
    } catch (FileNotFoundException e) {
        System.out.println("Fitxategia ez da aurkitu.");
        return;
    } catch (IOException e) {
        e.printStackTrace();
        return;
    }

    sc.nextLine(); // Scanner buffer garbitu

    // NAN aukeratu eguneratzeko
    String nan;
    Ikaslea ikasleaEguneratu = null;
    do {
        System.out.print(Gehigarriak.Horia + "Eguneratu nahi duzun pertsonaren NAN-a sartu: " + Gehigarriak.RESET);
        nan = sc.nextLine();
        if (!Filtroak.isDNI(nan)) {
            System.out.println(
                    Gehigarriak.Gorria + "NAN okerra. 8 zenbaki + 1 letra izan behar du." + Gehigarriak.RESET);
            continue;
        }
        // Pertsona bilatu
        for (Ikaslea p : ikasleak) {
            if (p.getNan().equalsIgnoreCase(nan)) {
                ikasleaEguneratu = p;
                break;
            }
        }
        if (ikasleaEguneratu == null) {
            System.out.println(Gehigarriak.Gorria + "Ez da NAN hori aurkitu." + Gehigarriak.RESET);
        }
    } while (ikasleaEguneratu == null);

    // Datu berriak sartzeko balidazioa
    String izena = "", abizena = "" , adinaStr = "", helbidea = "";
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
    }
    
    do {
        System.out.print("Adina: ");
        adinaStr = sc.nextLine();
        if (!Filtroak.isAdina(adinaStr)) {
            System.out.println("Adina okerra. Zenbaki bakarrik sartu.");
        }
    } while (!Filtroak.isAdina(adinaStr));
    adina = Integer.parseInt(adinaStr);
    
    if (luzeera == 5) {
        do {
            System.out.print("Helbidea: ");
            helbidea = sc.nextLine();
            if (!Filtroak.isHelbidea(helbidea)) {
                System.out.println("Helbidea okerra da.");
                continue;
            }
        } while (!Filtroak.isHelbidea(helbidea));
        helbidea = Filtroak.removeSpaces(helbidea);
    }

    // Datuak eguneratu
    if (luzeera == 5) {
        ikasleaEguneratu.setIzena(izena);
        ikasleaEguneratu.setAbizena(abizena);
        ikasleaEguneratu.setAdina(adina); // CORREGIDO: falta este set
        ikasleaEguneratu.setHelbidea(helbidea);
    } else if (luzeera == 2) {
        ikasleaEguneratu.setAdina(adina);
    }

    // Fitxategia BERRIDATZI completamente con los datos actualizados
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) { // CORREGIDO: sin append mode
        for (Ikaslea p : ikasleak) {
            if (luzeera == 5) {
                bw.write(p.toCSV()); // CSV formatuan
            } else {
                bw.write(p.toTXT()); // TXT formatu laburra
            }
            bw.newLine();
        }
        System.out.println(Gehigarriak.Berdea + "Datuak ondo eguneratu dira fitxategian." + Gehigarriak.RESET);
    } catch (IOException e) {
        System.out.println(
                Gehigarriak.Gorria + "Errorea: Datuak ezin izan dira eguneratu fitxategian." + Gehigarriak.RESET);
        System.err.println(e.getMessage());
    }
}

    // TXT fitxategian datuak ezabatu
    public static void txtFitxategianDatuakEzabatu() {
        String fileName, path;
        Scanner sc = Gehigarriak.in;

        System.out.println("TXT fitxategitik datuak ezabatu");
        txtFitxategiakBistaratu();

        // Fitxategia aukeratu erabiltzaileak emandako izenarekin
        System.out.print(
                Gehigarriak.Horia + "Zein fitxategitik datuak ezabatu nahi dituzu? Sartu izena: " + Gehigarriak.RESET);
        fileName = sc.next();
        fileName = Filtroak.removeSpaces(fileName);
        path = "apunteak\\konbertsorea\\src\\main\\resources\\TXT\\" + fileName + ".txt";

        sc.nextLine(); // Scanner buffer garbitu

        // NAN balidazioa (8 zenbaki + 1 letra)
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

        // Fitxategia irakurri eta NAN hori duten lerroak kendu
        try {
            ezabatuDatua(nan, path );

        } catch (IOException e) {
            // I/O errorea gertatzen bada, mezua erakutsi
            System.out.println(
                    Gehigarriak.Gorria + "Errorea: Datuak ezin izan dira ezabatu fitxategitik." + Gehigarriak.RESET);
            System.err.println(e.getMessage());
        }
    }

    public static void ezabatuDatua(String nan, String path) throws IOException {
        List<String> lines = new ArrayList<>();
            boolean found = false;

            // Fitxategiko lerro guztiak irakurri
            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                String line;
                while ((line = br.readLine()) != null) {
                    // Lerroa NAN horrekin hasten bada, ez dugu gehitzen (ezabatzen da)
                    if (line.startsWith(nan + ";")) {
                        found = true;
                        continue;
                    }
                    lines.add(line);
                }
            }

            // NAN aurkitu bada, fitxategia berridatzi NAN hori gabe
            if (found) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
                    for (String l : lines) {
                        bw.write(l);
                        bw.newLine();
                    }
                }
                System.out.println(Gehigarriak.Berdea + "Datuak ondo ezabatu dira fitxategitik." + Gehigarriak.RESET);
            } else {
                // NAN ez bada aurkitu, mezua erakutsi
                System.out.println(Gehigarriak.Horia + "Ez da aurkitu NAN hori fitxategian." + Gehigarriak.RESET);
            }
    }

    // TXT fitxategia CSV formatura bihurtu
    public static void txtFitxategiaCSVraBihurtu() {
        String fileName, path, csvPath;
        Scanner sc = Gehigarriak.in;

        System.out.println("TXT fitxategia CSV formatura bihurtu");
        txtFitxategiakBistaratu();

        // Fitxategia aukeratu
        System.out.print(Gehigarriak.Horia + "Zein fitxategi bihurtu nahi duzu? Sartu izena: " + Gehigarriak.RESET);
        fileName = sc.next();
        Filtroak.removeSpaces(fileName);
        path = "apunteak\\konbertsorea\\src\\main\\resources\\TXT\\" + fileName + ".txt";
        csvPath = "apunteak\\konbertsorea\\src\\main\\resources\\CSV\\" + fileName + "_txt-csv.csv";

        // Fitxategia irakurri eta CSVra idatzi
        try (BufferedReader br = new BufferedReader(new FileReader(path));
                BufferedWriter bw = new BufferedWriter(new FileWriter(csvPath))) {

            // CSV-ren kabezalak (Banatzailea: ';')
            String cabezalak = "NAN;Izena;Abizena;Adina;Helbidea";
            bw.write(cabezalak);
            bw.newLine();

            // TXT-ren banatzailea (adibidez, espazioa. Aldatu behar baduzu)
            final String TXT_BANATZAILEA = ",";

            // CSV-ren banatzailea
            final String CSV_BANATZAILEA = ";";

            String lerroa;
            while ((lerroa = br.readLine()) != null) {

                String[] datuak = lerroa.split(TXT_BANATZAILEA);
                String csvLerroa = "";

                // Egiaztatu lerroak zenbat zutabe dituen
                if (datuak.length == 2) {
                    // 1. Formatu Laburra: NAN eta Adina (2 zutabe)
                    String nan = datuak[0];
                    String adina = datuak[1];

                    // NAN ; Izena ; Abizena ; Adina ; Helbidea
                    csvLerroa = nan + CSV_BANATZAILEA +
                            "" + CSV_BANATZAILEA + // Izena (Hutsik)
                            "" + CSV_BANATZAILEA + // Abizena (Hutsik)
                            adina + CSV_BANATZAILEA + // Adina
                            ""; // Helbidea (Hutsik)

                } else if (datuak.length == 5) {
                    // 2. Formatu Osoa: NAN, Izena, Abizena, Adina, Helbidea (5 zutabe)
                    // Datuak jada CSV kabezalarekin bat datoz, beraz, berriz muntatu besterik ez.
                    csvLerroa = String.join(CSV_BANATZAILEA, datuak);

                } else {
                    // Beste formatu bat edo lerro akastuna
                    System.err.println("OHARRA: Lerroak ez du espero den formatua (2 edo 5 zutabe): " + lerroa);
                    continue; // Hurrengo lerrora salto egin

                }

                // CSV fitxategian idatzi
                bw.write(csvLerroa);
                bw.newLine();
            }

            System.out.println(Gehigarriak.Berdea + "Fitxategia ondo bihurtu da: " + csvPath + Gehigarriak.RESET);
        } catch (FileNotFoundException e) {
            System.out.println("Fitxategia ez da aurkitu.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TXT fitxategiak bistaratu
    public static void txtFitxategiakBistaratu() {
        // .txt fitxategiak bistaratu:
        System.out.println(Gehigarriak.Cyan + "================================");
        System.out.println(Gehigarriak.Berdea + "Fitxategi .txt-ak:" + Gehigarriak.Urdina);
        try {
            java.nio.file.Files.list(java.nio.file.Paths.get("apunteak\\konbertsorea\\src\\main\\resources\\TXT\\"))
                    .filter(p -> p.toString().endsWith(".txt"))
                    .forEach(p -> System.out.println(p.getFileName().toString().replaceFirst("\\.txt$", "")));
        } catch (java.io.IOException e) {
            System.out.println(Gehigarriak.Gorria + "Errorea: Fitxategiak ezin izan dira erakutsi.");
            System.err.println(e.getMessage() + Gehigarriak.RESET);
        }
        System.out.println(Gehigarriak.Cyan + "================================");

    }
}
