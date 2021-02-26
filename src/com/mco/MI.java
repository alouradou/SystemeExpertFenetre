package com.mco;

import java.util.ArrayList;

public class MI {
    private BDF baseFaits;
    private BDR baseRegles;

    public MI(String bf, String br){
        baseFaits = new BDF(bf);
        baseRegles = new BDR(br);
    }



    private boolean condDansBDF(String cond){
        boolean s = false;
        // parcourir la base de faits
        ArrayList<String> contenu = baseFaits.getContenu();
        for (int i=0; i<baseFaits.getTaille()-1;i++) {
            if (cond == contenu.get(i)) s = true;
        }
        // si on parcourt toute la boucle sans trouver, alors retourner faux
        return s;
    }


    private boolean regleDeclenchee(Regle r){
        boolean s = true;
        String[] valeurs = r.getValeurs();
        if (condDansBDF(valeurs[baseRegles.getTaille()])) return true;
        for (int i=0;i<baseRegles.getTaille();i++){
            if (!valeurs[i].equals("")){ // s'il y a effectivement une règle à vérifier
                if (!condDansBDF(valeurs[i])){
                    s = false;
                }
            }
        }
        return s; // sinon, on renvoie vrai
    }

    public void chainageAvant(){
        // faire un tableau dejaDeclenchee de bool de la taille de la base de règles
        boolean[] dejaDeclenchee = new boolean[baseRegles.getTaille()];
        /*for (int i=0;i<dejaDeclenchee.length;i++) dejaDeclenchee[i] = false;*/

        int cpt = 0; // compte les nouvelles découvertes au tour courant du tant que
        do {
            for (int i=0;i<baseRegles.getTaille();i++){
                if (!dejaDeclenchee[i]){
                    Regle r = baseRegles.getContenu().get(i);
                    if (regleDeclenchee(r)) {
                        dejaDeclenchee[i] = true;
                        baseFaits.ajoutBDF(r.getValeurs()[baseRegles.getTaille()]);
                        System.out.println("Nouveau fait avéré : " + r.getValeurs()[baseRegles.getTaille()]);
                        cpt += 1;
                    }
                }
            }
        } while (cpt!=0);
        baseFaits.majFichier();
    }


    // Pose la question de savoir si la règle existe bien avec les prémisses données
    private boolean verifRegleArriere(Regle r){
        String[] valeurs = r.getValeurs();

        for (int i=0;i<4;i++){
            if (!valeurs[i].equals("")){ // s'il y a effectivement une règle à vérifier
                if (!condDansBDF(valeurs[i])){
                    if (!chainageArriere(valeurs[i]))// appel récursif au chainage arriere
                        return false; // si ni l'appel récursif ni les faits ne le permettent, alors on ne peut pas vérfier la ccl
                }
            }
        }
        return true; // sinon, on renvoie vrai
    }

  /*  public boolean chainageArriere(String condAVerif){
        boolean s = false;
        if (condDansBDF(condAVerif)) s = true;
        else {
            for (int i=0;i<baseRegles.getTaille();i++){
                String conclusion = baseRegles.getContenu().get(i).getValeurs()[4];
                if (conclusion.equals(condAVerif)) {
                    if (verifRegleArriere(baseRegles.getContenu().get(i))) s = true;
                }
            }
        }
        baseFaits.majFichier();
        return s;
    }*/


    // fonctions de la correction de la prof
    public boolean chainageArriere(String but){
        if (condDansBDF(but))
            return true;
        for (int i=0;i<baseRegles.getTaille();i++){
            Regle rcourante = baseRegles.getContenu().get(i);
            if (rcourante.getValeurs()[baseRegles.getTaille()].equals(but))
                if (verifRegleArriere(rcourante))
                    return true;
        }
        return false;
    }

    public void setBaseFaits(BDF baseFaits) {
        this.baseFaits = baseFaits;
    }

    public BDF getBaseFaits() {
        return baseFaits;
    }

    public void setBaseRegles(BDR baseRegles) {
        this.baseRegles = baseRegles;
    }

    public BDR getBaseRegles() {
        return baseRegles;
    }

    @Override
    public String toString() {
        return "MI{" +
                "baseFaits=" + baseFaits +
                ", baseRegles=" + baseRegles +
                '}';
    }
}
