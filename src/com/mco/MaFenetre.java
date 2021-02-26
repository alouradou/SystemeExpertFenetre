// MaFenetre.java

package com.mco;

import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*; // pour le Listener de la JList

public class MaFenetre extends JFrame {

    private JLabel trace;
    private JButton quit;
    private JButton chava;
    private JButton charr;
    private JLabel labcb;
    private JComboBox cb;
    private JList lis;
    private JScrollPane pane;
    private JTable table;
    private JScrollPane panneau;
    private JLabel TabTitre;
    private JTextField saisie;

    private int width = 800;
    private int height = 500;


    private MI moteurInf = new MI("maBDF.txt","maBDR.csv");
    private ArrayList<String> contenuBDF;
    private ArrayList<Regle> contenuBDR;

    private DefaultListModel listModelFaitsDeduits;



    public MaFenetre(String s) {
        super(s);
        setSize(width, height);

        setLayout(null);

        BDF maBDF = new BDF("maBDF.txt");
        contenuBDF = maBDF.getContenu();

        BDR maBDR = new BDR("maBDR.csv");
        contenuBDR = maBDR.getContenu();

        Init_Accessoires();


        Init_Regles();

        image();

        listModelFaitsDeduits = new DefaultListModel();
        contenuBDF = moteurInf.getBaseFaits().getContenu();
        for (int i=0;i<contenuBDF.size();i++) {
            listModelFaitsDeduits.addElement(contenuBDF.toArray()[i]);
        }
        updateJList();

        Init_Faits();

        setVisible(true);
    }

    public void Init_Accessoires() {
        trace = new JLabel();
        trace.setBounds(width/10, 70+height/10, 200, 50);
        getContentPane().add(trace);

        quit = new JButton("Quitter");
        // positionnement et dimensionnement manuel du bouton
        quit.setBounds(width-width/10-20, height-height/10-20, 80, 30);
        getContentPane().add(quit);

        // traitement d'un clic sur le bouton : définition d'un Listener/Ecouteur
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // quand on a clique sur le bouton "Quitter", on sort du programme
                System.out.println("Sortie du programme");
                System.exit(0);
            }
        });

        // ajout des boutons ChAva et ChArr
        chava = new JButton("Chainage Avant");
        chava.setBounds(width-width/10-90, height/2, 150, 30);
        charr = new JButton("Chainage Arriere");
        charr.setBounds(width-width/10-90, 30+height/2, 150, 30);
        getContentPane().add(chava);
        getContentPane().add(charr);

        // ajout des 2 Listeners associés
        chava.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // lancer le chainage avant ...
                trace.setText("Lancement chainage avant");
                System.out.println("Lancement chainage avant sur moteurInf : " + moteurInf);
                moteurInf.chainageAvant();
                contenuBDF = moteurInf.getBaseFaits().getContenu();
                for (int i=0;i<contenuBDF.size();i++) {
                    listModelFaitsDeduits.addElement(contenuBDF.toArray()[i]);
                }
                updateJList();

            }
        });

        charr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // lancer le chainage arriere ...
                trace.setText("Lancement chainage arriere");
                System.out.println("Lancement chainage arriere");
                MI moteurInf = new MI("maBDF.txt","maBDR.csv");
                System.out.println(moteurInf);
                System.out.println(saisie.getText());
                System.out.println(moteurInf.chainageArriere(saisie.getText()));
            }
        });

        // gérer la saisie du fait utilisé dans le chainage arriere
        saisie = new JTextField(80);
        saisie.setBounds(width-width/10-90, height-height/3, 150, 30);
        getContentPane().add(saisie);
        saisie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField tf = (JTextField) e.getSource();
                // afficher la saisie
                trace.setText("Saisie = " + tf.getText());
            }
        });

    }

    public void Init_Faits() {

        // Gestion de la Base de faits et des faits déduits

        labcb = new JLabel("Base de Faits");
        labcb.setBounds(width/10, height/10, 200, 50);
        getContentPane().add(labcb);

        /*String[] bookTitles = new String[] {"Effective Java", "Head First Java", "Thinking in Java", "Java for Dummies"};*/
        cb = new JComboBox();
        for (int i=0;i<contenuBDF.size();i++){
            cb.addItem(contenuBDF.get(i));
        }
        cb.setBounds(width/10, 40+height/10, 200, 50);
        getContentPane().add(cb);

        // traitement d'un clic sur le bouton : définition d'un Listener/Ecouteur
        cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cBox = (JComboBox) e.getSource();

                // Display the selected item
                Object selected = cBox.getSelectedItem();
                trace.setText("Selected Item = " + selected);
                String command = e.getActionCommand();
                System.out.println("Action Command = " + command);
            }
        });

        // Faire la même chose pour la liste des faits déduits avec une JList


        // Créer l'objet JList avec ses éléments
        lis = new JList(listModelFaitsDeduits);
        lis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Ajouter la JList dans un JScrollPane
        pane = new JScrollPane(lis);
        pane.setBounds(width/2, 50+height/10, 200, 50);
        JLabel FaitsDeduits = new JLabel("Faits Deduits");
        FaitsDeduits.setBounds(width/2, height/10, 200, 50);

        // Create an ActionListener for the JList component
        lis.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {     // la valeur de la sélection a changé
                    // Get the source of the component, which is our list
                    JList liste = (JList) e.getSource();
                    Object selected = liste.getSelectedValue();
                    trace.setText("Element choisi = " + selected);
                }
            }
        });

        getContentPane().add(FaitsDeduits);
        getContentPane().add(pane);

    }


    public void Init_Regles() {

        // Ajout d'une JTable

        // Intitulés des colonnes
        Vector<String> colonnes = new Vector<String>();
        /* colonnes.add("Nom"); colonnes.add("Prenom"); colonnes.add("Age");*/

        // Contenu de la table
        Vector<Vector<String>> donnees = new Vector<Vector<String>>();

        for (int i=0;i<contenuBDR.size();i++){
            Regle r = contenuBDR.get(i);
            System.out.println(i);
            if (i == 0){
                String[] schema = r.getSchema();
                for (int j=0;j<schema.length;j++){
                    colonnes.add(schema[j]);
                }
            }
            String[] valeurs = r.getValeurs();
            Vector<String> donnee_i = new Vector<String>();
            for (int j=0;j<valeurs.length;j++){
                donnee_i.add(valeurs[j]);
            }
            donnees.add(donnee_i);
        }

        // Création de la table
        JTable table = new JTable(donnees, colonnes);
        JScrollPane panneau = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        panneau.setBounds(width/10, height-height/3, 200+4*width/10, height/3-50);
        JLabel TabTitre = new JLabel("Base de Regles");
        TabTitre.setBounds(width/10, height-height/3-50, 200, 50);
        getContentPane().add(TabTitre);
        getContentPane().add(panneau);

    }

    private void updateJList(){
        Object[] data = contenuBDF.toArray();
        listModelFaitsDeduits.removeAllElements();
        for (int i=0;i<contenuBDF.size();i++) {
            listModelFaitsDeduits.addElement(data[i]);
        }
        System.out.println("Updated table");
    }

    public void image(){
        // URL de l'image
        String imgUrl="centrale-logo.png";	// par exemple
        ImageIcon icone = new ImageIcon(imgUrl);

        // Création d'un JLabel avec un alignement gauche
        JLabel image = new JLabel(icone, JLabel.CENTER);
        image.setBounds(width-200,-40,200,200);	// par exemple
        //ajoute le JLabel a la JFrame
        getContentPane().add(image);
    }

}
