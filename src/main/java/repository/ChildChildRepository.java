/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entity.CareerType;
import entity.ChildChild;
import entity.TestType;
import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author VanHau
 */
public class ChildChildRepository implements RepositoryInterface{
    private static Connection connection= Conector.getConnection();
     @Override
    public ArrayList<Object> getAll() {
        ArrayList<Object> child_childs= new ArrayList<Object>();
        try {
            String getSQL="SELECT * FROM `chile_child`";
            PreparedStatement getST= connection.prepareStatement(getSQL);
            ResultSet rs=getST.executeQuery();
            while (rs.next()) {     
                ChildChild child_child= new ChildChild(rs.getInt("c_id1"), rs.getFloat("similarity"),rs.getInt("c_id2"));
                child_childs.add(child_child);
            }
            
        } catch (Exception e) {
        }
        return child_childs;
    }

    @Override
    public boolean save(Object ob) {
       ChildChild c_child=(ChildChild) ob; 
       try {  
         
           String sqlString= "INSERT INTO `chile_child`"
                   + " (`similarity`, `c_id2`, )"
                   + " VALUES (?,?)";
           PreparedStatement insertStatement= connection.prepareStatement(sqlString);
           insertStatement.setFloat(1,c_child.getSimilarity());
           insertStatement.setInt(2,c_child.getC_id2());
           int result=insertStatement.executeUpdate();
          
           if (result==0) {
               System.out.println("insert failed");
             return false;
               
             
         } else {
              return true; 
         }
       } catch (Exception e) {
           e.printStackTrace();
       }
    return false;  
    }

    @Override
    public boolean update(Object ob) {
    ChildChild childs=(ChildChild) ob; 
       try {  
         
           String sqlString= "UPDATE `chile_child` SET" 
                   + " `similarity`=?, `c_id2`=?"
                   + " WHERE `c_id1`=?";
           PreparedStatement updateStatement= connection.prepareStatement(sqlString);
           updateStatement.setFloat(1,childs.getSimilarity());
           updateStatement.setInt(2,childs.getC_id2());
           updateStatement.setInt(3, childs.getC_id1());
           
           int result=updateStatement.executeUpdate();
          
           if (result==0) {
               System.out.println("update failed");
             return false;
               
             
         } else {
              return true; 
         }
       } catch (Exception e) {
           e.printStackTrace();
       }
    return false;      
    }

    @Override
    public boolean deleteById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean deleteByRow(int key, int reference) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
