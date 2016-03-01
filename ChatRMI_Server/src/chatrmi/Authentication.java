/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatrmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author jordy
 */
public class Authentication extends UnicastRemoteObject implements IAuthentication {

    private ArrayList<IUser> users;
    
    public Authentication() throws RemoteException {
        this.users = new ArrayList<>();
    }
    
    @Override
    public IUser checkLoginInformation(String username, String password) throws RemoteException {
        if (username.isEmpty() || password.isEmpty()) {
            return null;
        }
        
        for (IUser user : users)
        {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void addUser(IUser user) {
        if (!users.contains(user)) {
            users.add(user);
        }
    }

    @Override
    public void removeUser(IUser user) {
       if (users.contains(user)) {
           users.remove(user);
       }
    }
    
}
