package com.example.reservezavlasnike;

public class ocemepokrast {
    private String broj;
    private String stanje;

    public ocemepokrast(String broj, String stanje) {
        this.broj = broj;
        this.stanje = stanje;
    }
    public ocemepokrast(){};

    public String getBroj() {
        return broj;
    }

    public void setBroj(String broj) {
        this.broj = broj;
    }

    public String getStanje() {
        return stanje;
    }

    public void setStanje(String stanje) {
        this.stanje = stanje;
    }
}
