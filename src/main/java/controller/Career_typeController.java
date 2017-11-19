/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import repository.CareerTypeRepository;
import repository.TokenRepository;

/**
 *
 * @author AnNguyen
 */
@RestController
public class Career_typeController {
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    CareerTypeRepository careerTypeRepository;
    
    
    @RequestMapping (value = "/getcareer_type",method = RequestMethod.GET)
    public Map getCareerType(){
        Map response= new HashMap();
        ArrayList<Object> careerTypes= careerTypeRepository.getAll();
        response.put("career_type", careerTypes);
    return response;
    }
    
    
}
