package main.model;

import main.utils.Filtroak;

public class Ikaslea {
    private String nan; // Adb. --> 0000000A
    private String izena; // Adb. --> Juan
    private String abizena; // Adb. --> Perez Gomez
    private int adina; // Adb. --> 25
    private String helbidea; // Adb. --> Calle Mayor 10, 28013 Madrid

    public Ikaslea() {
        nan = "";
        izena = "";
        abizena = "";
        adina = 0;
        helbidea = "";
    }

    public Ikaslea(String nan, String izena, String abizena, int adina, String email, String helbidea) {
        // Balidazioak
        if(Filtroak.isDNI(nan) == false) { // NAN balidazioa
            throw new IllegalArgumentException("NAN ez da zuzena.");
        }else{
            this.nan = nan;
        }

        if(Filtroak.isIzena(izena) == false) { // Izena balidazioa
            throw new IllegalArgumentException("Izena ez da zuzena.");
        }else{
            this.izena = izena;
        }

        if(Filtroak.isIzena(abizena) == false) { // Abizena balidazioa
            throw new IllegalArgumentException("Abizena ez da zuzena.");
        }else{
            this.abizena = abizena;
        }

        if(Filtroak.isAdinaInt(adina) == false) { // Adina balidazioa
            throw new IllegalArgumentException("Adina ez da zuzena.");
        }else{
            this.adina = adina;
        }

        if (Filtroak.isHelbidea(helbidea) == false) { // Helbidea balidazioa
            throw new IllegalArgumentException("Helbidea ez da zuzena.");
        }else{
            this.helbidea = helbidea;
        }
    }

    // Getters
    public String getNan() {
        return nan;
    }

    public String getIzena() {
        return izena;
    }

    public String getAbizena() {
        return abizena;
    }

    public int getAdina() {
        return adina;
    }

    public String getHelbidea() {
        return helbidea;
    }

    // Setters
    public void setNan(String nan) {
        this.nan = nan;
    }

    public void setIzena(String izena) {
        this.izena = izena;
    }

    public void setAbizena(String abizena) {
        this.abizena = abizena;
    }

    public void setAdina(int adina) {
        this.adina = adina;
    }

    public void setHelbidea(String helbidea) {
        this.helbidea = helbidea;
    }
}
