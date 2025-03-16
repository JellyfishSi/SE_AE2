import controller.ClassDirectorController;
import dao.TeachingRequirementDAO;
import dao.impl.FileTeachingRequirementDAO;
import view.ClassDirectorMenu;

import java.util.Scanner;

public class Main {
    // 文件路径常量
    private static final String DATA_FILE_PATH = "data/data.dat";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 初始化数据访问对象
        TeachingRequirementDAO requirementDAO = new FileTeachingRequirementDAO(DATA_FILE_PATH);

        // 初始化控制器
        ClassDirectorController classDirectorController = new ClassDirectorController(requirementDAO);

        // 初始化视图
        ClassDirectorMenu classDirectorMenu = new ClassDirectorMenu(classDirectorController, scanner);

        // Main Loop
        while (true) {
            // Menu Display
            System.out.println("\n========== Part-Time Teacher Management System ==========");
            System.out.println("1. Class Director System");
            System.out.println("2. Administrator System");
            System.out.println("3. Part-Time Teacher System");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            // Get Input
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
                    System.out.println("T.B.D. ");
                    break;

                case 3:
                    System.out.println("T.B.D. ");
                    break;

                case 4:
                    System.out.println("Saving data...");
                    requirementDAO.saveAll();1
                    System.out.println("Thank you for using! Goodbye.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 4.");
            }
        }
    }
}
