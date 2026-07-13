package com.example.aiacademy.model;
import jakarta.persistence.*;
@Entity
@Table(name="file_records")
public class FileRecord {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String title;
  private String type;
  private String driveFileId;
  private String description;
  public FileRecord() {}
  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public String getTitle(){return title;} public void setTitle(String title){this.title=title;}
  public String getType(){return type;} public void setType(String type){this.type=type;}
  public String getDriveFileId(){return driveFileId;} public void setDriveFileId(String driveFileId){this.driveFileId=driveFileId;}
  public String getDescription(){return description;} public void setDescription(String description){this.description=description;}
}
