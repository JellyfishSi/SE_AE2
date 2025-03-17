package dao.impl;

import java.io.*;
import java.util.*;
import model.TeachingRequirement;
import dao.TeachingRequirementDAO;

/**
 * 文件实现的教学需求数据访问对象 (File-based Teaching Requirement DAO)
 * 使用文件系统实现教学需求数据的持久化
 */
public class FileTeachingRequirementDAO implements TeachingRequirementDAO {
    private static final String DIRECTORY_PATH = "data";
    private static final String FILE_PATH = DIRECTORY_PATH + "/requirements.json";

    // 存储所有教学需求的内存集合
    private Map<String, TeachingRequirement> requirementsMap;

    /**
     * 构造函数
     */
    public FileTeachingRequirementDAO() {
        createDataDirectory();
        this.requirementsMap = new HashMap<>();
        loadAll();
    }

    /**
     * 构造函数
     *
     * @param filePath 数据文件路径
     */
    public FileTeachingRequirementDAO(String filePath) {
        this();
    }

    // 自动创建 data 目录
    private void createDataDirectory() {
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                System.err.println("无法创建 data 目录");
            }
        }
    }

    @Override
    public boolean save(TeachingRequirement requirement) {
        // 确保不存在相同ID的需求
        if (findById(requirement.getId()) != null) {
            return false;
        }

        requirementsMap.put(requirement.getId(), requirement);
        return saveAll();
    }

    @Override
    public boolean update(TeachingRequirement requirement) {
        // 查找并替换现有需求
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

        // 如果文件不存在，创建空列表并返回成功
        if (!file.exists()) {
            requirementsMap = new HashMap<>();
            return true;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<TeachingRequirement> requirementsList = (List<TeachingRequirement>) ois.readObject();

            // 将列表转换为Map
            requirementsMap.clear();
            for (TeachingRequirement requirement : requirementsList) {
                requirementsMap.put(requirement.getId(), requirement);
            }
            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("加载数据时出错: " + e.getMessage());
            // 如果加载失败，初始化一个新的空Map
            requirementsMap = new HashMap<>();
            return false;
        }
    }
}