/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entity.Child;
import entity.Token;
import entity.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChildRepository implements RepositoryInterface{
    private static Connection connection= Conector.getConnection();

    public boolean save(Object ob) {
        try {
            Child child= (Child) ob;
             String sqlString= "INSERT INTO `child`(`u_id`,`fullName`,`date_of_birth`,`father_name`,"
                     + "`mother_name`,`date_created`,`deleted`,`image_url`) VALUES (?,?,?,?,?,?,?,?)";
           PreparedStatement insertStatement= connection.prepareStatement(sqlString);
           insertStatement.setInt(1,child.getU_id());
           insertStatement.setString(2,child.getFullName());
           insertStatement.setDate(3,child.getDate_of_birth());
           insertStatement.setString(4,child.getFather_name());
           insertStatement.setString(5,child.getMother_name());
           insertStatement.setDate(6,child.getDate_created());
           insertStatement.setInt(7,child.getDeleted());
           insertStatement.setString(8,child.getImageURL());
           int result=insertStatement.executeUpdate();
          
             if (result==0) {
                    System.out.println("insert failed");
                    return false;
              } else {
                    return true; 
                }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
     public synchronized int newAndreturnId(Child child) {
        int id=-1;
        try {
            boolean success= save(child);
             if (!success) {
                     System.out.println("insert failed");
                     id=-1;
              } else {
                    String getIdSTM="SELECT MAX(c_id) as id FROM `child`";
                    PreparedStatement getPreparedStatement = connection.prepareStatement(getIdSTM);
                    ResultSet resultSet= getPreparedStatement.executeQuery();
                    while (resultSet.next()) {                     
                     id=resultSet.getInt("id");
                 }
                }
        } catch (Exception e) {
           id=-1;
          e.printStackTrace();
        }
          return id;  
    }

   public boolean update(Object ob) {
    Child childs=(Child) ob; 
       try {  
         
           String sqlString= "UPDATE `child` SET" 
                   + " `u_id`=?, `fullName`=?, `date_of_birth`=?, `father_name`=?, "
                   + "`mother_name`=?, `deleted`=? ,`image_url`"
                   + " WHERE `c_id`=?";
           PreparedStatement updateStatement= connection.prepareStatement(sqlString);
           updateStatement.setInt(1,childs.getU_id());
           updateStatement.setString(2,childs.getFullName());
           updateStatement.setDate(3, childs.getDate_of_birth());
           updateStatement.setString(4, childs.getFather_name());
           updateStatement.setString(5, childs.getMother_name());
           updateStatement.setInt(6, childs.getDeleted());
           updateStatement.setString(7,childs.getImageURL());
           updateStatement.setInt(8,childs.getC_id());
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

    public boolean deleteById(String id) {
       try {  
         
           String sqlString= "UPDATE `child` SET" 
                   + "`deleted`=?"
                   + " WHERE `c_id`=?";
           PreparedStatement updateStatement= connection.prepareStatement(sqlString);
           updateStatement.setInt(1, 1);
           updateStatement.setInt(2,Integer.parseInt(id));
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
    
    
    
public boolean isManaged(int u_id, int c_id) {
        try {

            String sqlString = "SELECT * FROM child WHERE  `u_id`=? AND `c_id`=?";
            PreparedStatement getM = connection.prepareStatement(sqlString);
            getM.setInt(1, u_id);
            getM.setInt(2, c_id);
            ResultSet result = getM.executeQuery();
            if (result.next() == false) {
                System.out.println("deo co");
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    public ArrayList<Object> getAll() {
        ArrayList<Object> childs= new ArrayList<Object>();
        try {
            String getSQL="SELECT * FROM `child`";
            PreparedStatement getST= connection.prepareStatement(getSQL);
            ResultSet rs=getST.executeQuery();
            while (rs.next()) {      
              
                Child child= new Child(rs.getInt("c_id"), rs.getInt("u_id"), rs.getString("fullName"), 
                        rs.getDate("date_of_birth"), rs.getString("father_name"), rs.getString("mother_name"),
                         rs.getDate("date_created"), rs.getInt("deleted"),rs.getString("image_url"));
              childs.add(child);
            }
            
        } catch (Exception e) {
        }
        return childs;
    }
    public ArrayList<Map> getChildsOfUser(int id) {
        ArrayList<Map> childs= new ArrayList<Map>();
        try {
            String getSQL="SELECT * FROM `child` WHERE u_id=? and deleted=0";
            PreparedStatement getST= connection.prepareStatement(getSQL);
            getST.setInt(1, id);
            ResultSet rs=getST.executeQuery();
            while (rs.next()) {      
                Map object= new HashMap();
                    object.put("c_id", Integer.toString(rs.getInt("c_id")));
                    object.put("name", rs.getString("fullName"));
                    object.put("date_of_birth", rs.getDate("date_of_birth"));
                    object.put("father", rs.getString("father_name"));
                    object.put("mother", rs.getString("mother_name"));
                    object.put("picture", rs.getString("image_url"));
                childs.add(object);
            }
            
        } catch (Exception e) {
        }
        return childs;
    }
  

    public boolean deleteByRow(int key, int reference) {
         try {  
         
           String sqlString= "UPDATE `child` SET" 
                   + "`deleted`=1"
                   + " WHERE `c_id`=? and u_id=?";
           PreparedStatement updateStatement= connection.prepareStatement(sqlString);
           updateStatement.setInt(1, key);
           updateStatement.setInt(2,reference);
         
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
    
    public Map getChildById(int c_id){
    Map child= new HashMap();
        try {
                String getSql="SELECT * FROM `child_view` WHERE c_id=?";
                PreparedStatement getPreparedStatement= connection.prepareStatement(getSql);
                getPreparedStatement.setInt(1, c_id);
                ResultSet rs=getPreparedStatement.executeQuery();
                while (rs.next()) {                
                child.put("name", rs.getString("name"));
                child.put("date_of_birth", rs.getDate("date_of_birth"));
                child.put("father", rs.getString("father"));
                child.put("mother", rs.getString("mother"));
                child.put("image_url", rs.getString("image_url"));
                child.put("monthly_income", rs.getString("income"));
                child.put("father_career", rs.getString("father_carrer"));
                child.put("mother_career", rs.getString("mother_carrer"));
                child.put("sex", rs.getInt("sex"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    return child;
    }
    
    public static void main(String[] args) {
       
      ChildRepository childRepository= new ChildRepository();
     
        Date date= new Date(1232343);
       Child child= new Child(0, 2, "adad",
               date, "da", "dads",
                date, 0, "dasds");
      int newId=childRepository.newAndreturnId(child);
        System.out.println(newId);
    }
}
