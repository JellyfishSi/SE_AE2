import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 教学需求类 (Teaching Requirement)
 * 表示课程主任创建的教学需求
 */
public class TeachingRequirement implements Serializable {
    // 序列化版本ID
    private static final long serialVersionUID = 1L;

    // 唯一标识符
    private String id;

    // 课程名称
    private String courseName;

    // 课程代码
    private String courseCode;

    // 时间安排 (例如: "周一 9:00-11:00")
    private String schedule;

    // 地点
    private String location;

    // 所需教师资格
    private List<String> requiredQualifications;

    // 状态 (未分配, 部分分配, 已分配)
    private RequirementStatus status;

    // 已分配的教师ID列表
    private List<String> assignedTeacherIds;

    // 备注
    private String notes;

    // 创建日期
    private long createdTimestamp;

    // 最后修改日期
    private long lastModifiedTimestamp;
}