package fpt.lhlvb.softskillcommunity.repository;

import fpt.lhlvb.softskillcommunity.entity.UserRoomTaskInfo;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.TasksMapping;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.UserRoomTaskOtherMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoomTaskInfoRepository extends JpaRepository<UserRoomTaskInfo, Long> {
    @Query(value = "SELECT\n" +
            "\turti.*\n" +
            "FROM\n" +
            "\tuser_room_task_info urti\n" +
            "JOIN tasks_relation tr ON\n" +
            "\ttr.task_id = urti.task_id\n" +
            "\tAND tr.delete_flag = FALSE\n" +
            "JOIN softskills s ON\n" +
            "\ts.id = tr.softskill_id\n" +
            "\tAND s.delete_flag = FALSE\n" +
            "WHERE\n" +
            "\turti.user_id = :userId\n" +
            "\tAND urti.task_id = :taskId\n" +
            "\tAND urti.status_task_id = :statusTaskId\n" +
            "\tAND urti.delete_flag = FALSE", nativeQuery = true)
    Optional<UserRoomTaskInfo> getUserRoomTaskInfoByCondition(Long userId, Long taskId, Integer statusTaskId);

    @Query(value = "SELECT\n" +
            "\turti.*\n" +
            "FROM\n" +
            "\tuser_room_task_info urti\n" +
            "JOIN tasks_relation tr ON\n" +
            "\ttr.task_id = urti.task_id\n" +
            "\tAND tr.delete_flag = FALSE\n" +
            "JOIN softskills s ON\n" +
            "\ts.id = tr.softskill_id\n" +
            "\tAND s.delete_flag = FALSE\n" +
            "WHERE\n" +
            "\turti.user_id = :userId\n" +
            "\tAND urti.task_id = :taskId\n" +
            "\tAND urti.delete_flag = FALSE", nativeQuery = true)
    Optional<UserRoomTaskInfo> getUserRoomTaskInfoByUserIdAndTaskIdAndRoomId(Long userId, Long taskId);

    @Query(value = "SELECT\n" +
            "            DISTINCT \n" +
            "            u.id AS userId,\n" +
            "            u.name AS nameUser,\n" +
            "            u.nickname AS nickname,\n" +
            "            tr.task_id AS taskId,\n" +
            "            u.picture,\n" +
            "            CAST(urti.created_date AS DATE) AS joinTime\n" +
            "            FROM\n" +
            "            users u\n" +
            "            JOIN \n" +
            "            user_room_task_info urti ON\n" +
            "            urti.user_id = u.id\n" +
            "            AND urti.task_id = :taskId\n" +
            "            AND urti.status_task_id = :statusTask\n" +
            "            AND urti.delete_flag = FALSE\n" +
            "            JOIN tasks_relation tr ON\n" +
            "            tr.task_id = urti.task_id\n" +
            "            AND urti.delete_flag = FALSE\n" +
            "            JOIN softskills s ON\n" +
            "            s.id = tr.softskill_id\n" +
            "            AND s.id = :softSkillId\n" +
            "            AND s.delete_flag = FALSE\n" +
            "            WHERE\n" +
            "            u.id <> :userId\n" +
            "            AND u.delete_flag = FALSE", nativeQuery = true)
    List<UserRoomTaskOtherMapping> getUsersRoomTaskOtherByTaskIdAndCondition(Long userId, Long taskId, Long softSkillId, Integer statusTask);

    @Query(value = "SELECT\n" +
            "\tDISTINCT \n" +
            "\tu.id AS userId,\n" +
            "\tu.name AS nameUser,\n" +
            "\tu.nickname AS nickname,\n" +
            "            u.picture,\n" +
            "\ttr.task_id AS taskId\n" +
            "FROM\n" +
            "\tusers u\n" +
            "JOIN \n" +
            "\tuser_room_task_info urti ON\n" +
            "\turti.user_id = u.id\n" +
            "\tAND urti.status_task_id = :statusTask\n" +
            "\tAND urti.delete_flag = FALSE\n" +
            "JOIN tasks_relation tr ON\n" +
            "\ttr.task_id = urti.task_id\n" +
            "\tAND urti.delete_flag = FALSE\n" +
            "JOIN softskills s ON\n" +
            "\ts.id = tr.softskill_id\n" +
            "\tAND s.id = :softSkillId\n" +
            "\tAND s.delete_flag = FALSE\n" +
            "WHERE\n" +
            "\tu.id <> :userId\n" +
            "\tAND u.delete_flag = FALSE", nativeQuery = true)
    List<UserRoomTaskOtherMapping> getUsersRoomTaskOtherByCondition(Long userId, Long softSkillId, Integer statusTask);

    @Query(value = "SELECT\n" +
            "\turti.user_id AS userId,\n" +
            "\turti.task_id AS taskId\n" +
            "FROM\n" +
            "\tuser_room_task_info urti\n" +
            "JOIN tasks_relation tr ON\n" +
            "\ttr.task_id = urti.task_id\n" +
            "WHERE\n" +
            "\ttr.softskill_id = :softSkillId\n" +
            "\tAND urti.user_id <> :userId\n" +
            "\tAND urti.delete_flag = FALSE", nativeQuery = true)
    List<UserRoomTaskOtherMapping> getUsersRoomTaskOtherNotStartBySoftSkillId(Long userId, Long softSkillId);

    @Query(value = "SELECT\n" +
            "\tu.id AS userId,\n" +
            "\tu.name AS nameUser,\n" +
            "\tu.nickname AS nickname,\n" +
            "            u.picture\n" +
            "FROM\n" +
            "\tusers u\n" +
            "LEFT JOIN\n" +
            "\tuser_room_task_info urti ON\n" +
            "\turti.user_id = u.id\n" +
            "\tAND urti.task_id = :taskId\n" +
            "\tAND (urti.status_task_id = :statusIdDoing\n" +
            "\t\tOR urti.status_task_id = :statusIdDone)\n" +
            "\tAND urti.delete_flag = FALSE\n" +
            "WHERE\n" +
            "\tu.id <> :userId\n" +
            "\tAND urti.id IS NULL\n" +
            "\tAND u.delete_flag = FALSE", nativeQuery = true)
    List<UserRoomTaskOtherMapping> getUsersRoomTaskOtherNotStartByTaskId(Long userId, Long taskId, Integer statusIdDoing, Integer statusIdDone);

    @Query(value = "SELECT\n" +
            "\tDISTINCT \n" +
            "\tu.id AS userId,\n" +
            "\tu.name AS nameUser,\n" +
            "\tu.nickname AS nickname,\n" +
            "            u.picture,\n" +
            "\ttr.task_id AS taskId\n" +
            "FROM\n" +
            "\tuser_task_favorite utf\n" +
            "JOIN users u ON\n" +
            "\tu.id = utf.created_by\n" +
            "\tAND u.delete_flag = FALSE\n" +
            "JOIN tasks_relation tr ON\n" +
            "\ttr.task_id = utf.task_id\n" +
            "\tAND tr.delete_flag = FALSE\n" +
            "JOIN softskills s ON\n" +
            "\ts.id = tr.softskill_id\n" +
            "\tAND s.delete_flag = FALSE\n" +
            "WHERE\n" +
            "\tutf.created_by <> :userId\n" +
            "\tAND s.id = :softSkillId\n" +
            "\tAND utf.delete_flag = FALSE", nativeQuery = true)
    List<UserRoomTaskOtherMapping> getOthersFavourite(Long softSkillId, Long userId);

    @Query(value = "SELECT\n" +
            "\tDISTINCT \n" +
            "\tu.id AS userId,\n" +
            "\tu.name AS nameUser,\n" +
            "\tu.nickname AS nickname,\n" +
            "            u.picture,\n" +
            "\ttr.task_id AS taskId\n" +
            "FROM\n" +
            "\tuser_task_favorite utf\n" +
            "JOIN users u ON\n" +
            "\tu.id = utf.created_by\n" +
            "\tAND u.delete_flag = FALSE\n" +
            "JOIN tasks_relation tr ON\n" +
            "\ttr.task_id = utf.task_id\n" +
            "\tAND tr.delete_flag = FALSE\n" +
            "JOIN softskills s ON\n" +
            "\ts.id = tr.softskill_id\n" +
            "\tAND s.delete_flag = FALSE\n" +
            "WHERE\n" +
            "\tutf.created_by <> :userId\n" +
            "\tAND utf.task_id = :taskId\n" +
            "\tAND s.id = :softSkillId\n" +
            "\tAND utf.delete_flag = FALSE", nativeQuery = true)
    List<UserRoomTaskOtherMapping> getOthersFavouriteByTaskId(Long softSkillId, Long userId, Long taskId);

    UserRoomTaskInfo findByRoomIdAndTaskIdAndUserIdAndDeleteFlag(Long roomId, Long taskId, Long userId, Boolean deleteFlag);

    List<UserRoomTaskInfo> findByRoomIdAndTaskIdAndDeleteFlag(Long roomId, Long taskId, Boolean deleteFlag);

    Optional<UserRoomTaskInfo> findByTaskIdAndUserIdAndDeleteFlag(Long taskId, Long userId, Boolean deleteFlag);
}
