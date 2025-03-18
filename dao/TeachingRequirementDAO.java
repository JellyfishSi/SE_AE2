package dao;

import java.util.List;
import model.TeachingRequirement;
/**
 * Teaching Requirement DAO Interface
 * Defines operations for accessing teaching requirement data
 */
public interface TeachingRequirementDAO {
    /**
     * Save a new teaching requirement
     *
     * @param requirement The teaching requirement object to save
     * @return Returns true if saved successfully, otherwise returns false
     */
    boolean save(TeachingRequirement requirement);

    /**
     * Update an existing teaching requirement
     *
     * @param requirement The teaching requirement object to update
     * @return Returns true if updated successfully, otherwise returns false
     */
    boolean update(TeachingRequirement requirement);

    /**
     * Delete a teaching requirement by ID
     *
     * @param id The ID of the teaching requirement to delete
     * @return Returns true if deleted successfully, otherwise returns false
     */
    boolean delete(String id);

    /**
     * Find a teaching requirement by ID
     *
     * @param id The ID of the teaching requirement to find
     * @return The found teaching requirement object, returns null if not found
     */
    TeachingRequirement findById(String id);

    /**
     * Get all teaching requirements
     *
     * @return A list of all teaching requirements
     */
    List<TeachingRequirement> findAll();

    /**
     * Save all teaching requirements
     *
     * @return Returns true if saved successfully, otherwise returns false
     */
    boolean saveAll();

    /**
     * Load all teaching requirements
     *
     * @return Returns true if loaded successfully, otherwise returns false
     */
    boolean loadAll();
}