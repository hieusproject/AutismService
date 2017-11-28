/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entity.ChildChild;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 *
 * @author AnNguyen
 */
@Repository
public class ClusterRepository {
    private static Connection connection= Conector.getConnection();
     
    public ArrayList<Map> getAll() {
        ArrayList<Map> clusters= new ArrayList<Map>();
        try {
            String getSQL="SELECT * FROM `cluster`";
            PreparedStatement getST= connection.prepareStatement(getSQL);
            ResultSet rs=getST.executeQuery();
            while (rs.next()) { 
                Map cluster= new HashMap();
                cluster.put("groupId",rs.getInt("groupId"));
                cluster.put("center",rs.getString("center"));
                clusters.add(cluster);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clusters;
     }
     public Map getCenterById(int groupId) {
      
        try {
            String getSQL="SELECT * FROM `cluster` WHERE groupId=?";
            PreparedStatement getST= connection.prepareStatement(getSQL);
            getST.setInt(1, groupId);
            ResultSet rs=getST.executeQuery();
            while (rs.next()) { 
                Map cluster= new HashMap();
                cluster.put("groupId",rs.getInt("groupId"));
                cluster.put("center",rs.getString("center"));
                return cluster;
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
     }
     
     
     //
     private static double getDistances(double []point,double []center){
         double distance_square=0;
         double distance=0;
         if (point.length==center.length) {
             for (int i = 0; i < center.length; i++) {
                 double xi=center[i]-point[i];
                 distance_square+= Math.pow(xi, 2);
                 
             }
             distance=Math.abs(Math.sqrt(distance_square));
         }
         else{
         
             distance=-1;
         }
     
     return distance;
     }
     private static double[] toDoubleArray(String point){
     double [] array= new double[60];
     //initialize array
         for (int i = 0; i < array.length; i++) {
             array[i]=0;
         }
     String[] array_str=point.split(" ");
       for (int i = 0; i < array_str.length; i++) {
             array[i]=Double.parseDouble(array_str[i]);
             
         }
     
     return array;
      }
     
     public int predicting_Group(String user_answer){
        double[] point=toDoubleArray(user_answer);
        ArrayList<Map> centers=getAll();
        double min_distance= getDistances(point,toDoubleArray((String) centers.get(0).get("center")));
        int groupId=1;
         for (int i = 1; i < centers.size(); i++) {
             Map cluster = centers.get(i);
             String centerz_str= (String) cluster.get("center");
             double[] center=toDoubleArray(centerz_str);
             double distance= getDistances(point, center);
             if (distance<=min_distance) {
                 groupId=i+1;
                 min_distance=distance;
                         
             }
             
         }
     
     return groupId;
     }
    public static void main(String[] args) {
        double [] array= new double[60];
        ClusterRepository repository= new ClusterRepository();
       
//        toDoubleArray("0 1 1.5 1 2.5 0 1 0");
    }
}
