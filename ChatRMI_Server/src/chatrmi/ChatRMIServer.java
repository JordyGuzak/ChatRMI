/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatrmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author jordy
 */
public class ChatRMIServer extends Application {
    
    private static Registry registry = null;
    int port = 1099;
    boolean isRunning = false;
    ILobby lobby;
    TableView table;
    
    IAuthentication auth;

    @Override
    public void start(Stage primaryStage) {
        try {
            lobby = new Lobby();
            auth = new Authentication();
            auth.addUser(new User("username", "password"));
        } catch (RemoteException ex) {
            Logger.getLogger(ChatRMIServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        table = new TableView();
        TableColumn col1 = new TableColumn("Chat Rooms");
        col1.setCellValueFactory(new PropertyValueFactory("roomName"));
        TableColumn col2 = new TableColumn("Users");
        col2.setCellValueFactory(new PropertyValueFactory("roomCount"));
        table.getColumns().addAll(col1, col2);
        
        Button btnStart = new Button();
        Button btnAddRoom = new Button();
        TextField txtField = new TextField();
        TextArea txtArea = new TextArea();

        btnStart.setText("Start");
        btnStart.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                if (!isRunning) {
                    try {
                        registry = LocateRegistry.createRegistry(port);
                        registry.rebind("Lobby", lobby);
                        registry.rebind("Auth", auth);
                        btnStart.setText("Stop");
                        isRunning = true;
                    } catch (RemoteException ex) {
                        Logger.getLogger(ChatRMIServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else {
                    try {
                        registry.unbind("Lobby");
                        registry.unbind("Auth");
                        registry = null;
                        JOptionPane.showMessageDialog(null, "Application needs to be restarted before you're able to start it again!", "Info", JOptionPane.INFORMATION_MESSAGE);
                    } catch (RemoteException | NotBoundException ex) {
                        Logger.getLogger(ChatRMIServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    btnStart.setText("Start");
                    isRunning = false;
                }
            }
        });

        btnAddRoom.setText("New room");
        btnAddRoom.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    String roomName = (String) JOptionPane.showInputDialog("Room name:");
                    try {
                        lobby.openRoom(roomName);
                        updateRoomTable();
                        registry.rebind("Lobby", lobby);
                    } catch (RemoteException ex) {
                        Logger.getLogger(ChatRMIServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (NullPointerException exc) {
                    exc.printStackTrace();
                }
            }
        });

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        //grid.add(txtArea, 0, 0);
        //grid.add(txtField, 0, 1);
        
        grid.add(btnStart, 0, 0);
        grid.add(btnAddRoom, 0, 2);
        grid.add(table, 0, 1);

        Scene scene = new Scene(grid, 300, 300);

        primaryStage.setTitle("ChatRMI Server");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public void updateRoomTable() throws RemoteException
    {
        ObservableList ol = FXCollections.observableArrayList();
        for (IChatRoom room : lobby.getChatRooms()) {
            ol.add(new ChatRoomRow(room.getRoomName(), room.getUserCount()));
        }
        table.setItems(ol);
    }
}
