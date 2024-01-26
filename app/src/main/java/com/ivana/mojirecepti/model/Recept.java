package com.ivana.mojirecepti.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Recept {
    String naziv, priprema, slika;
    ArrayList<Sastojak> sastojci;
    int vremePripreme, kalorije, omiljeni, id;
    Tip tip;

   public enum Tip  {DORUCAK(1), RUCAK(2), VECERA(3), NAPICI(4), UZINA(5);
        private final int value;
        private Tip(int value) { this.value = value; }
        public int getValue() {return value;}
    };
    public Recept() {}
    public Recept(int id, String naziv, int vremePripreme, int kalorije, String slika){
        this.id = id;
        this.naziv = naziv;
        this.vremePripreme = vremePripreme;
        this.kalorije = kalorije;
        this.slika = slika;
    }
    public Recept(int id, String naziv, String priprema, String slika, ArrayList<Sastojak> sastojci,
                  int vremePripreme, int omiljeni, int kalorije, Tip tip) {
        this.id = id;
        this.naziv = naziv;
        this.priprema = priprema;
        this.slika = slika;
        this.sastojci = sastojci;
        this.vremePripreme = vremePripreme;
        this.omiljeni = omiljeni;
        this.kalorije = kalorije;
        this.tip = tip;
    }

//    @Override
//    public String toString() {
//        String sastojciNiz = Arrays.toString(sastojci.toArray());
//        return naziv  + priprema +  sastojciNiz + vremePripreme +  kalorije + omiljeni;
//    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void setPriprema(String priprema) {
        this.priprema = priprema;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public void setSastojci(ArrayList<Sastojak> sastojci) {
        this.sastojci = sastojci;
    }

    public void setVremePripreme(int vremePripreme) {
        this.vremePripreme = vremePripreme;
    }

    public void setOmiljeni(int omiljeni) {
        this.omiljeni = omiljeni;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getPriprema() {
        return priprema;
    }

    public String getSlika() {
        return slika;
    }

    public ArrayList<Sastojak> getSastojci() {
        return sastojci;
    }

    public int getVremePripreme() {
        return vremePripreme;
    }

    public int getOmiljeni() {
        return omiljeni;
    }

    public int getKalorije() { return kalorije;   }

    public void setKalorije(int kalorije) { this.kalorije = kalorije;   }

    public Tip getTip() {        return tip;    }

    public void setTip(Tip tip) {        this.tip = tip;    }

    public int getId() {        return id;    }

    public void setId(int id) {        this.id = id;    }
}
