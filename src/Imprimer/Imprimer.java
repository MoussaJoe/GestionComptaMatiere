/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Imprimer;

import Lister.Materiels;
import acceuil.Acceuil;
import acceuil.Entree;
import distribuer.Distribuer;
import static distribuer.Distribuer.DateAutomatik;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author Moussa Joseph Sarr
 */
public class Imprimer extends Application {

    Connection con = null;
    Statement st;
    ResultSet rs, r;
    String serviceValue, nomService;
    public Materiels mat;

    private FileChooser fileChooser;
    private File fichier;
    private final Desktop desktop = Desktop.getDesktop();

    @Override
    public void start(Stage primaryStage) {
        ///************************fichier****************************
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Fichiers Textes", "*.docx", "*.pdf", "*.doc")
        );

        GridPane gp = new GridPane();
        BorderPane bp = new BorderPane();

        Label labNomService = new Label("Nom Service : ");
        Label labNumFacture = new Label("Numero Facture :");
        Label labNomFacture = new Label("Nom Fichier");
        ComboBox cbService = new ComboBox();
        TextField tfNumFacture = new TextField();
        TextField tfNomFacture = new TextField();
        Button btnRetour = new Button("Annuler");
        Button btnEnr = new Button("Enregistrer");
        cbService.setPromptText("service beneficiaire");
        tfNumFacture.setPromptText("saisir numero de la facture");
        tfNomFacture.setPromptText("Saisir le nom du fichier");

        gp.setConstraints(labNomService, 0, 0);
        gp.setConstraints(cbService, 1, 0);
        gp.setConstraints(labNumFacture, 0, 1);
        gp.setConstraints(tfNumFacture, 1, 1);
        gp.setConstraints(labNomFacture, 0, 2);
        gp.setConstraints(tfNomFacture, 1, 2);
//        gp.setConstraints(btnEnr, 1, 3);
//        gp.setConstraints(btnRetour, 1, 4);
        HBox hBox = new HBox();
        GridPane gridPane = new GridPane();
        Button btnAcceuil = new Button("Acceuil");
        hBox.getChildren().addAll(btnEnr, btnRetour, btnAcceuil);
        hBox.setPadding(new Insets(10));
        hBox.setSpacing(10);
        gridPane.setConstraints(hBox, 1, 3);
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(10);
        gp.setHgap(10);
        gp.setPadding(new Insets(10, 10, 10, 10));

        //*********************gererer setId pour la mise en forme*******************************
        labNomService.setId("lab");
        labNumFacture.setId("lab");
        labNomFacture.setId("lab");
        cbService.setId("tf");
        tfNumFacture.setId("tf");
        tfNomFacture.setId("tf");

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
                cbService.getItems().add(nomService);
                cbService.setOnAction((e) -> {
                    serviceValue = (String) cbService.getValue();
                });

            }
        } catch (Exception e) {
            System.out.println("Erreur de requete sur service");
        }
        ///////////////////////Fin Info Service///////////////////////////////
        /////////////////////////////////Fin Requete select sur la table service///////////////////////////

        tfNumFacture.setOnKeyTyped((KeyEvent e) -> {
            String ch = e.getCharacter();
            if (!(ch.equals("1") || ch.equals("2") || ch.equals("3") || ch.equals("4") || ch.equals("5") || ch.equals("6") || ch.equals("7") || ch.equals("8") || ch.equals("9") || ch.equals("0"))) {

                {
                    e.consume();
                    java.awt.Toolkit.getDefaultToolkit().beep();
                }
            }

        });
        btnRetour.setOnAction(e -> {
            tfNomFacture.clear();
            tfNumFacture.clear();

        });
        btnAcceuil.setOnAction(e -> {
            Entree distribuer = new Entree();
            distribuer.start(primaryStage);

        });
        btnEnr.setOnAction(e -> {
            if (tfNomFacture.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Information");
                alert.setContentText("Vérifier le(s) champs 'NUMERO FACTURE' et/ou 'NOM FICHIER'\n Ils ne peuvent etre vide!!!");
                alert.showAndWait();
            } else if (tfNumFacture.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Information");
                alert.setContentText("Vérifier le(s) champs 'NUMERO FACTURE' et/ou 'NOM FICHIER'\n Ils ne peuvent etre vide!!!");
                alert.showAndWait();
            } else {

                try {
                    FileWriter fw = new FileWriter("C:/Users/Ouzy NDIAYE/Desktop/GestionComptaMatiere/" + tfNomFacture.getText() + ".doc"
                    );
                    BufferedWriter bw = new BufferedWriter(fw);

                    r = st.executeQuery("select * from user where Etat='1' ");
                    r.next();
                    bw.write(r.getString(2) + " " + r.getString(3));
                    bw.write("                                           Facture :" + tfNumFacture.getText() + " ");
                    bw.write("Université Alioune Diop de Bambey");
                    bw.write("                          " + DateAutomatik());
                    bw.newLine();
                    bw.newLine();
                    bw.write("             Bon de Sortie ");
                    bw.newLine();
                    bw.newLine();
                    bw.write("             Bénéficiaire : " + serviceValue);
                    bw.newLine();
                    bw.newLine();
                    bw.newLine();
                    bw.write("Référence \t\t\t\tLibellé \t\t\t\tFournisseur");
                    bw.newLine();
                    bw.newLine();
                    rs = st.executeQuery("select * from materiel where DateDistribution = '" + DateAutomatik() + "' and NomService='" + serviceValue + "'");

                    while (rs.next()) {

                        mat = new Materiels(rs.getString(1), rs.getString(4), rs.getString(9));
                        bw.write(mat.toString());
                        bw.newLine();
                    }
                    bw.newLine();
                    bw.newLine();
                    bw.newLine();
                    bw.newLine();
                    bw.newLine();
                    bw.newLine();
                    bw.newLine();
                    bw.newLine();
                    bw.newLine();
                    bw.write("Comptable                                                    Service ");

                    bw.close();
                    fw.close();

                } catch (Exception ex_M) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Information");
                    alert.setContentText("Erreur d'enregistrement");
                    alert.showAndWait();
                    System.out.println("Error de la base avec le bouton Imprimer ");
                    ex_M.printStackTrace();
                }
                //Séléction d'un fichier////////
                fichier = fileChooser.showOpenDialog(primaryStage);

                if (fichier != null) {
                    try {
                        desktop.open(fichier);
                        fichier.canExecute();
                    } catch (IOException ex) {
                        Logger.getLogger(Imprimer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        gp.getChildren().addAll(labNomService, cbService, labNumFacture, tfNumFacture, labNomFacture, tfNomFacture, hBox);

        bp.setCenter(gp);
        bp.setId("imp");
        Scene scene = new Scene(bp, 1300, 700);
        scene.getStylesheets().add(Acceuil.class.getResource("style.css").toExternalForm());
        primaryStage.setTitle("Imprimer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static final LocalDate DateAutomatiQ() {
        String date = new SimpleDateFormat("EEEEE, dd MM yyyy", Locale.FRANCE).format(Calendar.getInstance().getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEEE, dd MM yyyy");
        LocalDate localdate = LocalDate.parse(date, formatter);
        return localdate;
    }

}
