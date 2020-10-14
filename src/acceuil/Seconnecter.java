/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acceuil;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Moussa Joseph Sarr
 */
public class Seconnecter implements Initializable{

    @FXML
        TextField tfId;
        PasswordField passwd;
    
    
    public void dbase() {
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

            rs = st.executeQuery("select * from user");
            rs.next();

            if (tfId.getText().equals(rs.getString(1)) || passwd.getText().equals(rs.getString(2))) {
                System.out.println("connecter");
            }else
                System.out.println("Non connecter");
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Erreur");
           

        }
       
    }
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
