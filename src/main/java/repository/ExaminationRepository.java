/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entity.CareerType;
import entity.Child;
import entity.Examination;
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
public class ExaminationRepository implements RepositoryInterface{
     private static Connection connection= Conector.getConnection();
     static TestRepository testRepository= new TestRepository();
     static Rule_detailRepository rule_detailRepository= new Rule_detailRepository();
    public ArrayList<Object> getAll() {
        ArrayList<Object> childs= new ArrayList<Object>();
        try {
            String getSQL="SELECT * FROM `examination`";
            PreparedStatement getST= connection.prepareStatement(getSQL);
            ResultSet rs=getST.executeQuery();
            while (rs.next()) {      
                Examination exam= new Examination(rs.getInt("ex_id"), rs.getInt("c_id"),
                        rs.getInt("test_rule_id"),rs.getString("exam_result"),
                        rs.getDate("date"),rs.getString("ex_title"),rs.getInt("deleted"));
              childs.add(exam);
            }
        } catch (Exception e) {
        }
        return childs;
    }

    @Override
   public boolean save(Object ob) {
        try {
            Examination ex= (Examination) ob;
             String sqlString= "INSERT INTO `examination` (`c_id`,`test_rule_id`,`exam_result`,`date`,`ex_title`) VALUES (?,?,?,?,?)";
           PreparedStatement insertStatement= connection.prepareStatement(sqlString);
           insertStatement.setInt(1,ex.getC_id());
           insertStatement.setInt(2,ex.getTest_rule_id());
           insertStatement.setString(3,ex.getExam_result());
           insertStatement.setDate(4,ex.getDate());
           insertStatement.setString(5,ex.getTitle());
           int result=insertStatement.executeUpdate();
          
             if (result==0) {
                    System.out.println("insert failed");
                    return false;
              } else {
              return true; 
                }
        } catch (Exception e) {
            return false;
        }}
   
   
   public synchronized int saveAndreturnID(Examination ex){
       int newId=-1;
       try {
           if (save(ex)) {
           String getNewID="SELECT MAX(ex_id) as newest_Id FROM `examination`";
           PreparedStatement getIdPreparedStatement=connection.prepareStatement(getNewID);
           ResultSet rs= getIdPreparedStatement.executeQuery();
           while (rs.next()) {               
               newId=rs.getInt("newest_Id");
           }
           }
       } catch (Exception e) {
       }
       return newId;
   }
       
   @Override
    public boolean update(Object ob) {
    Examination exams=(Examination) ob; 
       try {  
         
           String sqlString= "UPDATE `examination` SET" 
                   + " `c_id`=?, `test_rule_id`=?"
                   + " `exam_result`=?, `date`=?, `ex_title`=? "
                   + " WHERE `ex_id`=?";
           PreparedStatement updateStatement= connection.prepareStatement(sqlString);
           updateStatement.setInt(1,exams.getC_id());
           updateStatement.setInt(2,exams.getTest_rule_id());
           updateStatement.setString(3, exams.getExam_result());
           updateStatement.setDate(4, exams.getDate());
           updateStatement.setString(5, exams.getTitle());
           updateStatement.setInt(6, exams.getEx_id());
           
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
    public boolean updateExamResult(int ex_id,String result_str) {
    
       try {  
         
           String sqlString= "UPDATE `examination` SET" 
                   + " `exam_result`=?"
                   + " WHERE `ex_id`=? and deleted=0";
           PreparedStatement updateStatement= connection.prepareStatement(sqlString);
           updateStatement.setString(1,result_str);
           updateStatement.setInt(2,ex_id);
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
         
           String sqlString= "UPDATE `examination` SET" 
                   + "`deleted`=?"
                   + " WHERE `ex_id`=?";
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
    return false;       //To change body of generated methods, choose Tools | Templates.
    }
   
    
    public ArrayList<Examination> getExamsOfChild(int c_id){
    ArrayList<Examination> result= new ArrayList<Examination>();
    try {
            String getSQL="SELECT * FROM `examination` where c_id=? and deleted=0  ORDER BY examination.date DESC";
            PreparedStatement getST= connection.prepareStatement(getSQL);
            getST.setInt(1, c_id);
            ResultSet rs=getST.executeQuery();
            while (rs.next()) {      
                Examination exam= new Examination(rs.getInt("ex_id"), rs.getInt("c_id"), rs.getInt("test_rule_id"),rs.getString("exam_result"),
                        rs.getDate("date"),rs.getString("ex_title"),rs.getInt("deleted"));
                System.out.println(exam.getDate());
                result.add(exam);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    
    return result;
    }
    public Map getSpecificExam(int ex_id){
        Map result= new HashMap();
      
         try {
            String getSQL="SELECT * FROM `examination` where ex_id=? and deleted=0";
            PreparedStatement getST= connection.prepareStatement(getSQL);
            getST.setInt(1, ex_id);
            ResultSet rs=getST.executeQuery();
            while (rs.next()) {      
                
                 int test_rule_id= rs.getInt("test_rule_id");
                 result.put("ex_id", rs.getInt("ex_id"));
                 result.put("c_id", rs.getInt("c_id"));
                 result.put("exam_result", rs.getString("exam_result"));
                 result.put("date",  rs.getDate("date"));
                 result.put("testdone",testRepository.getTestDoneOfEx(ex_id));
                 result.put("test_rule",rule_detailRepository.getTestTypeofRule(test_rule_id));
                 result.put("title", rs.getString("ex_title"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    
    return result;
    }
    
   
   

    public boolean deleteByRow(int key, int reference) {
          try {  
         
           String sqlString= "UPDATE `examinationview` SET" 
                   + "`deleted`=?"
                   + " WHERE `ex_id`=? and u_id=?";
           PreparedStatement updateStatement= connection.prepareStatement(sqlString);
           updateStatement.setInt(1, 1);
           updateStatement.setInt(2,key);
           updateStatement.setInt(3,reference);
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
     public static void main(String[] args) {
        ExaminationRepository examinationRepository= new ExaminationRepository();
         System.out.println(examinationRepository.getSpecificExam(1).get("ex_id"));
    }
}
