import controller.ClassDirectorController;
import controller.AdminController;
import controller.TeacherController;
import dao.TeacherDAO;
import dao.TeachingRequirementDAO;
import dao.impl.FileTeachingRequirementDAO;
import model.FileTeacherDAO;
import model.Teacher;
import view.ClassDirectorMenu;
import view.AdminMenu;
import view.TeacherMenu;

import java.util.Scanner;

public class Main {
    // 文件路径常量
    private static final String REQUIREMENT_FILE_PATH = "requirements.dat";
    private static final String TEACHER_FILE_PATH = "teachers.dat";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 初始化数据访问对象
        TeachingRequirementDAO requirementDAO = new FileTeachingRequirementDAO(REQUIREMENT_FILE_PATH);
        TeacherDAO teacherDAO = new FileTeacherDAO();

        // 初始化控制器
        ClassDirectorController classDirectorController = new ClassDirectorController(requirementDAO);
        AdminController adminController = new AdminController(teacherDAO);
        TeacherController teacherController = new TeacherController(teacherDAO);

        // 初始化视图
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
