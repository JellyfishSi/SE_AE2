package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TrainingSession {
    private String id;
    private String name;
    private String date;
    private String location;
    private String content;
    private List<String> participatingTeacherIds;

    // Constructor
    public TrainingSession(String name, String date, String location, String content) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.date = date;
        this.location = location;
        this.content = content;
        this.participatingTeacherIds = new ArrayList<>();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getParticipatingTeacherIds() {
        return participatingTeacherIds;
    }

    // Add a teacher to the session
    public void addTeacher(String teacherId) {
        if (!participatingTeacherIds.contains(teacherId)) {
            participatingTeacherIds.add(teacherId);
        }
    }

    // Remove a teacher from the session
    public void removeTeacher(String teacherId) {
        participatingTeacherIds.remove(teacherId);
    }

    @Override
    public String toString() {
        return "TrainingSession{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", location='" + location + '\'' +
                ", content='" + content + '\'' +
                ", participatingTeacherIds=" + participatingTeacherIds +
                '}';
    }
}
