import controller.ClassDirectorController;
import controller.AdminController;
import controller.TeacherController;
import dao.TeacherDAO;
import dao.TeachingRequirementDAO;
import dao.impl.FileTeachingRequirementDAO;
import dao.impl.FileTeacherDAO;
import view.ClassDirectorMenu;
import view.AdminMenu;
import view.TeacherMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialising data access objects
        TeachingRequirementDAO requirementDAO = new FileTeachingRequirementDAO();
        TeacherDAO teacherDAO = new FileTeacherDAO();

        // Initialising the controller
        ClassDirectorController classDirectorController = new ClassDirectorController(requirementDAO);
        AdminController adminController = new AdminController(teacherDAO);
        TeacherController teacherController = new TeacherController(teacherDAO);

        // Initialising the view
        ClassDirectorMenu classDirectorMenu = new ClassDirectorMenu(classDirectorController, scanner);
        AdminMenu adminMenu = new AdminMenu(adminController, scanner);
        TeacherMenu teacherMenu = new TeacherMenu(teacherController);

        // Main Loop
        while (true) {
            // Menu display
            System.out.println("\n========== Part-Time Teacher Management System ==========");
            System.out.println("1. Class Director System");
            System.out.println("2. Administrator System");
            System.out.println("3. Part-Time Teacher System");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            // Get input
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }

            // Handle choice
            switch (choice) {
                case 1:
                    // Enter Class Director System
                    classDirectorMenu.showMainMenu();
                    break;
                case 2:
                    adminMenu.showMainMenu();
                    break;

                case 3:
                    teacherMenu.displayMenu();
                    break;

                case 0:
                    System.out.println("Saving data...");
                    requirementDAO.saveAll();
                    System.out.println("Thank you for using! Goodbye.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice! Please enter a number between 0 and 3.");
            }
        }
    }
}
