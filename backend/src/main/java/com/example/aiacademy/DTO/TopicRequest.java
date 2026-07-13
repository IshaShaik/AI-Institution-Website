package com.example.aiacademy.DTO;

public class TopicRequest {

    private String name;
    private Long moduleId;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getModuleId() { return moduleId; }
    public void setModuleId(Long moduleId) { this.moduleId = moduleId; }
}
