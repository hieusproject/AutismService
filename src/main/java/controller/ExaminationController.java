/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Examination;
import entity.Token;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import repository.ExaminationRepository;
import repository.TokenRepository;

/**
 *
 * @author AnNguyen
 */
@RestController
public class ExaminationController {
   static TokenRepository tokenRepository= new TokenRepository();
   static  ExaminationRepository examinationRepository= new ExaminationRepository();
    
    
    
    @RequestMapping(value = "/get_exam_list",method = RequestMethod.GET)
    public Map getExamOfaChild( 
            @RequestParam(name="token") String token,
            @RequestParam(name="c_id") String c_id_str   ) {
     Map response= new HashMap();
     Token tokenOb= tokenRepository.getTokenByCode(token);
     int c_id= Integer.parseInt(c_id_str);
        if (tokenOb==null) {
            response.put("status","0");
        }
        else {
             ArrayList<Examination> examinations= examinationRepository.getExamsOfChild(c_id);
              response.put("exam_list", examinations);
        }
        
    return response;
    }
    
    
    
    @RequestMapping(value = "/get_specifed_exam",method = RequestMethod.GET)
    public Map getSpecificedExam(@RequestParam(name = "token") String token,
                                    @RequestParam(name="ex_id") String ex_id_str){
        
     Map response= new HashMap();
     
    Token tokenOb= tokenRepository.getTokenByCode(token);
    int ex_id= Integer.parseInt(ex_id_str);
        if (tokenOb==null) {
            response.put("status","0");
        }
        else {
              response=examinationRepository.getSpecificExam(ex_id);
        }
        
    return response;
    }
    
    
    
    public static void main(String[] args) {
        System.out.println(examinationRepository.getExamsOfChild(1).size());
        
    }
}
