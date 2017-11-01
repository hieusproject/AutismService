/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entity.ChildSolution;
import entity.ChildSolutionRecommend;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ChildSolutionRecommendRepository implements RepositoryInterface{
    private static Connection connection= Conector.getConnection();

    public boolean save(Object ob) {
        try {
            ChildSolutionRecommend childsolutionreconmend= (ChildSolutionRecommend) ob;
            String sqlString= "INSERT INTO `child_solution_recommend`(`c_id`,`s_id`,`rating`) VALUES (?,?,?)";
           PreparedStatement insertStatement= connection.prepareStatement(sqlString);
           insertStatement.setInt(1,childsolutionreconmend.getC_id());
           insertStatement.setInt(2,childsolutionreconmend.getS_id());
           insertStatement.setInt(3,childsolutionreconmend.getRating() );
           int result=insertStatement.executeUpdate();
             if (result==0) {
                    System.out.println("insert failed");
                    return false;
              } else {
                 System.out.println("insert succeed");
              return true; 
                 
                }
        } catch (Exception e) {
            return false;
        }
    }
    
   

   public boolean update(Object ob) {
    ChildSolutionRecommend childs=(ChildSolutionRecommend) ob; 
       try {  
         
           String sqlString= "UPDATE `child_solution_recommend` SET" 
                   + " `rating`=? "
                   + " WHERE `c_id`=? and `s_id`=?";
           PreparedStatement updateStatement= connection.prepareStatement(sqlString);
           updateStatement.setInt(1,childs.getRating());
           updateStatement.setInt(2,childs.getC_id());
           updateStatement.setInt(3, childs.getS_id());
           
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
    return false;      
    }

    public ArrayList<Object> getAll() {
        ArrayList<Object> childsolutionrecommends= new ArrayList<Object>();
        try {
            String getSQL="SELECT * FROM `child_solution_recommend`";
            PreparedStatement getST= connection.prepareStatement(getSQL);
            ResultSet rs=getST.executeQuery();
            while (rs.next()) {     
              ChildSolutionRecommend childsolutionrecommend= new ChildSolutionRecommend(rs.getInt("c_id"), rs.getInt("s_id"),rs.getInt("rating"));
              childsolutionrecommends.add(childsolutionrecommend);
            }           
        } catch (Exception e) {
        }
        return childsolutionrecommends;
    }
   
    

    public static void main(String[] args) {
//       
      ChildSolutionRecommendRepository childs= new ChildSolutionRecommendRepository();
      ChildSolutionRecommend chi=new ChildSolutionRecommend(1, 2, 20);
      childs.update(chi);
    }

    @Override
    public boolean deleteByRow(int key, int reference) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}