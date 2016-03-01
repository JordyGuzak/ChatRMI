/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatrmi;

import fontys.observer.BasicPublisher;
import fontys.observer.RemotePropertyListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author jordy
 */
public class Lobby extends UnicastRemoteObject implements ILobby {

    private ArrayList<IChatRoom> chatRooms;
    private BasicPublisher bp;
    
    public Lobby() throws RemoteException {
        this.chatRooms = new ArrayList<>();
        this.bp = new BasicPublisher(new String[] {
            "Rooms"
        });
    }
    
    
    @Override
    public ArrayList<IChatRoom> getChatRooms() throws RemoteException {
        return this.chatRooms;
    }

    @Override
    public void openRoom(String roomName) throws RemoteException {
        int roomNr = this.chatRooms.size() + 1;
        IChatRoom newChatRoom = new ChatRoom(roomNr, roomName);
        chatRooms.add(newChatRoom);
        bp.inform(this, "Rooms", null, chatRooms);
    }

    @Override
    public void closeRoom(IChatRoom chatRoom) throws RemoteException {
        chatRooms.remove(chatRoom);
    }

    @Override
    public void addListener(RemotePropertyListener rl, String string) throws RemoteException {
        bp.addListener(rl, string);
    }

    @Override
    public void removeListener(RemotePropertyListener rl, String string) throws RemoteException {
        bp.removeListener(rl, string);
    }
    
}
