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
 }