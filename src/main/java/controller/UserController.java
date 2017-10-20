/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import entity.Child;
import entity.User;
import java.io.File;

import java.io.IOException;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;
import repository.ChildRepository;
import DataUtil.PassWordUtil;
import entity.Token;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpRequest;
import org.springframework.ui.Model;
import repository.TokenRepository;
import repository.UserRepository;
import DataUtil.DataUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import org.json.simple.JSONObject;

/**
 *
 * @author AnNguyen
 */
@RestController
//@RequestMapping(value = "/ManageUser")
public class UserController {
    private static UserRepository userRepository= new UserRepository();
    private static ChildRepository childRepository=new ChildRepository();
    private static TokenRepository tokenRepository= new TokenRepository();
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public Object sayHello() throws ParserConfigurationException, SAXException, IOException{
    String jsonfile="";
    File xmlfile=
    new File("C:\\Users\\AnNguyen\\Documents\\NetBeansProjects\\Diagnosis_services\\src\\main\\webapp\\WEB-INF\\files\\xmlfile.xml");
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = (Document) dBuilder.parse(xmlfile);
        
    
    return doc;
    }
    @RequestMapping(value = "/getfile",method = RequestMethod.GET)
    public String getFile() throws IOException, ParseException {
       String src="C:/Users/AnNguyen/Documents/NetBeansProjects/Diagnosis_services/src/main/webapp/json.txt";
        JSONObject jsonObj=DataUtil.readJsonObject(src);
        String respone=JSONObject.toJSONString(jsonObj);
        return respone;
    }
    
    
    @RequestMapping(value = "/getUsers",method = RequestMethod.GET)
    public Map getAllUser(@Context HttpServletRequest request,@Context HttpServletResponse respone ){
            Map response= new HashMap();
            ArrayList<Object> users= userRepository.getAll();
            String contextpath= request.getServletContext().getRealPath("/files");
            response.put("contextPath",contextpath);
            response.put("users", users);
            return response;
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Map saveNewUser(@RequestBody JSONObject user
                                ) throws NoSuchAlgorithmException {
        boolean success=false;
        String error="";
        Map respone= new HashMap();
        Date date_created= new Date();
        String username= (String) user.get("username");
        String password= (String) user.get("password");
        String fullname= (String) user.get("fullname");
        String address= (String) user.get("address");
        String phone= (String) user.get("phone");
        String email= (String) user.get("email");
        String age= (String) user.get("age");
        Date date_of_birth = null;
        try {
            date_of_birth = DataUtil.StringtoDate(age);
        } catch (java.text.ParseException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        User userObj= new User(0, username, password,
                fullname, address, phone, email,
                DataUtil.toSQLDATE(date_of_birth), DataUtil.toSQLDATE(date_created), 2);
        if (userRepository.isExist(username)) {
            error="This username already existed";
            success=false;
        }else{
            success=userRepository.save(userObj);
            if (!success) {
                error="Can't create this user ";
            }
        }
        if (success) {
            User newUser= userRepository.getUserbyUserName(username);
            String newToken= DataUtil.newTokenforUser(newUser.getU_id());
            Token token= new Token(0,newUser.getU_id() , newToken);
            tokenRepository.save(token);
            respone.put("status", "1");
            respone.put("token", newToken);
        } else {
            respone.put("status", "0");
            respone.put("failed_message", error);
        }
       return respone; 
    }
    @RequestMapping(value = "/getUserByinput",method = RequestMethod.POST)
    public User getUserByInput(@RequestParam(name="username") String username,
                                @RequestParam(name="password") String password) throws NoSuchAlgorithmException{
         password= PassWordUtil.hashPassword(password);
         User user=userRepository.getUserByInput(username, password);
         return user;   
    }
    @RequestMapping(value = "/getchildren",method = RequestMethod.GET)
    public Map getChildsOfUser(@RequestParam(name="token") String token 
                               ) throws NoSuchAlgorithmException{
        Map resspone= new HashMap();
        Token tokenOb = tokenRepository.getTokenByCode(token);
       
        if (token==null) {
            resspone.put("status","0");
        } else {
              ArrayList<Map> childs= childRepository.getChildsOfUser(tokenOb.getU_id());
              resspone.put("status","1");
              resspone.put("children", childs);
        }
        
       return resspone;
          
    }
    public static void main(String[] args) {
       
        System.out.println("controller.UserController.main()");
    }
}
