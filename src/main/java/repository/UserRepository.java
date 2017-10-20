/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;


import DataUtil.DataUtil;
import entity.User;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AnNguyen
 */
public class UserRepository implements RepositoryInterface{
    private static Connection connection= Conector.getConnection();
    
    public ArrayList<User> getNormalUser(){
    ArrayList<User> users=new ArrayList<User>();
     try {
      
           String sqlString= "SELECT * FROM `user`";
           PreparedStatement getStatement= connection.prepareStatement(sqlString);
           ResultSet rs= getStatement.executeQuery();
             while (rs.next()) {                 
                 User user= new User(rs.getInt("u_id"), rs.getString("username"),
                         rs.getString("password"), rs.getString("fullName"),
                         rs.getString("address"), rs.getString("phone"),
                         rs.getString("email"), rs.getDate("age"),
                         rs.getDate("date_created"),rs.getInt("role"));
                 if (user.getRole()==1) {
                     continue;
                 }
                 users.add(user);
             }
       } catch (Exception e) {
       }
    return users;
    }
     public User getUserByInput(String username,String password){
     try {
      
           String sqlString= "SELECT * FROM `user` WHERE userName= ? and password=?";
           PreparedStatement getStatement= connection.prepareStatement(sqlString);
                        getStatement.setString(1,username);
                        getStatement.setString(2,password);
           ResultSet rs= getStatement.executeQuery();
           
             while (rs.next()) {                 
                 User user= new User(rs.getInt("u_id"), rs.getString("username"),
                         rs.getString("password"), rs.getString("fullName"),
                         rs.getString("address"), rs.getString("phone"),
                         rs.getString("email"), rs.getDate("age"),
                         rs.getDate("date_created"),rs.getInt("role"));
                 return user;
             }
       } catch (Exception e) {
       }
    return null;
    
    }
      public boolean isExist(String username){
     try {
      
           String sqlString= "SELECT * FROM `user` WHERE userName= ?";
           PreparedStatement getStatement= connection.prepareStatement(sqlString);
                        getStatement.setString(1,username);     
           ResultSet rs= getStatement.executeQuery();
           
             while (rs.next()) {                 
                 User user= new User(rs.getInt("u_id"), rs.getString("username"),
                         rs.getString("password"), rs.getString("fullName"),
                         rs.getString("address"), rs.getString("phone"),
                         rs.getString("email"), rs.getDate("age"),
                         rs.getDate("date_created"),rs.getInt("role"));
                 return true;
             }
       } catch (Exception e) {
       }
    return false;
    
    }
         public User getUserbyUserName(String username){
     try {
      
           String sqlString= "SELECT * FROM `user` WHERE userName= ?";
           PreparedStatement getStatement= connection.prepareStatement(sqlString);
                        getStatement.setString(1,username);     
           ResultSet rs= getStatement.executeQuery();
           
             while (rs.next()) {                 
                 User user= new User(rs.getInt("u_id"), rs.getString("username"),
                         rs.getString("password"), rs.getString("fullName"),
                         rs.getString("address"), rs.getString("phone"),
                         rs.getString("email"), rs.getDate("age"),
                         rs.getDate("date_created"),rs.getInt("role"));
                 return user;
             }
       } catch (Exception e) {
           return null;
       }
    return null;
    
    }

    public boolean save(Object ob) {
       User user=(User) ob; 
       try {  
         
           String sqlString= "INSERT INTO `user`"
                   + " (`userName`, `password`, `fullName`, `address`, `phone`, `email`, `age`, `date_created`, `role`)"
                   + " VALUES (?,?,?,?,?,?,?,?,?)";
           PreparedStatement insertStatement= connection.prepareStatement(sqlString);
           insertStatement.setString(1,user.getUsername());
           insertStatement.setString(2,user.getPassword());
           insertStatement.setString(3, user.getFullname());
           insertStatement.setString(4, user.getAddress());
           insertStatement.setString(5, user.getPhone());
           insertStatement.setString(6, user.getEmail());
           insertStatement.setDate(7, user.getAge());
           insertStatement.setDate(8, user.getDate_created());
           insertStatement.setInt(9,user.getRole());
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

    public boolean update(Object ob) {
    User user=(User) ob; 
       try {  
         
           String sqlString= "UPDATE `user` SET" 
                   + " `userName`=?, `password`=?, `fullName`=?, `address`=?, "
                   + "`phone`=?, `email`=?, `age`=?, `date_created`=?, `role`=?"
                   + " WHERE `u_id`=?";
           PreparedStatement updateStatement= connection.prepareStatement(sqlString);
           updateStatement.setString(1,user.getUsername());
           updateStatement.setString(2,user.getPassword());
           updateStatement.setString(3, user.getFullname());
           updateStatement.setString(4, user.getAddress());
           updateStatement.setString(5, user.getPhone());
           updateStatement.setString(6, user.getEmail());
           updateStatement.setDate(7, user.getAge());
           updateStatement.setDate(8, user.getDate_created());
           updateStatement.setInt(9,user.getRole());
           updateStatement.setInt(10,user.getU_id());
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

    
    //lỡ xóa mẹ luôn cái user rồi, tự sữa đi
    public boolean deleteById(String id) {
//       try {  
//         
//           String sqlString= "DELETE FROM  `user`" 
//                   + " WHERE `u_id`=?";
//           PreparedStatement updateStatement= connection.prepareStatement(sqlString);
//           updateStatement.setString(1,id);
//           int result=updateStatement.executeUpdate();
//          
//           if (result==0) {
//               System.out.println("delete failed");
//             return false;
//               
//             
//         } else {
//              return true; 
//         }
//       } catch (Exception e) {
//           e.printStackTrace();
//       }
    return false;   
    }

    public ArrayList<Object> getAll() {
        ArrayList<Object> users=new ArrayList<Object>();
     try {
      
           String sqlString= "SELECT * FROM `user`";
           PreparedStatement getStatement= connection.prepareStatement(sqlString);
           ResultSet rs= getStatement.executeQuery();
             while (rs.next()) {    
            
                  User user= new User(rs.getInt("u_id"), rs.getString("username"),
                         rs.getString("password"), rs.getString("fullName"),
                         rs.getString("address"), rs.getString("phone"),
                         rs.getString("email"), rs.getDate("age"),
                         rs.getDate("date_created"),rs.getInt("role"));
                 users.add(user);
             }
       } catch (Exception e) {
       }
    return  users;
    }
     
    public static void main(String[] args) throws ParseException {
        UserRepository repository= new UserRepository();
        System.out.println(repository.getAll().size());
    }
}
