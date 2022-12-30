package fpt.lhlvb.softskillcommunity.repository;

import fpt.lhlvb.softskillcommunity.entity.GuidelineTask;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.GuidelineTaskMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuidelineTaskRepository extends JpaRepository<GuidelineTask, Long> {

    @Query(value = "SELECT \n" +
            "\tgt.title AS guidelineTitle, \n" +
            "\tgt.content AS guidelineContent\n" +
            "FROM\n" +
            "\tguideline_task gt\n" +
            "WHERE\n" +
            "\tgt.task_id =:taskId  AND gt.delete_flag =:deleteFlag", nativeQuery = true)
    List<GuidelineTaskMapping> findByTaskIdAndDeleteFlagCustom(Long taskId, Boolean deleteFlag);
//    @Query(value = "SELECT\n" +
//            "t.id taskId,t.name as taskName,t.content as taskContent,gt.image_data as imageData,\n" +
//            "\tgt.title AS guidelineTitle,\n" +
//            "\tgt.CONTENT AS guidelineContent \n" +
//            "FROM\n" +
//            "\tguideline_task gt \n" +
//            "\tjoin tasks t on gt.task_id = t.id AND t.delete_flag = FALSE\n" +
//            "WHERE\n" +
//            "\tgt.task_id =:taskId  AND gt.delete_flag =:deleteFlag", nativeQuery = true)
    List<GuidelineTask> findByDeleteFlag(Boolean deleteFlag);

    List<GuidelineTask> findByTaskIdAndDeleteFlag(Long id, Boolean deleteFlag);
}
