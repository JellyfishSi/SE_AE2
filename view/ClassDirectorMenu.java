package view;

import controller.ClassDirectorController;
import model.TeachingRequirement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Class Director Menu
 * Provide course director user interface to handle user input and display outputs
 */
public class ClassDirectorMenu {
    private final ClassDirectorController controller;
    private final Scanner scanner;

    /**
     * constructor
     *
     * @param controller Course Director Controller
     * @param scanner Input Scanner
     */
    public ClassDirectorMenu(ClassDirectorController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }
    /**
     * Display the Course Director Main Menu
     */
    public void showMainMenu() {
        while (true) {
            System.out.println("\n======== Class Director System ========");
            System.out.println("1. Create Teaching Requirement");
            System.out.println("2. View All Requirements");
            System.out.println("3. Modify Requirement");
            System.out.println("4. Delete Requirement");
            System.out.println("5. View Requirements by Status");
            System.out.println("6. Search Requirements");
            System.out.println("0. Return to Main Menu");
            System.out.print("Please select: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a number");
                continue;
            }

            switch (choice) {
                case 0:
                    return;
                case 1:
                    createRequirement();
                    break;
                case 2:
                    viewAllRequirements();
                    break;
                case 3:
                    modifyRequirement();
                    break;
                case 4:
                    deleteRequirement();
                    break;
                case 5:
                    viewRequirementsByStatus();
                    break;
                case 6:
                    searchRequirements();
                    break;
                default:
                    System.out.println("Invalid choice, please try again");
            }
        }
    }

    /**
     * Creating Instructional Requirements
     */
    private void createRequirement() {
        System.out.println("\n===== Create Teaching Requirement =====");

        System.out.print("Course Name: ");
        String courseName = scanner.nextLine().trim();

        System.out.print("Course Code: ");
        String courseCode = scanner.nextLine().trim();

        System.out.print("Schedule （e.g. Mon 9:00-11:00）: ");
        String schedule = scanner.nextLine().trim();

        System.out.print("Location: ");
        String location = scanner.nextLine().trim();

        System.out.print("Required Qualifications: ");
        String qualificationsInput = scanner.nextLine().trim();
        List<String> qualifications = new ArrayList<>();
        if (!qualificationsInput.isEmpty()) {
            qualifications = Arrays.asList(qualificationsInput.split(","));
        }

        System.out.print("Notes: ");
        String notes = scanner.nextLine().trim();

        TeachingRequirement requirement = controller.createRequirement(
                courseName, courseCode, schedule, location, qualifications, notes);

        if (requirement != null) {
            System.out.println("Requirement created successfully");
        } else {
            System.out.println("Creation failed, please check your input");
        }
    }

    /**
     * View all teaching needs
     */
    private void viewAllRequirements() {
        List<TeachingRequirement> requirements = controller.getAllRequirements();
        displayRequirements(requirements, "All Teaching Requirements");
    }

    /**
     * Modification of teaching requirements
     */
    private void modifyRequirement() {
        List<TeachingRequirement> requirements = controller.getAllRequirements();

        if (requirements.isEmpty()) {
            System.out.println("\nNo requirements to modify");
            return;
        }

        displayRequirements(requirements, "All Teaching Requirements");

        System.out.print("\nEnter the ID of the requirement to modify: ");
        String id = scanner.nextLine().trim();

        TeachingRequirement requirement = controller.getRequirementById(id);
        if (requirement == null) {
            System.out.println("Requirement with this ID not found");
            return;
        }

        System.out.println("\n===== Modify Teaching Requirement =====");
        System.out.println("Current information:");
        System.out.println(requirement);
        System.out.println("\nEnter new information, leave blank to keep unchanged:");

        System.out.print("Course Name [" + requirement.getCourseName() + "]: ");
        String courseName = scanner.nextLine().trim();
        if (courseName.isEmpty()) {
            courseName = requirement.getCourseName();
        }

        System.out.print("Course Code [" + requirement.getCourseCode() + "]: ");
        String courseCode = scanner.nextLine().trim();
        if (courseCode.isEmpty()) {
            courseCode = requirement.getCourseCode();
        }

        System.out.print("Schedule [" + requirement.getSchedule() + "]: ");
        String schedule = scanner.nextLine().trim();
        if (schedule.isEmpty()) {
            schedule = requirement.getSchedule();
        }

        System.out.print("Location [" + requirement.getLocation() + "]: ");
        String location = scanner.nextLine().trim();
        if (location.isEmpty()) {
            location = requirement.getLocation();
        }

        System.out.print("Required Qualifications [" +
                String.join(",", requirement.getRequiredQualifications()) + "]: ");
        String qualificationsInput = scanner.nextLine().trim();
        List<String> qualifications = requirement.getRequiredQualifications();
        if (!qualificationsInput.isEmpty()) {
            qualifications = Arrays.asList(qualificationsInput.split(","));
        }

        System.out.print("Notes [" + requirement.getNotes() + "]: ");
        String notes = scanner.nextLine().trim();
        if (notes.isEmpty()) {
            notes = requirement.getNotes();
        }

        boolean success = controller.updateRequirement(
                id, courseName, courseCode, schedule, location, qualifications, notes);

        if (success) {
            System.out.println("Requirement updated successfully");
        } else {
            System.out.println("Update failed");
        }
    }

    /**
     * Delete Instructional Requirements
     */
    private void deleteRequirement() {
        List<TeachingRequirement> requirements = controller.getAllRequirements();

        if (requirements.isEmpty()) {
            System.out.println("\nNo requirements to delete");
            return;
        }

        displayRequirements(requirements, "All Teaching Requirements");

        System.out.print("\nEnter the ID of the requirement to delete: ");
        String id = scanner.nextLine().trim();

        System.out.print("Confirm deletion?? (Y/N): ");
        String confirm = scanner.nextLine().trim().toUpperCase();

        if ("Y".equals(confirm)) {
            boolean success = controller.deleteRequirement(id);

            if (success) {
                System.out.println("Requirement deleted successfully");
            } else {
                System.out.println("Deletion failed, please check if the ID is correct");
            }
        } else {
            System.out.println("Deletion cancelled");
        }
    }

    /**
     * View Requirements by Status
     */
    private void viewRequirementsByStatus() {
        System.out.println("\n===== View Requirements by Status =====");
        System.out.println("1. Unassigned");
        System.out.println("2. Partially Assigned");
        System.out.println("3. Fully Assigned");
        System.out.print("Please select status: ");

        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input");
            return;
        }

        TeachingRequirement.RequirementStatus status;
        String statusName;

        switch (choice) {
            case 1:
                status = TeachingRequirement.RequirementStatus.UNASSIGNED;
                statusName = "Unassigned";
                break;
            case 2:
                status = TeachingRequirement.RequirementStatus.PARTIALLY_ASSIGNED;
                statusName = "Partially Assigned";
                break;
            case 3:
                status = TeachingRequirement.RequirementStatus.FULLY_ASSIGNED;
                statusName = "Fully Assigned";
                break;
            default:
                System.out.println("Invalid choice");
                return;
        }

        List<TeachingRequirement> requirements = controller.getRequirementsByStatus(status);
        displayRequirements(requirements, statusName + " Requirements with status: " + statusName + ")");
    }

    /**
     * Search for Teaching Requirements
     */
    private void searchRequirements() {
        System.out.print("\nEnter course name keyword: ");
        String keyword = scanner.nextLine().trim();

        List<TeachingRequirement> requirements = controller.searchRequirementsByCourseName(keyword);
        displayRequirements(requirements, "Search Results");
    }

    /**
     * Display requirements list
     *
     * @param requirements Requirements List
     * @param title title
     */
    private void displayRequirements(List<TeachingRequirement> requirements, String title) {
        System.out.println("\n===== " + title + " =====");

        if (requirements.isEmpty()) {
            System.out.println("No requirements found");
            return;
        }

        int index = 1;
        for (TeachingRequirement requirement : requirements) {
            System.out.println(index + ". " + requirement.getCourseName() + " (" + requirement.getCourseCode() + ")");
            System.out.println("   ID: " + requirement.getId());
            System.out.println("   Course: " + requirement.getCourseName() + " (" + requirement.getCourseCode() + ")");
            System.out.println("   Schedule: " + requirement.getSchedule());
            System.out.println("   Location: " + requirement.getLocation());
            System.out.println("   Required Qualifications: " + String.join(", ", requirement.getRequiredQualifications()));
            System.out.println("   Status: " + requirement.getStatus().name());
            System.out.println("   Notes: " + requirement.getNotes());
            System.out.println();
            index++;
        }
    }
}