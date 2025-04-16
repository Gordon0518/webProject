package com.project.webproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class LectureNote {
    @Id
    private String id;
    private String fileName;
    private String fileUrl;

    @ManyToOne
    private Lecture lecture;

    public LectureNote() {}
    public LectureNote(String id, String fileName, String fileUrl, Lecture lecture) {
        this.id = id;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.lecture = lecture;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
    public Lecture getLecture() { return lecture; }
    public void setLecture(Lecture lecture) { this.lecture = lecture; }
}