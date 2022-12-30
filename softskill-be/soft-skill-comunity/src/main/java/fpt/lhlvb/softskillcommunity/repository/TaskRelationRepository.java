package fpt.lhlvb.softskillcommunity.repository;

import fpt.lhlvb.softskillcommunity.entity.TasksRelation;
import fpt.lhlvb.softskillcommunity.model.response.ComboboxResponse;
import fpt.lhlvb.softskillcommunity.model.response.DropdownResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRelationRepository extends JpaRepository<TasksRelation, Long> {
    @Query( value = "SELECT t.id, t.name \n" +
            "FROM tasks_relation tr \n" +
            "join softskills sk ON tr.softskill_id  = sk.\"id\" and sk.delete_flag = FALSE\n" +
            "join tasks t ON tr.task_id = t.\"id\" and t.delete_flag = FALSE\n" +
            "WHERE sk.id =:softSkillId AND tr.delete_flag = FALSE AND tr.is_general = TRUE\n" +
            "ORDER BY id\n",nativeQuery = true)
    List<DropdownResponse> getBySoftSkillId(Long softSkillId);

    Optional<TasksRelation> findByTaskIdAndDeleteFlag(Long taskId, Boolean deleteFlag);

    Optional<TasksRelation> findByTaskId(Long taskId);

    @Query(value = "SELECT b.\"id\" as value, b.\"content\" as label\n" +
            "FROM \"tasks_relation\" a JOIN tasks b on a.task_id = b.\"id\"\n" +
            "WHERE a.is_general = FALSE AND a.delete_flag = FALSE and b.delete_flag = FALSE\n" +
            "ORDER BY b.\"id\"", nativeQuery = true)
    List<ComboboxResponse> getPersonalTask();

    @Query(value = "SELECT\n" +
            "\ttr.sub_task AS subTask\n" +
            "FROM\n" +
            "\ttasks_relation tr\n" +
            "WHERE\n" +
            "\ttr.task_id = :taskId\n" +
            "\tAND tr.delete_flag = FALSE", nativeQuery = true)
    Long getSubTaskByTaskId(Long taskId);
}
