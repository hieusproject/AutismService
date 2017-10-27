/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Date;

/**
 *
 * @author VanHau
 */
public class Solution {
    private int s_id,u_id;
    private String s_title, s_content, s_picture;
    private Date date_created;
    private int deleted;

    public Solution(int s_id, int u_id, String s_title, String s_content, String s_picture, Date date_created,int deleted) {
        this.s_id = s_id;
        this.u_id = u_id;
        this.s_title = s_title;
        this.s_content = s_content;
        this.s_picture = s_picture;
        this.date_created = date_created;
        this.deleted= deleted;
    }

    public int getS_id() {
        return s_id;
    }

    public int getU_id() {
        return u_id;
    }

    public String getS_title() {
        return s_title;
    }

    public String getS_content() {
        return s_content;
    }

    public String getS_picture() {
        return s_picture;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public void setS_title(String s_title) {
        this.s_title = s_title;
    }

    public void setS_content(String s_content) {
        this.s_content = s_content;
    }

    public void setS_picture(String s_picture) {
        this.s_picture = s_picture;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
    
}