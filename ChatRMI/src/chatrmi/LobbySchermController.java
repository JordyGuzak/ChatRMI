/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatrmi;

import static chatrmi.ChatRMI.stage;
import fontys.observer.RemotePropertyListener;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author jordy
 */
public class LobbySchermController extends UnicastRemoteObject implements Initializable, RemotePropertyListener {

    @FXML
    TableView tblRooms;

    @FXML
    TableColumn clmnRooms;

    @FXML
    TableColumn clmnUsers;

    private ObservableList<IChatRoom> chatRooms;
    private ObservableList<ChatRoomRow> rows;
    private ILobby lobby;
    
    public LobbySchermController() throws RemoteException {
        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chatRooms = FXCollections.observableArrayList();
        rows = FXCollections.observableArrayList();
        
        clmnRooms.setCellValueFactory(new PropertyValueFactory("roomName"));
        clmnUsers.setCellValueFactory(new PropertyValueFactory("roomCount"));
        
        tblRooms.setItems(rows);

        tblRooms.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    ChatRoomRow roomRow = (ChatRoomRow) tblRooms.getSelectionModel().getSelectedItem();
                    String roomName = roomRow.getRoomName();

                    if (!roomName.equals("")) {
                        for (IChatRoom room : chatRooms) {
                            try {
                                if (room.getRoomName().equals(roomName)) {
                                    joinRoom(room);
                                }
                            } catch (RemoteException ex) {
                                Logger.getLogger(LobbySchermController.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(LobbySchermController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }

        });

        try {
            // TODO
            lobby = (ILobby) ChatRMI.registry.lookup("Lobby");
            lobby.addListener(this, "Rooms");
            updateRoomTable();
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(LobbySchermController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateRoomTable() throws RemoteException {
        for (IChatRoom room : lobby.getChatRooms()) {
            ChatRoomRow row = new ChatRoomRow(room.getRoomName(), room.getUserCount());
            rows.add(row);
            chatRooms.add(room);
        }
    }
    
    private void updateTableRows() throws RemoteException {
        ArrayList<ChatRoomRow> updatedRows = new ArrayList<>();
        for (IChatRoom room : chatRooms) {
            ChatRoomRow row = new ChatRoomRow(room.getRoomName(), room.getUserCount());
            updatedRows.add(row);
        } 
        rows.setAll(updatedRows);
    }

    public void joinRoom(IChatRoom room) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RoomScherm.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        
        room.addUser(ChatRMI.user);
        RoomSchermController controller = loader.getController();
        controller.initData(room);
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) throws RemoteException {
       ArrayList<IChatRoom> updatedRooms = (ArrayList<IChatRoom>) pce.getNewValue();
       chatRooms.setAll(updatedRooms);
       updateTableRows();
    }
}
