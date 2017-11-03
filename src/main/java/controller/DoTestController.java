/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.ExaminationController.tokenRepository;
import entity.Token;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import repository.ChildRepository;
import repository.ExaminationRepository;
import repository.TokenRepository;

/**
 *
 * @author AnNguyen
 */
@RestController
public class DoTestController {
    private static TokenRepository tokenRepository= new TokenRepository();
    private static ExaminationRepository examinationRepository= new ExaminationRepository();
    private static ChildRepository childRepository= new ChildRepository();
    static final String ONE="1";
    static final String ONE_STAR="1*";   
    // result=-1->error
    public static double calculatingTest(String input,int test_type){
        double result=0;
        String [] inputs= input.trim().split(" ");
        int num_of_one=0,num_of_one_Star=0;
        
        if (test_type==1) {
            num_of_one=getNumsOfLetter(inputs,ONE);
            if (num_of_one>0) {
                result=1;
            }
            else{ result=0;}
        }
        if (test_type==2) {
            num_of_one=getNumsOfLetter(inputs, ONE);
            num_of_one_Star=getNumsOfLetter(inputs, ONE_STAR);
            if (num_of_one>=3) {
                result=1;
            }
            else{
                if (num_of_one_Star==2) {
                    result=1;
                }
                if (num_of_one_Star<2) {
                    result=0;
                }
            }
            
        }
        if (test_type==3) {
            double sum= getSum(inputs);
            
        }
        
        return result;
    }
    public static int  getNumsOfLetter(String [] intputs,String letter){
        int sum=0;
            for (int i = 0; i < intputs.length; i++) {
                String intput = intputs[i];
                if (intput.equals(letter)) {
                    sum++;
                }
            }
        return sum;
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
            double result= calculatingTest(answers, test_type);
           
        }
 
        return response;
    }
    
    
    public static void main(String[] args) {
        String ansers="0 0 0 0 0";
        System.out.println(calculatingTest(ansers,1));
    }
}
