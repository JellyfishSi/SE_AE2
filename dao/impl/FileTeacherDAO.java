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

    // 自动创建 data 目录
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

        // 如果文件不存在，创建空Map并返回成功
        if (!file.exists()) {
            teacherDB.clear();
            return true;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();

            // 检查是否为List类型
            if (!(obj instanceof List<?>)) {
                System.err.println("Error: Deserialized object is not a List");
                teacherDB.clear();
                return false;
            }

            // 安全地转换为List<Teacher>
            List<?> rawList = (List<?>) obj;
            List<Teacher> teacherList = new ArrayList<>();

            for (Object item : rawList) {
                if (item instanceof Teacher) {
                    Teacher teacher = (Teacher) item;
                    // 可以在这里添加额外的验证
                    teacherList.add(teacher);
                } else if (item != null) {
                    System.err.println("Warning: Non-Teacher object found in the list");
                }
            }

            // 将列表转换为Map
            teacherDB.clear();
            for (Teacher teacher : teacherList) {
                if (teacher.getId() != 0) {  // 假设0是无效ID
                    teacherDB.put(teacher.getId(), teacher);
                } else {
                    System.err.println("Warning: Skipped a teacher with invalid ID");
                }
            }
            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data: " + e.getMessage());
            // 如果加载失败，清空当前Map
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
