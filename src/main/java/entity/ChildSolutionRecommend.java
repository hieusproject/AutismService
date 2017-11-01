/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author AnNguyen
 */
public class ChildSolutionRecommend {
    
    public ChildSolutionRecommend(int c_id, int s_id, int rating) {
        this.c_id = c_id;
        this.s_id = s_id;
        this.rating = rating;
    }

    public int getC_id() {
        return c_id;
    }

    public int getS_id() {
        return s_id;
    }

    public int getRating() {
        return rating;
    }
    private int c_id,s_id,rating;

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
