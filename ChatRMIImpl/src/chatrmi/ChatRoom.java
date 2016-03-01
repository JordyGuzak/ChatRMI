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
public class ChatRoom extends UnicastRemoteObject implements IChatRoom {
    private int roomNr;
    private String roomName;
    private ArrayList<IUser> users;
    private ArrayList<IMessage> messages;
    private BasicPublisher bp;
    
    public ChatRoom() throws RemoteException{ 
    }
    
    public ChatRoom(int roomNr, String roomName) throws RemoteException {
        this.roomNr = roomNr;
        this.roomName = roomName;
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.bp = new BasicPublisher(new String[] {
            "Message",
            "User"
        });
    }
    
    @Override
    public int getRoomNr()
    {
        return this.roomNr;
    }

    @Override
    public void addMessage(IUser sender, String message) throws RemoteException {
        IMessage newMessage = new Message(sender, message);
        this.messages.add(newMessage);
        bp.inform(this, "Message", null, newMessage);
    }

    @Override
    public void addUser(IUser user) {
        this.users.add(user);
        bp.inform(this, "User", null, user);
    }

    @Override
    public ArrayList<IMessage> getMessages() throws RemoteException {
       return this.messages;
    }

    @Override
    public String getRoomName() throws RemoteException {
        return this.roomName;
    }

    @Override
    public ArrayList<IUser> getUsers() throws RemoteException {
        return this.users;
    }

    @Override
    public int getUserCount() throws RemoteException {
        return users.size();
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
