package com.example.reservezavlasnike;

public class termin {
   private String termin;
   private String korisnik;

    public termin(String termin, String korisnik) {
        this.termin = termin;
        this.korisnik = korisnik;
    }
    public termin(){
    }

    public String getTermin() {
        return termin;
    }

    public void setTermin(String termin) {
        this.termin = termin;
    }

    public String getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(String korisnik) {
        this.korisnik = korisnik;
    }
}
