/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatrmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author jordy
 */
public interface IAuthentication extends Remote{
    public IUser checkLoginInformation(String username, String password) throws RemoteException ;
    public void addUser(IUser user) throws RemoteException;
    public void removeUser(IUser user) throws RemoteException;
}
