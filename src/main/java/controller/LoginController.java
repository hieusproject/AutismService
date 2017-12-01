/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DataUtil.DataUtil;
import entity.Token;
import entity.User;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import DataUtil.PassWordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import repository.TokenRepository;
import repository.UserRepository;

@RestController
public class LoginController {
    @Autowired
   UserRepository userRepository;
    @Autowired
    TokenRepository tokenRepository;
   
   
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Map getUserByInput(@RequestParam(name="username") String username,
                                @RequestParam(name="password") String password) throws NoSuchAlgorithmException{
         Map respone= new HashMap();
         User user=userRepository.getUserByInput(username, password);
         if (user!=null) {
            //new token,save token
            String newCode= 
                    Integer.toString(tokenRepository.getMaxId())
                    +DataUtil.generatingRandomCode();
            newCode= Integer.toString(user.getU_id())+PassWordUtil.hashPassword(newCode);
            Token tk= new Token(0,user.getU_id(), newCode);
            tokenRepository.save(tk);
            respone.put("status","1");
            respone.put("token",tk.getToken_code());
            user.setPassword("*********");
            respone.put("user",user);
        } else {
            respone.put("status","0");
            respone.put("token","");
        }
         return respone;
    } 
    
    
    
    @RequestMapping(value="/login",method= RequestMethod.GET)
    public Map getUserTokenByCode(@RequestParam(name="token") String token){
        Map respone= new HashMap();
        Token tk= tokenRepository.getTokenByCode(token);
     
       
        if (tk==null) {
           respone.put("status","0");
           respone.put("token","");
        } else {
            User user=userRepository.getUserbyId(tk.getU_id());
            respone.put("status","1");
            respone.put("token",tk.getToken_code());
            user.setPassword("*******");
            respone.put("user", user);
        }
         
    return respone;
    } @RequestMapping(value="/logout",method= RequestMethod.GET)
    public Map log_out(@RequestParam(name="token") String token){
        Map respone= new HashMap();
        Token tk= tokenRepository.getTokenByCode(token);
     
       
        if (tk==null) {
           respone.put("status","0");
           respone.put("token","");
        } else {
             tokenRepository.deleteById(Integer.toString(tk.getToken_id()));
             respone.put("status","1");
        }
         
    return respone;
    }
  
   
}
    