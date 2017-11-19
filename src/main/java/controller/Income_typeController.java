/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Token;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import repository.IncomeTypeRepository;
import repository.TokenRepository;

/**
 *
 * @author AnNguyen
 */
@RestController
public class Income_typeController {
    @Autowired
    IncomeTypeRepository incomeTypeRepository;
    @Autowired
    TokenRepository tokenRepository ;
    @RequestMapping(value = "/get_income_type",method = RequestMethod.GET)
    public Map getChildsOfUser( ) {
        Map resspone= new HashMap();
              ArrayList<Object> income_types= incomeTypeRepository.getAll();
              resspone.put("income_type_list", income_types);
       return resspone;
          
    }
    
}
