/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package materiel;

import acceuil.Acceuil;
import acceuil.Entree;
import fournisseur.EnregistrerFournisseur2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author Ouzy NDIAYE
 */
public class EnregistrerMateriel extends Application {

    Connection con = null;
    Statement st;
    ResultSet rs;
    PreparedStatement pst;
    PreparedStatement pst2;
    GenererRef gr = new GenererRef();
    DatePicker datePicker;
    String date;
    String serviceValue;
    String descriptionValue;
    String fournisseurValue;

    @Override
    public void start(Stage primaryStage) {
        String nomService;
        String description;
        String nomFournisseur;
        GridPane gp = new GridPane();
        HBox hBox = new HBox();
        GridPane gridPane = new GridPane();

        ///////////////////////Info Materiel de la table matériel////////////////
        /////////////***************Référence Matériel*******////////////////
        Label lbRef = new Label();
        lbRef.setText("Référence :");
        gp.setConstraints(lbRef, 0, 0);
        TextField tfRef = new TextField();
        tfRef.setText(gr.genererRef());
        tfRef.setEditable(false);
        gp.setConstraints(tfRef, 1, 0);
        //////////********************Fin Référence Matériel/////////////////
        ///////******************Libelle Matériel***************////////////////

        Label lbLibelle = new Label();
        lbLibelle.setText("Libellé :");
        gp.setConstraints(lbLibelle, 0, 1);
        TextField tfLibelle = new TextField();
        tfLibelle.setPromptText("Saisir le libellé");
        gp.setConstraints(tfLibelle, 1, 1);
        ////////////***************Fin Libellé************////////////////////

        //////////*****************Date Livraison*********/////////////////
        Label dateLiv = new Label();
        gp.setConstraints(dateLiv, 0, 2);
        datePicker = new DatePicker();
        dateLiv.setText("Date Livraison :");
        datePicker.setPromptText("Date de Livraison");
        gp.setConstraints(datePicker, 1, 2);
        //////////**************Fin Date Livraison*********/////////////////
        /////////***************Fin Info Matériel**********/////////////
        ///////////////////Info Etat//////////////////
        Label lbEtat = new Label();
        lbEtat.setText("Etat :");
        gp.setConstraints(lbEtat, 0, 3);
        TextField tfEtat = new TextField();
        tfEtat.setText("BON");
        tfEtat.setEditable(false);
        gp.setConstraints(tfEtat, 1, 3);
        //////////////////Fin Info Etat//////////////

        //////////***********************/////////////////
        //////////////////////////Info Service/////////////////////////
        ///////////////////***********Nom Service **********/////////////
        Label lbSerice = new Label();
        lbSerice.setText("Nom Service");
        gp.setConstraints(lbSerice, 0, 4);
        ComboBox cbServive = new ComboBox();
        cbServive.setPromptText("Nom Service");
        gp.setConstraints(cbServive, 1, 4);
        ////////////****************Connect BD************/////////////////////////
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/gestioncomptamatiere", "root", "");
            st = con.createStatement();
        } catch (Exception ex) {
            System.out.println("Erreur: " + ex);
        }
        ////////////*****************Fin Connect BD************/////////////////////

        /////////////////////////////////Requete select sur la table service/////////////////////////////////
        try {
            String requeteSelect = "select NomService from service ";
            rs = st.executeQuery(requeteSelect);

            while (rs.next()) {
                nomService = rs.getString("NomService");
                cbServive.getItems().add(nomService);
                cbServive.setOnAction((e) -> {
                    serviceValue = (String) cbServive.getValue();
                });

            }
        } catch (Exception e) {
            System.out.println("Erreur de requete");
        }
        ///////////////////////Fin Info Service///////////////////////////////
        /////////////////////////////////Fin Requete select sur la table service///////////////////////////
        //********************************************************************************************/////
        ///////////////////////////////////Type de Matériel///////////////////////////////////////////////
        Label lbTM = new Label();
        lbTM.setText("Type Matériel");
        gp.setConstraints(lbTM, 0, 5);
        ComboBox cbTM = new ComboBox();
        cbTM.setPromptText("Description");
        gp.setConstraints(cbTM, 1, 5);
        /////////////////////////////////Requete select sur la table typemateriel/////////////////////////////////
        try {
            String requeteSelect = "select Description from typemateriel ";
            rs = st.executeQuery(requeteSelect);

            while (rs.next()) {
                description = rs.getString("Description");
                cbTM.getItems().add(description);
                cbTM.setOnAction((e) -> {
                    descriptionValue = (String) cbTM.getValue();
                });

            }
        } catch (Exception e) {
            System.out.println("Erreur de requete");
        }
        /////////////////////////////////Fin Requete select sur la table typemateriel///////////////////////////
        ///////////////***********************////////////////////////
        ////////////////////////Info Fournisseur//////////////////////////////
        Label lbFournisseur = new Label();
        lbFournisseur.setText("Fournisseur");
        gp.setConstraints(lbFournisseur, 0, 6);
        ComboBox cbFournisseur = new ComboBox();
        cbFournisseur.setPromptText("Nom Fournisseur");
        gp.setConstraints(cbFournisseur, 1, 6);
        /////////////////////////////////Requete select sur la table fournisseur/////////////////////////////////
        try {
            String requeteSelect = "select NomFournisseur from fournisseur ";
            rs = st.executeQuery(requeteSelect);

            while (rs.next()) {
                nomFournisseur = rs.getString("NomFournisseur");
                cbFournisseur.getItems().add(nomFournisseur);
            }
            cbFournisseur.getItems().add("Autre Fournisseur");
            cbFournisseur.setOnAction((e) -> {
                if (cbFournisseur.getValue() != "Autre Fournisseur") {
                    fournisseurValue = (String) cbFournisseur.getValue();
                } else {
                    EnregistrerFournisseur2 enregistrerFournisseur = new EnregistrerFournisseur2();
                    enregistrerFournisseur.start(primaryStage);
                }

            });
            ///////////************Fin Fournisseur/*****************////////////////////

        } catch (Exception e) {
            System.out.println("Erreur de requete");
        }
        /////////////////////////////////Fin Requete select sur la table fournisseur///////////////////////////

