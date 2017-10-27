/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.ArrayList;

/**
 *
 * @author AnNguyen
 */
public interface RepositoryInterface {
    public boolean save(Object ob); 
    public boolean update(Object ob); 
    public boolean deleteById(String id);
    public ArrayList<Object> getAll(); 
     public boolean deleteByRow(int key,int reference );
}
