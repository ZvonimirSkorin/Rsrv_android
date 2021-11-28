package com.example.reservezavlasnike;

public class zavrti {
    String status;
    String datum;

    public zavrti(String status, String datum) {
        this.status = status;
        this.datum = datum;
    }
    public zavrti(){}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }
}
