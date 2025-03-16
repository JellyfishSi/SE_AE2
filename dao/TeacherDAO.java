package dao;

import model.Teacher;

import java.util.List;

/** Teacher DAO Interface 
 * Defines operations for managing teacher data(CRUD)
 * and data persistence
 * @author Tianshu Luo
 * @version 1.0.0
*/
public interface TeacherDAO {
    /**
     * Save the information of a new teacher
     * @param teacher the teacher to be saved
     * @return true if successful, otherwise false
     */
    boolean save(Teacher teacher);

    /**
     * Update the information of a registered teacher
     * @param teacher the teacher to be updated
     * @return true if successful, otherwise false
     */
    boolean update(Teacher teacher);

    /**
     * Delete the information of a registered teacher by ID
     * @param id the id of the teacher to be deleted
     * @return true if successful, otherwise false
     */
    boolean deleteById(int id);

    /**
     * Find the registered teacher by ID
     * @param id the id of the teacher to be found
     * @return the found teacher
     */
    Teacher findById(int id);

    /**
     * Get a list of all the teachers
     * @return list of all the teachers
     */
    List<Teacher> getAll();

    /**
     * Save information of all the teachers
     * @return true if successful, otherwise false
     */
    boolean saveAll();

    /**
     * Load information of all the teachers
     * @return true if successful, otherwise false
     */
    boolean loadAll();
}