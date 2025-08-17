package com.freelance.marketplace.Entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@Table
@Entity
@NoArgsConstructor
@DiscriminatorValue("1")
@EqualsAndHashCode(callSuper=true)
public class Employer extends User{
    
}
