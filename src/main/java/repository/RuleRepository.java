/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entity.Child;
import entity.Examination;
import entity.ExtraInfo;
import entity.Rule;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.stereotype.Repository;

/**
 *
 * @author AnNguyen
 */
@Repository

public class RuleRepository implements RepositoryInterface{
    private static Connection connection= Conector.getConnection();
     public ArrayList<Object> getAll() {
        ArrayList<Object> rules= new ArrayList<Object>();
        try {
            String getSQL="SELECT * FROM `test_rule`";
            PreparedStatement getST= connection.prepareStatement(getSQL);
            ResultSet rs=getST.executeQuery();
            while (rs.next()) {      
              
                Rule rule= new Rule(rs.getInt("rule_id"), rs.getDate("date_define"), rs.getInt("active"));
              rules.add(rule);
            }
            
        } catch (Exception e) {
        }
        return rules;
    }
      @Override
      public boolean save(Object ob) {
        try {
            Rule rl= (Rule) ob;
             String sqlString= "INSERT INTO `test_rule` (`date_define`,`active`) VALUES (?,?)";
           PreparedStatement insertStatement= connection.prepareStatement(sqlString);
           insertStatement.setDate(1,rl.getDate_define());
           insertStatement.setInt(2,rl.getActive());
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
    Rule rule=(Rule) ob; 
       try {  
         //`father_career_id`,`divorce_status`,"
                  //   + "`mother_career_id`,`monthly_income`,`height`,`weight`,`sex`,group
           String sqlString= "UPDATE `test_rule` SET" 
                   + " `date_define`=?, `active`=?"
                   + " WHERE `rule_id`=?";
           PreparedStatement updateStatement= connection.prepareStatement(sqlString);
           updateStatement.setDate(1,rule.getDate_define());
           updateStatement.setInt(2,rule.getActive());
           updateStatement.setInt(3,rule.getRule_id());
         
           
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
     public Rule getActivedRule() {
       Rule  rule= null;
        try {
            String getSQL="SELECT * FROM `test_rule` where active=1";
            PreparedStatement getST= connection.prepareStatement(getSQL);
            ResultSet rs=getST.executeQuery();
            while (rs.next()) {      
              
            rule= new Rule(rs.getInt("test_rule_id"), rs.getDate("date_created"), rs.getInt("active"));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rule;
    }
    public static void main(String[] args) {
        RuleRepository repository = new RuleRepository();
        System.out.println(repository.getActivedRule().getRule_id());
    }
    
}
