package com.example.reservezavlasnike;

public class terminiFirmi {
    private String datum;
    private String korisnik;
    private String status;
    private String termin;
    private String usluge;
    private String redni;

    public terminiFirmi(String datum, String korisnik, String status, String termin, String usluge, String redni) {
        this.datum = datum;
        this.korisnik = korisnik;
        this.status = status;
        this.termin = termin;
        this.usluge = usluge;
        this.redni = redni;
    }
    public terminiFirmi(){}

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(String korisnik) {
        this.korisnik = korisnik;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTermin() {
        return termin;
    }

    public void setTermin(String termin) {
        this.termin = termin;
    }

    public String getUsluge() {
        return usluge;
    }

    public void setUsluge(String usluge) {
        this.usluge = usluge;
    }

    public String getRedni() {
        return redni;
    }

    public void setRedni(String redni) {
        this.redni = redni;
    }
}
