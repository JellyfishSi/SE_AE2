import controller.ClassDirectorController;
import dao.TeachingRequirementDAO;
import dao.impl.FileTeachingRequirementDAO;
import view.ClassDirectorMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Menu Display
            System.out.println("\n========== Part-Time Teacher Management System ==========");
            System.out.println("1. Choice 1");
            System.out.println("2. Choice 2");
            System.out.println("3. Choice 3");
            System.out.println("4. Choice 4");
            System.out.println("5. Exit");
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
                    System.out.println("T.B.D.");
                    break;

                case 2:
                    System.out.println("T.B.D. ");
                    break;

                case 3:
                    System.out.println("T.B.D. ");
                    break;

                case 4:
                    System.out.println("T.B.D. ");
                    break;

                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 5.");
            }
        }
    }
}
