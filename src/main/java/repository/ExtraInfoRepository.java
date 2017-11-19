/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entity.Examination;
import entity.ExtraInfo;
import entity.TestType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.stereotype.Repository;

/**
 *
 * @author VanHau
 */
@Repository

public class ExtraInfoRepository implements RepositoryInterface{
    private static Connection connection= Conector.getConnection();
     @Override
    public ArrayList<Object> getAll() {
        ArrayList<Object> extra_infos= new ArrayList<Object>();
        try {
            String getSQL="SELECT * FROM `extra_info`";
            PreparedStatement getST= connection.prepareStatement(getSQL);
            ResultSet rs=getST.executeQuery();
            while (rs.next()) {     
                ExtraInfo extra_info= new ExtraInfo(rs.getInt("c_id"), rs.getInt("father_career_id"),rs.getInt("divorce_status")
                ,rs.getInt("mother_career_id"),rs.getInt("monthly_income"),rs.getFloat("height"),
                rs.getFloat("weight"),rs.getInt("sex"),rs.getInt("group"));
                extra_infos.add(extra_info);
            }
            
        } catch (Exception e) {
        }
        return extra_infos;
    }

    @Override
     public boolean save(Object ob) {
        try {
            ExtraInfo exif= (ExtraInfo) ob;
           String sqlString= "INSERT INTO `extra_info`(`c_id`, `fater_career_id`, `divorce_status`, `mother_career_id`, `monthly_income`, `height`, `weight`, `sex`, `group`)"
                   + " VALUES (?,?,?,?,?,?,?,?,?)";
           PreparedStatement insertStatement= connection.prepareStatement(sqlString);
           insertStatement.setInt(1,exif.getC_id());
           insertStatement.setInt(2,exif.getFather_career_id());
           insertStatement.setInt(3,exif.getDivorce_status());
           insertStatement.setInt(4,exif.getMother_career_id());
           insertStatement.setInt(5,exif.getMonthly_income());
           insertStatement.setFloat(6,exif.getHeight());
           insertStatement.setFloat(7,exif.getWeight());
           insertStatement.setInt(8,exif.getSex());
           insertStatement.setInt(9,exif.getGroup());
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
        }
     
     }

        @Override
    public boolean update(Object ob) {
    ExtraInfo extrainfo=(ExtraInfo) ob; 
       try {  
         //`father_career_id`,`divorce_status`,"
                  //   + "`mother_career_id`,`monthly_income`,`height`,`weight`,`sex`,group
           String sqlString= "UPDATE `extra_info` SET" 
                   + " `father_career_id`=?, `divorce_status`=?"
                   + " `mother_career_id`=?, `monthly_income`=?"
                   + " `height`=?, `weight`=?"
                   + " `sex`=?, `group`=?"
                   + " WHERE `c_id`=?";
           PreparedStatement updateStatement= connection.prepareStatement(sqlString);
           updateStatement.setInt(1,extrainfo.getC_id());
           updateStatement.setInt(2,extrainfo.getFather_career_id());
           updateStatement.setInt(3,extrainfo.getDivorce_status());
           updateStatement.setInt(4,extrainfo.getMother_career_id());
           updateStatement.setFloat(5,extrainfo.getMonthly_income());
           updateStatement.setFloat(6,extrainfo.getHeight());
           updateStatement.setFloat(7, extrainfo.getWeight());
           updateStatement.setInt(8, extrainfo.getSex());
           updateStatement.setInt(9, extrainfo.getGroup());
           
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
    public static void main(String[] args) {
        ExtraInfoRepository extraInfoRepository = new ExtraInfoRepository();
        ExtraInfo extraInfo= new ExtraInfo(41, 2, 0, 2 ,1, 0, 0, 0, 0);
        System.out.println(extraInfoRepository.save(extraInfo));
    }

    public boolean deleteByRow(int key, int reference) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
