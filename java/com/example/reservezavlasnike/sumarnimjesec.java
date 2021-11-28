package com.example.reservezavlasnike;

public class sumarnimjesec {
    private String mjesec;
    private String status;
    private String sumarno;

    public sumarnimjesec(String mjesec, String status, String sumarno) {
        this.mjesec = mjesec;
        this.status = status;
        this.sumarno = sumarno;
    }

    public sumarnimjesec() {
    }

    public String getMjesec() {
        return mjesec;
    }

    public void setMjesec(String mjesec) {
        this.mjesec = mjesec;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSumarno() {
        return sumarno;
    }

    public void setSumarno(String sumarno) {
        this.sumarno = sumarno;
    }
}
