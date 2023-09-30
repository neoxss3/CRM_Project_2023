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
public class Task {
    private String task_id;
    private String task_name;
    private String task_desc;
    private String starting_date;
    private String end_date;
    private Project pj;
    private Status stt;

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getTask_desc() {
        return task_desc;
    }

    public void setTask_desc(String task_desc) {
        this.task_desc = task_desc;
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

    public Project getPj() {
        return pj;
    }

    public void setPj(Project pj) {
        this.pj = pj;
    }

    public Status getStt() {
        return stt;
    }

    public void setStt(Status stt) {
        this.stt = stt;
    }
    
}
