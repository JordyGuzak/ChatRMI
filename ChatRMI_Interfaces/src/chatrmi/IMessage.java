/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatrmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author jordy
 */
public interface IMessage extends Remote{
    public IUser getSender() throws RemoteException;
    public String getMessage() throws RemoteException;
}
