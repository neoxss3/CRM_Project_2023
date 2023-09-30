/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crm22.service;

import crm22.entity.User;
import crm22.repositories.UserRepository;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author anhbs
 */
public class UserService {
    
    private UserRepository ur = new UserRepository();
    
    public boolean getAllUser(List<User> lst) throws ClassNotFoundException, SQLException {
        ur.getAllUsers(lst);
        return lst.size() > 0;
    }
    
    public boolean getAllManager(List<User> lst) throws ClassNotFoundException, SQLException {
        ur.getAllManagers(lst);
        return lst.size() > 0;
    }
    
    public boolean addUser(User us) throws ClassNotFoundException, SQLException {
        return ur.addUser(us.getFullName(), us.getEmail(), us.getPassword(), us.getAddress(), us.getPhone(), us.getRole().getId());
    }
    
    public boolean checkUserExist(String name) throws ClassNotFoundException, SQLException {
        return ur.checkUserExist(name);
    }
    
    public User getDetailedUser(int id) throws ClassNotFoundException, SQLException {
        return ur.getDetailedUser(id);
    }
    
    public boolean updateUser(int id, String name, String email, String password, String address, String phone, int roleid) throws ClassNotFoundException, SQLException {
        return ur.updateUser(id, name, email, password, address, phone, roleid);
    }
}
