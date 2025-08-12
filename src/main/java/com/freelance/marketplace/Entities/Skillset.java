package com.freelance.marketplace.Entities;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Skillset {
    private List<Skills> skills;

    public void addSkill(Skills skill){
        skills.add(skill);
    }
    public void removeSkill(Skills skill){
        skills.remove(skill);
    }
}
