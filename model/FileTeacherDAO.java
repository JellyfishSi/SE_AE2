package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dao.TeacherDAO;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class FileTeacherDAO implements TeacherDAO {
    private static final String DIRECTORY_PATH = "data";
    private static final String FILE_PATH = DIRECTORY_PATH + "/teachers.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Map<Integer, Teacher> teacherDB;

    public FileTeacherDAO() {
        createDataDirectory();
        createJsonFileIfNotExists();
        this.teacherDB = loadFromFile();
    }

    // 自动创建 data 目录
    private void createDataDirectory() {
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                throw new RuntimeException("无法创建 data 目录。");
            }
        }
    }

    // 自动创建 JSON 文件，如果不存在
    private void createJsonFileIfNotExists() {
        File file = new File(FILE_PATH);
        try {
            if (!file.exists()) {
                boolean created = file.createNewFile();
                if (created) {
                    // 初始化为空 JSON 对象
                    try (Writer writer = new FileWriter(file)) {
                        writer.write("{}");
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("创建 teachers.json 文件时出错。", e);
        }
    }

    // 从 JSON 文件加载数据
    private Map<Integer, Teacher> loadFromFile() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type type = new TypeToken<Map<Integer, Teacher>>() {}.getType();
            Map<Integer, Teacher> data = gson.fromJson(reader, type);
            return data != null ? data : new HashMap<>();
        } catch (IOException e) {
            throw new RuntimeException("读取 teachers.json 文件时出错。", e);
        }
    }

    // 将数据保存到 JSON 文件
    private void saveToFile() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(teacherDB, writer);
        } catch (IOException e) {
            throw new RuntimeException("保存 teachers.json 文件时出错。", e);
        }
    }

    @Override
    public boolean save(Teacher teacher) {
        teacherDB.put(teacher.getId(), teacher);
        saveToFile();
        return true;
    }

    @Override
    public boolean update(Teacher teacher) {
        if (teacherDB.containsKey(teacher.getId())) {
            teacherDB.put(teacher.getId(), teacher);
            saveToFile();
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        if (teacherDB.remove(id) != null) {
            saveToFile();
            return true;
        }
        return false;
    }

    @Override
    public Teacher findById(int id) {
        return teacherDB.get(id);
    }

    @Override
    public List<Teacher> getAll() {
        if (teacherDB == null || teacherDB.isEmpty()) {
            System.out.println("数据库中没有教师数据。");
            return new ArrayList<>();
        }
        return new ArrayList<>(teacherDB.values());
    }

    @Override
    public boolean saveAll() {
        saveToFile();
        return true;
    }

    @Override
    public boolean loadAll() {
        return !teacherDB.isEmpty();
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
