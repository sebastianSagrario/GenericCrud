/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud.crudEjemplo.controllers;

import com.crud.crudEjemplo.entities.User;
import com.crud.crudEjemplo.services.UserService;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    private UserService us;

    @Autowired
    public UserController(UserService us) {
        this.us = us;
    }
        
    @GetMapping
    public List<User> getAll()
    {
        System.out.println("getting all users from db");             
        return us.getAll();
    }
    
    @PostMapping("/user")
    @ResponseBody
    public ResponseEntity add(@RequestBody User u)
    {     
        try {
            us.save(u);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }       
    
    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id)
    {        
        
        try {
            return new ResponseEntity(us.getById(id),HttpStatus.CREATED);
        } catch (Exception ex) {
           return new ResponseEntity(HttpStatus.NOT_FOUND); 
        }
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(@PathVariable Long id)
    {
        try
        {
            us.eliminate(id);
            return new ResponseEntity(HttpStatus.OK);
            
        }catch(Exception e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }                
    }
    
    
}
