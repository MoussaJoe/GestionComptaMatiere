/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compte;

import acceuil.Acceuil;
import acceuil.Entree;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author HP
 */
public class Compte extends Application {

    Scene scene;
    Connection con;
    Statement st;
    ResultSet rs;
    PreparedStatement pst;
    PreparedStatement pst2;
    private FileChooser fileChooser;
    private File fichier;
    private Desktop desktop = Desktop.getDesktop();
    private ImageView imageView;
    private Image image;
    private ImageView imageViewDefault;
    private Image imageDefault;
    private FileInputStream fileInputStream;

    @Override
    public void start(Stage primaryStage) {
        //////////////////////Instanciation////////
        String login;
        AnchorPane anchorPane = new AnchorPane();
        HBox hBox = new HBox();
        HBox hBoxDefaultImg = new HBox();
        GridPane gp = new GridPane();
        Label lbLogin = new Label();
        TextField tfLogin = new TextField();
        Label lbPrenom = new Label();
        TextField tfPrenom = new TextField();
        Label lbNom = new Label();
        TextField tfNom = new TextField();
        Label lbEmail = new Label();
        TextField tfEmail = new TextField();
        Label lbPassword = new Label();
        PasswordField passwordField = new PasswordField();
        Label lbConfirmPwd = new Label();
        PasswordField confirmPwdField = new PasswordField();
        TextArea textArea = new TextArea();
        ////////////****************Connect BD************/////////////////////////
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/gestioncomptamatiere", "root", "");
            st = con.createStatement();
        } catch (Exception ex) {
            System.out.println("Erreur: " + ex);
        }
        ////////////*****************Fin Connect BD************/////////////////////

        ///////////////////////Info Compte de la table user////////////////
        /////////////***************Login*******////////////////
        ////////////////***********Requete Select**********/////////////
        try {
            String requeteSelect = "select Login,Prenom,Nom,Email from user ";
            pst = con.prepareStatement(requeteSelect);
            rs = pst.executeQuery();
            rs.next();
            login = rs.getString("Login");
            lbLogin.setText("Nom d'utilisateur :");
            gp.setConstraints(lbLogin, 0, 0);
            tfLogin.setText(rs.getString("Login"));
            gp.setConstraints(tfLogin, 1, 0);
            //////////********************Fin Login/////////////////
            ///////******************Prénom***************////////////////
            lbPrenom.setText("Prénom :");
            gp.setConstraints(lbPrenom, 0, 1);
            tfPrenom.setText(rs.getString("Prenom"));
            gp.setConstraints(tfPrenom, 1, 1);
            ////////////***************Fin Prénom************////////////////////

            /////////////////// Nom//////////////////
            lbNom.setText("Nom :");
            gp.setConstraints(lbNom, 0, 2);

            tfNom.setText(rs.getString("Nom"));
            gp.setConstraints(tfNom, 1, 2);
            //////////////////***********Fin Nom**************///////////////
            ///////******************Email***************////////////////
            lbEmail.setText("E-mail :");
            gp.setConstraints(lbEmail, 0, 3);
            tfEmail.setText(rs.getString("Email"));
            gp.setConstraints(tfEmail, 1, 3);

            /////////////************ImageView********////////////////
//            imageViewDefault.setFitWidth(200);
//            imageViewDefault.setFitHeight(200);
//            imageViewDefault.setPreserveRatio(true);
//            hBoxDefaultImg.getChildren().add(imageViewDefault);
//            hBoxDefaultImg.setLayoutX(530);
//            hBoxDefaultImg.setLayoutY(180);
//            hBoxDefaultImg.getChildren().add(imageViewDefault);
            /////////////************Fin ImageView********////////////////
            ///////******************Fin Email***************////////////////
            /////////////////New Password///////////////////////////////////////
            lbPassword.setText("Nouveau mot de passe :");
            gp.setConstraints(lbPassword, 0, 4);
            passwordField.setPromptText("Votre mot de passe");
            gp.setConstraints(passwordField, 1, 4);
            ////////////////*********Fin Password************/////////////
            /////////////////Confirm Password///////////////////////////////////////
            lbConfirmPwd.setText("Confirmer mot de passe :");
            gp.setConstraints(lbConfirmPwd, 0, 5);
            confirmPwdField.setPromptText("Votre mot de passe");
            gp.setConstraints(confirmPwdField, 1, 5);
            ////////////////*********Zone de texte***********/////////////
            textArea.setPromptText("Importer une image");
            textArea.setPrefSize(300, 50);
            textArea.setEditable(true);
            gp.setConstraints(textArea, 1, 6);
            pst.execute();
            pst.close();
            rs.close();
            ///////////////*********Fin zone de texte********//////////
            ////////////////*********Fin Confirm Password************/////////////

            ///////////////**********************Fin Requete Select*************/////////////
            ///////////////********Fichier********//////////
            fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new ExtensionFilter("Fichiers Images", "*.jpg", "*.png", "*.gif")
            );
            Button btnBrowser = new Button("Parcourir");
            btnBrowser.setOnAction(e -> {
                //Séléction d'un fichier////////
                fichier = fileChooser.showOpenDialog(primaryStage);
                if (fichier != null) {
                    //desktop.open(fichier);

                    textArea.setText(fichier.getAbsolutePath());
                    image = new Image(fichier.toURI().toString(), 200, 200, true, true);//Image avec le chemin ,la largeur,la hauteur
                    //image = new Image("mise_a_jour/materiel.jpg");
                    imageView = new ImageView(image);
                    imageView.setFitWidth(200);
                    imageView.setFitHeight(200);
                    imageView.setPreserveRatio(true);
                    hBox.getChildren().add(imageView);

                }
            });
            ////////////**************Fin Fichier************////////////

