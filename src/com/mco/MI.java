package com.mco;

import java.util.ArrayList;
import java.util.Arrays;

public class MI {
    private BDF baseFaits;
    private BDR baseRegles;

    public MI(String bf, String br){
        baseFaits = new BDF(bf);
        baseRegles = new BDR(br);
    }



    public boolean condDansBDF(String condition){
        for (int i=0;i<baseFaits.getTaille();i++)
            if (condition.equals(baseFaits.getContenu().get(i))){
                //System.out.println(condition + " dans la bdf");
                return true;
            }
        //System.out.println(condition + " pas dans la bdf");
        return false;
    }


    public boolean regleDeclenchee(Regle r){
        if (condDansBDF(r.getValeurs()[4])) {
            //System.out.println(r + " est bien déclenchée mais conclusion déjà dans la bdf");
            return false;
        }
        for (int i=0;i<=3;i++) {
            if (!r.getValeurs()[i].equals(""))
                if (!condDansBDF(r.getValeurs()[i])) {
                    //System.out.println(r + " n'est pas déclenchée");
                    return false;
                }
        }
        //System.out.println(r + " est bien déclenchée");
        return true;
    }

    public void chainageAvant(){
        boolean [] declenchee;
        declenchee=new boolean[baseRegles.getTaille()];

        boolean saturation=false;
        while (!saturation){
            int cpt=0;
            for(int i=0;i<baseRegles.getTaille();i++) {
                if (!declenchee[i]) {
                    Regle rcourante = baseRegles.getContenu().get(i);
                    if (regleDeclenchee(rcourante)) {
                        cpt++;
                        declenchee[i] = true;
                        baseFaits.ajoutBDF(rcourante.getValeurs()[4]);
                        System.out.println("Fait ajouté : " + rcourante.getValeurs()[4]);
                    }
                }
            }
            if (cpt==0) saturation=true;
        }
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

    public boolean chainageArriere(String condAVerif){
        boolean s = false;
        if (condDansBDF(condAVerif)) s = true;
        else {
            for (int i=0;i<baseRegles.getTaille();i++){
                String conclusion = baseRegles.getContenu().get(i).getValeurs()[4];
                if (conclusion.equals(condAVerif)) {
                    if (verifRegleArriere(baseRegles.getContenu().get(i))) {
                        baseFaits.ajoutBDF(condAVerif);
                        s = true;
                    }
                }
            }
        }
        // baseFaits.majFichier(); Laissé au loisir de la class MaFenetre()
        return s;
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
