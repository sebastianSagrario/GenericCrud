/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.crud.crudEjemplo.services;

import com.crud.crudEjemplo.entities.User;
import com.crud.crudEjemplo.repositories.UserRepo;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    
    public void save(User us) throws Exception
    {
        validateUser(us);
        encodePass(us);
        userRepo.save(us);               
    }
   
    public List<User> getAll()    
    {
        return userRepo.findAll();
    }

    private void validateUser(User us) throws Exception {

        if(!validEmail(us.getEmail()))
        {
            throw new Exception ("email no valido");
        }
        if(us.getPassword()==null || us.getPassword().isBlank())
        {
            throw new Exception("cannot save user-password not valid");
        }
        if(emailIsRepeated(us.getEmail()))
        {
            System.out.println(us);
            throw new Exception("cannot save user- email already registered");
        }        
    }

    
    public void encodePass(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
       user.setPassword(encodedPassword);
    }
    
    
    
    private Boolean validEmail(String email) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";  
        Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(email);          
        return matcher.matches();
    }

    private boolean emailIsRepeated(String email) {        
        Optional<User> userFinder=userRepo.findOneByEmail(email);
        return (userFinder.isPresent());         
    }

    public User getById(Long id) throws Exception {
        Optional<User> uResponse=userRepo.findById(id);
        if (! uResponse.isPresent())
        {
            throw new Exception("cannot find user");
        }
       return uResponse.get();                
        
    }

    public void eliminate(Long id) throws Exception {                    
        User u=getById(id);
        userRepo.delete(u);                    
    }

    
    
}







