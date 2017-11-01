
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DataUtil.DataUtil;
import entity.Child;
import entity.ExtraInfo;
import entity.Token;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import repository.ChildChildRepository;
import repository.ChildRepository;
import repository.ExtraInfoRepository;
import repository.TokenRepository;

/**
 *
 * @author AnNguyen
 * can toi uu cho upload anh
 */
@RestController
public class ChildController {
    private TokenRepository tokenRepository=new TokenRepository();
    private  ChildRepository childRepository = new ChildRepository();
    private ExtraInfoRepository extraInfoRepository= new ExtraInfoRepository();
    private static String resouce="";
    private static final  int NEW=1;
    private static final  int EDIT=2;
    private String rootPath="/images";
    public ChildController() throws UnsupportedEncodingException {
        String path =  this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        String fullPath = URLDecoder.decode(path, "UTF-8");
        String pathArr[] = fullPath.split("/target/");
        resouce = pathArr[0]+"/src/main/webapp/images";    
    }
    
@RequestMapping(value = "/newchild",method = RequestMethod.POST)     
public Map newChild(
        @Context HttpServletRequest httpRequest,
        @Context HttpServletRequest httpRespone
                        ) throws ParseException, IOException{
     ServletContext  servletContext= httpRequest.getServletContext();
   
     Map respone = new HashMap();
            
     MultipartResolver resolver= new CommonsMultipartResolver(servletContext);
            MultipartHttpServletRequest multipartResquest= resolver.resolveMultipart(httpRequest);
            MultipartFile file= multipartResquest.getFile("file");
            String fileName= file.getOriginalFilename();
            String token= multipartResquest.getParameter("token");
            String fullname= multipartResquest.getParameter("fullname");
            String date_of_birth= multipartResquest.getParameter("date_of_birth");
            String date_created_str= multipartResquest.getParameter("date_created");
            String father= multipartResquest.getParameter("father");
            String mother= multipartResquest.getParameter("mother");
            String father_career= multipartResquest.getParameter("father_career");
            String mother_career= multipartResquest.getParameter("mother_career");
            String monthly_income= multipartResquest.getParameter("monthly_income");
            String child_sex= multipartResquest.getParameter("child_sex");
            String action_str=multipartResquest.getParameter("action");
            String c_id_str= multipartResquest.getParameter("c_id");
            int action=0;
            try {
                action=Integer.parseInt(action_str);
                } catch (Exception e) {
                action=0;    
            }
            String imgUrl= "/images/"+fileName;
            Token tokenOb = tokenRepository.getTokenByCode(token);
            
        if (tokenOb==null||action==0) {
            respone.put("status","0");
        } else {    
                    if (action==NEW) {
                    Date dateCreated=new Date();
                    Date birthDate= DataUtil.StringtoDate(date_of_birth);
                    Child child= new Child(0,tokenOb.getU_id() ,
                            fullname, DataUtil.toSQLDATE(birthDate), father,
                            mother,DataUtil.toSQLDATE(dateCreated), 0, mother);
                    child.setImageURL(imgUrl);
                    int new_c_id=childRepository.newAndreturnId(child);
                    int father_career_id= Integer.parseInt(father_career);
                    int mother_career_id= Integer.parseInt(mother_career);
                    int monthly_income_int= Integer.parseInt(monthly_income);
                    int child_sex_int = Integer.parseInt(child_sex);
                    
                    ExtraInfo extraInfor= new ExtraInfo(new_c_id, father_career_id, 0,
                            mother_career_id, monthly_income_int,
                            0, 0, child_sex_int, 0);
                    extraInfoRepository.save(extraInfor);
                    child.setC_id(new_c_id);
                    respone.put("status","1");
                    respone.put("child", child);
                     if (!file.isEmpty()) {
                               writeTofile(file, fileName);
                            
                        }
                
            }
                    if (action==EDIT) {
                    Date birthDate= DataUtil.StringtoDate(date_of_birth);    
                    Date date_created=DataUtil.StringtoDate(date_created_str);  
                    int c_id= Integer.parseInt(c_id_str);
                    Child child= new Child(c_id, tokenOb.getU_id(), fullname,
                             DataUtil.toSQLDATE(birthDate), father, mother,
                             DataUtil.toSQLDATE(date_created), 0, imgUrl);
                     childRepository.update(child);
                    int father_career_id= Integer.parseInt(father_career);
                    int mother_career_id= Integer.parseInt(mother_career);
                    int monthly_income_int= Integer.parseInt(monthly_income);
                    int child_sex_int = Integer.parseInt(child_sex);
                    ExtraInfo extraInfor= new ExtraInfo(c_id, father_career_id, 0,
                            mother_career_id, monthly_income_int,
                            0, 0, child_sex_int, 0);
                    extraInfoRepository.update(extraInfor);
                    respone.put("status","1");
                    respone.put("child", child);
                        if (!file.isEmpty()) {
                               writeTofile(file, fileName);
                        }
                 
                
            }
                  
             
        }

            return respone;
}    

    @RequestMapping(value = "/delete_child",method=RequestMethod.POST)
    public Map deleteChildbyID(@RequestParam(name="token") String token,
                               @RequestParam(name="c_id") String c_id_str ){
        Map response= new HashMap();
        Token tokenObj= tokenRepository.getTokenByCode(token);
        boolean error= false;
        int c_id=0;
        String message="";
        try {
            c_id=Integer.parseInt(c_id_str);
        } catch (Exception e) {
            error=true;
            message+="Invalid c_id,";
        }
        if (tokenObj==null) {
            error=true;
            message+="Log in to continue,";
        } 
       
        if (error) {
            response.put("status","0");
            response.put("message",message);
        } else {
            response.put("status","1");
            childRepository.deleteByRow(c_id,tokenObj.getU_id());
        }
        
        return response;
    }
    
    public void writeTofile(MultipartFile filePart,String filename) throws IOException{
            if(! new File(rootPath).exists()) {
                  new File(rootPath).mkdir();
                }
            OutputStream out = null;
            InputStream filecontent = null;
            out = new FileOutputStream(new File(rootPath + File.separator
                + filename));
            filecontent = filePart.getInputStream();

        int read = 0;
        final byte[] bytes = new byte[1024];

        while ((read = filecontent.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
    }
}
