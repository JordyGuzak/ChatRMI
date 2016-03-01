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
public class User extends UnicastRemoteObject implements IUser{
    
    String userName;
    String password;
    
    public User() throws RemoteException
    {
    }
    
    public User(String userName, String password) throws RemoteException
    {
        if (userName.equals("") || password.equals("")) {
            throw new IllegalArgumentException("Username or password invalid.");
        }
        this.userName = userName;
        this.password = password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
    
}
