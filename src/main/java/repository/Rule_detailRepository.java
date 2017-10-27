/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entity.ExtraInfo;
import entity.Rule_Detail;
import entity.TestType;
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
public class Rule_detailRepository implements RepositoryInterface{
     private static Connection connection= Conector.getConnection();
     public ArrayList<Object> getAll() {
        ArrayList<Object> rule_details= new ArrayList<Object>();
        try {
            String getSQL="SELECT * FROM `test_ruler_detail`";
            PreparedStatement getST= connection.prepareStatement(getSQL);
            ResultSet rs=getST.executeQuery();
            while (rs.next()) {      
              
                Rule_Detail rule_detail= new Rule_Detail(rs.getInt("detail_id"), rs.getInt("rule_id"),
                        rs.getString("test_type_id"),rs.getInt("level"));
              rule_details.add(rule_detail);
            }
            
        } catch (Exception e) {
        }
        return rule_details;
    }
         @Override
     public boolean save(Object ob) {
        try {
            Rule_Detail rl_dt= (Rule_Detail) ob;
             String sqlString= "INSERT INTO `test_ruler_detail` (`rule_id`,`test_type_id`,`level`)"
                     + " VALUES (?,?,?)";
           PreparedStatement insertStatement= connection.prepareStatement(sqlString);
           insertStatement.setInt(1,rl_dt.getRule_id());
           insertStatement.setString(2,rl_dt.getTest_type_id());
           insertStatement.setInt(3,rl_dt.getLevel());
           
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
    Rule_Detail detail=(Rule_Detail) ob; 
       try {  
         //`father_career_id`,`divorce_status`,"
                  //   + "`mother_career_id`,`monthly_income`,`height`,`weight`,`sex`,group
           String sqlString= "UPDATE `Rule_Detail` SET" 
                   + " `rule_id`=?, `test_type_id`=?"
                   + " `level`=?"
                   + " WHERE `detail_id`=?";
           PreparedStatement updateStatement= connection.prepareStatement(sqlString);
           updateStatement.setInt(1,detail.getRule_id());
           updateStatement.setString(2,detail.getTest_type_id());
           updateStatement.setInt(3,detail.getLevel());
           updateStatement.setInt(4,detail.getDetail_id());
         
           
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
    
    public Map getTestTypeofRule(int rule_id){
    Map result= new HashMap();
    ArrayList<TestType> types= new ArrayList<TestType>();
     try {
            String getSQL="SELECT test_type.type_id as type_id,test_type.type_name as type_name,test_type.question_src as question_src "
                    + "FROM `test_rule_detail` JOIN test_type"
                    + " ON test_rule_detail.type_id= test_type.type_id "
                    + "WHERE test_rule_id=?";
            PreparedStatement getST= connection.prepareStatement(getSQL);
            getST.setInt(1, rule_id);
            ResultSet rs=getST.executeQuery();
            while (rs.next()) {      
                TestType type= new TestType(rs.getInt("type_id"),rs.getString("type_name"),rs.getString("question_src"));
                types.add(type);
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    result.put("test_rule_id",rule_id);
    result.put("levels",types);
    return result;
    
    }
    public static void main(String[] args) {
        Rule_detailRepository rule_detailRepository= new Rule_detailRepository();
        System.out.println(rule_detailRepository.getTestTypeofRule(1).toString());
    }

    public boolean deleteByRow(int key, int reference) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
