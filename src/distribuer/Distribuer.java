/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distribuer;

import Imprimer.Imprimer;
import acceuil.Entree;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Lister.Materiels;
import acceuil.Acceuil;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 *
 * @author HP
 */
public class Distribuer extends Application {

    Scene scene;
    Connection con;
    Statement st;
    ResultSet rs;
    PreparedStatement pst;
    private TableView<Materiels> tableView;
    private TableColumn<Materiels, String> Reference;
    private TableColumn<Materiels, String> dateLivraison;
    private TableColumn<Materiels, String> Libelle;
    private TableColumn<Materiels, String> NomService;
    private TableColumn<Materiels, String> ReferenceTypeMateriel;
    private TableColumn<Materiels, String> NomFournisseur;
    private ObservableList<Materiels> data;

    @Override
    public void start(Stage primaryStage) {
        //////////////////////Instanciation////////
        BorderPane bp = new BorderPane();
        GridPane gp = new GridPane();
        Button btnRetour = new Button();
        btnRetour.setText("Acceuil");
        Button btnsup = new Button();
        btnsup.setText("Distribuer");
        Button btnimp = new Button();
        btnimp.setText("Imprimer");
        TextField text = new TextField();
        text.setText("Référence");
        VBox vb = new VBox();
        HBox vb2 = new HBox();
        //////////////////////Fin Instanciation////////
        //////////////////Connection BD///////////////////////////////////////////////
        // Normalement ces initialisations sont faites par le FXMLLoader dans ton cas.
        tableView = new TableView();
        tableView.setId("tableau");
        Reference = new TableColumn<>("Reference");
        Reference.setMinWidth(216);
        dateLivraison = new TableColumn<>("Date Livraison");
        dateLivraison.setMinWidth(216);
        Libelle = new TableColumn<>("Libelle");
        Libelle.setMinWidth(216);
        NomService = new TableColumn<>("Nom Service");
        NomService.setMinWidth(216);
        ReferenceTypeMateriel = new TableColumn<>("Description Materiel");
        ReferenceTypeMateriel.setMinWidth(216);
        NomFournisseur = new TableColumn<>("Nom Fournisseur");
        NomFournisseur.setMinWidth(216);
        // La plupart de ces instructions devraient être soit dans le FXML soit dans la méthode initialize() du contrôleur dans ton cas.
//        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // tableView.getColumns().setAll(Reference, dateLivraison, dateDistribution, Libelle, Retour, NomService, ReferenceTypeMateriel, NomFournisseur);
        try {
            /*chargement du driver*/
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception er_dv) {
            System.out.println("Erreur driver:  " + er_dv.getMessage());
        }

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/gestioncomptamatiere", "root", "");
            data = FXCollections.observableArrayList();
        } catch (Exception er_con) {
            System.out.println("Erreur de connexion " + er_con.getMessage());
        }
        try {
            st = con.createStatement();
        } catch (SQLException er_st) {
            System.out.println("Erreur de Statement " + er_st.getMessage());
        }

        try {
            rs.close();
            st.close();
            con.close();
        } catch (Exception d) {
        }
        setCellTable();
        loadeDataFromDatabase();
        btnRetour.setOnAction(e -> {
            Entree entree = new Entree();
            entree.start(primaryStage);
        });
        btnsup.setOnAction(e -> {
            Materiels mat = tableView.getSelectionModel().getSelectedItem();
            try {
                System.out.println(mat.getReference());
                String requeteM = "UPDATE  materiel SET  Livrer =1 WHERE  Reference ='" + mat.getReference() + "'";
                String requeted = "UPDATE  materiel SET  DateDistribution ='" + DateAutomatik() + "'" + "WHERE  Reference ='" + mat.getReference() + "'";
                int rs = con.createStatement().executeUpdate(requeteM);
                int r = con.createStatement().executeUpdate(requeted);
                delMateriel(null);
            } catch (Exception ex_M) {
                System.out.println("Error records type materiel");
                ex_M.printStackTrace();

            }
        });
        btnimp.setOnAction(e -> {
            Imprimer imp = new Imprimer();
            imp.start(primaryStage);
        });
        vb2.getChildren().addAll(btnsup, btnimp, btnRetour);
        vb2.setPadding(new Insets(10));
        vb2.setSpacing(10);
        vb.getChildren().add(tableView);
//        gp.setConstraints(vb2, 0, 1);
        gp.setConstraints(vb2, 0, 1);
//        gp.setConstraints(btnRetour, 2, 0);
//        gp.setConstraints(btnimp, 1, 0);
        gp.setConstraints(vb, 0, 0);
        gp.getChildren().addAll(vb, vb2);
        bp.setCenter(gp);
        gp.setVgap(10);
        gp.setHgap(10);
        gp.setPadding(new Insets(10, 10, 10, 10));
        scene = new Scene(bp, 1300, 750);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Livraison");
        scene.getStylesheets().add(Acceuil.class.getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setCellTable() {
        Reference.setCellValueFactory(new PropertyValueFactory<>("Reference"));
        dateLivraison.setCellValueFactory(new PropertyValueFactory<>("dateLivraison"));
        Libelle.setCellValueFactory(new PropertyValueFactory<>("Libelle"));
        NomService.setCellValueFactory(new PropertyValueFactory<>("NomService"));
        ReferenceTypeMateriel.setCellValueFactory(new PropertyValueFactory<>("Description"));
        NomFournisseur.setCellValueFactory(new PropertyValueFactory<>("NomFournisseur"));
        tableView.getColumns().setAll(Reference, dateLivraison, Libelle, NomService, ReferenceTypeMateriel, NomFournisseur);
    }

    public void loadeDataFromDatabase() {
        try {
            rs = st.executeQuery("select * from materiel where livrer=0 and Retour=0");
            while (rs.next()) {
                data.add(new Materiels(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9)));

            }
        } catch (Exception e) {

        }
        tableView.setItems(data);
    }

//    private String getDate() {
//        return LocalDateTime.now().toString();
//    }
    public void delMateriel(ActionEvent event) {
        data = tableView.getItems();
        data.removeAll(tableView.getSelectionModel().getSelectedItems());
    }

    // DATE AUTOMATQUE
    public static final LocalDate DateAutomatik() {
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE).format(Calendar.getInstance().getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localdate = LocalDate.parse(date, formatter);
        return localdate;
    }

}
