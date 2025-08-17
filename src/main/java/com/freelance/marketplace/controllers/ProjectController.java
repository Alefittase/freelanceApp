package com.freelance.marketplace.controllers;

import java.util.List;
import java.util.UUID;

import javax.swing.text.View;

import com.freelance.marketplace.Entities.Project;
import com.freelance.marketplace.Entities.ProjectState;
import com.freelance.marketplace.services.ProjectService;

public class ProjectController {
    private ProjectService projectService;

    public void CreateProject(){
        //--
    }

    public Project findById(UUID projectId){
        //--
    }

    public Project findByTitle(String title){
        //--
    }

    public List<Project> findByState(ProjectState state){
        //--
    }

    public List<Project> findByEmloyer(UUID employerId){
        //--
    }
}
