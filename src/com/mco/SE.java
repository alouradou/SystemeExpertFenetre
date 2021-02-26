package com.mco;

public class SE {
    private BDF baseFaits;
    private BDR baseRegles;

    public SE(String bf, String br) {
        baseFaits = new BDF(bf);
        baseRegles = new BDR(br);
        System.out.println("SE cree");

    }


    @Override
    public String toString() {
        return "SE{" +
                "baseFaits=" + baseFaits +
                ", baseRegles=" + baseRegles +
                '}';
    }

    public boolean condDansBDF(String condition){
        for (int i=0;i<baseFaits.getTaille();i++)
            if (condition.equals(baseFaits.getContenu().get(i)))
                return true;
        return false;
    }

    public boolean regleDeclenchee(Regle r){
        if (condDansBDF(r.getValeurs()[4]))
            return true;
        for (int ind=0;ind<4;ind++)
            if (!condDansBDF(r.getValeurs()[ind]))
                return false;
        return true;
    }

    public boolean verifRegleArr(Regle r){

        for (int ind=0;ind<4;ind++)
            if (!condDansBDF(r.getValeurs()[ind]))
                if(!chainageArriere(r.getValeurs()[ind]))
                    return false;
        return true;
    }


    public void chainageAvant(){
        boolean [] declenchee;
        declenchee=new boolean[baseRegles.getTaille()];
        for(int i=0;i<declenchee.length;i++)
            declenchee[i]=false;
        boolean saturation=false;

        while (!saturation){
            int cpt=0;
            for(int i=0;i<baseRegles.getTaille();i++) {
                if (declenchee[i]==false) {
                    Regle rcourante = baseRegles.getContenu().get(i);
                    if (regleDeclenchee(rcourante)) {
                        cpt++;
                        declenchee[i] = true;
                        baseFaits.ajoutBDF(rcourante.getValeurs()[4]);
                        System.out.println("Fait ajoute : " + rcourante.getValeurs()[4]);
                    }
                }
            }
            if (cpt==0) saturation=true;
        }
        baseFaits.majFichier();
    }

    public boolean chainageArriere(String but){
        if (condDansBDF(but))
            return true;
        for(int i=0;i<baseRegles.getTaille();i++) {
            System.out.println("for "+i);
            Regle rcourante = baseRegles.getContenu().get(i);
            if (rcourante.getValeurs()[baseRegles.getTaille()].equals(but)){
                System.out.println("if but");
                if(verifRegleArr(rcourante))
                    return true;}

        }
        return false;
    }

    public BDR getBaseRegles() {
        return baseRegles;
    }

    public BDF getBaseFaits() {
        return baseFaits;
    }

    public void setBaseRegles(BDR baseRegles) {
        this.baseRegles = baseRegles;
    }

    public void setBaseFaits(BDF baseFaits) {
        this.baseFaits = baseFaits;
    }
}
