/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entity.Examination;
import entity.ExtraInfo;
import entity.Solution;
import entity.TestType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author VanHau
 */
public class SolutionRepository implements  RepositoryInterface{
    public static final int LIKE=1;
    public static final int SUBCRIBE=2;
    private static Connection connection= Conector.getConnection();
     @Override
    public ArrayList<Object> getAll() {
        ArrayList<Object> solutions= new ArrayList<Object>();
        try {
            String getSQL="SELECT * FROM `solution` where deleted=0";
            PreparedStatement getST= connection.prepareStatement(getSQL);
            ResultSet rs=getST.executeQuery();
            while (rs.next()) {     
                Solution solution= new Solution(rs.getInt("s_id"), rs.getInt("u_id"),rs.getString("s_title"),
                        rs.getString("s_content"),rs.getString("s_picture"),
                        rs.getDate("date_created"),rs.getInt("deleted"));
                solutions.add(solution);
            }
            
        } catch (Exception e) {
        }
        return solutions;
    }
     public ArrayList<Map> getTopByc_id(int c_id) {
        ArrayList<Map> result= new ArrayList<Map>();
        try {
            String getSQL="SELECT * FROM `solution_view` JOIN child_solution_recommend csr"
                    + " ON solution_view.id=csr.s_id "
                    + "WHERE csr.c_id=? and solution_view.deleted=0 "
                    + "ORDER BY solution_view.likes DESC LIMIT 5";
            PreparedStatement getST= connection.prepareStatement(getSQL);
            getST.setInt(1, c_id);
            ResultSet rs=getST.executeQuery();
    
            while (rs.next()) {     
                Map line= new HashMap();
                line.put("title",rs.getString("s_title"));
                line.put("content",rs.getString("s_content"));
                line.put("likes",rs.getInt("likes"));
                line.put("subs", rs.getInt("subcribes"));
                line.put("id",rs.getInt("id"));
                line.put("owner",rs.getString("contributer"));
                result.add(line);
            }
            
        } catch (Exception e) {
             e.printStackTrace();
        }
        return result;
    }
      public ArrayList<Map> getTopbyPage(int pageIndex) {
         int begin= (pageIndex-1)*10;
         int end=pageIndex*10-1;
        ArrayList<Map> result= new ArrayList<Map>();
        
        try {
            String getSQL="SELECT * FROM `solution_view` where deleted=0 ORDER BY solution_view.likes DESC LIMIT ?,?";
            PreparedStatement getST= connection.prepareStatement(getSQL);
            getST.setInt(1, begin);
            getST.setInt(2, end);
            ResultSet rs=getST.executeQuery();
             System.out.println("get sucsess");
            while (rs.next()) {     
                Map line= new HashMap();
                line.put("title",rs.getString("s_title"));
                line.put("content",rs.getString("s_content"));
                line.put("likes",rs.getInt("likes"));
                line.put("subs", rs.getInt("subcribes"));
                line.put("id",rs.getInt("id"));
                result.add(line);
            }
            
        } catch (Exception e) {
             e.printStackTrace();
        }
        return result;
    }
       public ArrayList<Map> getRatedSolution(int c_id) {
         
        ArrayList<Map> result= new ArrayList<Map>();
        
        try {
            String getSQL="SELECT solution.s_id as s_id,s_title,s_content,likeview.likes as likes"
                    + " FROM solution JOIN child_solution"
                    + " ON solution.s_id= child_solution.s_id "
                    + "left JOIN likeview "
                    + "ON solution.s_id=likeview.s_id  "
                    + " WHERE child_solution.c_id=  ?  and deleted=0";
            PreparedStatement getST= connection.prepareStatement(getSQL);
            getST.setInt(1, c_id);
            ResultSet rs=getST.executeQuery();
             System.out.println("get sucsess");
            while (rs.next()) {  
            
                Map line= new HashMap();
                line.put("title",rs.getString("s_title"));
                line.put("content",rs.getString("s_content"));
                line.put("likes",rs.getInt("likes"));
                Map solution= new HashMap();
                solution.put(Integer.toString(rs.getInt("s_id")), line);
                result.add(solution);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
     public boolean save(Object ob) {
        try {
            Solution sl= (Solution) ob;
             String sqlString= "INSERT INTO `solution` (`u_id`,`s_title`,`s_content`,`s_picture`,"
                     + "`date_created`) VALUES (?,?,?,?,?)";
           PreparedStatement insertStatement= connection.prepareStatement(sqlString);
           insertStatement.setInt(1,sl.getU_id());
           insertStatement.setString(2,sl.getS_title());
           insertStatement.setString(3,sl.getS_content());
           insertStatement.setString(4,sl.getS_picture());
           insertStatement.setDate(5,sl.getDate_created());
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
    Solution solution=(Solution) ob; 
       try {  
           String sqlString= "UPDATE `solution` SET" 
                   + " `u_id`=?, `s_title`=?"
                   + " `s_content`=?, `s_picture`=?"
                   + " `date_created`=?"
                   + " WHERE `s_id`=?";
           PreparedStatement updateStatement= connection.prepareStatement(sqlString);
           updateStatement.setInt(1,solution.getU_id());
           updateStatement.setString(2,solution.getS_title());
           updateStatement.setString(3,solution.getS_content());
           updateStatement.setString(4,solution.getS_picture());
           updateStatement.setDate(5,solution.getDate_created());
           updateStatement.setInt(6,solution.getS_id());
           
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
    public Map reactSolution(int s_id,int u_id,int action){
    Map result= new HashMap();
    String table="solution_like";
        if (action==SolutionRepository.SUBCRIBE) {
            table="solution_subcribed";
        } 
        try {
            String insertStm="INSERT INTO "+table+" (`s_id`, `u_id`) VALUES (?,?)";
            PreparedStatement preparedStatement= connection.prepareStatement(insertStm);
            preparedStatement.setInt(1,s_id);
            preparedStatement.setInt(2,u_id);
            int excutedResult=preparedStatement.executeUpdate();
            if (excutedResult>0) {
                result.put("s_id", s_id);
                result.put("u_id",u_id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
   
    return result;
    }
    public Map solutionRating(int s_id,int u_id,int rate){
    Map result= new HashMap();
        try {
            String insertStm="INSERT INTO `child_solution`(`c_id`, `s_id`, `rating`) VALUES (?,?,?)";
            PreparedStatement preparedStatement= connection.prepareStatement(insertStm);
            preparedStatement.setInt(1,s_id);
            preparedStatement.setInt(2,u_id);
            preparedStatement.setInt(3, rate);
            int excutedResult=preparedStatement.executeUpdate();
            if (excutedResult>0) {
                result.put("s_id", s_id);
                result.put("u_id",u_id);
                result.put("u_id",rate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
   
    return result;
    }
    @Override
    public boolean deleteById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

    public boolean deleteByRow(int key, int reference) {
        try {  
           String sqlString= "UPDATE `solution` SET `deleted`=1 WHERE s_id=? and u_id=?";
           PreparedStatement updateStatement= connection.prepareStatement(sqlString);
             updateStatement.setInt(1,key);
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
    public  Map getSpecificSolution(int s_id){
    Map result= new HashMap();
        try {
            String getSql= "SELECT * FROM `solution_view` where id=? and deleted=0";
            PreparedStatement preparedStatement=connection.prepareStatement(getSql);
            preparedStatement.setInt(1, s_id);
            ResultSet rs= preparedStatement.executeQuery();
            while (rs.next()) {                
                result.put("s_title",rs.getString("s_title"));
                result.put("s_content",rs.getString("s_content"));
                result.put("likes",rs.getInt("likes"));
                result.put("subs",rs.getInt("subcribes"));
                result.put("contributer",rs.getString("contributer"));
                result.put("rating",rs.getString("rating"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    return result;
    }
    
    ///kiem tra user da like or subcribe hay cha
    public Map checkReactedSolutionOfUser(int u_id,int s_id){
        Map result = new HashMap();
        int liked=0;
        int subcribed=0;
        try {
            String checkLikedSql="SELECT * FROM `solution_like` WHERE u_id=?";
            String checkSubcribedSql="SELECT * FROM `solution_subcribed` WHERE u_id=?";
            PreparedStatement likedPreparedStatement= connection.prepareStatement(checkLikedSql);
            likedPreparedStatement.setInt(1, u_id);
            PreparedStatement subedPreparedStatement= connection.prepareStatement(checkSubcribedSql);
            subedPreparedStatement.setInt(1, u_id);
            ResultSet rs1= likedPreparedStatement.executeQuery();
            ResultSet rs2= subedPreparedStatement.executeQuery();
            while(rs1.next()){
                liked=1;
                break;
            }
            while(rs1.next()){
                subcribed=1;
                break;
            }
            
        } catch (Exception e) {
        }
        result=getSpecificSolution(s_id);
        result.put("liked",liked);
        result.put("subcribed",subcribed);
        return result;
    }
    
    
    
      public static void main(String[] args) {
        SolutionRepository soRepository= new SolutionRepository();
        soRepository.checkReactedSolutionOfUser(2, 2);
    }
}
