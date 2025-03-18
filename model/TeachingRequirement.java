package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Teaching Requirement Class
 * Represents a teaching requirement created by the class director
 */
public class TeachingRequirement implements Serializable {
    // Serialization version ID
    private static final long serialVersionUID = 1L;

    // Unique identifier
    private final String id;

    // Course name
    private String courseName;

    // Course code
    private String courseCode;

    // Time schedule (e.g. "Monday 9:00-11:00")
    private String schedule;

    // Location
    private String location;

    // Required teacher qualifications
    private List<String> requiredQualifications;

    // Status (unassigned, partially assigned, fully assigned)
    private RequirementStatus status;

    // List of assigned teacher IDs
    private List<String> assignedTeacherIds;

    // Notes
    private String notes;

    // Creation date
    private final long createdTimestamp;

    // Last modification date
    private long lastModifiedTimestamp;

    /**
     * Teaching requirement status enumeration
     */
    public enum RequirementStatus {
        UNASSIGNED("Unassigned"),
        PARTIALLY_ASSIGNED("Partially Assigned"),
        FULLY_ASSIGNED("Fully Assigned");

        private final String displayName;

        RequirementStatus(String displayName) {
            this.displayName = displayName;
        }

        @SuppressWarnings("unused")
        public String getDisplayName() {
            return displayName;
        }
    }

    /**
     * Default constructor
     */
    public TeachingRequirement() {
        this.id = UUID.randomUUID().toString();
        this.requiredQualifications = new ArrayList<>();
        this.assignedTeacherIds = new ArrayList<>();
        this.status = RequirementStatus.UNASSIGNED;
        this.createdTimestamp = System.currentTimeMillis();
        this.lastModifiedTimestamp = System.currentTimeMillis();
        this.notes = "";
    }

    /**
     * Parameterized constructor
     */
    public TeachingRequirement(String courseName, String courseCode, String schedule,
                               String location, List<String> requiredQualifications) {
        this();
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.schedule = schedule;
        this.location = location;
        if (requiredQualifications != null) {
            this.requiredQualifications = requiredQualifications;
        }
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
        updateModificationTime();
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
        updateModificationTime();
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
        updateModificationTime();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
        updateModificationTime();
    }

    public List<String> getRequiredQualifications() {
        return requiredQualifications;
    }

    public void setRequiredQualifications(List<String> requiredQualifications) {
        this.requiredQualifications = requiredQualifications;
        updateModificationTime();
    }

    @SuppressWarnings("unused")
    public void addRequiredQualification(String qualification) {
        this.requiredQualifications.add(qualification);
        updateModificationTime();
    }

    public RequirementStatus getStatus() {
        return status;
    }

    @SuppressWarnings("unused")
    public void setStatus(RequirementStatus status) {
        this.status = status;
        updateModificationTime();
    }

    @SuppressWarnings("unused")
    public List<String> getAssignedTeacherIds() {
        return assignedTeacherIds;
    }

    @SuppressWarnings("unused")
    public void setAssignedTeacherIds(List<String> assignedTeacherIds) {
        this.assignedTeacherIds = assignedTeacherIds;
        updateStatus();
        updateModificationTime();
    }

    @SuppressWarnings("unused")
    public void assignTeacher(String teacherId) {
        if (!assignedTeacherIds.contains(teacherId)) {
            assignedTeacherIds.add(teacherId);
            updateStatus();
            updateModificationTime();
        }
    }

    @SuppressWarnings("unused")
    public void removeTeacher(String teacherId) {
        assignedTeacherIds.remove(teacherId);
        updateStatus();
        updateModificationTime();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
        updateModificationTime();
    }

    @SuppressWarnings("unused")
    public long getCreatedTimestamp() {
        return createdTimestamp;
    }

    @SuppressWarnings("unused")
    public long getLastModifiedTimestamp() {
        return lastModifiedTimestamp;
    }

    /**
     * Update requirement status
     */
    private void updateStatus() {
        if (assignedTeacherIds.isEmpty()) {
            status = RequirementStatus.UNASSIGNED;
        } else {
            // Business logic can be defined here to determine when a requirement is
            // partially or fully assigned
            // For example, based on how many teachers are needed, or other criteria
            status = RequirementStatus.PARTIALLY_ASSIGNED;
        }
    }

    /**
     * Update last modification time
     */
    private void updateModificationTime() {
        this.lastModifiedTimestamp = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "Requirement [ID=" + id +
                ", Course=" + courseName +
                " (" + courseCode + ")" +
                ", Schedule=" + schedule +
                ", Location=" + location +
                ", Status=" + status.name() +
                ", Assigned Teachers=" + assignedTeacherIds.size() +
                "]";
    }
}