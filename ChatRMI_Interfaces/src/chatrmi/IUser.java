/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatrmi;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author jordy
 */
public interface IUser extends Remote {
    public String getUsername() throws RemoteException;
    public String getPassword() throws RemoteException;
}
