
package repository;

import entity.Child;
import entity.ChildSolution;
import entity.Token;
import entity.User;
import java.sql.Connection;
import java.sql.Date;
import static java.sql.JDBCType.NULL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;
@Repository

public class ChildSolutionRepository implements RepositoryInterface{
    private static Connection connection= Conector.getConnection();

    public boolean save(Object ob) {
        try {
            ChildSolution childsolution= (ChildSolution) ob;
             String sqlString= "INSERT INTO `child_solution`(`c_id`,`s_id`,`rating`) VALUES (?,?,?)";
           PreparedStatement insertStatement= connection.prepareStatement(sqlString);
           insertStatement.setInt(1,childsolution.getC_id());
           insertStatement.setInt(2,childsolution.getS_id());
           insertStatement.setInt(3,childsolution.getRating() );
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
    ChildSolution childs=(ChildSolution) ob; 
       try {  
        
           String sqlString= "UPDATE `child_solution` SET" 
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
        ArrayList<Object> childsolutions= new ArrayList<Object>();
        try {
            String getSQL="SELECT * FROM `child_solution`";
            PreparedStatement getST= connection.prepareStatement(getSQL);
            ResultSet rs=getST.executeQuery();
            while (rs.next()) {     
              ChildSolution childsolution= new ChildSolution(rs.getInt("c_id"), rs.getInt("s_id"),rs.getInt("rating"));
              childsolutions.add(childsolution);
            }           
        } catch (Exception e) {
        }
        return childsolutions;
    }
   
    

    public static void main(String[] args) {
//       
      ChildSolutionRepository childs= new ChildSolutionRepository();
      ChildSolution chi=new ChildSolution(2, 3, 20);
      childs.update(chi);
    }

    @Override
    public boolean deleteByRow(int key, int reference) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
