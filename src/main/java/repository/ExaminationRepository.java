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
                Examination exam= new Examination(rs.getInt("ex_id"), rs.getInt("c_id"), rs.getInt("test_rule_id"),rs.getString("exam_result"),
                        rs.getDate("date"));
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
             String sqlString= "INSERT INTO `examination` (`c_id`,`test_rule_id`,`exam_result`,`date`) VALUES (?,?,?,?)";
           PreparedStatement insertStatement= connection.prepareStatement(sqlString);
           insertStatement.setInt(1,ex.getC_id());
           insertStatement.setInt(2,ex.getTest_rule_id());
           insertStatement.setString(3,ex.getExam_result());
           insertStatement.setDate(4,ex.getDate());
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

        @Override
    public boolean update(Object ob) {
    Examination exams=(Examination) ob; 
       try {  
         
           String sqlString= "UPDATE `examination` SET" 
                   + " `c_id`=?, `test_rule_id`=?"
                   + " `exam_result`=?, `date`=?"
                   + " WHERE `ex_id`=?";
           PreparedStatement updateStatement= connection.prepareStatement(sqlString);
           updateStatement.setInt(1,exams.getC_id());
           updateStatement.setInt(2,exams.getTest_rule_id());
           updateStatement.setString(3, exams.getExam_result());
           updateStatement.setDate(4, exams.getDate());
           updateStatement.setInt(5, exams.getEx_id());
           
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
    
    
    public ArrayList<Examination> getExamsOfChild(int c_id){
    ArrayList<Examination> result= new ArrayList<Examination>();
    try {
            String getSQL="SELECT * FROM `examination` where c_id=?";
            PreparedStatement getST= connection.prepareStatement(getSQL);
            getST.setInt(1, c_id);
            ResultSet rs=getST.executeQuery();
            while (rs.next()) {      
                Examination exam= new Examination(rs.getInt("ex_id"), rs.getInt("c_id"), rs.getInt("test_rule_id"),rs.getString("exam_result"),
                        rs.getDate("date"));
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
            String getSQL="SELECT * FROM `examination` where ex_id=?";
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
                 result.put("test_rule",rule_detailRepository.getTestTypeofRule(ex_id));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    
    return result;
    }
    
   
    public static void main(String[] args) {
        ExaminationRepository examinationRepository= new ExaminationRepository();
        examinationRepository.getSpecificExam(1);
    }
}
