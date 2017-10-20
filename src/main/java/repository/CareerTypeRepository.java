/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entity.CareerType;
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
public class CareerTypeRepository implements RepositoryInterface{
    private static Connection connection= Conector.getConnection();
     @Override
    public ArrayList<Object> getAll() {
        ArrayList<Object> career_types= new ArrayList<Object>();
        try {
            String getSQL="SELECT * FROM `career_type`";
            PreparedStatement getST= connection.prepareStatement(getSQL);
            ResultSet rs=getST.executeQuery();
            while (rs.next()) {     
                CareerType career_type= new CareerType(rs.getInt("career_id"), rs.getString("career_title"),rs.getString("career_description"));
                career_types.add(career_type);
            }
            
        } catch (Exception e) {
        }
        return career_types;
    }

    @Override
    public boolean save(Object ob) {
        CareerType ct=(CareerType) ob; 
       try {  
         
           String sqlString= "INSERT INTO `carrer_type`"
                   + " ( `carrer_title`, `career_description`)"
                   + " VALUES (?,?)";
           PreparedStatement insertStatement= connection.prepareStatement(sqlString);
           insertStatement.setString(1,ct.getCareer_title());
           insertStatement.setString(2, ct.getCareer_description());
          
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
    CareerType carrers=(CareerType) ob; 
       try {  
         
           String sqlString= "UPDATE `carrer_type` SET" 
                   + " `carrer_title`=?, `career_description`=?"
                   + " WHERE `caree_id`=?";
           PreparedStatement updateStatement= connection.prepareStatement(sqlString);
           updateStatement.setString(1,carrers.getCareer_title());
           updateStatement.setString(2,carrers.getCareer_description());
           updateStatement.setInt(3, carrers.getCareer_id());
           
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
    public static void main(String[] args) {
        CareerTypeRepository careerTypeRepository= new CareerTypeRepository();
        int size=careerTypeRepository.getAll().size();
        
        System.out.println(size);
    }
}
