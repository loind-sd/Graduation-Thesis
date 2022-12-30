package fpt.lhlvb.softskillcommunity.repository;

import fpt.lhlvb.softskillcommunity.entity.Tasks;
import fpt.lhlvb.softskillcommunity.model.response.manager.TasksBySoftSkillMapping;
import fpt.lhlvb.softskillcommunity.model.response.manager.mapping.GroupTaskInfoMapping;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, Long> {
    @Query(value = "WITH t AS (\n" +
            "SELECT\n" +
            "\trt.room_id,\n" +
            "\tMAX(rt.created_date) AS created_date\n" +
            "FROM\n" +
            "\trooms_task rt\n" +
            "JOIN tasks t ON\n" +
            "\tt.id = rt.task_id\n" +
            "WHERE\n" +
            "\trt.delete_flag = FALSE\n" +
            "GROUP BY\n" +
            "\trt.room_id)\n" +
            "SELECT\n" +
            "\tt.room_id AS roomId,\n" +
            "\trt2.task_id AS taskId,\n" +
            "\trt2.status_id AS statusTask,\n" +
            "\trt2.start_time AS startTime,\n" +
            "\trt2.count_cancel AS countCancel\n" +
            "FROM\n" +
            "\tt\n" +
            "JOIN rooms_task rt2 ON\n" +
            "\tt.room_id = rt2.room_id\n" +
            "\tAND rt2.room_id = :roomId\n" +
            "JOIN tasks t2 ON\n" +
            "\tt2.id = rt2.task_id\n" +
            "\tAND t2.delete_flag = FALSE\n" +
            "WHERE\n" +
            "\trt2.status_id = 2", nativeQuery = true)
    Optional<TaskLatestMapping> getTaskLatestByRoomId(Long roomId);

    @Query(value = "WITH tblResult AS(WITH task_latest AS (\n" +
            "SELECT\n" +
            "\trt.room_id,\n" +
            "\tMAX(rt.created_date) AS created_date\n" +
            "FROM\n" +
            "\trooms_task rt\n" +
            "JOIN tasks t ON\n" +
            "\tt.id = rt.task_id\n" +
            "WHERE\n" +
            "\trt.delete_flag = FALSE\n" +
            "GROUP BY\n" +
            "\trt.room_id)\n" +
            "SELECT\n" +
            "\trt2.task_id AS taskId,\n" +
            "\tt2.name AS taskName,\n" +
            "\tt2.\"content\" AS taskContent,\n" +
            "\tt2.image_data AS imageData,\n" +
            "\ttr.sub_task AS subTaskId\n" +
            "FROM\n" +
            "\ttask_latest t\n" +
            "JOIN rooms_task rt2 ON\n" +
            "\tt.room_id = rt2.room_id\n" +
            "\tAND rt2.room_id = :roomId\n" +
            "JOIN tasks t2 ON\n" +
            "\tt2.id = rt2.task_id\n" +
            "\tAND t2.delete_flag = FALSE\n" +
            "JOIN tasks_relation tr ON\n" +
            "\ttr.task_id = t2.id\n" +
            "\tAND tr.delete_flag = FALSE\n" +
            "WHERE\n" +
            "\trt2.status_id = 2)\n" +
            "SELECT\n" +
            "\ttblResult.*,\n" +
            "\tt1.\"name\" AS subTaskName,\n" +
            "\tt1.\"content\" AS subTaskContent,\n" +
            "\tCASE\n" +
            "\t\tWHEN ut.status_id IS NULL THEN 1\n" +
            "\t\tELSE ut.status_id\n" +
            "\tEND AS statusSubTask\n" +
            "FROM\n" +
            "\ttasks t1\n" +
            "RIGHT JOIN tblResult ON\n" +
            "\ttblResult.subTaskId = t1.id\n" +
            "LEFT JOIN user_task ut ON\n" +
            "\tut.task_id = tblResult.subTaskId\n" +
            "\tAND ut.user_id = :userId", nativeQuery = true)
    TaskLatestInRoomMapping getTaskLatestDetailsMappingByRoomId(Long roomId, Long userId);

    @Query(value = "SELECT\n" +
            "\tt.id AS taskId,\n" +
            "\tt.name AS taskName,\n" +
            "\tt.content AS taskContent,\n" +
            "\tCASE\n" +
            "\t\tWHEN utf.favorite IS NULL THEN FALSE\n" +
            "\t\tELSE utf.favorite\n" +
            "\tEND AS isFavorite\n" +
            "FROM\n" +
            "\ttasks t\n" +
            "LEFT JOIN user_task_favorite utf ON\n" +
            "\tutf.task_id = t.id\n" +
            "\tAND utf.created_by = :userId\n" +
            "\tAND utf.delete_flag = FALSE\n" +
            "WHERE\n" +
            "\tt.delete_flag = FALSE\n" +
            "\tAND t.id = :taskId", nativeQuery = true)
    Optional<TasksMapping> findByUserIdAndTaskId(Long userId, Long taskId);

    @Query(value = "WITH tmp_user_room_task_isDoingOrDone AS(\n" +
            "SELECT\n" +
            "\tu.task_id AS tmp_task_id\n" +
            "FROM\n" +
            "\tuser_room_task_info u\n" +
            "WHERE\n" +
            "\tu.user_id = :userId \n" +
            "\tAND u.status_task_id <> :statusId\n" +
            ")\n" +
            "SELECT\n" +
            "\tt.id AS taskId,\n" +
            "\tt.name AS taskName,\n" +
            "\tt.content AS taskContent,\n" +
            "\tCASE\n" +
            "\t\tWHEN utf.favorite IS NULL THEN FALSE\n" +
            "\t\tELSE utf.favorite\n" +
            "\tEND AS isFavorite,\n" +
            "\tt.image_data AS imageData\n" +
            "FROM\n" +
            "\ttasks t\n" +
            "LEFT JOIN tmp_user_room_task_isDoingOrDone ON\n" +
            "\ttmp_user_room_task_isDoingOrDone.tmp_task_id = t.id\n" +
            "LEFT JOIN user_task_favorite utf ON\n" +
            "\tutf.task_id = t.id\n" +
            "\tAND utf.created_by = :userId\n" +
            "\tAND utf.delete_flag = FALSE\n" +
            "JOIN tasks_relation tr ON\n" +
            "\ttr.task_id = t.id\n" +
            "\tAND tr.is_general = TRUE\n" +
            "\tAND tr.delete_flag = FALSE\n" +
            "JOIN softskills s ON\n" +
            "\ts.id = tr.softskill_id\n" +
            "\tAND s.id = :softSkillId\n" +
            "\tAND s.delete_flag = FALSE\n" +
            "WHERE\n" +
            "\tt.delete_flag = FALSE\n" +
            "\tAND tmp_user_room_task_isDoingOrDone.tmp_task_id IS NULL", nativeQuery = true)
    List<TasksMapping> getTasksNotStartBySoftSkillIdAndUserId(Long softSkillId, Long userId, Integer statusId);

    @Query(value = "SELECT\n" +
            "            DISTINCT \n" +
            "            t.id AS taskId,\n" +
            "            t.name AS taskName,\n" +
            "            t.content AS taskContent,\n" +
            "            CASE\n" +
            "            WHEN utf.favorite IS NULL THEN FALSE\n" +
            "            ELSE utf.favorite\n" +
            "            END AS isFavorite,\n" +
            "            t.image_data AS imageData\n" +
            "            FROM\n" +
            "            tasks t\n" +
            "            JOIN user_room_task_info urti ON\n" +
            "            urti.task_id = t.id\n" +
            "            AND urti.status_task_id = :statusId\n" +
            "            AND urti.user_id = :userId\n" +
            "            AND urti.delete_flag = FALSE\n" +
            "            JOIN tasks_relation tr ON\n" +
            "            tr.task_id = t.id\n" +
            "            AND tr.is_general = TRUE\n" +
            "            AND tr.delete_flag = FALSE\n" +
            "            JOIN softskills s ON\n" +
            "            s.id = tr.softskill_id\n" +
            "            AND s.id = :softSkillId\n" +
            "            AND s.delete_flag = false\n" +
            "            LEFT JOIN user_task_favorite utf ON\n" +
            "            utf.created_by = urti.user_id and t.id = utf.task_id and tr.softskill_id = :softSkillId\n" +
            "            AND utf.delete_flag = FALSE\n" +
            "            WHERE\n" +
            "            t.delete_flag = FALSE", nativeQuery = true)
    List<TasksMapping> getTasksByCondition(Long softSkillId, Long userId, Integer statusId);

    @Query(value = "SELECT\n" +
            "\tDISTINCT \n" +
            "\tt.id AS taskId,\n" +
            "\tt.name AS taskName,\n" +
            "\tt.content AS taskContent,\n" +
            "\tutf.favorite AS isFavorite,\n" +
            "\tt.image_data AS imageData\n" +
            "FROM\n" +
            "\tuser_task_favorite utf\n" +
            "JOIN tasks t ON\n" +
            "\tt.id = utf.task_id\n" +
            "\tAND t.delete_flag = FALSE\n" +
            "JOIN tasks_relation tr ON\n" +
            "\ttr.task_id = t.id\n" +
            "\tAND tr.delete_flag = FALSE\n" +
            "JOIN softskills s ON\n" +
            "\ts.id = tr.softskill_id\n" +
            "\tAND s.delete_flag = FALSE\n" +
            "WHERE\n" +
            "\tutf.created_by = :userId\n" +
            "\tAND s.id = :softSkillId\n" +
            "\tAND utf.favorite = TRUE\n" +
            "\tAND utf.delete_flag = FALSE", nativeQuery = true)
    List<TasksMapping> getTasksFavourite(Long softSkillId, Long userId);

    @Query(value = "WITH tmp_user_task_with_status AS(\n" +
            "SELECT\n" +
            "\tu.task_id AS tmp_task_id, u.status_task_id AS status_task\n" +
            "FROM\n" +
            "\tuser_room_task_info u\n" +
            "WHERE\n" +
            "\tu.user_id = :userId\n" +
            "\tAND u.status_task_id <> 1\n" +
            "\tAND u.delete_flag = FALSE\n" +
            ")\n" +
            "SELECT\n" +
            "\ttmp_user_task_with_status.status_task AS statusTaskId,\n" +
            "\tt.id AS taskId,\n" +
            "\tt.name AS taskName,\n" +
            "\trt.softskill_id AS softSkillId,\n" +
            "\tCASE\n" +
            "\t\tWHEN utf.favorite IS NULL THEN FALSE\n" +
            "\t\tELSE utf.favorite\n" +
            "\tEND AS isFavorite\n" +
            "FROM\n" +
            "\ttasks t\n" +
            "JOIN tmp_user_task_with_status ON\n" +
            "\ttmp_user_task_with_status.tmp_task_id = t.id\n" +
            "JOIN rooms_task rt ON\n" +
            "\trt.task_id = t.id\n" +
            "\tAND rt.delete_flag = FALSE\n" +
            "LEFT JOIN user_task_favorite utf ON\n" +
            "\tutf.task_id = t.id\n" +
            "\tAND utf.created_by = :userId\n" +
            "\tAND utf.status_id <> 1\n" +
            "\tAND utf.delete_flag = FALSE\n" +
            "JOIN tasks_relation tr ON\n" +
            "\ttr.task_id = t.id\n" +
            "\tAND tr.is_general = TRUE\n" +
            "\tAND tr.delete_flag = FALSE\n" +
            "JOIN softskills s ON\n" +
            "\ts.id = rt.softskill_id\n" +
            "\tAND s.delete_flag = FALSE\n" +
            "WHERE\n" +
            "\t t.delete_flag = FALSE ", nativeQuery = true)
    List<TasksSearchMapping> getAllTaskUserRoom(Long userId);

    @Query(value = "SELECT\n" +
            "\tDISTINCT\n" +
            "\tt.id AS taskId,\n" +
            "\tt.name AS taskName,\n" +
            "\tt.content AS taskContent,\n" +
            "\tt.image_data AS imageData\n" +
            "FROM\n" +
            "\ttasks t\n" +
            "JOIN tasks_relation tr ON\n" +
            "\ttr.task_id = t.id\n" +
            "\tAND tr.delete_flag = FALSE\n" +
            "\tAND tr.is_general = TRUE\n" +
            "JOIN softskills s ON\n" +
            "\ts.id = tr.softskill_id\n" +
            "\tAND s.id = :softSkillId\n" +
            "\tAND s.delete_flag = FALSE\n" +
            "WHERE\n" +
            "\tt.delete_flag = FALSE\n" +
            "\tAND (CAST(t.created_date AS DATE) >= :startTime\n" +
            "\t\tAND CAST(t.created_date AS DATE) <= :endTime)", nativeQuery = true)
    List<TasksMapping> getAllTaskBySoftSkill(Long softSkillId, Date startTime, Date endTime);

    @Query(value = "SELECT COUNT(DISTINCT t.id)\n" +
            "FROM\n" +
            "\ttasks t\n" +
            "JOIN tasks_relation tr ON\n" +
            "\ttr.task_id = t.id\n" +
            "\tAND tr.delete_flag = FALSE\n" +
            "\tAND tr.is_general = TRUE\n" +
            "JOIN softskills s ON\n" +
            "\ts.id = tr.softskill_id\n" +
            "\tAND s.id = :softSkillId\n" +
            "\tAND s.delete_flag = FALSE\n" +
            "WHERE\n" +
            "\tt.delete_flag = FALSE\n" +
            "\tAND (CAST(t.created_date AS DATE) >= :startTime\n" +
            "\t\tAND CAST(t.created_date AS DATE) <= :endTime)", nativeQuery = true)
    int getSizeAllTaskBySoftSkill(Long softSkillId, Date startTime, Date endTime);

    @Query(value = "WITH count_task_completed_tmp AS(\n" +
            "SELECT\n" +
            "\turti.task_id,\n" +
            "\tCOUNT(urti.status_task_id)\n" +
            "FROM \n" +
            "\tuser_room_task_info urti\n" +
            "WHERE\n" +
            "\turti.delete_flag = FALSE\n" +
            "\tAND urti.status_task_id = :statusId\n" +
            "GROUP BY\n" +
            "\turti.task_id\n" +
            "UNION\n" +
            "SELECT\n" +
            "\tut.task_id,\n" +
            "\tCOUNT(ut.status_id)\n" +
            "FROM \n" +
            "\t\tuser_task ut\n" +
            "WHERE\n" +
            "\tut.delete_flag = FALSE\n" +
            "\tAND ut.status_id = :statusId\n" +
            "GROUP BY\n" +
            "\tut.task_id\n" +
            "),\n" +
            "count_task_favorite_tmp AS(\n" +
            "SELECT\n" +
            "\tutf.task_id,\n" +
            "\tCOUNT(utf.id)\n" +
            "FROM \n" +
            "\tuser_task_favorite utf\n" +
            "WHERE\n" +
            "\tutf.delete_flag = FALSE\n" +
            "GROUP BY\n" +
            "\tutf.task_id\n" +
            ")\n" +
            "SELECT\n" +
            "\tDISTINCT\n" +
            "\tt.id AS taskId,\n" +
            "\tt.name AS taskName,\n" +
            "\tt.content AS taskContent,\n" +
            "\ttr.is_general AS isGeneral,\n" +
            "\tCAST(t.created_date AS DATE) AS createDate,\n" +
            "\tu.name AS createdBy,\n" +
            "\tCAST(tr.start_time AS DATE) AS startTime,\n" +
            "\tCAST(tr.end_time AS DATE) AS endTime,\n" +
            "\tCASE\n" +
            "\t\tWHEN tmp.count IS NULL THEN 0\n" +
            "\t\tELSE tmp.count\n" +
            "\tEND AS countCompleted,\n" +
            "\tCASE\n" +
            "\t\tWHEN tmp1.count IS NULL THEN 0\n" +
            "\t\tELSE tmp1.count\n" +
            "\tEND AS countFavourite\n" +
            "FROM\n" +
            "\ttasks t\n" +
            "LEFT JOIN count_task_completed_tmp tmp ON\n" +
            "\ttmp.task_id = t.id\n" +
            "LEFT JOIN count_task_favorite_tmp tmp1 ON\n" +
            "\ttmp1.task_id = t.id\n" +
            "JOIN tasks_relation tr ON\n" +
            "\ttr.task_id = t.id\n" +
            "\tAND tr.delete_flag = FALSE\n" +
            "JOIN softskills s ON\n" +
            "\ts.id = tr.softskill_id\n" +
            "\tAND s.id = :softSkillId\n" +
            "\tAND s.delete_flag = FALSE\n" +
            "LEFT JOIN user_room_task_info urti ON\n" +
            "\turti.task_id = t.id\n" +
            "\tAND urti.status_task_id = :statusId\n" +
            "\tAND urti.delete_flag = FALSE\n" +
            "LEFT JOIN user_task ut ON\n" +
            "\tut.task_id = t.id\n" +
            "\tAND ut.status_id = :statusId\n" +
            "\tAND ut.delete_flag = FALSE\n" +
            "JOIN users u ON\n" +
            "\tu.id = t.created_by\n" +
            "\tAND u.delete_flag = FALSE\n" +
            "WHERE\n" +
            "\tt.delete_flag = FALSE\n" +
            "\tAND (CAST(t.created_date AS DATE) >= :startTime\n" +
            "\t\tAND CAST(t.created_date AS DATE) <= :endTime)", nativeQuery = true)
    List<TasksBySoftSkillMapping> getAllTaskBySoftSkillForManager(Long softSkillId, Date startTime, Date endTime, Integer statusId);

    @Query(value = "SELECT\n" +
            "\tt.id AS taskId,\n" +
            "\tt.name AS taskName,\n" +
            "\tt.content AS taskContent,\n" +
            "\tt.image_data AS imageTask,\n" +
            "\ttr.sub_task AS subTaskId,\n" +
            "\tt1.name AS subTaskName\n" +
            "FROM\n" +
            "\ttasks_relation tr\n" +
            "JOIN tasks t ON\n" +
            "\tt.id = tr.task_id\n" +
            "\tAND t.delete_flag = FALSE\n" +
            "LEFT JOIN tasks t1 ON\n" +
            "\tt1.id = tr.sub_task\n" +
            "\tAND t1.delete_flag = FALSE\n" +
            "WHERE\n" +
            "\ttr.task_id = :taskId\n" +
            "\tAND tr.delete_flag = FALSE", nativeQuery = true)
    Optional<TaskDetailsInRoomMapping> getTaskDetailsInRoomMapping(Long taskId);

    @Query(value = "SELECT\n" +
            "\tt.id AS taskId,\n" +
            "\tt.name AS taskName,\n" +
            "\tt.content AS taskContent,\n" +
            "\tt.image_data AS imageData,\n" +
            "\tcount(rt.task_id) AS countGrDoingOrDone\n" +
            "FROM\n" +
            "\ttasks t\n" +
            "JOIN tasks_relation tr ON\n" +
            "\ttr.task_id = t.id\n" +
            "\tAND tr.delete_flag = FALSE\n" +
            "\tAND tr.is_general = TRUE\n" +
            "JOIN softskills s ON\n" +
            "\ts.id = tr.softskill_id\n" +
            "\tAND s.id = :softSkillId\n" +
            "\tAND s.delete_flag = FALSE\n" +
            "LEFT JOIN rooms_task rt ON\n" +
            "\trt.task_id = t.id\n" +
            "\t\tAND rt.status_id = :statusId\n" +
            "\tAND rt.delete_flag = FALSE\n" +
            "WHERE\n" +
            "\tt.delete_flag = FALSE\n" +
            "\tAND (CAST(t.created_date AS DATE) >= :startTime\n" +
            "\t\tAND CAST(t.created_date AS DATE) <= :endTime)\n" +
            "GROUP BY\n" +
            "\tt.id,\n" +
            "\tt.name,\n" +
            "\tt.content,\n" +
            "\tt.image_data\n" +
            "ORDER BY\n" +
            "\tcount(rt.task_id) DESC,\n" +
            "\tt.name ASC", nativeQuery = true)
    List<TasksMapping> getTasksWithSoftSkillAndTimeAndSortByStatusGroup(Long softSkillId, Date startTime, Date endTime, Integer statusId );

    Optional<Tasks> findByIdAndDeleteFlag(Long id, Boolean deleteFlag);

    @Query(value = "WITH doing as (SELECT a.task_id, count(b.status_task_id) as doingNumber\n" +
            "            FROM tasks_relation a \n" +
            "            JOIN user_room_task_info b on a.task_id = b.task_id\n" +
            "            WHERE a.is_general = TRUE AND b.status_task_id = 2\n" +
            "            GROUP BY a.task_id\n" +
            "            ORDER BY a.task_id), \n" +
            "            done AS \n" +
            "            (SELECT a.task_id, count(b.status_task_id) as doneNumber\n" +
            "            FROM tasks_relation a \n" +
            "            JOIN user_room_task_info b on a.task_id = b.task_id\n" +
            "            WHERE a.is_general = TRUE AND b.status_task_id = 3\n" +
            "            GROUP BY a.task_id\n" +
            "            ORDER BY a.task_id), \n" +
            "            favorite AS \n" +
            "            (SELECT a.task_id, count(b.favorite) as favoriteNumber\n" +
            "            FROM tasks_relation a \n" +
            "            JOIN user_task_favorite b on a.task_id = b.task_id\n" +
            "            WHERE a.is_general = TRUE AND b.favorite = TRUE and b.delete_flag = FALSE\n" +
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
            "            WHEN d.content IS NULL THEN\n" +
            "            ''\n" +
            "            ELSE\n" +
            "            d.content\n" +
            "            END as guideName, \n" +
            "            CASE \n" +
            "            WHEN b.sub_task IS NULL THEN\n" +
            "            -1\n" +
            "            ELSE\n" +
            "            b.sub_task\n" +
            "            END as requiredTask, a.image_data as taskImage, d.image_data as guidelineImage, \n" +
            "            CASE \n" +
            "            WHEN doing.doingNumber IS NULL THEN\n" +
            "            0\n" +
            "            ELSE\n" +
            "            doing.doingNumber\n" +
            "            END as doingNumber, \n" +
            "            CASE \n" +
            "            WHEN done.doneNumber IS NULL THEN\n" +
            "            0\n" +
            "            ELSE\n" +
            "            done.doneNumber\n" +
            "            END AS doneNumber, \n" +
            "            CASE \n" +
            "            WHEN favorite.favoriteNumber IS NULL THEN\n" +
            "            0\n" +
            "            ELSE\n" +
            "            favorite.favoriteNumber\n" +
            "            END AS favoriteNumber\n" +
            "            FROM tasks a  \n" +
            "            JOIN tasks_relation b on a.id = b.task_id AND b.is_general = TRUE\n" +
            "            join softskills c on b.softskill_id = c.id\n" +
            "            join users e on a.created_by = e.id\n" +
            "            left join guideline_task d on a.id = d.task_id and d.delete_flag = FALSE\n" +
            "            left join doing ON a.id = doing.task_id\n" +
            "            LEFT JOIN done on a.id = done.task_id\n" +
            "            LEFT JOIN favorite on a.id = favorite.task_id\n" +
            "            ORDER BY a.id, d.id", nativeQuery = true)
    List<GroupTaskInfoMapping> getGroupTaskInfo();

    @Query(value = "WITH doing as (SELECT a.task_id, count(b.status_task_id) as doingNumber\n" +
            "            FROM tasks_relation a \n" +
            "            JOIN user_room_task_info b on a.task_id = b.task_id\n" +
            "            WHERE a.is_general = TRUE AND b.status_task_id = 2\n" +
            "            GROUP BY a.task_id\n" +
            "            ORDER BY a.task_id), \n" +
            "            done AS \n" +
            "            (SELECT a.task_id, count(b.status_task_id) as doneNumber\n" +
            "            FROM tasks_relation a \n" +
            "            JOIN user_room_task_info b on a.task_id = b.task_id\n" +
            "            WHERE a.is_general = TRUE AND b.status_task_id = 3\n" +
            "            GROUP BY a.task_id\n" +
            "            ORDER BY a.task_id), \n" +
            "            favorite AS \n" +
            "            (SELECT a.task_id, count(b.favorite) as favoriteNumber\n" +
            "            FROM tasks_relation a \n" +
            "            JOIN user_task_favorite b on a.task_id = b.task_id\n" +
            "            WHERE a.is_general = TRUE AND b.favorite = TRUE\n" +
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
            "            WHEN d.content IS NULL THEN\n" +
            "            ''\n" +
            "            ELSE\n" +
            "            d.content\n" +
            "            END as guideName, \n" +
            "            CASE \n" +
            "            WHEN b.sub_task IS NULL THEN\n" +
            "            -1\n" +
            "            ELSE\n" +
            "            b.sub_task\n" +
            "            END as requiredTask, a.image_data as taskImage, d.image_data as guidelineImage, \n" +
            "            CASE \n" +
            "            WHEN doing.doingNumber IS NULL THEN\n" +
            "            0\n" +
            "            ELSE\n" +
            "            doing.doingNumber\n" +
            "            END as doingNumber, \n" +
            "            CASE \n" +
            "            WHEN done.doneNumber IS NULL THEN\n" +
            "            0\n" +
            "            ELSE\n" +
            "            done.doneNumber\n" +
            "            END AS doneNumber, \n" +
            "            CASE \n" +
            "            WHEN favorite.favoriteNumber IS NULL THEN\n" +
            "            0\n" +
            "            ELSE\n" +
            "            favorite.favoriteNumber\n" +
            "            END AS favoriteNumber\n" +
            "            FROM tasks a  \n" +
            "            JOIN tasks_relation b on a.id = b.task_id AND b.is_general = TRUE\n" +
            "            join softskills c on b.softskill_id = c.id\n" +
            "            join users e on a.created_by = e.id\n" +
            "            left join guideline_task d on a.id = d.task_id and d.delete_flag = FALSE\n" +
            "            left join doing ON a.id = doing.task_id\n" +
            "            LEFT JOIN done on a.id = done.task_id\n" +
            "            LEFT JOIN favorite on a.id = favorite.task_id\n" +
            "            WHERE a.id = :id", nativeQuery = true)
    List<GroupTaskInfoMapping> getGroupTaskInfoById(@Param("id") Integer id);
}
