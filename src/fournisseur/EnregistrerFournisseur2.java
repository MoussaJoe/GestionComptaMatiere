package fournisseur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.sql.PreparedStatement;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import acceuil.Acceuil;
import acceuil.Entree;
import javafx.scene.layout.HBox;
import materiel.EnregistrerMateriel;

public class EnregistrerFournisseur2 extends Application {

    Scene scene;
    Connection con;
    Statement st;
    ResultSet rs;
    PreparedStatement pst;
    DatePicker datePicker;

    @Override
    public void start(Stage primaryStage) {

        //////////////////////Instanciation////////
        BorderPane bpFournisseur = new BorderPane();
        GridPane gp = new GridPane();

///////////////////////Info Fournisseur de la table fournisseur////////////////
        Label lbFournisseur = new Label();
        lbFournisseur.setText("Nom Fournisseur");
        TextField tfFournisseur = new TextField();
        tfFournisseur.setPromptText("Saisir le nom du fournisseur");
        //////////***********************/////////////////
        Label lbAdr = new Label();
        lbAdr.setText("Adresse");
        TextField tfAdr = new TextField();
        tfAdr.setPromptText("Saisir l'adresse");
        //////////***********************/////////////////
        Label lbNumTel = new Label();
        lbNumTel.setText("Numéro Téléphone");
        TextField tfNumTel = new TextField();
        tfNumTel.setPromptText("Saisir le téléphone");
        //////////***********************/////////////////
////////////////////////////Boutton/////////////////////////
        Button btnEnre = new Button();
        Button btnRetour = new Button();
        btnRetour.setText("Annuler");
        btnEnre.setText("Continuer l'enregistrement");
        /////////////////////Fin Instanciation///////////
        ////////////*****************************************************///////////
        ////////////////////Materiel///////////////////////////////

        //////////////////Fin Type Matériel////////////////////////////////
        //////////////////////////////////////////Fournisseur/////////////////////////////////////////
        gp.setConstraints(lbFournisseur, 0, 0);
        gp.setConstraints(lbAdr, 0, 1);
        gp.setConstraints(lbNumTel, 0, 2);
        gp.setConstraints(tfFournisseur, 1, 0);
        gp.setConstraints(tfAdr, 1, 1);
        gp.setConstraints(tfNumTel, 1, 2);
        gp.setConstraints(btnEnre, 1, 3);
        //gp.setConstraints(btnRetour, 1, 4);
        HBox hBox = new HBox();
        GridPane gridPane = new GridPane();
        Button btnAcceuil = new Button("Acceuil");
        hBox.getChildren().addAll(btnRetour, btnAcceuil);
        hBox.setPadding(new Insets(10));
        hBox.setSpacing(10);
        gridPane.setConstraints(hBox, 1, 4);
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(10);
        gp.setHgap(10);
        gp.setPadding(new Insets(10, 10, 10, 10));
        gp.getChildren().addAll(lbFournisseur, lbAdr, lbNumTel, tfFournisseur, tfAdr, tfNumTel, btnEnre, hBox);
        bpFournisseur.setCenter(gp);
        bpFournisseur.setCenter(gp);

        //*********************gererer setId pour la mise en forme*******************************
        lbFournisseur.setId("lab");
        lbAdr.setId("lab");
        lbNumTel.setId("lab");
        tfFournisseur.setId("tf");
        tfAdr.setId("tf");
        tfNumTel.setId("tf");

        //////////////////Fin Méthodes///////////////
        ////////*******************************************************/////////////////////
        /////////////////Action///////////////////////////////////////////////
        btnRetour.setOnAction((KeyEvent) -> {
            tfAdr.clear();
            tfFournisseur.clear();
            tfNumTel.clear();
        });
        btnAcceuil.setOnAction((KeyEvent) -> {
            Entree acc = new Entree();
            acc.start(primaryStage);
        });
        btnEnre.setOnAction((KeyEvent) -> {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost/gestioncomptamatiere", "root", "");
                st = con.createStatement();
            } catch (Exception ex) {
                System.out.println("Erreur: " + ex);
            }

///////////////////////////////Insertion Fournisseur//////////////////////
            if ((tfFournisseur.getText().length() != 0) & (tfAdr.getText().length() != 0) & (tfNumTel.getText().length() != 0)) {
                try {
                    String requeteFournisseur = "insert into fournisseur (NomFournisseur,Adresse,NumTel) values (?,?,?)";
                    pst = con.prepareStatement(requeteFournisseur);
                    pst.setString(1, tfFournisseur.getText().toUpperCase());
                    pst.setString(2, tfAdr.getText());
                    pst.setString(3, tfNumTel.getText());
                    pst.execute();
                    pst.close();
                } catch (Exception ex_Fourn) {
                    System.out.println("Error records fournisseur");

                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setContentText("Données bien enregistré");
                alert.showAndWait();
                EnregistrerMateriel enregistrerMateriel = new EnregistrerMateriel();
                enregistrerMateriel.start(primaryStage);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("INFORMATION");
                alert.setHeaderText("Vérifier le(s) champs");
                alert.setContentText("Vérifier le(s) champs 'NOM FOURNISSEUR' ou 'ADRESSE' ou 'NUMERO DE TELEPHONE'\n Ils ne peuvent etre vide!!!");
                alert.showAndWait();
            }

        });
        tfFournisseur.setOnKeyTyped((KeyEvent e) -> {
            String ch = e.getCharacter();
            if ((ch.equals("1") || ch.equals("2") || ch.equals("3") || ch.equals("4") || ch.equals("5") || ch.equals("6") || ch.equals("7") || ch.equals("8") || ch.equals("9") || ch.equals("0"))) {

                {
                    e.consume();
                    java.awt.Toolkit.getDefaultToolkit().beep();
                }
            }

        });
        tfNumTel.setOnKeyTyped((KeyEvent e) -> {
            String ch = e.getCharacter();
            if (!(ch.equals("1") || ch.equals("2") || ch.equals("3") || ch.equals("4") || ch.equals("5") || ch.equals("6") || ch.equals("7") || ch.equals("8") || ch.equals("9") || ch.equals("0"))) {

                {
                    e.consume();
                    java.awt.Toolkit.getDefaultToolkit().beep();
                }
            }

        });
        tfNumTel.setOnMouseExited(e -> {
            String ch2 = tfNumTel.getText().substring(0, 2);
            if (!(ch2.equals("77") || ch2.equals("78") || ch2.equals("76") || ch2.equals("70") || ch2.equals("33") || ch2.equals("30"))) {
                Alert err = new Alert(Alert.AlertType.ERROR);
                err.setTitle("Erreur Numéro Téléphone ");
                err.setHeaderText("Vérifier les 2 premiers chiffres");
                err.setContentText("Le numéro de téléphone doit commencer soit par: \n77, 78, 76, 70, 33, 30");
                err.showAndWait();
            } else if (tfNumTel.getText().length() != 9) {
                Alert err = new Alert(Alert.AlertType.ERROR);
                err.setTitle("Erreur Numéro Téléphone ");
                err.setHeaderText("Vérifier la longueur du numéro");
                err.setContentText("La longueur du numéro de téléphone doit etre égal à de 9 chiffres");
                err.showAndWait();
            }

        });
        ////////////////Fin Action////////////////////////////////////////
        gp.setLayoutY(10);
        bpFournisseur.setId("bp");
        scene = new Scene(bpFournisseur, 1300, 750);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(Acceuil.class.getResource("style.css").toExternalForm());
        primaryStage.setTitle("Enregistrer Fournisseur");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
