/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acceuil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 *
 * @author Moussa Joseph Sarr
 */
public class Acceuil extends Application {

    //******initialisation pour se connecter
    Connection con;
    Statement st;
    ResultSet rs;

    @Override
    public void start(Stage primaryStage) {
        // *************Initialisation*******************************************
        VBox vb = new VBox();   // c'est le conteneur le plus haut qui contient tous autres conteneurs
        VBox vb0 = new VBox();
        BorderPane pane = new BorderPane();

        HBox hb1 = new HBox(); // c'est le conteneur de l'entete image et titre
        Image img1 = new Image("img/logo.gif");
        //Image img2 = new Image("img/gestion-d-entreprise.jpg");
        ImageView imgLogo1 = new ImageView(img1);
        // ImageView imgfond = new ImageView(img2);
        Label labBambey = new Label("   Université Alioune Diop de Bambey   ");
        ImageView imgLogo2 = new ImageView(img1);

        HBox hb2 = new HBox();      // c'est le conteneur du label de gstion compta
        Label labgcm = new Label("Gestion de Comptabilité de Matière");

        HBox hb3 = new HBox();            // le HBox pour contenir la ligne de l'indentifiant
        Label labId = new Label("Identifiant :    ");
        TextField tfId = new TextField();

        HBox hb4 = new HBox();       ///le HBox contenent le password
        Label labpasswd = new Label("Mot de Passe : ");
        PasswordField passwd = new PasswordField();

        HBox hb5 = new HBox();                 // le HBox pour le bouton connecter
        Button btnConnect = new Button("Se Connecter");
        btnConnect.setId("btn");

        //  ****************************Ajout composant******************************************
        hb1.getChildren().addAll(imgLogo1, labBambey, imgLogo2);
        hb2.getChildren().add(labgcm);
        hb3.getChildren().addAll(labId, tfId);
        hb4.getChildren().addAll(labpasswd, passwd);
        hb5.getChildren().add(btnConnect);
        vb0.getChildren().addAll(hb3, hb4, hb5);
        pane.setCenter(vb0);

        vb.getChildren().addAll(hb1, hb2, pane);

        vb0.setMinHeight(200);
        vb0.setMinWidth(800);

        //*****************************Mise en Forme******************************************************
        //centrer toutes les lignes de la page
        hb1.setAlignment(Pos.CENTER);
        hb2.setAlignment(Pos.CENTER);
        hb3.setAlignment(Pos.BOTTOM_CENTER);
        hb4.setAlignment(Pos.BOTTOM_CENTER);
        hb5.setAlignment(Pos.CENTER);
        //la generation d'ID pour le css
        vb0.setId("bit");
        hb1.setId("hb1");
        hb2.setId("hb2");
        //hb1.setMinHeight(77.0); hb1.setMinWidth(600.0);
        imgLogo1.setFitHeight(150);
        imgLogo1.setFitWidth(150);
        imgLogo2.setFitHeight(150);
        imgLogo2.setFitWidth(150);

        //***pour les trois label jusqu'a identifiant
        labBambey.setId("labBambey");
        labId.setId("lab");
        labpasswd.setId("lab");
        tfId.setId("tf");
        tfId.setId("tf");
        passwd.setId("tf");
        labgcm.setFont(Font.font("Times New Roman", FontWeight.BOLD, 60.0));
        hb1.setSpacing(20);

        tfId.setPromptText("identifiant");
        tfId.setAlignment(Pos.CENTER);
        hb3.setMinHeight(150.0);
        hb1.setMinWidth(600.0);
        hb4.setMinHeight(60);
        pane.setMinSize(1000, 1000);

        //****pour mot de pass
        passwd.setPromptText("mot de passe");
        passwd.setAlignment(Pos.CENTER);
        //vb.setSpacing(20);

        //****pour le bouton se connecter
        hb5.setMinHeight(150.0);

        labpasswd.setTextFill(Paint.valueOf("#1760bf"));
        labBambey.setTextFill(Paint.valueOf("#1760bf"));
        labId.setTextFill(Paint.valueOf("#1760bf"));
        labgcm.setTextFill(Paint.valueOf("#1760bf"));
        btnConnect.setTextFill(Paint.valueOf("#1760bf"));

        //****************traitement sur le bouton seconnecter avec la base*******************************
        btnConnect.setOnAction(e -> {
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

//                rs = st.executeQuery("select * from user");
//                rs.next();

//                if (tfId.getText().equals(rs.getString(1)) && passwd.getText().equals(rs.getString(5))) {
                   // String requete = "UPDATE user set Etat=1 where login='" + rs.getString(1) + "'";
                   // int rst = con.createStatement().executeUpdate(requete);
                    Entree entree = new Entree();
                    entree.start(primaryStage);
////                } else {
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Information");
//                    alert.setContentText("login et/ou mot de passe incorrect");
//                    alert.showAndWait();
//                }
//                rs.close();
//                st.close();
//                con.close();
            } catch (Exception exc) {
                System.out.println("Erreur");
            }
        });
        //*****************************Fin du traitement du bouton Seconnecter************************************

        Scene scene = new Scene(vb, 1300, 700);
        scene.getStylesheets().add(Acceuil.class.getResource("style.css").toExternalForm());
        primaryStage.setTitle("Application Gestion Comptabilite Matiere");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
