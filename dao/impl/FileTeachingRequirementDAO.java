package dao.impl;

import java.io.*;
import java.util.*;
import model.TeachingRequirement;
import dao.TeachingRequirementDAO;

/**
 * File-based Teaching Requirement DAO Implementation
 * Implements data persistence of teaching requirements using the file system
 */
public class FileTeachingRequirementDAO implements TeachingRequirementDAO {
    private static final String DIRECTORY_PATH = "data";
    private static final String FILE_PATH = DIRECTORY_PATH + "/requirements.json";

    // In-memory collection for storing all teaching requirements
    private Map<String, TeachingRequirement> requirementsMap;

    /**
     * Constructor
     */
    public FileTeachingRequirementDAO() {
        createDataDirectory();
        this.requirementsMap = new HashMap<>();
        loadAll();
    }

    // Automatically create the data directory
    private void createDataDirectory() {
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                System.err.println("Cannot create data directory");
            }
        }
    }

    @Override
    public boolean save(TeachingRequirement requirement) {
        // Ensure there is no requirement with the same ID
        if (findById(requirement.getId()) != null) {
            return false;
        }

        requirementsMap.put(requirement.getId(), requirement);
        return saveAll();
    }

    @Override
    public boolean update(TeachingRequirement requirement) {
        // Find and replace the existing requirement
        if (!requirementsMap.containsKey(requirement.getId())) {
            return false;
        }

        requirementsMap.put(requirement.getId(), requirement);
        return saveAll();
    }

    @Override
    public boolean delete(String id) {
        if (!requirementsMap.containsKey(id)) {
            return false;
        }

        requirementsMap.remove(id);
        return saveAll();
    }

    @Override
    public TeachingRequirement findById(String id) {
        return requirementsMap.get(id);
    }

    @Override
    public List<TeachingRequirement> findAll() {
        return new ArrayList<>(requirementsMap.values());
    }

    @Override
    public boolean saveAll() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(new ArrayList<>(requirementsMap.values()));
            return true;
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean loadAll() {
        File file = new File(FILE_PATH);

        // If the file doesn't exist, create an empty list and return success
        if (!file.exists()) {
            requirementsMap = new HashMap<>();
            return true;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<TeachingRequirement> requirementsList = (List<TeachingRequirement>) ois.readObject();

            // Convert the list to a map
            requirementsMap.clear();
            for (TeachingRequirement requirement : requirementsList) {
                requirementsMap.put(requirement.getId(), requirement);
            }
            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data: " + e.getMessage());
            // If loading fails, initialize a new empty map
            requirementsMap = new HashMap<>();
            return false;
        }
    }
}