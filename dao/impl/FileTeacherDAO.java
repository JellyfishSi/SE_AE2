package dao.impl;


import dao.TeacherDAO;
import model.Teacher;

import java.io.*;
import java.util.*;

public class FileTeacherDAO implements TeacherDAO {
    private static final String DIRECTORY_PATH = "data";
    private static final String FILE_PATH = DIRECTORY_PATH + "/teachers.json";
    private final Map<Integer, Teacher> teacherDB = new HashMap<>();

    public FileTeacherDAO() {
        createDataDirectory();
        loadAll();
    }

    // Automatically create data directory
    private void createDataDirectory() {
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                throw new RuntimeException("The data directory could not be created.");
            }
        }
    }

    @Override
    public boolean save(Teacher teacher) {
        teacherDB.put(teacher.getId(), teacher);
        return saveAll();
    }

    @Override
    public boolean update(Teacher teacher) {
        if (teacherDB.containsKey(teacher.getId())) {
            teacherDB.put(teacher.getId(), teacher);
            return saveAll();
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        if (teacherDB.remove(id) != null) {
            return saveAll();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        return deleteById(id);
    }

    @Override
    public Teacher findById(int id) {
        return teacherDB.get(id);
    }

    @Override
    public List<Teacher> getAll() {
        return new ArrayList<>(teacherDB.values());
    }

    @Override
    public List<Teacher> findAll() {
        return getAll();
    }

    @Override
    public boolean saveAll() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(new ArrayList<>(teacherDB.values()));
            return true;
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean loadAll() {
        File file = new File(FILE_PATH);

        // If file doesn't exist, create an empty Map and return success
        if (!file.exists()) {
            teacherDB.clear();
            return true;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();

            // Check if it's a List type
            if (!(obj instanceof List<?>)) {
                System.err.println("Error: Deserialized object is not a List");
                teacherDB.clear();
                return false;
            }

            // Safely convert to List<Teacher>
            List<?> rawList = (List<?>) obj;
            List<Teacher> teacherList = new ArrayList<>();

            for (Object item : rawList) {
                if (item instanceof Teacher) {
                    Teacher teacher = (Teacher) item;
                    // Additional validation can be added here
                    teacherList.add(teacher);
                } else if (item != null) {
                    System.err.println("Warning: Non-Teacher object found in the list");
                }
            }

            // Convert list to Map
            teacherDB.clear();
            for (Teacher teacher : teacherList) {
                if (teacher.getId() != 0) {  // Assuming 0 is an invalid ID
                    teacherDB.put(teacher.getId(), teacher);
                } else {
                    System.err.println("Warning: Skipped a teacher with invalid ID");
                }
            }
            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data: " + e.getMessage());
            // If loading fails, clear the current Map
            teacherDB.clear();
            return false;
        }
    }

    @Override
    public Teacher getTeacherById(String teacherId) {
        try {
            int id = Integer.parseInt(teacherId);
            return teacherDB.get(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}