        Button btnEnre = new Button("Enregistrer");
        Button btnRetour = new Button("Annuler");
        Button btnAcceuil = new Button("Acceuil");
        hBox.getChildren().addAll(btnEnre, btnRetour, btnAcceuil);
        hBox.setPadding(new Insets(10));
        hBox.setSpacing(10);
        gridPane.setConstraints(hBox, 1, 7);

        gp.setAlignment(Pos.CENTER);
        gp.setVgap(20);
        gp.setHgap(20);
        gp.setPadding(new Insets(10, 10, 10, 10));
        gp.getChildren().addAll(lbRef, lbLibelle, dateLiv, lbEtat, lbSerice, lbTM, lbFournisseur, tfRef, tfLibelle, tfEtat, datePicker, cbServive, cbTM, cbFournisseur, hBox);

        //******************Generer les setID pour la mise en forme****************
        lbRef.setId("lab");
        lbLibelle.setId("lab");
        dateLiv.setId("lab");
        lbSerice.setId("lab");
        lbTM.setId("lab");
        lbFournisseur.setId("lab");
        tfRef.setId("tf");
        tfLibelle.setId("tf");
        cbServive.setId("tf");
        cbTM.setId("tf");
        cbFournisseur.setId("tf");
        datePicker.setId("tf");
        lbEtat.setId("lab");
        tfEtat.setId("tf");
        //////////////////////////////////////CONTROLE DE SAISI////////////////////////////////////
        date = "" + datePicker.getValue();
        tfLibelle.setOnKeyTyped((KeyEvent e) -> {
            String ch = e.getCharacter();
            if ((ch.equals("1") || ch.equals("2") || ch.equals("3") || ch.equals("4") || ch.equals("5") || ch.equals("6") || ch.equals("7") || ch.equals("8") || ch.equals("9") || ch.equals("0"))) {

                {
                    e.consume();
                    java.awt.Toolkit.getDefaultToolkit().beep();
                }
            }

        });
        //********************controle pour le bouton Annuler********************************
        btnRetour.setOnAction((KeyEvent) -> {
            tfEtat.clear();
            tfLibelle.clear();
        });
        btnAcceuil.setOnAction((KeyEvent) -> {
            Entree entree = new Entree();
            entree.start(primaryStage);
        });

        //////////////////////////////////////INSERTION DAND LA BASE DE DONNEES//////////////////////////////
        btnEnre.setOnAction((KeyEvent) -> {
            date = "" + datePicker.getValue();
            if ((tfLibelle.getText().length() != 0) & (date.length() == 10)) {

                /////////////////////////////Insertion Type Matériel//////////////////////
                try {
//                    String annee = date.getChars(0, 0, dst, 0);
//                    String ch2 = datePicker.getEditor().getText().substring(6, 10);
//                    System.out.println(ch2);
                    String requeteMateriel = "insert into materiel (Reference,Libelle,DateLivraison,NomService,NomFournisseur,Description) values (?,?,?,?,?,?)";
                    pst = con.prepareStatement(requeteMateriel);
                    pst.setString(1, tfRef.getText());
                    pst.setString(2, tfLibelle.getText());
                    pst.setString(3, (datePicker.getEditor().getText()));
                    pst.setString(4, serviceValue);
                    pst.setString(5, fournisseurValue);
                    pst.setString(6, descriptionValue);
                    pst.execute();
                    pst.close();

                    if (!descriptionValue.substring(0, 11).equals("CONSOMMABLE")) {
                        String requeteInventaire = "insert into inventairemateriel (Annee,Reference,Etat) values (?,?,?)";
                        pst2 = con.prepareStatement(requeteInventaire);
                        pst2.setString(1, (datePicker.getEditor().getText(6, 10)));
                        pst2.setString(2, tfRef.getText());
                        pst2.setString(3, tfEtat.getText());
                        pst2.execute();
                        pst2.close();
                        System.out.println("Données bien enregistrées");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information");
                        alert.setContentText("Données bien enregistrées");
                        alert.showAndWait();
                    } else {
                        System.out.println("Données bien enregistrées");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information");
                        alert.setContentText("Données bien enregistrées");
                        alert.showAndWait();
                    }

                } catch (Exception ex_mat) {
                    System.out.println("Error records materiel");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Information");
                    alert.setContentText("Erreur d'enregistrement \n Veuillez réessayer!!");
                    alert.showAndWait();
                }
                EnregistrerMateriel enregistrerMateriel = new EnregistrerMateriel();
                enregistrerMateriel.start(primaryStage);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Information");
                alert.setContentText("Vérifier le(s) champs 'LIBELLE' ou 'DATE DE LIVRAISON'\n Ils ne peuvent etre vide!!!");
                alert.showAndWait();
            }

            /////////////////////////////Insertion Type Matériel//////////////////////
        });
        gp.setId("bp1");
        Scene scene = new Scene(gp, 1300, 700);
        scene.getStylesheets().add(Acceuil.class.getResource("style.css").toExternalForm());
        primaryStage.setTitle("Enregistrer Matériel");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
}
