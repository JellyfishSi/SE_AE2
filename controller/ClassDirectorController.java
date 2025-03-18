package controller;

import dao.TeachingRequirementDAO;
import model.TeachingRequirement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class Director Controller
 * Handles business logic for class directors, including creating, updating, deleting, and querying teaching requirements
 */
public class ClassDirectorController {

    // Teaching requirement data access object
    private final TeachingRequirementDAO requirementDAO;

    /**
     * Constructor
     *
     * @param requirementDAO Teaching requirement data access object
     */
    public ClassDirectorController(TeachingRequirementDAO requirementDAO) {
        this.requirementDAO = requirementDAO;
    }

    /**
     * Create a new teaching requirement
     *
     * @param courseName Course name
     * @param courseCode Course code
     * @param schedule Time schedule
     * @param location Location
     * @param qualifications Required teacher qualifications list
     * @param notes Additional notes
     * @return Created requirement object on success, null on failure
     */
    public TeachingRequirement createRequirement(
            String courseName,
            String courseCode,
            String schedule,
            String location,
            List<String> qualifications,
            String notes) {

        // Parameter validation
        if (courseName == null || courseName.trim().isEmpty() ||
                courseCode == null || courseCode.trim().isEmpty()) {
            return null;
        }

        // Create new teaching requirement object
        TeachingRequirement requirement = new TeachingRequirement(
                courseName, courseCode, schedule, location, qualifications);
        requirement.setNotes(notes);

        // Save to data access object
        boolean success = requirementDAO.save(requirement);

        // Return the newly created requirement object if save was successful
        return success ? requirement : null;
    }

    /**
     * Update an existing teaching requirement
     *
     * @param id Requirement ID
     * @param courseName Course name
     * @param courseCode Course code
     * @param schedule Time schedule
     * @param location Location
     * @param qualifications Required teacher qualifications list
     * @param notes Additional notes
     * @return true on successful update, false on failure
     */
    public boolean updateRequirement(
            String id,
            String courseName,
            String courseCode,
            String schedule,
            String location,
            List<String> qualifications,
            String notes) {

        // Find existing requirement
        TeachingRequirement requirement = requirementDAO.findById(id);
        if (requirement == null) {
            return false;
        }

        // Update requirement properties
        if (courseName != null && !courseName.trim().isEmpty()) {
            requirement.setCourseName(courseName);
        }

        if (courseCode != null && !courseCode.trim().isEmpty()) {
            requirement.setCourseCode(courseCode);
        }

        if (schedule != null) {
            requirement.setSchedule(schedule);
        }

        if (location != null) {
            requirement.setLocation(location);
        }

        if (qualifications != null) {
            requirement.setRequiredQualifications(qualifications);
        }

        if (notes != null) {
            requirement.setNotes(notes);
        }

        // Save the updated requirement
        return requirementDAO.update(requirement);
    }

    /**
     * Delete a teaching requirement
     *
     * @param id Requirement ID
     * @return true on successful deletion, false on failure
     */
    public boolean deleteRequirement(String id) {
        return requirementDAO.delete(id);
    }

    /**
     * Get all teaching requirements
     *
     * @return List of all teaching requirements
     */
    public List<TeachingRequirement> getAllRequirements() {
        return requirementDAO.findAll();
    }

    /**
     * Get a teaching requirement by ID
     *
     * @param id Requirement ID
     * @return Found teaching requirement object, or null if not found
     */
    public TeachingRequirement getRequirementById(String id) {
        return requirementDAO.findById(id);
    }

    /**
     * Get teaching requirements by status
     *
     * @param status Requirement status
     * @return List of teaching requirements matching the status
     */
    public List<TeachingRequirement> getRequirementsByStatus(TeachingRequirement.RequirementStatus status) {
        if (status == null) {
            return new ArrayList<>();
        }

        return requirementDAO.findAll().stream()
                .filter(requirement -> requirement.getStatus() == status)
                .collect(Collectors.toList());
    }

    /**
     * Search teaching requirements by course name
     *
     * @param courseName Course name keyword
     * @return List of teaching requirements matching the search criteria
     */
    public List<TeachingRequirement> searchRequirementsByCourseName(String courseName) {
        if (courseName == null || courseName.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String keyword = courseName.toLowerCase().trim();

        return requirementDAO.findAll().stream()
                .filter(requirement -> {
                    String name = requirement.getCourseName();
                    return name != null && name.toLowerCase().contains(keyword);
                })
                .collect(Collectors.toList());
    }

}