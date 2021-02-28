package com.mco;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.Buffer;

public class StreamDeRegles {
    private FileReader file;
    private BufferedReader buffer;
    private Regle regle; // règle courante lors du parcours du fichier par le buffer

    public StreamDeRegles(String fileName){
        try{
            file = new FileReader(fileName);
            buffer = new BufferedReader(file);
            //System.out.println("File ready");
        }
        catch(Exception e){
            System.out.println("[Error opening file in StreamDeRegles]" + e);
        }
        String line = null;
        try{
            line = buffer.readLine();
        }
        catch(Exception e){
            System.out.println("[Error reading line in StreamDeRegles]" + e);
        }

        String[] head;
        head = line.split(";");
        regle = new Regle(head);
    }

    public Regle getRegle() {
        return regle;
    }

    public Regle getRegleSuivante() {
        String line;
        try{
            line = buffer.readLine();
            //System.out.println(line);
            String[] head;
            head = line.split(";");
            regle.setValeurs(head);
            return regle;
        }
        catch(Exception e){
            //System.out.println("[Error reading line] " + e);
            return null;
        }

    }

    @Override
    public String toString() {
        return "StreamDeRegles{" +
                "file=" + file +
                ", buffer=" + buffer +
                ", regle=" + regle +
                '}';
    }
}
