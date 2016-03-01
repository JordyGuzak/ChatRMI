/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatrmi;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author jordy
 */
public class ChatRMI extends Application implements Initializable {
    @FXML
    private TextField tfUserName;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private Label lblUserName;
    @FXML
    private Label lblPassword;
    @FXML
    private Label lblChatRMI;
    @FXML
    private Label lblLogin;
    @FXML 
    private Button btnLogin;
    
    public static Stage stage;
    public static Registry registry;
    public static IUser user;
    
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LoginScherm.fxml"));
        Scene scene = new Scene(root);
        
        stage = primaryStage;
        stage.setScene(scene);
        
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ConnectToServer();
    }
    
    public void Login() throws IOException {
        String userName = tfUserName.getText();
        String password = tfPassword.getText();
        
        IAuthentication auth;
        try {
            auth = (IAuthentication) registry.lookup("Auth");
            user = (IUser) auth.checkLoginInformation(userName, password);
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(ChatRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (user != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LobbyScherm.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        }
    }
    
    private void ConnectToServer() {
        try {
            registry = LocateRegistry.getRegistry("localhost", 1099);
            System.out.println("Succesfully connected to server.");
        } catch (RemoteException ex) {
            Logger.getLogger(ChatRMI.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Connection failed.");
        }
    }
}
