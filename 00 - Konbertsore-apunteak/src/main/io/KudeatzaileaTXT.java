package main.io;

import main.App;
import main.utils.*; // Filtroak, Gehigarriak
import main.error.*; // ErroreenKudeaketa
import main.model.*; // Objetuak (ikaslea)
import resources.*; // Fitxategiak (CSV, TXT, XML, JSON)

public class KudeatzaileaTXT {
    
    public static void menua() {
        String aukera;
        do {
            Gehigarriak.kontsolaGarbitu();
            System.out.println(Gehigarriak.Urdina + "Zer egin nahi duzu?");
            System.out.println(Gehigarriak.Cyan + "================================");
            System.out.println(Gehigarriak.Berdea + "1. TXT fitxategia sortu.");
            System.out.println("2. TXT fitxategiak bistaratu.");
            System.out.println("3. TXT fitxategia irakurri.");
            System.out.println("4. TXT fitxategian datuak gehitu");
            System.out.println("5. TXT fitxategia eguneratu.");
            System.out.println("6. TXT fitxategia ezabatu.");
            System.out.println("7. TXT fitxategia CSV formatura bihurtu.");
            System.out.println(Gehigarriak.Urdina + "8. Irten");
            System.out.println(Gehigarriak.Cyan + "================================");
            System.out.print(Gehigarriak.Horia + "Aukera: " + Gehigarriak.RESET);
            aukera = Gehigarriak.in.next();
            if (Filtroak.isnumeric(aukera) == true) {
                switch (aukera) {
                    case "1":
                        Gehigarriak.kontsolaGarbitu();
                        System.out.println("TXT fitxategia sortu");
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
                        System.out.println("TXT fitxategian datuak gehitu");
                        //txtFitxategiaGehitu();
                        break;
                    case "5":
                        Gehigarriak.kontsolaGarbitu();
                        //txtFitxategiaEguneratu();
                        System.out.println("TXT fitxategia eguneratu");
                        break;
                    case "6":
                        Gehigarriak.kontsolaGarbitu();
                        //txtFitxategiaEzabatu();
                        System.out.println("TXT fitxategia ezabatu");
                        break;
                    case "7":
                        Gehigarriak.kontsolaGarbitu();
                        //txtFitxategiaCSVraBihurtu();
                        System.out.println("TXT fitxategia CSV formatura bihurtu");
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
