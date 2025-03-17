package view;

import controller.AdminController;
import model.Teacher;

import java.util.List;
import java.util.Scanner;

/**
 * AdminMenu - Menu for administrator operations
 * @author Tianshu Luo
 * @version 1.0.0
 */
public class AdminMenu {
    private final AdminController adminController;
    private final Scanner scanner;

    /**
     * Constructor
     * @param adminController administrator controller
     * @param scanner user's input
     */
    public AdminMenu(AdminController adminController, Scanner scanner) {
        this.adminController = adminController;
        this.scanner = scanner;
    }
    
    /**
     * Display administrator menu
     */
    public void showMainMenu() {
        while (true) {
            System.out.println("\n===== Administrator System =====");
            System.out.println("1. Add Teacher");
            System.out.println("2. Update Teacher Information");
            System.out.println("3. Delete Teacher by ID");
            System.out.println("4. List All Teachers");
            System.out.println("5. Find Teacher by ID");
            System.out.println("6. Search Teachers by Qualification");
            System.out.println("0. Return to Main Menu");
            System.out.print("Enter choice: ");

        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please check again!");
            continue;
        }

            switch (choice) {
                case 1:
                    addTeacher();
                    break;
                case 2:
                    updateTeacher();
                    break;
                case 3:
                    deleteTeacher();
                    break;
                case 4:
                    listAllTeachers();
                    break;
                case 5:
                    findTeacherById();
                    break;
                case 6:
                    searchTeachersByQualification();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice! Please enter a valid number.");
                    break;
            }
    }
}

    /**
     * Add a new teacher
     */
    private void addTeacher() {
        System.out.print("Enter Teacher ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Teacher Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Teacher Contact: ");
        String contact = scanner.nextLine();

        System.out.print("Is Teacher Available? (y/n): ");
        String availableInput = scanner.nextLine().trim();
        boolean isAvailable = availableInput.equalsIgnoreCase("y");

        System.out.print("Enter Teacher Qualifications: ");
        String qualifications = scanner.nextLine();

        if (adminController.addTeacher(id, name, contact, isAvailable, qualifications)) {
            System.out.println("Teacher added successfully!");
        } else {
            System.out.println("Failed to add teacher. ID may already exist.");
        }
    }

    /**
     * Update information of the teacher
     */
    private void updateTeacher() {
        System.out.print("Enter Teacher ID to update: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter New Name (leave blank to keep current): ");
        String name = scanner.nextLine();

        System.out.print("Enter New Contact (leave blank to keep current): ");
        String contact = scanner.nextLine();

        System.out.print("Is Teacher Available? (y/n, leave blank to keep current): ");
        String availableInput = scanner.nextLine().trim();
        Boolean isAvailable = availableInput.isEmpty() ? null : availableInput.equalsIgnoreCase("y");
                
        System.out.print("Enter New Qualifications (leave blank to keep current): ");
        String qualifications = scanner.nextLine();

        if (adminController.updateTeacher(id, name, contact, isAvailable, qualifications)) {
            System.out.println("Teacher information updated successfully!");
        } else {
            System.out.println("Update failed! Please check the Teacher ID.");
        }
    }

    /**
     * Delete a registered teacher
     */
    private void deleteTeacher() {
        System.out.print("Enter Teacher ID to delete: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid numeric ID.");
            return;
        }
    
        if (adminController.deleteTeacher(id)) {
            System.out.println("Teacher deleted successfully!");
        } else {
            System.out.println("Delete failed! Teacher not found.");
        }
    }
    

    /**
     * List all teachers
     */
    private void listAllTeachers() {
        List<Teacher> teachers = adminController.listAllTeachers();
        if (teachers.isEmpty()) {
            System.out.println("No teachers available.");
        } else {
            teachers.forEach(System.out::println);
        }
    }

    /**
     * Find the teacher by ID
     */
    private void findTeacherById() {
        System.out.print("Enter Teacher ID: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid numeric ID.");
            return;
        }
    
        Teacher teacher = adminController.findTeacherById(id);
        if (teacher == null) {
            System.out.println("Teacher not found!");
        } else {
            System.out.println(teacher);
        }
    }

    /**
     * Search teachers by qualification
     */
    private void searchTeachersByQualification() {
        System.out.print("Enter Qualification Keyword: ");
        String keyword = scanner.nextLine();

        List<Teacher> teachers = adminController.searchTeachersByQualification(keyword);
        if (teachers.isEmpty()) {
            System.out.println("No teachers found with the given qualification.");
        } else {
            teachers.forEach(System.out::println);
        }
    }
}
