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
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author jordy
 */
public class RoomSchermController extends UnicastRemoteObject implements Initializable, RemotePropertyListener {

    @FXML
    Button btnLeave;

    @FXML
    Button btnSend;

    @FXML
    TextArea taMessage;

    @FXML
    ListView lvChat;

    @FXML
    ListView lvUsers;

    @FXML
    Label lblRoomName;

    IChatRoom chatRoom;
    ObservableList olUsers = FXCollections.observableArrayList();
    ObservableList olMessages = FXCollections.observableArrayList();

    public RoomSchermController() throws RemoteException {

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnLeave.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("LobbyScherm.fxml"));
                    Parent root;
                    root = loader.load();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                } catch (IOException ex) {
                    Logger.getLogger(RoomSchermController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        btnSend.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                sendMessage();
            }
        });

        taMessage.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    sendMessage();
                    event.consume();
                    taMessage.clear();
                }
            }
        });
    }

    private void sendMessage() {
        String message = taMessage.getText();
        if (!message.equals("")) {
            try {
                chatRoom.addMessage(ChatRMI.user, message);
            } catch (RemoteException ex) {
                Logger.getLogger(RoomSchermController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void initData(IChatRoom room) throws RemoteException {
        chatRoom = room;
        chatRoom.addListener(this, "Message");
        chatRoom.addListener(this, "User");
        lblRoomName.setText(chatRoom.getRoomName());
        for (IUser user : chatRoom.getUsers()) {

        }
        lvUsers.setItems(olUsers);
        lvChat.setItems(olMessages);
    }

    private String createChatString(IUser user, String message) throws RemoteException {
        if (user != null && !message.equals("")) {
            return user.getUsername() + ": " + message;
        }
        return "";
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) throws RemoteException {

        String propertyName = pce.getPropertyName();
        switch (propertyName) {
            case "Message":
                IMessage newMessage = (IMessage) pce.getNewValue();

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            olMessages.add(createChatString(newMessage.getSender(), newMessage.getMessage()));
                        } catch (RemoteException ex) {
                            Logger.getLogger(RoomSchermController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });

                break;
            case "User":
                IUser newUser = (IUser) pce.getNewValue();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            olUsers.add(newUser.getUsername());
                        } catch (RemoteException ex) {
                            Logger.getLogger(RoomSchermController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                break;
        }
    }
}
