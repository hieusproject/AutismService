/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entity.CareerType;
import entity.ExtraInfo;
import entity.Token;
import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author AnNguyen
 */
public class TokenRepository implements RepositoryInterface{
    private static Connection connection= Conector.getConnection();

    public boolean save(Object ob) {
        try {
            Token tk= (Token) ob;
             String sqlString= "INSERT INTO `token`(`u_id`, `token`) VALUES (?,?)";
           PreparedStatement insertStatement= connection.prepareStatement(sqlString);
           insertStatement.setInt(1,tk.getU_id());
           insertStatement.setString(2,tk.getToken_code());
           int result=insertStatement.executeUpdate();
          
             if (result==0) {
                    System.out.println("insert failed");
                    return false;
              } else {
              return true; 
                }
        } catch (Exception e) {
            return false;
        }
        
    }

        @Override
    public boolean update(Object ob) {
    Token token=(Token) ob; 
       try {  
         //`fINSERT INTO `token`(`u_id`, `token`) VALUES (?,?)";
           String sqlString= "UPDATE `token` SET" 
                   + " `token`=?"
                   + " WHERE `u_id`=?";
           PreparedStatement updateStatement= connection.prepareStatement(sqlString);
           updateStatement.setString(1,token.getToken_code());
           updateStatement.setInt(2,token.getToken_id());
          
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

     @Override
    public ArrayList<Object> getAll() {
        ArrayList<Object> tokens= new ArrayList<Object>();
        try {
            String getSQL="SELECT * FROM `token`";
            PreparedStatement getST= connection.prepareStatement(getSQL);
            ResultSet rs=getST.executeQuery();
            while (rs.next()) {     
                Token token= new Token(rs.getInt("tk_id"), rs.getInt("u_id"),rs.getString("token"));
                tokens.add(token);
            }
            
        } catch (Exception e) {
        }
        return tokens;
    }

    public Token getTokenByCode(String hashCode){
     try {
      
           String sqlString= "SELECT * FROM `token` WHERE token.token=?";
           PreparedStatement getStatement= connection.prepareStatement(sqlString);
                        getStatement.setString(1,hashCode);
           ResultSet rs= getStatement.executeQuery();
           
             while (rs.next()) {                 
                 Token token= new Token(rs.getInt("tk_id"), rs.getInt("u_id"),rs.getString("token"));
                 return token;
             }
       } catch (Exception e) {
           return null;
       }
    return null;
    
    }

    public int getMaxId(){
        int max_id=-1;
        try {
      
           String sqlString= "SELECT MAX(tk_id) as max_id FROM `token`";
           PreparedStatement getStatement= connection.prepareStatement(sqlString);
                      
           ResultSet rs= getStatement.executeQuery();
           
             while (rs.next()) {                 
                 
                 max_id= rs.getInt("max_id");
             }
       } catch (Exception e) {
           
       }
    return max_id; 
    
    }
    public static void main(String[] args) {
        System.out.println(new TokenRepository().getTokenByCode("62f67d43ced797b39b090dcd8ff61541cefeb3a5b841116a87ee7a6cd5326eaf").getU_id());
    }

    public boolean deleteByRow(int key, int reference) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
