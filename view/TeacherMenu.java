package view;

import controller.TeacherController;
import model.Teacher;

import java.util.Scanner;

public class TeacherMenu {
    private final TeacherController teacherController;
    private final Scanner scanner;

    // Constructor
    public TeacherMenu(TeacherController teacherController) {
        this.teacherController = teacherController;
        this.scanner = new Scanner(System.in);
    }

    // Display the main menu without requiring login
    public void displayMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n====== Teacher Menu ======");
            System.out.println("1. Select Teacher by ID");
            System.out.println("2. View All Teachers (For Reference)");
            System.out.println("0. Exit");

            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    selectTeacher();
                    break;
                case "2":
                    teacherController.displayAllTeachers();
                    break;
                case "0":
                    exit = true;
                    System.out.println("Exiting Teacher Menu...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Select a teacher by ID and show teacher operations
    private void selectTeacher() {
        System.out.print("Enter Teacher ID: ");
        int teacherId = Integer.parseInt(scanner.nextLine());

        Teacher teacher = teacherController.teacherDAO.findById(teacherId);
        if (teacher == null) {
            System.out.println("Teacher not found.");
            return;
        }

        boolean back = false;
        while (!back) {
            System.out.println("\n--- Teacher Operations for " + ((model.Teacher) teacher).getName() + " ---");
            System.out.println("1. View Profile");
            System.out.println("2. Update Profile");
            System.out.println("3. Apply for Course");
            System.out.println("4. Accept or Decline Course Assignment");
            System.out.println("5. Attend Training Session");
            System.out.println("6. View Completed Trainings");
            System.out.println("0. Back to Main Menu");

            System.out.print("Choose an option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    viewProfile(teacherId);
                    break;
                case "2":
                    teacherController.updateProfile(teacherId);
                    break;
                case "3":
                    applyForCourse(teacherId);
                    break;
                case "4":
                    acceptOrDeclineCourse(teacherId);
                    break;
                case "5":
                    attendTraining(teacherId);
                    break;
                case "6":
                    teacherController.viewCompletedTraining(teacherId);
                    break;
                case "0":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // View teacher profile
    private void viewProfile(int teacherId) {
        Teacher teacher = teacherController.teacherDAO.findById(teacherId);
        if (teacher != null) {
            System.out.println("\n--- Teacher Profile ---");
            System.out.println(teacher);
        } else {
            System.out.println("Teacher not found.");
        }
    }

    // Apply for a course
    private void applyForCourse(int teacherId) {
        System.out.print("Enter Course ID to apply for: ");
        String courseId = scanner.nextLine();
        teacherController.applyForCourse(teacherId, courseId);
    }

    // Accept or decline a course assignment
    private void acceptOrDeclineCourse(int teacherId) {
        System.out.print("Enter Course ID to accept or decline: ");
        String courseId = scanner.nextLine();
        teacherController.acceptOrDeclineCourse(teacherId, courseId);
    }

    // Attend a training session
    private void attendTraining(int teacherId) {
        System.out.print("Enter Training Session ID to attend: ");
        String trainingId = scanner.nextLine();
        teacherController.attendTraining(teacherId, trainingId);
    }
}
