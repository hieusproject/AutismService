/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.ChildSolution;
import entity.Solution;
import entity.Token;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import repository.SolutionRepository;
import repository.TokenRepository;

/**
 *
 * @author AnNguyen
 */
@RestController
public class SolutionController {
    private static SolutionRepository solutionRepository= new SolutionRepository();
    private static TokenRepository tokenRepostirory= new TokenRepository();
    
    @RequestMapping(value = "/get_recommended_solution",method=RequestMethod.GET)
    public Map getRecommededSolution(@RequestParam(name="token") String token,
                                     @RequestParam(name="c_id") String c_idStr){
        Map respone= new HashMap();
        int c_id=Integer.parseInt(c_idStr);
        Token tokenOb = tokenRepostirory.getTokenByCode(token);
        if (tokenOb==null) {
            respone.put("status","0"); 
        }
        else{
            respone.put("status","1");
            ArrayList<Map> solutionList= solutionRepository.getTopByc_id(c_id);
            respone.put("solutions",solutionList);
            
        }
        return respone;
    }
    
    @RequestMapping(value = "/get_top_solution",method=RequestMethod.GET)
     public Map getTopbyLikesSolution(@RequestParam(name="token") String token,
                                     @RequestParam(name="page") String page){
        Map respone= new HashMap();
        int pageIndex=Integer.parseInt(page);
        Token tokenOb = tokenRepostirory.getTokenByCode(token);
        if (tokenOb==null) {
            respone.put("status","0"); 
        }
        else{
            respone.put("status","1");
            ArrayList<Map> solutionList= solutionRepository.getTopbyPage(pageIndex);
            respone.put("solutions",solutionList);
            
        }
        return respone;
    }
     
    @RequestMapping(value = "/get_user_solution",method=RequestMethod.GET)
    public Map getRatedSolutionByC_id(@RequestParam(name="token") String token,
                                     @RequestParam(name="c_id") String c_idStr){
        Map respone= new HashMap();
        int c_id=Integer.parseInt(c_idStr);
        Token tokenOb = tokenRepostirory.getTokenByCode(token);
        if (tokenOb==null) {
            respone.put("status","0"); 
        }
        else{
            respone.put("status","1");
            ArrayList<Map> solutionList= solutionRepository.getRatedSolution(c_id);
            respone.put("solutions",solutionList);
            
        }
        return respone;
    }
    
     @RequestMapping(value = "/get_solution",method=RequestMethod.GET)
    public Map getSolutionBys_id(@RequestParam(name="s_id") String s_id_str){
        Map respone= new HashMap();
        int s_id=Integer.parseInt(s_id_str);
        
        
        return respone;
    }
    
    @RequestMapping(value = "/new_solution",method = RequestMethod.POST)
    public Map newSolutionForChild(@RequestParam(name = "token") String token,
                                    @RequestParam(name = "c_id") String c_idStr,
                                    @RequestParam(name = "title") String title,
                                    @RequestParam(name = "content")String content,
                                    @RequestParam(name = "rating")String rating){
    Map respone= new HashMap();
    Token tokenOb = tokenRepostirory.getTokenByCode(token);
    int c_id=Integer.parseInt(c_idStr);
        
        if (tokenOb==null) {
            respone.put("status","0"); 
        }
        else{
            respone.put("status","1");
            Date date_created = new Date();
            Solution solution = new Solution(0, c_id, title, content, title, DataUtil.DataUtil.toSQLDATE(date_created));
            solutionRepository.save(solution);
            ChildSolution childSolution = new ChildSolution(c_id, c_id, c_id);
        }
       return respone; 
    }
    
    @RequestMapping(value = "/react_solution",method = RequestMethod.POST)
    public Map reactSolotion(
            @RequestParam(name="token") String token,
                             @RequestParam(name="s_id") String s_id_str,
                             @RequestParam(name="action") String action_str
    ){
        Map respone= new HashMap();
        Token tokenOb = tokenRepostirory.getTokenByCode(token);
        boolean error=false;
        String message="";
        int s_id=0;
        int action=0;
        try {
             s_id=Integer.parseInt(s_id_str);
             action=Integer.parseInt(action_str);
        } catch (Exception e) {
            error=true;
             message+= "your request contains invalid value, ";
        }
        if (action<1||action>2) {
            error=true;
            message+= "Invalid action, ";
        }
        if (tokenOb==null) {
            error=true;
            message+= "Please log in to continue";
        }
      
        if (error) {
            respone.put("status", "0");
            respone.put("message",message);
        } 
        else {
              int u_id= tokenOb.getU_id();
              solutionRepository.reactSolution(s_id, u_id, action);
              respone.put("status","1");
        }
         return respone;
    }
    @RequestMapping(value = "/rate_solution",method = RequestMethod.POST)
    public Map rateSolotion(
            @RequestParam(name="token") String token,
                             @RequestParam(name="s_id") String s_id_str,
                             @RequestParam(name="rate") String rate_str
    ){
        Map respone= new HashMap();
        Token tokenOb = tokenRepostirory.getTokenByCode(token);
        boolean error=false;
        String message="";
        int s_id=0;
        int rate=0;
        try {
             s_id=Integer.parseInt(s_id_str);
             rate=Integer.parseInt(rate_str);
        } catch (Exception e) {
            error=true;
             message+= "your request contains invalid value, ";
        }
       
        if (tokenOb==null) {
            error=true;
            message+= "Please log in to continue";
        }
      
        if (error) {
            respone.put("status", "0");
            respone.put("message",message);
        } 
        else {
              int u_id= tokenOb.getU_id();
              solutionRepository.solutionRating(s_id, u_id, rate);
              respone.put("status","1");
        }
         return respone;
    }
    
}
