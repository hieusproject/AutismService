/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author VanHau
 */
public class CareerType {
    private int career_id;
    private String career_title,career_description;

    public CareerType() {
    }

    public CareerType(int career_id, String career_title, String career_description) {
        this.career_id = career_id;
        this.career_title = career_title;
        this.career_description = career_description;
    }

    public int getCareer_id() {
        return career_id;
    }

    public void setCareer_id(int career_id) {
        this.career_id = career_id;
    }

    public String getCareer_title() {
        return career_title;
    }

    public void setCareer_title(String career_title) {
        this.career_title = career_title;
    }

    public String getCareer_description() {
        return career_description;
    }

    public void setCareer_description(String career_description) {
        this.career_description = career_description;
    }

    
   
}
