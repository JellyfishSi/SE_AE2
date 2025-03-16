package controller;

import dao.TeacherDAO;
import model.Teacher;

import java.util.List;
import java.util.Scanner;

public class TeacherController {
    public final TeacherDAO teacherDAO;
    private final Scanner scanner = new Scanner(System.in);

    // Constructor to initialize the DAO
    public TeacherController(TeacherDAO teacherDAO) {
        this.teacherDAO = teacherDAO;
    }

    // SC-07: Update teacher profile (personal details and qualifications)
    public void updateProfile(int teacherId) {
        Teacher teacher = teacherDAO.findById(teacherId);
        if (teacher == null) {
            System.out.println("Teacher not found.");
            return;
        }

        System.out.println("Updating profile for Teacher ID: " + teacherId);
        System.out.println("Current Name: " + teacher.getName() + ". Enter new name:");
        String name = scanner.nextLine();
        teacher.setName(name);

        System.out.println("Current Contact: " + teacher.getContact() + ". Enter new contact:");
        String contact = scanner.nextLine();
        teacher.setContact(contact);

        System.out.println("Current Qualifications: " + teacher.getQualifications() + ". Enter new qualifications:");
        String qualifications = scanner.nextLine();
        teacher.setQualifications(qualifications);

        System.out.println("Is the teacher available? (true/false):");
        boolean isAvailable = Boolean.parseBoolean(scanner.nextLine());
        teacher.setAvailable(isAvailable);

        if (teacherDAO.update(teacher)) {
            System.out.println("Profile updated successfully.");
        } else {
            System.out.println("Failed to update profile.");
        }
    }

    // SC-08: Apply for a course
    public void applyForCourse(int teacherId, String courseId) {
        Teacher teacher = teacherDAO.findById(teacherId);
        if (teacher == null) {
            System.out.println("Teacher not found.");
            return;
        }

        if (teacher.isAvailable() && teacher.getQualifications().contains(courseId)) {
            System.out.println("Teacher " + teacher.getName() + " applied for course: " + courseId);
            // Logic to notify the admin can be implemented here.
        } else {
            System.out.println("Teacher is either not available or lacks the required qualifications.");
        }
    }

    // SC-08: Accept or decline course assignments
    public void acceptOrDeclineCourse(int teacherId, String courseId) {
        Teacher teacher = teacherDAO.findById(teacherId);
        if (teacher == null) {
            System.out.println("Teacher not found.");
            return;
        }

        System.out.println("Accept (A) or Decline (D) the course assignment for course: " + courseId + "?");
        String decision = scanner.nextLine();

        if ("A".equalsIgnoreCase(decision)) {
            System.out.println("Course assignment accepted.");
            // Logic to confirm assignment
        } else if ("D".equalsIgnoreCase(decision)) {
            System.out.println("Course assignment declined.");
            // Logic to decline assignment
        } else {
            System.out.println("Invalid input.");
        }
    }

    // SC-09: Attend a training session
    public void attendTraining(int teacherId, String trainingId) {
        Teacher teacher = teacherDAO.findById(teacherId);
        if (teacher == null) {
            System.out.println("Teacher not found.");
            return;
        }

        System.out.println("Teacher " + teacher.getName() + " has attended training: " + trainingId);
        // Logic to record training attendance can be implemented here.
    }

    // SC-09: View all training sessions attended (Assuming data is stored elsewhere)
    public void viewCompletedTraining(int teacherId) {
        // Placeholder logic to show completed trainings
        System.out.println("Completed trainings for Teacher ID " + teacherId + ": [Training1, Training2]");
    }

    // Display all teachers for reference
    public void displayAllTeachers() {
        List<Teacher> teachers = teacherDAO.getAll();
        for (Teacher teacher : teachers) {
            System.out.println(teacher);
        }
    }
}
