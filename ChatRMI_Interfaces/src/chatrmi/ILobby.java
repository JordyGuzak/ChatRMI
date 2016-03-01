/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatrmi;

import fontys.observer.RemotePublisher;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author jordy
 */
public interface ILobby extends RemotePublisher{
    public ArrayList<IChatRoom> getChatRooms() throws RemoteException;
    public void openRoom(String roomName) throws RemoteException;
    public void closeRoom(IChatRoom chatRoom) throws RemoteException;
}
