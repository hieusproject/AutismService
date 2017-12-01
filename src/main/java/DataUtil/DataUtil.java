/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataUtil;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import org.json.simple.JSONObject;
import repository.TokenRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
/**
 *
 * @author AnNguyen
 */
public class DataUtil {
    private static TokenRepository tokenRepository= new TokenRepository();
    
    //utility function
    
    public static java.sql.Date toSQLDATE(java.util.Date javaDate)    {   
        return new java.sql.Date(javaDate.getTime()); 
    }
    public static int getRandomInt(){
        Random randomor= new Random();
        int newRan=0;
        do {            
            newRan=randomor.nextInt();
        } while (newRan<0);
        return newRan;        
    }
     public static String generatingRandomCode(){
        int characterRan;
        int charCode=0;
        Random random= new Random();
        StringBuffer captchaStrBuffer= new StringBuffer();
        for (int i = 0; i <10; i++) {
//    
              characterRan= Math.abs(random.nextInt())%62;
              if (characterRan<26) {
                charCode=65+characterRan;
            }
              else if (characterRan<52) {
                charCode=97+(characterRan-26);
            }
              else{
                 charCode=48+(characterRan-52); 
              
              }
           captchaStrBuffer.append((char) charCode);   
        }
    
    return captchaStrBuffer.toString();
    }
     
     
    public static String newTokenforUser(int u_id) throws NoSuchAlgorithmException{
         String newCode= Integer.toString(u_id)+
                    Integer.toString(tokenRepository.getMaxId())
                    +DataUtil.generatingRandomCode();
            newCode= Integer.toString(u_id)+PassWordUtil.hashPassword(newCode);
    return newCode;
    }
    
    
    
    public static Date StringtoDate(String dateString) throws ParseException{
		String DATE_FORMAT = "mm/dd/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Date date = sdf.parse(dateString);
		return date;
	}
    public static JSONObject readJsonObject(String src) throws IOException, org.json.simple.parser.ParseException{
        FileReader reader= new FileReader(src);
        BufferedReader bufferedReader= new BufferedReader(reader);
        String line= bufferedReader.readLine();
        String jSonString="";
        while (line!=null) {            
            jSonString+= line;
            line= bufferedReader.readLine();
        }   
       JSONParser parser = new JSONParser();
       JSONObject json = (JSONObject) parser.parse(jSonString);
    return json;
       
    }
    
    public static void main(String[] args) throws ParseException, IOException, org.json.simple.parser.ParseException {
        String src="C:/Users/AnNguyen/Documents/NetBeansProjects/Diagnosis_services/src/main/webapp/json.txt";
        String string="{\"phonetype\":\"N95\",\"cat\":\"WP\"}";
        System.out.println(string);
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(string);
        readJsonObject(src);
    }
}
