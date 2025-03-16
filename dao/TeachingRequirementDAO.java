import java.util.List;

/**
 * 教学需求数据访问对象接口 (Teaching Requirement DAO Interface)
 * 定义对教学需求数据的访问操作
 */
public interface TeachingRequirementDAO {
    /**
     * 保存一个新的教学需求
     *
     * @param requirement 要保存的教学需求对象
     * @return 保存成功返回true，否则返回false
     */
    boolean save(TeachingRequirement requirement);

    /**
     * 更新已存在的教学需求
     *
     * @param requirement 要更新的教学需求对象
     * @return 更新成功返回true，否则返回false
     */
    boolean update(TeachingRequirement requirement);

    /**
     * 根据ID删除教学需求
     *
     * @param id 要删除的教学需求ID
     * @return 删除成功返回true，否则返回false
     */
    boolean delete(String id);

    /**
     * 根据ID查找教学需求
     *
     * @param id 要查找的教学需求ID
     * @return 找到的教学需求对象，如果不存在则返回null
     */
    TeachingRequirement findById(String id);

    /**
     * 获取所有教学需求
     *
     * @return 所有教学需求的列表
     */
    List<TeachingRequirement> findAll();

    /**
     * 保存所有教学需求
     *
     * @return 保存成功返回true，否则返回false
     */
    boolean saveAll();

    /**
     * 加载所有教学需求
     *
     * @return 加载成功返回true，否则返回false
     */
    boolean loadAll();
}
