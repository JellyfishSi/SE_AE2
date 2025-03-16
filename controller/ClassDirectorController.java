package controller;

import dao.TeachingRequirementDAO;
import model.TeachingRequirement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 课程主任控制器 (Class Director Controller)
 * 处理课程主任相关的业务逻辑，包括教学需求的创建、修改、删除和查询
 */
 public class ClassDirectorController {

     // 教学需求数据访问对象
     private final TeachingRequirementDAO requirementDAO;

     /**
      * 构造函数
      *
      * @param requirementDAO 教学需求数据访问对象
      */
     public ClassDirectorController(TeachingRequirementDAO requirementDAO) {
         this.requirementDAO = requirementDAO;
     }

     /**
      * 创建新的教学需求
      *
      * @param courseName 课程名称
      * @param courseCode 课程代码
      * @param schedule 时间安排
      * @param location 地点
      * @param qualifications 所需教师资格列表
      * @param notes 备注
      * @return 创建成功返回需求对象，失败返回null
      */
     public TeachingRequirement createRequirement(
             String courseName,
             String courseCode,
             String schedule,
             String location,
             List<String> qualifications,
             String notes) {

         // 参数验证
         if (courseName == null || courseName.trim().isEmpty() ||
             courseCode == null || courseCode.trim().isEmpty()) {
             return null;
         }

         // 创建新的教学需求对象
         TeachingRequirement requirement = new TeachingRequirement(
                 courseName, courseCode, schedule, location, qualifications);
         requirement.setNotes(notes);

         // 保存到数据访问对象
         boolean success = requirementDAO.save(requirement);

         // 如果保存成功，返回新创建的需求对象
         return success ? requirement : null;
     }

    /**
     * 更新现有教学需求
     *
     * @param id 需求ID
     * @param courseName 课程名称
     * @param courseCode 课程代码
     * @param schedule 时间安排
     * @param location 地点
     * @param qualifications 所需教师资格列表
     * @param notes 备注
     * @return 更新成功返回true，失败返回false
     */
    public boolean updateRequirement(
            String id,
            String courseName,
            String courseCode,
            String schedule,
            String location,
            List<String> qualifications,
            String notes) {

        // 查找现有需求
        TeachingRequirement requirement = requirementDAO.findById(id);
        if (requirement == null) {
            return false;
        }

        // 更新需求属性
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

        // 保存更新后的需求
        return requirementDAO.update(requirement);
    }

    /**
     * 删除教学需求
     *
     * @param id 需求ID
     * @return 删除成功返回true，失败返回false
     */
    public boolean deleteRequirement(String id) {
        return requirementDAO.delete(id);
    }

    /**
     * 获取所有教学需求
     *
     * @return 所有教学需求的列表
     */
    public List<TeachingRequirement> getAllRequirements() {
        return requirementDAO.findAll();
    }

    /**
     * 根据ID获取教学需求
     *
     * @param id 需求ID
     * @return 找到的教学需求对象，如果不存在则返回null
     */
    public TeachingRequirement getRequirementById(String id) {
        return requirementDAO.findById(id);
    }

    /**
     * 根据状态获取教学需求列表
     *
     * @param status 需求状态
     * @return 符合状态的教学需求列表
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
     * 根据课程名称搜索教学需求
     *
     * @param courseName 课程名称关键字
     * @return 符合搜索条件的教学需求列表
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

    /**
     * 保存所有更改
     *
     * @return 保存成功返回true，失败返回false
     */
    public boolean saveAllChanges() {
        return requirementDAO.saveAll();
    }
}