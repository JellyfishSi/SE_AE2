package controller;

import dao.TeacherDAO;
import model.Teacher;

import java.util.List;

/**
 * Controller for managing Teacher-related operations for administrator.
 * @author Tianshu Luo
 * @version 1.0.0
 */
public class AdminController {
    private final TeacherDAO teacherDAO;

    /**
     * Constructor
     * @param teacherDAO data access object for teacher operations
     */
    public AdminController(TeacherDAO teacherDAO) {
        this.teacherDAO = teacherDAO;
    }

    /**
     * Add a new teacher
     */
    public boolean addTeacher(int id, String name, String contact, Boolean isAvailable, String qualifications) {
        Teacher newTeacher = new Teacher(id, name, contact, isAvailable, qualifications);
        return teacherDAO.save(newTeacher);
    }

    /**
     * Update information of a registered teacher
     */
    public boolean updateTeacher(int id, String name, String contact, Boolean isAvailable, String qualifications) {
        Teacher teacher = teacherDAO.findById(id);
        if (teacher == null) {
            return false;
        }

        if (name != null && !name.isEmpty()) teacher.setName(name);
        if (contact != null && !contact.isEmpty()) teacher.setContact(contact);
        if (isAvailable != null) teacher.setAvailable(isAvailable);
        if (qualifications != null && !qualifications.isEmpty()) teacher.setQualifications(qualifications);

        return teacherDAO.update(teacher);
    }

    /**
     * Delete teacher by ID
     */
    public boolean deleteTeacher(int id) {
        return teacherDAO.delete(id);
    }

    /**
     * List all teachers
     */
    public List<Teacher> listAllTeachers() {
        return teacherDAO.findAll();
    }

    /**
     * Find the teacher by ID
     */
    public Teacher findTeacherById(int id) {
        return teacherDAO.findById(id);
    }

    /**
     * Search teachers by qualification
     */
    public List<Teacher> searchTeachersByQualification(String keyword) {
        return teacherDAO.findAll().stream()
                .filter(t -> t.getQualifications().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }

}