            Button btnEnre = new Button("Enregistrer");
            Button btnRetour = new Button("Annuler");
            Button btnAcceuil = new Button("Acceuil");
            HBox hBox1 = new HBox();
            GridPane gridPane = new GridPane();
            hBox1.getChildren().addAll(btnEnre, btnRetour, btnAcceuil);
            hBox1.setPadding(new Insets(10));
            hBox1.setSpacing(10);
            gridPane.setConstraints(hBox1, 1, 7);

//            gp.setConstraints(btnRetour, 1, 7);
//            gp.setConstraints(btnEnre, 1, 8);
            gp.setConstraints(btnBrowser, 2, 6);
            //******************Generer les setID pour la mise en forme****************
            lbLogin.setId("labForm");
            lbPassword.setId("labForm");
            lbPrenom.setId("labForm");
            lbEmail.setId("labForm");
            lbConfirmPwd.setId("labForm");
            lbNom.setId("labForm");
            tfLogin.setId("tfForm");
            tfPrenom.setId("tfForm");
            tfNom.setId("tfForm");
            tfEmail.setId("tfForm");
            passwordField.setId("tfForm");
            confirmPwdField.setId("tfForm");
            textArea.setId("tfForm");

            gp.setAlignment(Pos.CENTER_LEFT);
            gp.setLayoutY(170);
            anchorPane.setId("compte");

            gp.setVgap(20);
            gp.setHgap(20);
            gp.setPadding(new Insets(10, 10, 10, 10));
            hBox.setLayoutX(550);
            hBox.setLayoutY(180);
            gp.getChildren().addAll(lbLogin, lbPrenom, lbEmail, lbNom, lbPassword, lbConfirmPwd, tfLogin, tfPrenom, tfNom, tfEmail, passwordField, confirmPwdField, btnBrowser, textArea, hBox1);
            anchorPane.getChildren().addAll(hBox, gp, hBoxDefaultImg);
            ///////////////////////////TRAITEMENT////////////////////////////////////
            ///////////Requete///////////////////////////////////////////////
            btnEnre.setOnAction((KeyEvent) -> {
                if (passwordField.getText().equals(confirmPwdField.getText()) /*&& tfLogin.getText().length() != 0 && tfPrenom.getText().length() != 0 && tfNom.getText().length() != 0 && tfEmail.getText().length() != 0 && passwordField.getText().length() != 0*/) {

                    try {
                        String requeteCompte = "update user set Login=?,Prenom=?,Nom=?,email=?,Password=?,Image=? where Login='" + login + "'";
                        pst = con.prepareStatement(requeteCompte);
                        pst.setString(1, tfLogin.getText());
                        pst.setString(2, tfPrenom.getText());
                        pst.setString(3, tfNom.getText());
                        pst.setString(4, tfEmail.getText());
                        pst.setString(5, passwordField.getText());
                        fileInputStream = new FileInputStream(fichier);
                        pst.setBinaryStream(6, (InputStream) fileInputStream);
                        pst.execute();
                        pst.close();

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information");
                        alert.setContentText("Modifications effectuées!!");
                        alert.showAndWait();
                        tfLogin.setPromptText("Votre nom d'utilisateur");
                        tfPrenom.setPromptText("Votre prénom");
                        tfNom.setPromptText("votre nom");
                        tfEmail.setPromptText("Votre e-mail");
                        passwordField.setPromptText("Nouveau mot de passe");

                        confirmPwdField.setPromptText("Confirmer mot de passe");
                    } catch (Exception e) {
                        System.out.println("erreur requete");
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setContentText("Vérifiez les mots de passe et/ou veillez à ce que aucun champs ne soit vide");
                    alert.showAndWait();
                }
            });

            btnRetour.setOnAction((KeyEvent) -> {
                tfLogin.clear();
                tfPrenom.clear();
                tfNom.clear();
                tfEmail.clear();
                passwordField.clear();
                confirmPwdField.clear();
            });
            btnAcceuil.setOnAction((KeyEvent) -> {
                Entree entree = new Entree();
                entree.start(primaryStage);
            });

        } catch (Exception e) {
            System.out.println("Erreur de requete");
        }
        ////////////////***********Fin Requete Select**********/////////////
        ////////////////Fin Requete///////////////////////////////////
        Scene scene = new Scene(anchorPane, 1300, 700);

        scene.getStylesheets().add(Acceuil.class.getResource("style.css").toExternalForm());
        primaryStage.setTitle("Compte");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
