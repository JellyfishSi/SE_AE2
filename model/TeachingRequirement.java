package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 教学需求类 (Teaching Requirement)
 * 表示课程主任创建的教学需求
 */
public class TeachingRequirement implements Serializable {
    // 序列化版本ID
    private static final long serialVersionUID = 1L;

    // 唯一标识符
    private String id;

    // 课程名称
    private String courseName;

    // 课程代码
    private String courseCode;

    // 时间安排 (例如: "周一 9:00-11:00")
    private String schedule;

    // 地点
    private String location;

    // 所需教师资格
    private List<String> requiredQualifications;

    // 状态 (未分配, 部分分配, 已分配)
    private RequirementStatus status;

    // 已分配的教师ID列表
    private List<String> assignedTeacherIds;

    // 备注
    private String notes;

    // 创建日期
    private long createdTimestamp;

    // 最后修改日期
    private long lastModifiedTimestamp;

    /**
     * 教学需求状态枚举
     */
    public enum RequirementStatus {
        UNASSIGNED("未分配"),
        PARTIALLY_ASSIGNED("部分分配"),
        FULLY_ASSIGNED("已分配");

        private final String displayName;

        RequirementStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    /**
     * 默认构造函数
     */
    public TeachingRequirement() {
        this.id = UUID.randomUUID().toString();
        this.requiredQualifications = new ArrayList<>();
        this.assignedTeacherIds = new ArrayList<>();
        this.status = RequirementStatus.UNASSIGNED;
        this.createdTimestamp = System.currentTimeMillis();
        this.lastModifiedTimestamp = System.currentTimeMillis();
    }

    /**
     * 带参数的构造函数
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

    public void addRequiredQualification(String qualification) {
        this.requiredQualifications.add(qualification);
        updateModificationTime();
    }

    public RequirementStatus getStatus() {
        return status;
    }

    public void setStatus(RequirementStatus status) {
        this.status = status;
        updateModificationTime();
    }

    public List<String> getAssignedTeacherIds() {
        return assignedTeacherIds;
    }

    public void setAssignedTeacherIds(List<String> assignedTeacherIds) {
        this.assignedTeacherIds = assignedTeacherIds;
        updateStatus();
        updateModificationTime();
    }

    public void assignTeacher(String teacherId) {
        if (!assignedTeacherIds.contains(teacherId)) {
            assignedTeacherIds.add(teacherId);
            updateStatus();
            updateModificationTime();
        }
    }

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

    public long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public long getLastModifiedTimestamp() {
        return lastModifiedTimestamp;
    }

    /**
     * 更新需求状态
     */
    private void updateStatus() {
        if (assignedTeacherIds.isEmpty()) {
            status = RequirementStatus.UNASSIGNED;
        } else {
            // 这里可以根据业务需求定义何时为部分分配，何时为完全分配
            // 例如，可以设定每个需求需要几位教师，或根据其他标准
            status = RequirementStatus.PARTIALLY_ASSIGNED;
        }
    }

    /**
     * 更新最后修改时间
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