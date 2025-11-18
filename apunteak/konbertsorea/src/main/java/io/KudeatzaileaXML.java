package main.java.io;

import main.java.App;
import main.java.utils.*; // Filtroak, Gehigarriak
import main.java.error.*; // ErroreenKudeaketa
import main.java.model.*; // Objetuak (ikaslea)
import main.resources.*; // Fitxategiak (CSV, TXT, XML, JSON)

public class KudeatzaileaXML {
    
    public static void menua() {
        String aukera;
        do {
            Gehigarriak.kontsolaGarbitu();
            System.out.println(Gehigarriak.Urdina + "Zer egin nahi duzu?");
            System.out.println(Gehigarriak.Cyan + "================================");
            System.out.println(Gehigarriak.Berdea + "1. XML fitxategia sortu.");
            System.out.println("2. XML fitxategiak bistaratu.");
            System.out.println("3. XML fitxategia irakurri.");
            System.out.println("4. XML fitxategian datuak gehitu");
            System.out.println("5. XML fitxategia eguneratu.");
            System.out.println("6. XML fitxategia ezabatu.");
            System.out.println("7. XML fitxategia CSV formatura bihurtu.");
            System.out.println(Gehigarriak.Urdina + "8. Irten");
            System.out.println(Gehigarriak.Cyan + "================================");
            System.out.print(Gehigarriak.Horia + "Aukera: " + Gehigarriak.RESET);
            aukera = Gehigarriak.in.next();
            if (Filtroak.isnumeric(aukera) == true) {
                switch (aukera) {
                    case "1":
                        Gehigarriak.kontsolaGarbitu();
                        System.out.println("XML fitxategia sortu");
                        //txtFitxategiaSortu();
                        break;
                    case "2":
                        Gehigarriak.kontsolaGarbitu();
                        //txtFitxategiakBistaratu();
                        Gehigarriak.aurreraJarraitu(); // erabiltzaileak enter sakatu aurrera joateko
                        break;
                    case "3":
                        Gehigarriak.kontsolaGarbitu();
                        //txtFitxategiaIrakurri();
                        Gehigarriak.aurreraJarraitu();// erabiltzaileak enter sakatu aurrera joateko
                        break;
                    case "4":
                        Gehigarriak.kontsolaGarbitu();
                        System.out.println("XML fitxategian datuak gehitu");
                        //txtFitxategiaGehitu();
                        break;
                    case "5":
                        Gehigarriak.kontsolaGarbitu();
                        //txtFitxategiaEguneratu();
                        System.out.println("XML fitxategia eguneratu");
                        break;
                    case "6":
                        Gehigarriak.kontsolaGarbitu();
                        //txtFitxategiaEzabatu();
                        System.out.println("XML fitxategia ezabatu");
                        break;
                    case "7":
                        Gehigarriak.kontsolaGarbitu();
                        //txtFitxategiaCSVraBihurtu();
                        System.out.println("XML fitxategia CSV formatura bihurtu");
                        break;
                    case "8":
                        Gehigarriak.kontsolaGarbitu();
                        System.out.println(Gehigarriak.Gorria + "Atzera!");
                        try {
                            App.main(null);
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println(Gehigarriak.Gorria + "Errorea aplikazioa abiarazteko: " + e.getMessage());
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
        } while (!aukera.equals("8"));
    }
}
