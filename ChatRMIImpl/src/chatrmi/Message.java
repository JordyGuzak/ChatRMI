/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatrmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author jordy
 */
public class Message extends UnicastRemoteObject implements IMessage{

    private IUser sender;
    private String message;
    
    public Message() throws RemoteException {};
    
    public Message(IUser sender, String message) throws RemoteException
    {
        if (sender == null || message.equals(""))
        {
            throw new IllegalArgumentException("No sender or empty message.");
        }
        this.sender = sender;
        this.message = message;
    }
    
    @Override
    public IUser getSender() {
        return this.sender;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
    
}
