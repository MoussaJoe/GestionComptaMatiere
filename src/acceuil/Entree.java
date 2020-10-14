/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acceuil;

import Lister.Lister;
import compte.Compte;
import distribuer.Distribuer;
import fournisseur.EnregistrerFournisseur;
import inventaire.Inventaire;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import materiel.EnregistrerMateriel;
import mise_a_jour.MiseAjour;

/**
 *
 * @author Ouzy NDIAYE
 */
public class Entree extends Application {

    Scene scene;

    @Override
    public void start(Stage primaryStage) {

        //////////////////////////Instanciation///////////////////////
        VBox vb = new VBox();
        HBox hblab = new HBox();
        Label labBambey = new Label("Universite Alioune Diop de Bambey");
        HBox hbMenu = new HBox();
        MenuButton btnEnregistrer = new MenuButton("Enregistrer");
        Button btnLister = new Button("Lister");
        Button btnUpdate = new Button("Mettre à jour");
        Button btnDistribuer = new Button("Distribuer");
        Button btnInventaire = new Button("Inventaire");
        Button btndeconnect = new Button("Se Déconnecter");
        Button btncompte = new Button("Compte");
        btnDistribuer.setId("btn");
        btnEnregistrer.setId("btn");
        btnLister.setId("btn");
        btnUpdate.setId("btn");
        btnInventaire.setId("btn");
        btncompte.setId("btn");
        btndeconnect.setId("btn");

        Image img = new Image("img/acceuil.jpg");
        ImageView imgv = new ImageView(img);

        /////////////////////////Fin Instanciation////////////////////
        ////////////////barre du Menu////////////////////
        MenuItem menuItemMat = new MenuItem("Materiel");
        btnEnregistrer.getItems().add(menuItemMat);
        MenuItem menuItemFour = new MenuItem("Fournisseur");
        btnEnregistrer.getItems().add(menuItemFour);

        //****************les ID pour le css************************
        btnEnregistrer.setId("btnEnregistrer");
        labBambey.setId("labBambey");

        //////////Mettre les composants dans des conteneurs////////////////////////////////////
        hblab.getChildren().add(labBambey);
        hbMenu.getChildren().addAll(btnEnregistrer, btnLister, btnUpdate, btnDistribuer, btnInventaire, btncompte, btndeconnect);
        vb.getChildren().addAll(hblab, hbMenu, imgv);

        //*******************Mise en forme***********************************************
        hblab.setAlignment(Pos.CENTER);
        hblab.setMinHeight(200.0);
        hblab.setId("hb1");
        hbMenu.setAlignment(Pos.CENTER);
        vb.setAlignment(Pos.CENTER);

        labBambey.setTextFill(Paint.valueOf("#1760bf"));
        hbMenu.setMinHeight(100);
        btnEnregistrer.setTextFill(Paint.valueOf("#1760bf"));
        btnLister.setTextFill(Paint.valueOf("#1760bf"));
        btnUpdate.setTextFill(Paint.valueOf("#1760bf"));
        btnDistribuer.setTextFill(Paint.valueOf("#1760bf"));
        btnInventaire.setTextFill(Paint.valueOf("#1760bf"));
        btncompte.setTextFill(Paint.valueOf("#1760bf"));
        btndeconnect.setTextFill(Paint.valueOf("#1760bf"));

        //////////////Actions////////////////////
        menuItemMat.setOnAction((KeyEnvent) -> {

            EnregistrerMateriel enregistrerMateriel = new EnregistrerMateriel();
            enregistrerMateriel.start(primaryStage);

        });
        menuItemFour.setOnAction((KeyEnvent) -> {

            EnregistrerFournisseur enregistrerFournisseur = new EnregistrerFournisseur();
            enregistrerFournisseur.start(primaryStage);
        });

        btnLister.setOnAction((KeyEnvent) -> {
            Lister lister = new Lister();
            lister.start(primaryStage);
        });
        btncompte.setOnAction(e -> {
            Compte cpt = new Compte();
            cpt.start(primaryStage);
        });

        btndeconnect.setOnAction(e -> {
            Connection con = null;

            Statement st = null;
            ResultSet rs;

            try {

                try {
                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                } catch (Exception er_dv) {
                    System.out.println("Erreur driver " + er_dv.getMessage());
                }
                // ******la connection a la base
                try {
                    con = DriverManager.getConnection("jdbc:mysql://localhost/gestionComptaMatiere", "root", "");
                } catch (Exception er_con) {
                    System.out.println("Erreur de connection " + er_con.getMessage());
                }
                //*******le statement************
                try {
                    st = con.createStatement();
                } catch (SQLException er_st) {
                    System.out.println("erreur de Statement " + er_st.getMessage());
                }
                String requete = "UPDATE user set Etat=0 where Etat=1";
                int rst = con.createStatement().executeUpdate(requete);
                //  rs.close();
                st.close();
                con.close();
            } catch (Exception exc) {
                System.out.println("Erreur");
            }
            Acceuil acc = new Acceuil();
            acc.start(primaryStage);
        });

        btnDistribuer.setOnAction(e -> {
            Distribuer dis = new Distribuer();
            dis.start(primaryStage);
        });

        btnUpdate.setOnAction(e -> {
            MiseAjour update = new MiseAjour();
            update.start(primaryStage);
        });
        btnInventaire.setOnAction(e -> {
            Inventaire inv = new Inventaire();
            inv.start(primaryStage);
        });
        /////////////Fin Actions/////////////////

        //vb.getChildren().addAll(hbEnTete, hb);
        ///////////////////////Fin Méthodes//////////////////////////
        Scene scene = new Scene(vb, 1300, 700);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(Acceuil.class.getResource("style.css").toExternalForm());
        primaryStage.setTitle("GESTION COMPTABILITE MATIERE");
        primaryStage.show();
    }

     public static void main(String[] args) {
        launch(args);
    }
    /**
     * @param args the command line arguments
     */
}
