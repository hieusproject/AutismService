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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import repository.ChildSolutionRepository;
import repository.SolutionRepository;
import repository.TokenRepository;

/**
 *
 * @author AnNguyen
 */
@RestController
public class SolutionController {
    public static final int LIKE=1;
    public static final int SUBCRIBE=2;
    public static final int UNLIKE=3;
    public static final int UNSUBCRIBE=4;
    @Autowired
     SolutionRepository solutionRepository;
    @Autowired
     TokenRepository tokenRepostirory;
    @Autowired
     ChildSolutionRepository childSolutionRepository;
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
            ArrayList<Map> solutionList= solutionRepository.getRatedSolution(c_id,tokenOb.getU_id());
            respone.put("solutions",solutionList);
            
        }
        return respone;
    }
    
     @RequestMapping(value = "/get_solution",method=RequestMethod.GET)
    public Map getSolutionBys_id(
            @RequestParam(name="token") String token,
            @RequestParam(name="s_id") String s_id_str,
            @RequestParam(name="c_id") String c_id_str){
        Map respone= new HashMap();
        Token tokenOb = tokenRepostirory.getTokenByCode(token);
        int s_id=Integer.parseInt(s_id_str);
        int c_id=Integer.parseInt(c_id_str);
         if (tokenOb==null) {
             respone.put("status","0");
         } else {
             int u_id=tokenOb.getU_id();
             respone.put("status","1");

             respone=solutionRepository.checkReactedSolutionOfUser(u_id, s_id, c_id);

         }
        
        
        return respone;
    }
    // chua done/.....
    // can chet is managed the given child
    @RequestMapping(value = "/new_solution",method = RequestMethod.POST)
    public Map newSolutionForChild(@RequestParam(name = "token") String token,
                                    @RequestParam(name = "c_id") String c_idStr,
                                    @RequestParam(name = "title") String title,
                                    @RequestParam(name = "content")String content,
                                    @RequestParam(name = "rating")String rating_str){
    Map respone= new HashMap();
    Token tokenOb = tokenRepostirory.getTokenByCode(token);
    
    int c_id=Integer.parseInt(c_idStr);
    int rating= Integer.parseInt(rating_str);
        if (tokenOb==null) {
            respone.put("status","0"); 
        }
        else{
            respone.put("status","1");
            Date date_created = new Date();
            Solution solution = new Solution(0,tokenOb.getU_id(), title, content,"none", DataUtil.DataUtil.toSQLDATE(date_created),0);
            int s_id=solutionRepository.saveAndReturnID(solution);
            solution.setS_id(s_id);
            ChildSolution childSolution = new ChildSolution(s_id, c_id, rating);
            childSolutionRepository.save(childSolution);
            solutionRepository.likeSolution(s_id, tokenOb.getU_id());
            solutionRepository.subSolution(s_id, c_id);
            respone.put("solution", solution);
        }
       return respone; 
    }
    
    @RequestMapping(value = "/react_solution",method = RequestMethod.POST)
    public Map reactSolotion(
            @RequestParam(name="token") String token,
                             @RequestParam(name="s_id") String s_id_str,
                             @RequestParam(name="c_id") String c_id_str,
                             @RequestParam(name="action") String action_str
    ){
        Map respone= new HashMap();
        Token tokenOb = tokenRepostirory.getTokenByCode(token);
        boolean error=false;
        String message="";
        int s_id=0;
        int c_id=0;
        int action=0;
        try {
             if (c_id_str!=null) {
                c_id=Integer.parseInt(c_id_str);
            } 
             s_id=Integer.parseInt(s_id_str);
             action=Integer.parseInt(action_str);
        } catch (Exception e) {
            error=true;
             message+= "your request contains invalid value, ";
        }
        if (action<1||action>4) {
            error=true;
            message+= "Invalid action, ";
        }
        if (tokenOb==null) {
            error=true;
            message+= "Please log in to continue";
        }
      /// respone
        if (error) {
            respone.put("status", "0");
            respone.put("message",message);
        } 
        else {
              int u_id= tokenOb.getU_id();
              if (action==LIKE) {
                solutionRepository.likeSolution(s_id, u_id);
              }
              if (action==SUBCRIBE) {
                  if (c_id!=0) {
                       solutionRepository.subSolution(s_id, c_id);
                  }
              }
              if (action==UNLIKE) {
                  solutionRepository.unlikeSolution(s_id, u_id);
                
            }
                if (action==UNSUBCRIBE) {
                  solutionRepository.unSubSolution(s_id, c_id);
                
            }
    
          
            
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
    
    
    @RequestMapping(value = "delete_solution",method=RequestMethod.POST)
    public Map deleteSolution( @RequestParam(name="token") String token,
                                 @RequestParam(name="s_id") String s_id_str){
     Map respone= new HashMap();
     Token tokenOb = tokenRepostirory.getTokenByCode(token);
        if (tokenOb==null) {
            respone.put("status","0");
        } else {
            solutionRepository.deleteByRow(tokenOb.getU_id(),Integer.parseInt(s_id_str));
            respone.put("status","1");
        }
    return respone;
    }
    
}
