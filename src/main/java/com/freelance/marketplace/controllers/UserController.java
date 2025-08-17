package com.freelance.marketplace.controllers;

import java.util.List;
import java.util.UUID;

import com.freelance.marketplace.Entities.User;
import com.freelance.marketplace.services.EmployerService;
import com.freelance.marketplace.services.FreelancerService;

public class UserController {
    private EmployerService employerService;
    private FreelancerService freelancerService;
    
    
    public void login(String userName, String password){
        //--
    }

    public void registerFreelancer(){
        //--
    }
    public void registerEmployer(){
        //--
    }
    //--------------------------------------
    public List<User> findById(UUID id){
        //--
    }
    
    public List<User> findByName(String name){
        //--
    }

    public List<User> findByUserName(String userName){
        //--
    }
}
