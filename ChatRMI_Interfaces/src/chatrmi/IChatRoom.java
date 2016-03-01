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
public interface IChatRoom extends RemotePublisher{
    public int getRoomNr() throws RemoteException;
    public void addMessage(IUser sender, String message) throws RemoteException;
    public void addUser(IUser user) throws RemoteException;
    public ArrayList<IMessage> getMessages() throws RemoteException;
    public String getRoomName() throws RemoteException;
    public ArrayList<IUser> getUsers() throws RemoteException;
    public int getUserCount() throws RemoteException;
}
