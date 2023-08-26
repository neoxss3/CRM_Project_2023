/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crm22.service;

import crm22.entity.Role;
import crm22.repositories.RoleRepository;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author anhbs
 */
public class RoleService {
    private RoleRepository rp = new RoleRepository();
    public boolean addRole(String name,String desc) throws ClassNotFoundException, SQLException{
        return rp.insertRole(name, desc);
    }
    public boolean getAllRole(List<Role> res) throws ClassNotFoundException{
        rp.getAllRole(res);
        return res.size() > 0;
    }
    public boolean deleteRole(int id) throws ClassNotFoundException{
        return rp.deleteRole(id);
    }
}
