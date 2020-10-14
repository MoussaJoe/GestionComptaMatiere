/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mise_a_jour;

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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 *
 * @author HP
 */
public class MiseAjour extends Application {

    Scene scene;
    Connection con;
    Statement st;
    ResultSet rs;
    PreparedStatement pst;
    String serviceValue;
    String descriptionValue;
    String fournisseurValue;
    private TableView<Materiels> tableView;
    private TableColumn<Materiels, String> Reference;
    private TableColumn<Materiels, String> dateLivraison;
    private TableColumn<Materiels, String> dateDistribution;
    private TableColumn<Materiels, String> Libelle;
    private TableColumn<Materiels, String> NomService;
    private TableColumn<Materiels, String> ReferenceTypeMateriel;
    private TableColumn<Materiels, String> NomFournisseur;
    private ObservableList<Materiels> data;

    @Override
    public void start(Stage primaryStage) {
        String nomService;
        String description;
        String nomFournisseur;
        ////////////****************Connect BD************/////////////////////////
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
        ////////////*****************Fin Connect BD************/////////////////////
        //////////////////////Instanciation////////
        BorderPane bp = new BorderPane();
        GridPane gp = new GridPane();
        Button btnRetour = new Button();
        btnRetour.setText("Acceuil");
        Button btnsup = new Button();
        Button btnImprimer = new Button();
        btnImprimer.setText("Imprimer");
        btnsup.setText("Retourner");
        VBox vb = new VBox();
        VBox vb1 = new VBox();
        //////////////////////Fin Instanciation////////
        //////////////////Connection BD///////////////////////////////////////////////
        /////////////////Déclaration des textFild/////////////////////////////////////
        TextField tfRef = new TextField();
        tfRef.setId("tf");
        TextField tfDateLiv = new TextField();
        tfDateLiv.setId("tf");
        TextField tfLib = new TextField();
        tfLib.setId("tf");
        ComboBox cbServive = new ComboBox();
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
        cbServive.setId("tf");
        ComboBox cbDescription = new ComboBox();
        ///////////////////////Info Type de materiel///////////////////////////////
        try {
            String requeteSelect = "select Description from typemateriel ";
            rs = st.executeQuery(requeteSelect);

            while (rs.next()) {
                description = rs.getString("Description");
                cbDescription.getItems().add(description);
                cbDescription.setOnAction((e) -> {
                    descriptionValue = (String) cbDescription.getValue();
                });

            }
        } catch (Exception e) {
            System.out.println("Erreur de requete");
        }
        ///////////////////////Fin Info type de materiel///////////////////////////////
        cbDescription.setId("tf");
        ComboBox cbFour = new ComboBox();
        /////////////////////////////////Requete select sur la table fournisseur/////////////////////////////////
        try {
            String requeteSelect = "select NomFournisseur from fournisseur ";
            rs = st.executeQuery(requeteSelect);

            while (rs.next()) {
                nomFournisseur = rs.getString("NomFournisseur");
                cbFour.getItems().add(nomFournisseur);
            }
            cbFour.setOnAction((e) -> {
                fournisseurValue = (String) cbFour.getValue();
            });
        } catch (Exception e) {
            System.out.println("Erreur de requete");
        }
        ///////////************Fin Fournisseur/*****************////////////////////
        cbFour.setId("tf");
        ComboBox cbEtat = new ComboBox();
        cbEtat.setId("btnEnregistrer");
        cbEtat.setPromptText("Choisir l'état");
        cbEtat.getItems().add("DEFECTUEUX");
        cbEtat.getItems().add("PERDU");
        cbEtat.getItems().add("BON");
        Button btnok = new Button();
        btnok.setText("Selectionner");

        ////////////////Fin déclaration//////////////////////////////////////////////
        tableView = new TableView();
        tableView.setId("tableau");
        Reference = new TableColumn<>("Reference");
        Reference.setMinWidth(100);
        dateLivraison = new TableColumn<>("Date Livraison");
        dateLivraison.setMinWidth(100);
        dateDistribution = new TableColumn<>("Date Distibution");
        dateDistribution.setMinWidth(100);
        Libelle = new TableColumn<>("Libelle");
        Libelle.setMinWidth(100);
        NomService = new TableColumn<>("Nom Service");
        NomService.setMinWidth(200);
        ReferenceTypeMateriel = new TableColumn<>("Description Materiel");
        ReferenceTypeMateriel.setMinWidth(200);
        NomFournisseur = new TableColumn<>("Nom Fournisseur");
        NomFournisseur.setMinWidth(200);
        String ref;
        // La plupart de ces instructions devraient être soit dans le FXML soit dans la méthode initialize() du contrôleur dans ton cas.
//        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // tableView.getColumns().setAll(Reference, dateLivraison, dateDistribution, Libelle, Retour, NomService, ReferenceTypeMateriel, NomFournisseur);

        setCellTable();
        loadeDataFromDatabase();
        btnRetour.setOnAction(e -> {
            Entree entree = new Entree();
            entree.start(primaryStage);
        });
        btnok.setOnAction(e -> {
            Materiels mat = tableView.getSelectionModel().getSelectedItem();
            try {
//                 String requeteM = "UPDATE  materiel SET  Retour =1 WHERE  Reference ='" + mat.getReference() + "'";
//                 int   rs = con.createStatement().executeUpdate(requeteM);
                tfRef.setText(mat.getReference());
                tfRef.setEditable(false);
                tfDateLiv.setText(mat.getDateLivraison());
                tfLib.setText(mat.getLibelle());
                cbServive.setPromptText(mat.getNomService());
                cbDescription.setPromptText(mat.getDescription());
                cbFour.setPromptText(mat.getNomFournisseur());
                //delMateriel(null);
            } catch (Exception ex_M) {
                System.out.println("Error records type materiel");
                ex_M.printStackTrace();

            }
        });
        String service = "" + cbServive.getValue();
        String description1 = "" + cbDescription.getValue();
        String fournisseur = "" + cbFour.getValue();
        btnsup.setOnAction(e -> {
            Materiels mat = tableView.getSelectionModel().getSelectedItem();
            try {
                String requetemat = "UPDATE  materiel SET DateLivraison=?,Libelle=?,NomService=?,Description=?,NomFournisseur=? WHERE  Reference ='" + mat.getReference() + "'";
                pst = con.prepareStatement(requetemat);
                pst.setString(1, tfDateLiv.getText());
                pst.setString(2, tfLib.getText());
                pst.setString(3, service);
                pst.setString(4, description1);
                pst.setString(5, fournisseur);
                pst.execute();
                pst.close();

            } catch (Exception ex_M) {
                System.out.println("Error records type materiel");
                ex_M.printStackTrace();

            }
            try {
                String requeteM = "UPDATE  materiel SET  Retour =1 WHERE  Reference ='" + mat.getReference() + "'";
                System.out.println(mat.getReference());
                int rs = con.createStatement().executeUpdate(requeteM);
                String requeteI = "UPDATE  inventairemateriel SET  Etat ='" + cbEtat.getValue() + "' WHERE  Reference ='" + mat.getReference() + "'";
                int rst = con.createStatement().executeUpdate(requeteI);
            } catch (Exception ex_M) {
                System.out.println("Error records type materiel");
                ex_M.printStackTrace();

            }
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information");
            alert.setContentText("Modifications bien enregistrées");
            alert.showAndWait();

            delMateriel(null);
        });
        btnImprimer.setOnAction(e -> {
            ImprimerBonRetour imprimerBonRetour = new ImprimerBonRetour();
            imprimerBonRetour.start(primaryStage);
        });
        vb1.getChildren().addAll(tfRef, tfDateLiv, tfLib, cbServive, cbDescription, cbFour, cbEtat, btnsup, btnImprimer);
        vb1.setPadding(new Insets(10));
        vb1.setSpacing(10);
        vb.getChildren().add(tableView);
//        gp.setConstraints(btnRetour, 0, 2);
//        gp.setConstraints(btnok, 0, 1);
        gp.setConstraints(vb, 0, 0);
        GridPane gridPane = new GridPane();
        HBox hBox = new HBox();
        hBox.getChildren().addAll(btnok, btnRetour);
        hBox.setPadding(new Insets(10));
        hBox.setSpacing(10);
        gridPane.setConstraints(hBox, 0, 1);

        gp.getChildren().addAll(vb, hBox);
        bp.setLeft(vb1);
        bp.setRight(gp);
        gp.setVgap(10);
        gp.setHgap(10);
        gp.setPadding(new Insets(10, 10, 10, 10));
        scene = new Scene(bp, 1300, 750);
        scene.getStylesheets().add(Acceuil.class.getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Mise A Jour");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setCellTable() {
        Reference.setCellValueFactory(new PropertyValueFactory<>("Reference"));
        dateLivraison.setCellValueFactory(new PropertyValueFactory<>("dateLivraison"));
        dateDistribution.setCellValueFactory(new PropertyValueFactory<>("dateDistribution"));
        Libelle.setCellValueFactory(new PropertyValueFactory<>("Libelle"));
        NomService.setCellValueFactory(new PropertyValueFactory<>("NomService"));
        ReferenceTypeMateriel.setCellValueFactory(new PropertyValueFactory<>("Description"));
        NomFournisseur.setCellValueFactory(new PropertyValueFactory<>("NomFournisseur"));
        tableView.getColumns().setAll(Reference, dateLivraison, dateDistribution, Libelle, NomService, ReferenceTypeMateriel, NomFournisseur);
    }

    public void loadeDataFromDatabase() {
        try {
            rs = st.executeQuery("select * from materiel where Retour=0 and Livrer=1");
            while (rs.next()) {
                data.add(new Materiels(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9)));
            }
        } catch (Exception e) {

        }
        tableView.setItems(data);
    }

    public void delMateriel(ActionEvent event) {
        data = tableView.getItems();
        data.removeAll(tableView.getSelectionModel().getSelectedItems());
    }

}
