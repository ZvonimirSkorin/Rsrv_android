package com.example.reservezavlasnike;

public class cijenik {
    private String vrstaUsluge;
    private long cijena;
    private long trajanje;
    private String spol;

    public cijenik(String vrstaUsluge, long cijena, long trajanje, String spol) {
        this.vrstaUsluge = vrstaUsluge;
        this.cijena = cijena;
        this.trajanje = trajanje;
        this.spol = spol;
    }
    public cijenik(){}

    public String getVrstaUsluge() {
        return vrstaUsluge;
    }

    public void setVrstaUsluge(String vrstaUsluge) {
        this.vrstaUsluge = vrstaUsluge;
    }

    public long getCijena() {
        return cijena;
    }

    public void setCijena(long cijena) {
        this.cijena = cijena;
    }

    public long getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(long trajanje) {
        this.trajanje = trajanje;
    }

    public String getSpol() {
        return spol;
    }

    public void setSpol(String spol) {
        this.spol = spol;
    }
}