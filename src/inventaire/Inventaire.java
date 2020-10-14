/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventaire;

import acceuil.Acceuil;
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

/**
 *
 * @author Ouzy NDIAYE
 */
public class Inventaire extends Application {

    Scene scene;
    Connection con;
    Statement st;
    ResultSet rs;
    PreparedStatement pst;
    private TableView<InventaireMateriels> tableView;
    private TableColumn<InventaireMateriels, String> Reference;
    private TableColumn<InventaireMateriels, String> dateLivraison;
    private TableColumn<InventaireMateriels, String> dateDistribution;
    private TableColumn<InventaireMateriels, String> Libelle;
    private TableColumn<InventaireMateriels, String> NomService;
    private TableColumn<InventaireMateriels, String> description;
    private TableColumn<InventaireMateriels, String> NomFournisseur;
    private TableColumn<InventaireMateriels, String> etat;
    private TableColumn<InventaireMateriels, String> annee;
    private ObservableList<InventaireMateriels> data;

    @Override
    public void start(Stage primaryStage) {
        //////////////////////Instanciation////////
        BorderPane bp = new BorderPane();
        bp.setId("invantaire");
        GridPane gp = new GridPane();
        Button btnRetour = new Button();
        btnRetour.setText("Acceuil");
        VBox vb = new VBox();

        //////////////////////Fin Instanciation////////
        //////////////////Connection BD///////////////////////////////////////////////
        // Normalement ces initialisations sont faites par le FXMLLoader dans ton cas.
        tableView = new TableView();
        tableView.setId("tableau");
        Reference = new TableColumn<>("Référence");
        Reference.setMinWidth(156);
        dateLivraison = new TableColumn<>("Date Livraison");
        dateLivraison.setMinWidth(156);
//        dateDistribution = new TableColumn<>("Date Distribution");
//        dateDistribution.setMinWidth(138);
        Libelle = new TableColumn<>("Libellé");
        Libelle.setMinWidth(156);
        NomService = new TableColumn<>("Nom Service");
        NomService.setMinWidth(156);
        description = new TableColumn<>("Description Matériel");
        description.setMinWidth(156);
        NomFournisseur = new TableColumn<>("Nom Fournisseur");
        NomFournisseur.setMinWidth(156);
        etat = new TableColumn<>("Etat");
        etat.setMinWidth(156);
        annee = new TableColumn<>("Année");
        annee.setMinWidth(156);
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

        vb.getChildren().add(tableView);
        gp.setConstraints(btnRetour, 0, 1);
        gp.setConstraints(vb, 0, 0);
        gp.getChildren().addAll(vb, btnRetour);
        bp.setCenter(gp);
        gp.setVgap(10);
        gp.setHgap(10);
        gp.setPadding(new Insets(10, 10, 10, 10));
        scene = new Scene(bp, 1300, 750);
        scene.getStylesheets().add(Acceuil.class.getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Inventaire");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setCellTable() {
        Reference.setCellValueFactory(new PropertyValueFactory<>("Reference"));
        dateLivraison.setCellValueFactory(new PropertyValueFactory<>("dateLivraison"));
        //dateDistribution.setCellValueFactory(new PropertyValueFactory<>("dateDistribution"));
        Libelle.setCellValueFactory(new PropertyValueFactory<>("Libelle"));
        NomService.setCellValueFactory(new PropertyValueFactory<>("NomService"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        NomFournisseur.setCellValueFactory(new PropertyValueFactory<>("NomFournisseur"));
        etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        annee.setCellValueFactory(new PropertyValueFactory<>("annee"));
        tableView.getColumns().setAll(Reference, dateLivraison, Libelle, NomService, description, NomFournisseur, etat, annee);
    }

    public void loadeDataFromDatabase() {
        try {
            rs = st.executeQuery("select mat.Reference,DateLivraison,DateDistribution,Libelle,NomService,Description,NomFournisseur,Etat,Annee from materiel mat,inventairemateriel im where mat.Reference=im.Reference and mat.Retour=0");
            while (rs.next()) {
                data.add(new InventaireMateriels(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));
            }
        } catch (Exception e) {
        }
        tableView.setItems(data);
    }

    public void delMateriel(ActionEvent event) {
        data = tableView.getItems();
        data.removeAll(tableView.getSelectionModel().getSelectedItems());
    }

    /**
     * @param args the command line arguments
     */
}
