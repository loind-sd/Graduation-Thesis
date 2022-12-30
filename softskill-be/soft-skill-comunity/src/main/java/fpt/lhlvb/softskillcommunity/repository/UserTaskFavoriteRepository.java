package fpt.lhlvb.softskillcommunity.repository;

import fpt.lhlvb.softskillcommunity.entity.UserTaskFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTaskFavoriteRepository extends JpaRepository<UserTaskFavorite, Long> {

    List<UserTaskFavorite> findByTaskIdAndDeleteFlag(Long taskId, Boolean deleteFlag);

    List<UserTaskFavorite> findByCreatedByAndDeleteFlag(Long userId, Boolean deleteFlag);

    UserTaskFavorite findByTaskIdAndCreatedByAndDeleteFlag(Long taskId, Long createdBy, Boolean deleteFlag);

    Optional<UserTaskFavorite> findByTaskIdAndDeleteFlagAndCreatedBy(Long taskId, Boolean deleteFlag, Long createdBy);

    @Query(value = "SELECT\n" +
            "\tutf.*\n" +
            "FROM\n" +
            "\tuser_task_favorite utf\n" +
            "JOIN tasks_relation tr ON\n" +
            "\ttr.task_id = utf.task_id\n" +
            "\tAND tr.delete_flag = FALSE\n" +
            "JOIN softskills s ON\n" +
            "\ts.id = tr.softskill_id\n" +
            "\tAND s.delete_flag = FALSE\n" +
            "WHERE\n" +
            "\tutf.created_by = :userId\n" +
            "\tAND utf.task_id = :taskId\n" +
            "\tAND utf.status_id = :statusId\n" +
            "\tAND utf.delete_flag = FALSE", nativeQuery = true)
    UserTaskFavorite getTaskByCondition(Long taskId, Long userId, Integer statusId);
}
