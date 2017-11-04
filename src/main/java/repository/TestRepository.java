/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entity.Examination;
import entity.ExtraInfo;
import entity.Solution;
import entity.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author AnNguyen
 */
public class TestRepository implements RepositoryInterface {
    private static Connection connection= Conector.getConnection();
    @Override
    public ArrayList<Object> getAll() {
        ArrayList<Object> tests= new ArrayList<Object>();
        try {
            String getSQL="SELECT * FROM `test`";
            PreparedStatement getST= connection.prepareStatement(getSQL);
            ResultSet rs=getST.executeQuery();
            while (rs.next()) {      
              
                Test test= new Test(rs.getInt("test_id"), rs.getInt("type_id"), rs.getInt("ex_id")
                        ,rs.getString("result_test"),
                        rs.getInt("deleted"));
              tests.add(test);
            }
            
        } catch (Exception e) {
        }
        return tests;
    }
   @Override
     public boolean save(Object ob) {
        try {
            Test test= (Test) ob;
             String sqlString= "INSERT INTO `test` (`type_id`,`ex_id`,`result_test`,`deleted`)"
                     + " VALUES (?,?,?,?)";
           PreparedStatement insertStatement= connection.prepareStatement(sqlString);
           insertStatement.setInt(1,test.getType_id());
           insertStatement.setInt(2,test.getEx_id());
           insertStatement.setString(3,test.getResult_test());
           insertStatement.setInt(4,test.getDeleted());
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
        }}


            @Override
    public boolean update(Object ob) {
    Test test=(Test) ob; 
       try {  
           String sqlString= "UPDATE `test` SET" 
                   + " `type_id`=?, `ex_id`=?"
                   + " `result_test`=?, `deleted`=?"
                   + " WHERE `test_id`=?";
           PreparedStatement updateStatement= connection.prepareStatement(sqlString);
           updateStatement.setInt(1,test.getType_id());
           updateStatement.setInt(2,test.getEx_id());
           updateStatement.setString(3,test.getResult_test());
           updateStatement.setInt(4,test.getDeleted());
           updateStatement.setFloat(5,test.getTest_id());
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
        try {  
           String sqlString= "UPDATE `test` SET" 
                   + " `deleted`=?"
                   + " WHERE `test_id`=?";
           PreparedStatement updateStatement= connection.prepareStatement(sqlString);
           updateStatement.setString(1,"0");
           updateStatement.setString(2,id);
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

   public ArrayList<Map> getTestDoneOfEx(int ex_id){
   
   ArrayList<Map> result= new ArrayList<Map>();
     try {
            String getSQL="SELECT * FROM `test` where ex_id=?";
            PreparedStatement getST= connection.prepareStatement(getSQL);
            getST.setInt(1,ex_id);
            ResultSet rs=getST.executeQuery();
            while (rs.next()) {      
              
                Map test= new HashMap();
                test.put("id",rs.getInt("test_id"));
                String result_test=  rs.getString("result_test");
                test.put("result", result_test.charAt(result_test.length()-1));
                result.add(test);
            }
            
        } catch (Exception e) {
        }
   
   return result;
   }

    public boolean deleteByRow(int key, int reference) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void main(String[] args) {
        TestRepository repository= new TestRepository();
        Test  test= new Test(0, 0, 0, "1 0 1 0 1", 0);
    }
}
