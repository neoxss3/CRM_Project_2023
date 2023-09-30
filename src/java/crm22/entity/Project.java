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
public class Project {

    private String project_id;
    private String project_name;
    private String starting_date;
    private String end_date;
    private User us;
    private Status stt;

    public Project() {
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getStarting_date() {
        return starting_date;
    }

    public void setStarting_date(String starting_date) {
        this.starting_date = starting_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public User getUs() {
        return us;
    }

    public void setUs(User us) {
        this.us = us;
    }

    public Status getStt() {
        return stt;
    }

    public void setStt(Status stt) {
        this.stt = stt;
    }
    
}
