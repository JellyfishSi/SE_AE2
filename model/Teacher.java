package model;

import java.io.Serializable;

public class Teacher implements Serializable {
    private int id;
    private String name;
    private String contact;
    private boolean isAvailable;
    private String qualifications;

    // Constructor
    public Teacher(int id, String name, String contact, boolean isAvailable, String qualifications) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.isAvailable = isAvailable;
        this.qualifications = qualifications;
    }

    // Getter and Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    public String getQualifications() { return qualifications; }
    public void setQualifications(String qualifications) { this.qualifications = qualifications; }

    // toString
    @Override
    public String toString() {
        return "Teacher{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", contact='" + contact + '\'' +
               ", available=" + isAvailable +
               ", qualifications='" + qualifications + '\'' +
               '}';
    }
}