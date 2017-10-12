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

/**
 *
 * @author VanHau
 */
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
             String sqlString= "INSERT INTO `extra_info` (`father_career_id`,`divorce_status`,"
                     + "`mother_career_id`,`monthly_income`,`height`,`weight`,`sex`,group`)"
                     + " VALUES (?,?,?,?,?,?,?,?)";
           PreparedStatement insertStatement= connection.prepareStatement(sqlString);
           insertStatement.setInt(1,exif.getFather_career_id());
           insertStatement.setInt(2,exif.getDivorce_status());
           insertStatement.setInt(3,exif.getMother_career_id());
           insertStatement.setInt(4,exif.getMonthly_income());
           insertStatement.setFloat(5,exif.getHeight());
           insertStatement.setFloat(6,exif.getWeight());
           insertStatement.setInt(7,exif.getSex());
           insertStatement.setInt(8,exif.getGroup());
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
           updateStatement.setInt(1,extrainfo.getFather_career_id());
           updateStatement.setInt(2,extrainfo.getDivorce_status());
           updateStatement.setInt(3,extrainfo.getMother_career_id());
           updateStatement.setInt(4,extrainfo.getMonthly_income());
           updateStatement.setFloat(5,extrainfo.getHeight());
           updateStatement.setFloat(6,extrainfo.getWeight());
           updateStatement.setInt(7, extrainfo.getSex());
           updateStatement.setInt(8, extrainfo.getGroup());
           updateStatement.setInt(9, extrainfo.getC_id());
           
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
}
