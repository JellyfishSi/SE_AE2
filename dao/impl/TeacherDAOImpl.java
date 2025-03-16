package dao.impl;

import model.Teacher;
import dao.TeacherDAO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Teacher DAO Implementation
 * @author Tianshu Luo
 * @version 1.0.0
 */
public class TeacherDAOImpl implements TeacherDAO {
    private List<Teacher> teacherList;

    private final String filePath;

    /**
     * Constructor
     * 
     * @param filePath file path of the data
     */
    public TeacherDAOImpl(String filePath) {
        this.filePath = filePath;
        loadAll();
    }

    @Override
    public boolean save(Teacher teacher) {
        // Ensure no duplicate id exists
        if (findById(teacher.getId()) != null) {
            return false;
        }

        teacherList.add(teacher);
        return saveAll();
    }

    @Override
    public boolean update(Teacher teacher) {
        // Find and update the information of the teacher
        for (int i = 0; i < teacherList.size(); i++) {
            if (teacherList.get(i).getId() == teacher.getId()) {
                teacherList.set(i, teacher);
                return saveAll();
            }
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        boolean removed = teacherList.removeIf(t -> t.getId() == id);
        if (removed) {
            return saveAll();
        } else {
            System.err.println("Delete failed: Teacher with ID " + id + " not found.");
            return false;
        }
    }

    @Override
    public Teacher findById(int id) {
        return teacherList.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Teacher> findAll() {
        return new ArrayList<>(teacherList);
    }

    @Override
    public boolean saveAll() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(teacherList);
            return true;
        } catch (IOException e) {
            System.err.println("Error when saving the data: " + e.getMessage());
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean loadAll() {
        File file = new File(filePath);

        // If file does not exist, create an empty list and return true
        if (!file.exists()) {
            teacherList = new ArrayList<>();
            return true;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            teacherList = (List<Teacher>) ois.readObject();
            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error when loading teacher data: " + e.getMessage());
            // If IO error, create an empty list
            teacherList = new ArrayList<>();
            return false;
        }
    }
}
