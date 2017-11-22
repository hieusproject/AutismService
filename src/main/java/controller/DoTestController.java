/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import entity.Test;
import entity.Token;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import repository.ChildRepository;
import repository.ExaminationRepository;
import repository.TestRepository;
import repository.TokenRepository;

/**
 *
 * @author AnNguyen
 */
@RestController
public class DoTestController {
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    ExaminationRepository examinationRepository;
    @Autowired
    ChildRepository childRepository;
    @Autowired
    TestRepository testRepository;
   
    static final String ONE="1";
    static final String ONE_STAR="1*"; 
    static final int CODO=1;
    static final int MCHAT=2;
    static final int CARS=3;

    //Mchat
    static final int[] one_star_position={2,7,9,14,15};// 
    static final int[] no_is_one_position={11,18,20,22};
    // result=-1->error
    public static boolean isOneStarQuestion(int i){
        for (int j = 0; j < one_star_position.length; j++) {
            int k = one_star_position[j];
            if (k==i) {
                return true;
            }
        }
        return false;
    }
    private static boolean is_No_is_One_Position(int index){
        for (int j = 0; j < no_is_one_position.length; j++) {
            int item = no_is_one_position[j];
            if (item==index) {
                return true;
            }
        }
        return false;
    }
    private static String[] normalizeMchatResult(String []intputs){
        for (int i = 0; i < intputs.length; i++) {
            if (is_No_is_One_Position(i+1)) {
                intputs[i]="1";
            }        
        }
       return intputs; 
    }
    private static int calculatingTest(String input,int test_type){
        int result=0;
        
        String [] inputs= input.trim().split(" ");
        int num_of_one=0,num_of_one_Star=0;
        double sum=0;
        double onestart_sum=0;
        if (test_type==CODO) {
            sum=getSum(inputs);
            if (sum>0) {
                result=1;
            }
            else{ result=0;}
        }
        if (test_type==MCHAT) {
            inputs=normalizeMchatResult(inputs);
            sum=getSum(inputs);
            onestart_sum=getSumOneStar(inputs);
            sum=sum-onestart_sum;
            if (sum>=3) {
                result=1;
            }
            else{
                if (onestart_sum==2) {
                    result=1;
                }
                if (onestart_sum<2) {
                    result=0;
                }
            }
            
        }
        if (test_type==CARS) {
             sum= getSum(inputs);
            if (sum>=15&&sum<=29.5) {
                result=0;
            }
             if (sum>=30&&sum<=36.5) {
                result=1;
            }
              if (sum>=37&&sum<=60) {
                result=2;
            }
            
        }
        
        return result;
    }
    
    public static double  getSum(String [] intputs){
    double sum=0;
        for (int i = 0; i < intputs.length; i++) {
            String intput = intputs[i];
            try {
                sum+=Double.parseDouble(intput);
            } catch (Exception e) {
                e.printStackTrace();
                sum=-1;
            }
        }
    return sum;
    }
    
    
    public  static double  getSumOneStar(String [] intputs){
    double sum=0;
        for (int i = 0; i < intputs.length; i++) {
            String intput = intputs[i];
            if (isOneStarQuestion(i+1)) {
                try {
                sum+=Double.parseDouble(intput);
                  } catch (Exception e) {
                e.printStackTrace();
                sum=-1;
                 }
            }
           
        }
    return sum;
    }
    @RequestMapping(value = "/do_test",method = RequestMethod.POST)
    public Map deletedExam(@RequestParam(name = "token") String token,
                           @RequestParam(name="ex_id") String ex_id_str,
                            @RequestParam(name="test_type") String test_type_str,
                            @RequestParam(name="c_id") String c_id_str,
                            @RequestParam(name="answers") String answers){
        Map response= new HashMap();
        Token tokenOb= tokenRepository.getTokenByCode(token);
        int c_id=0;        int ex_id=0;        int test_type=0;
        boolean error=false;
        try {
            c_id=Integer.parseInt(c_id_str);
            ex_id=Integer.parseInt(ex_id_str);
            test_type=Integer.parseInt(test_type_str);
        } catch (Exception e) {
            e.printStackTrace();
            error=true;
        }
        if (tokenOb==null) {
            error=true;
        }
        if (error) {
            response.put("status","0");
        } else {
            int result= calculatingTest(answers, test_type);
            String result_str=Integer.toString(result);
            String saved_result_test= answers+" "+result_str;
            Test doing_test= new Test(0, test_type, ex_id, saved_result_test, 0);
            testRepository.save(doing_test);
            response.put("status",1);
            if (result==0) {
                examinationRepository.updateExamResult(ex_id,"0");
                response.put("exam_result", 0);
            }
            else{
                response.put("status","1");
                response.put("test_result",result);
                int level_of_type_id=testRepository.getLevelOftypeID(test_type);
                int highest_level=testRepository.getHighestLevel();
                
                //get  value from database success
                if (level_of_type_id!=-1&&highest_level!=-1) {
                     if (level_of_type_id==highest_level) {
                        examinationRepository.updateExamResult(ex_id, "1");
                        response.put("exam_result", 1);
                    }else{
                     response.put("exam_result","none");
                     }
                }
                
            }
           response.put("test_result",result);
        }
        
        return response;
    }
    
    
    public static void main(String[] args) {
        String ansers="0.0 1.0 1.0 1.0 0.0 0.0 0.0";
        System.out.println(getSum(ansers.split(" ")));
        
    }
}