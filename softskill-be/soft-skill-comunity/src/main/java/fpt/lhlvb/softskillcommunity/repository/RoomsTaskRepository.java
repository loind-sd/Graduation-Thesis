package fpt.lhlvb.softskillcommunity.repository;

import fpt.lhlvb.softskillcommunity.entity.RoomsTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomsTaskRepository extends JpaRepository<RoomsTask, Long> {

    List<RoomsTask> findByRoomIdAndSoftSkillIdAndDeleteFlag(Long roomId, Long softSkillId, Boolean deleteFlag);

    Optional<RoomsTask> findByRoomIdAndTaskIdAndDeleteFlag(Long roomId, Long taskId, Boolean deleteFlag);

    RoomsTask getByRoomIdAndTaskIdAndDeleteFlag(Long roomId, Long taskId, Boolean deleteFlag);

    RoomsTask findByRoomIdAndSoftSkillIdAndTaskIdAndDeleteFlag(Long roomId, Long softSkillId, Long taskId, Boolean deleteFlag);

   RoomsTask findByRoomIdAndSoftSkillIdAndTaskIdAndDeleteFlagAndStatusId(Long roomId, Long softSkillId, Long taskId, Boolean deleteFlag,Long statusId);

    RoomsTask findByRoomIdAndSoftSkillIdAndTaskId(Long roomId, Long softSkillId, Long taskId);

    @Query(value = "SELECT rt.status_id\n" +
            "FROM\n" +
            "rooms_task rt\n" +
            "WHERE\n" +
            "rt.room_id = :roomId\n" +
            "AND rt.softskill_id = :softSkillId\n" +
            "AND rt.delete_flag = FALSE\n" +
            "ORDER BY rt.task_no DESC \n" +
            "LIMIT 1", nativeQuery = true)
    Integer getStatusCurrentTask(Long roomId, Long softSkillId);

    @Query(value = "SELECT\n" +
            "\tMAX(rt.task_no)\n" +
            "FROM\n" +
            "\trooms_task rt\n" +
            "WHERE\n" +
            "\trt.room_id = :roomId\n" +
            "\tAND rt.softskill_id = :softSkillId\n" +
            "\tAND rt.delete_flag = FALSE", nativeQuery = true)
    Integer getMaxTaskNo(Long roomId, Long softSkillId);
}
