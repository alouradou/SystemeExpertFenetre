package com.mco;

import java.util.ArrayList;

public class BDR {
    private ArrayList<Regle> contenu;
    private int taille;
    private String fileName;

    public BDR(String nomBDR){

        contenu = new ArrayList<Regle>();
        this.fileName = nomBDR;
        StreamDeRegles stream = new StreamDeRegles(nomBDR);
        taille = 0;
        while (stream.getRegleSuivante() != null){
            Regle rtemp = new Regle(stream.getRegle().getSchema());
            rtemp.setValeurs(stream.getRegle().getValeurs());
            contenu.add(rtemp);
        }
        taille = contenu.size();
    }

    public ArrayList<Regle> getContenu() {
        return contenu;
    }

    public int getTaille() {
        return taille;
    }

    @Override
    public String toString() {
        return "BDR{" +
                "contenu=" + contenu +
                ", taille=" + taille +
                '}';
    }
}
