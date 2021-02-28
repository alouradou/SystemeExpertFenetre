// MaFenetre.java

package com.mco;

import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;
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
    private JCheckBox checkBox;

    private int width = 800;
    private int height = 600;


    private MI moteurInf = new MI("maBDF.txt","maBDR.csv");

    private DefaultListModel listModelFaitsDeduits = new DefaultListModel();




    public MaFenetre(String s) {
        super(s);
        setSize(width, height);

        setLayout(null);


        Init_Accessoires();


        Init_Regles();

        image();

        updateJList();

        Init_Faits();

        Init_Checkbox();

        setVisible(true);
    }

    public void Init_Accessoires() {
        trace = new JLabel();
        trace.setBounds( width-width/10-90, height-height/3+30, 200, 50);
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
                System.out.println("Sortie par le bouton Quitter");
                System.exit(0);
            }
        });

        // ajout des boutons ChAva et ChArr
        chava = new JButton("Chaînage Avant");
        chava.setBounds(width-width/10-90, height/2, 150, 30);
        chava.setFocusable(false);
        charr = new JButton("Chaînage Arrière");
        charr.setBounds(width-width/10-90, 30+height/2, 150, 30);
        charr.setFocusable(false);
        getContentPane().add(chava);
        getContentPane().add(charr);

        // ajout des 2 Listeners associés
        chava.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // lancer le chainage avant ...
                trace.setText("<html>Lancement chaînage<br>avant...</html>");
                System.out.println("Lancement chainage avant");
                moteurInf.chainageAvant();

                if (checkBox.isSelected()) // permet de choisir si l'action de chainage modifie le fichier
                    moteurInf.getBaseFaits().majFichier();

                updateJList();

            }
        });

        charr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // lancer le chainage arriere ...
                //trace.setText("<html>Lancement chaînage<br>arrière...</html>");
                System.out.println("Lancement chainage arriere");
                if (moteurInf.chainageArriere(saisie.getText()))
                    trace.setText("<html>Chaînage arrière<br>-> Conclusion vérifiée</html>");
                else trace.setText("<html>Chaînage arrière<br>-> Non vérifié</html>");;

                if (checkBox.isSelected()) // permet de choisir si l'action de chainage modifie le fichier
                    moteurInf.getBaseFaits().majFichier();

                updateJList();
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
        cb.addItem("- Base de Faits -");
        for (int i=0;i<moteurInf.getBaseFaits().getContenu().size();i++){
            cb.addItem(moteurInf.getBaseFaits().getContenu().get(i));
        }
        cb.setBounds(width/10, 40+height/10, 200, 50);
        cb.setFocusable(false);
        getContentPane().add(cb);

        // traitement d'un clic sur le bouton : définition d'un Listener/Ecouteur
        cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cBox = (JComboBox) e.getSource();

                // Display the selected item
                Object selected = cBox.getSelectedItem();
                if (selected!=null)
                    trace.setText("<html>Élément sélectionné<br>dans la base de faits<br>-> " + selected + "</html>");
            }
        });

        // Faire la même chose pour la liste des faits déduits avec une JList


        // Créer l'objet JList avec ses éléments
        lis = new JList(listModelFaitsDeduits);
        lis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lis.setFocusable(false);

        // Ajouter la JList dans un JScrollPane
        pane = new JScrollPane(lis);
        pane.setBounds(width/2, 50+height/10, 200, 100);
        JLabel faitsDeduits = new JLabel("Faits Déduits");
        faitsDeduits.setBounds(width/2, height/10, 200, 50);

        // Create an ActionListener for the JList component
        lis.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {     // la valeur de la sélection a changé
                    // Get the source of the component, which is our list
                    JList liste = (JList) e.getSource();
                    Object selected = liste.getSelectedValue();
                    if (selected!=null)
                        trace.setText("<html>Élément sélectionné<br>dans les faits déduits<br>-> " + selected + "</html>");
                }
            }
        });

        getContentPane().add(faitsDeduits);
        getContentPane().add(pane);

    }


    public void Init_Regles() {

        // Ajout d'une JTable

        // Intitulés des colonnes
        Vector<String> colonnes = new Vector<String>();
        /* colonnes.add("Nom"); colonnes.add("Prenom"); colonnes.add("Age");*/

        // Contenu de la table
        Vector<Vector<String>> donnees = new Vector<Vector<String>>();

        for (int i=0;i<moteurInf.getBaseRegles().getContenu().size();i++){
            Regle r = moteurInf.getBaseRegles().getContenu().get(i);
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
        table.setFocusable(false);
        panneau.setBounds(width/10, height-height/3, 200+4*width/10, height/3-50);
        JLabel TabTitre = new JLabel("Base de Règles");
        TabTitre.setBounds(width/10, height-height/3-50, 200, 50);
        getContentPane().add(TabTitre);
        getContentPane().add(panneau);

    }

    private void updateJList(){
        listModelFaitsDeduits.removeAllElements();
        for (int i=0;i<moteurInf.getBaseFaits().getContenu().size();i++) {
            listModelFaitsDeduits.addElement(moteurInf.getBaseFaits().getContenu().toArray()[i]);
        }
        //System.out.println("Updated table");
    }

    private void Init_Checkbox(){
        checkBox = new JCheckBox();
        checkBox.setBounds(width-width/10-90, height/2-45, 30, 30);
        checkBox.setFocusable(false);
        getContentPane().add(checkBox);

        checkBox.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (checkBox.isSelected())
                    trace.setText("<html>Sera enregistré dans<br>le fichier de la base<br>de faits</html>");
                else trace.setText("");
            }
        });

        JLabel checkBoxIndication = new JLabel();
        checkBoxIndication.setBounds(width-width/10-55, height/2-60, 180, 60);
        checkBoxIndication.setText("<html>Enregistrer<br>le chaînage</html>");
        getContentPane().add(checkBoxIndication);
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
