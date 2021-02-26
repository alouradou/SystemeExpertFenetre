package com.mco;

public class Regle {
    private String [] schema;
    private String [] valeurs;
    private int m;

   public Regle(String[] schema){
       this.schema = schema;
       m = schema.length;
       this.valeurs = new String[m];
   }

    public void setValeurs(String[] valeurs) {
        this.valeurs = valeurs;
    }

    public String[] getSchema() {
        return schema;
    }

    public String[] getValeurs() {
        return valeurs;
    }

    @Override
    public String toString() {
        String res = "Regle{" ;
        for (int i=0; i<this.m; i++)
            res +=  schema[i] + ": " + valeurs[i] + ", ";

        return res + "}";
    }
}
