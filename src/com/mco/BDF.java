package com.mco;

import java.awt.image.DataBufferDouble;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Scanner;

public class BDF {
    private ArrayList<String> contenu;
    private int taille = 0;
    private String fileName;

    public BDF(String nomBDF){
        //String fait="";
        contenu = new ArrayList<String>();
        fileName = nomBDF;
        BufferedReader buffer;
        String line = "";
        try {
            buffer = new BufferedReader(new FileReader(fileName));
            while (line != null){
                line = buffer.readLine();
                if (line != null && line != "") {
                    ajoutBDF(line);
                }
            }
            buffer.close();
        }
        catch (Exception e) {
            System.out.println("[Error opening file in BDF]" + e);
        }
        taille = contenu.size();
    }


    public ArrayList<String> getContenu() {
        return contenu;
    }

    public int getTaille() {
        return taille;
    }

    public void ajoutBDF(String fait){
        contenu.add(fait);
        taille ++;
    }

    public void majFichier(){
        FileWriter file;
        try {
            file = new FileWriter(fileName);
            for (int i=0;i<taille;i++){
                if (contenu.get(i) != null && contenu.get(i) != ""){
                    file.write(contenu.get(i)+"\n");
                    //System.out.println("Écriture du fichier : "+contenu.get(i)+"\n");
                }
            }
            file.close();
        } catch (Exception e) {
            System.out.println("[Error writing file in majFichier()] " + e);
        }
    }

    @Override
    public String toString() {
        return "BDF{" +
                "contenu=" + contenu +
                ", taille=" + taille +
                '}';
    }
}
