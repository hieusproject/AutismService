/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;


import java.sql.Connection;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.UserRepository;


/**
 *
 * @author PC
 */
@Configuration

public class SpringConfig {
 @Bean
 public UserRepository userRepository(){
     UserRepository userRepository= new UserRepository();
     
     return userRepository;
 }
}
