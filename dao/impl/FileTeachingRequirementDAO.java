package dao.impl;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.TeachingRequirement;
import dao.TeachingRequirementDAO;

/**
 * 文件实现的教学需求数据访问对象 (File-based Teaching Requirement DAO)
 * 使用文件系统实现教学需求数据的持久化
 */
public class FileTeachingRequirementDAO implements TeachingRequirementDAO {
    // 存储所有教学需求的内存集合
    private List<TeachingRequirement> requirements;

    // 数据文件路径
    private final String filePath;

    /**
     * 构造函数
     *
     * @param filePath 数据文件路径
     */
    public FileTeachingRequirementDAO(String filePath) {
        this.filePath = filePath;
        this.requirements = new ArrayList<>();
        // 尝试从文件加载数据
        loadAll();
    }

    @Override
    public boolean save(TeachingRequirement requirement) {
        // 确保不存在相同ID的需求
        if (findById(requirement.getId()) != null) {
            return false;
        }

        requirements.add(requirement);
        return saveAll();
    }

    @Override
    public boolean update(TeachingRequirement requirement) {
        // 查找并替换现有需求
        for (int i = 0; i < requirements.size(); i++) {
            if (requirements.get(i).getId().equals(requirement.getId())) {
                requirements.set(i, requirement);
                return saveAll();
            }
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        boolean removed = requirements.removeIf(r -> r.getId().equals(id));
        if (removed) {
            return saveAll();
        }
        return false;
    }

    @Override
    public TeachingRequirement findById(String id) {
        return requirements.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<TeachingRequirement> findAll() {
        return new ArrayList<>(requirements);
    }

    @Override
    public boolean saveAll() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(requirements);
            return true;
        } catch (IOException e) {
            System.err.println("保存数据时出错: " + e.getMessage());
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean loadAll() {
        File file = new File(filePath);

        // 如果文件不存在，创建空列表并返回成功
        if (!file.exists()) {
            requirements = new ArrayList<>();
            return true;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            requirements = (List<TeachingRequirement>) ois.readObject();
            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("加载数据时出错: " + e.getMessage());
            // 如果加载失败，初始化一个新的空列表
            requirements = new ArrayList<>();
            return false;
        }
    }
}