package com.ivana.mojirecepti.model;

public class Sastojak {
    String naziv, mernaJedinica;
    int kolicina;

    public Sastojak( String naziv, String mernaJedinica, int kolicina) {
        this.naziv = naziv;
        this.mernaJedinica = mernaJedinica;
        this.kolicina = kolicina;
    }
    public Sastojak( String naziv, String mernaJedinica) {
        this.naziv = naziv;
        this.mernaJedinica = mernaJedinica;
    }

    @Override
    public String toString() {
        return naziv + " " + String.valueOf(kolicina)  + " " + mernaJedinica+ ".";
    }

    public String getNaziv() { return naziv;}
    public String getMernaJedinica() {return mernaJedinica;}
    public void setNaziv(String naziv) { this.naziv = naziv;  }
    public void setMernaJedinica(String mernaJedinica) { this.mernaJedinica = mernaJedinica; }
    public int getKolicina() {        return kolicina;    }
    public void setKolicina(int kolicina) {        this.kolicina = kolicina;    }
}
