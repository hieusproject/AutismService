/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DataUtil.DataUtil;
import entity.Examination;
import entity.Rule;
import entity.Token;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import repository.ChildRepository;
import repository.ExaminationRepository;
import repository.RuleRepository;
import repository.TokenRepository;
import repository.UserRepository;

/**
 *
 * @author AnNguyen
 */
@RestController
public class ExaminationController {
   static TokenRepository tokenRepository= new TokenRepository();
   static  ExaminationRepository examinationRepository= new ExaminationRepository();
   static RuleRepository ruleRepository = new RuleRepository();
   static UserRepository userRepository = new UserRepository();
   static ChildRepository childRepository = new ChildRepository();
    public ExaminationController() {
    }
    
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
    
    @RequestMapping(value = "/delete_exam",method = RequestMethod.POST)
    public Map deletedExam(@RequestParam(name = "token") String token,
                           @RequestParam(name="ex_id") String ex_id_str){
        Map response= new HashMap();
        Token tokenOb= tokenRepository.getTokenByCode(token);
        if (tokenOb==null) {
            response.put("status","0");
        } else {
            examinationRepository.deleteByRow(Integer.parseInt(ex_id_str),tokenOb.getU_id());
            response.put("status","1");
        }
 
        return response;
    }
    @RequestMapping(value = "create_examination",method = RequestMethod.POST)
    
     public Map create_examination(@RequestParam(name = "token") String token,
                                    @RequestParam(name="c_id") String c_id_str,
                                    @RequestParam(name="title") String title){
        
     Map response= new HashMap();
     
     Token tokenOb= tokenRepository.getTokenByCode(token);
    int c_id= Integer.parseInt(c_id_str);
        if (tokenOb==null) {
            response.put("status","0");
        }
        else {
              if (childRepository.isManaged(tokenOb.getU_id(), c_id)) {
              Rule rule= ruleRepository.getActivedRule();
              Date date_create = new Date();
              Examination exam= new Examination(0, c_id, rule.getRule_id(),"0", DataUtil.toSQLDATE(date_create),title,0);
              int new_ex_id =examinationRepository.saveAndreturnID(exam);
              if (new_ex_id!=(-1)) {
                exam.setEx_id(c_id);
                response.put("status","1");
                response.put("New_Exam",exam);
            }
            }else{
                  response.put("status","0");
                  response.put("error","You don't manage this child");
              
              
              }
             
        }
        
    return response;
    }
    
    
    public static void main(String[] args) {
        System.out.println(examinationRepository.getExamsOfChild(1).size());
        
    }
}
