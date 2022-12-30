package fpt.lhlvb.softskillcommunity.repository;

import fpt.lhlvb.softskillcommunity.entity.UserTask;
import fpt.lhlvb.softskillcommunity.model.base.PageableCustom;
import fpt.lhlvb.softskillcommunity.model.response.manager.mapping.IndividualTaskInfoMapping;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.UserTaskMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTaskRepository extends JpaRepository<UserTask, Long> {

//    @Query(value = "SELECT\n" +
//            "\tut.task_id AS taskId,\n" +
//            "\tt.name AS taskName,\n" +
//            "\tt.content AS taskContent,\n" +
//            "\tut.status_id AS statusId, \n" +
//            "\tts.name AS statusName\n" +
//            "FROM\n" +
//            "user_task ut\n" +
//            "JOIN task_status ts ON\n" +
//            "ts.id = ut.status_id\n" +
//            "AND ts.delete_flag = FALSE\n" +
//            "JOIN tasks t ON\n" +
//            "t.id = ut.task_id\n" +
//            "AND t.delete_flag = FALSE\n" +
//            "JOIN tasks_relation tr ON\n" +
//            "tr.task_id = ut.task_id\n" +
//            "AND tr.is_general = FALSE\n" +
//            "AND tr.delete_flag = FALSE\n" +
//            "JOIN softskills s ON\n" +
//            "s.id = tr.softskill_id\n" +
//            "AND s.id = :softSkillId\n" +
//            "AND s.delete_flag = FALSE\n" +
//            "WHERE\n" +
//            "ut.user_id = :userId\n" +
//            "AND ut.status_id = :statusId\n" +
//            "AND ut.delete_flag = FALSE\n" +
//            "ORDER BY\n" +
//            "ut.status_id ASC", nativeQuery = true)
    @Query(value = "SELECT \n" +
            "       t.id AS taskId,\n" +
            "        t.name AS taskName,\n" +
            "        t.content AS taskContent,\n" +
            "        2 AS statusId,\n" +
            "        'Đang làm' AS statusName \n" +
            "FROM tasks t \n" +
            "JOIN tasks_relation tr ON t.\"id\" = tr.task_id AND tr.is_general = FALSE AND tr.delete_flag = FALSE \n" +
            "JOIN\n" +
            "        softskills s \n" +
            "            ON s.id = tr.softskill_id \n" +
            "            AND s.id = :softSkillId\n" +
            "            AND s.delete_flag = FALSE \n" +
            "LEFT JOIN user_task ut ON t.id = ut.task_id AND ut.delete_flag = FALSE AND ut.user_id = :userId \n" +
            "WHERE t.delete_flag = FALSE AND ut.\"id\" IS NULL\n" +
            "    ORDER BY\n" +
            "        t.id\n", nativeQuery = true)
    List<UserTaskMapping> getAllUserTasksDoing(Long userId, Long softSkillId);

    @Query(value = "SELECT \n" +
            "       t.id AS taskId,\n" +
            "        t.name AS taskName,\n" +
            "        t.content AS taskContent,\n" +
            "        3 AS statusId,\n" +
            "        'Hoàn thành' AS statusName \n" +
            "FROM tasks t \n" +
            "JOIN tasks_relation tr ON t.\"id\" = tr.task_id AND tr.is_general = FALSE AND tr.delete_flag = FALSE \n" +
            "JOIN\n" +
            "        softskills s \n" +
            "            ON s.id = tr.softskill_id \n" +
            "            AND s.id = :softSkillId\n" +
            "            AND s.delete_flag = FALSE \n" +
            "LEFT JOIN user_task ut ON t.id = ut.task_id AND ut.delete_flag = FALSE AND ut.user_id = :userId \n" +
            "WHERE t.delete_flag = FALSE AND ut.\"id\" IS NOT NULL\n" +
            "    ORDER BY\n" +
            "        t.id\n", nativeQuery = true)
    List<UserTaskMapping> getAllUserTasksDone(Long userId, Long softSkillId);

    @Query(value = "SELECT COUNT(*)\n"+
            "FROM\n" +
            "user_task ut\n" +
            "JOIN user_task_status uts ON\n" +
            "uts.id = ut.status_id\n" +
            "AND uts.delete_flag = FALSE\n" +
            "JOIN tasks t ON\n" +
            "t.id = ut.task_id\n" +
            "AND t.delete_flag = FALSE\n" +
            "JOIN tasks_relation tr ON\n" +
            "tr.task_id = ut.task_id\n" +
            "AND tr.is_general = FALSE\n" +
            "AND tr.delete_flag = FALSE\n" +
            "JOIN softskills s ON\n" +
            "s.id = tr.softskill_id\n" +
            "AND s.delete_flag = FALSE\n" +
            "WHERE\n" +
            "ut.user_id = :userId\n" +
            "AND ut.delete_flag = FALSE\n", nativeQuery = true)
    int getSizeAllUserTasks(Long userId);

    Optional<UserTask> findByUserIdAndTaskIdAndDeleteFlag(Long userId, Long taskId, Boolean deleteFlag);

//    @Query(value = "DELETE\n" +
//            "FROM user_task\n" +
//            "WHERE \n" +
//            "user_id = :userId \n" +
//            "AND task_id = :taskId", nativeQuery = true)
//    void deleteIndividualTaskByUserIdAndTaskId(Long userId, Long taskId);

    @Query(value = "WITH done AS \n" +
            "            (SELECT a.task_id, count(1) as doneNumber\n" +
            "            FROM tasks_relation a \n" +
            "            JOIN user_task b on a.task_id = b.task_id\n" +
            "            WHERE a.is_general = FALSE AND b.status_id = 3\n" +
            "            GROUP BY a.task_id\n" +
            "            ORDER BY a.task_id)\n" +
            "            \n" +
            "            SELECT a.id, a.name AS taskName, a.content as taskContent, c.id as softSkillId, c.name as softSkillName, b.start_time as startDate, b.end_time as endDate, a.created_date as createdDate, e.name as createdName,\n" +
            "            CASE \n" +
            "            WHEN a.delete_flag = TRUE THEN\n" +
            "            '0'\n" +
            "            ELSE\n" +
            "            '1'\n" +
            "            END AS status,\n" +
            "            CASE \n" +
            "            WHEN done.doneNumber IS NULL THEN\n" +
            "            0\n" +
            "            ELSE\n" +
            "            done.doneNumber\n" +
            "            END AS doneNumber \n" +
            "           \n" +
            "            FROM tasks a  \n" +
            "            JOIN tasks_relation b on a.id = b.task_id AND b.is_general = FALSE\n" +
            "            join softskills c on b.softskill_id = c.id\n" +
            "            join users e on a.created_by = e.id\n" +
            "            LEFT JOIN done on a.id = done.task_id\n" +
            "               WHERE a.ID <> :id\n" +
            "            ORDER BY a.id", nativeQuery = true)
    List<IndividualTaskInfoMapping> getIndividualTaskInfo(@Param("id") int id);
}
