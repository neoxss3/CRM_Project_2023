/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crm22.entity;

/**
 *
 * @author anhbs
 */
public class Status {
    private String status_id;
    private String status_name;
    private String status_desc;

    public Status() {
    }

    public String getStatus_id() {
        return status_id;
    }

    public String getStatus_name() {
        return status_name;
    }

    public String getStatus_desc() {
        return status_desc;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public void setStatus_desc(String status_desc) {
        this.status_desc = status_desc;
    }
    
}
