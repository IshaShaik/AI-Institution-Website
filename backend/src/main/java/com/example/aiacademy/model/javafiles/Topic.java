package com.example.aiacademy.model.javafiles;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue; // Naya import
import jakarta.persistence.GenerationType; // Naya import
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private ModuleEntity module;

    public ModuleEntity getModule() {
        return module;
    }

    public void setModule(ModuleEntity module) {
        this.module = module;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   
}
