package fpt.lhlvb.softskillcommunity.repository;

import fpt.lhlvb.softskillcommunity.entity.RecommendTask;
import fpt.lhlvb.softskillcommunity.model.response.manager.mapping.RecommendTaskInfoMapping;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.RecommentTaskMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecommentTaskRepository extends JpaRepository<RecommendTask, Long> {

    @Query(value = "SELECT a.id, a.content, b.name as typeName, c.name, c.nickname, c.mail, e.\"name\" as softSkillName, a.status as statusId, d.\"name\" as statusName\n" +
            "FROM recomment_task a  \n" +
            "JOIN recomment_task_type b ON a.type_id = b.id \n" +
            "JOIN users c ON a.created_by = c.id \n" +
            "JOIN recomment_task_status d on a.status = d.\"id\"\n" +
            "JOIN softskills e on a.softskill_id = e.\"id\"\n" +
            "WHERE a.delete_flag = :deleteFlag\n" +
            "ORDER BY a.status, a.created_date ", nativeQuery = true)
    List<RecommentTaskMapping> getRecommendTask(@Param("deleteFlag") boolean deleteFlag);

    Optional<RecommendTask> findByIdAndDeleteFlag(Long id, boolean deleteFlag);

    @Query(value = "SELECT a.id, a.content, a.guideline, b.name as typeName, c.name, c.nickname, c.mail, e.\"name\" as softSkillName, " +
            "a.status as statusId, d.\"name\" as statusName, to_char( A.created_date, 'DD/MM/YYYY') as dateCreate," +
            "a.image_data as taskImage,\n" +
            "a.guideline_image as guidelineImage\n" +
            "    FROM recomment_task a\n" +
            "    JOIN recomment_task_type b ON a.type_id = b.id\n" +
            "    JOIN users c ON a.created_by = c.id \n" +
            "    JOIN recomment_task_status d on a.status = d.\"id\"\n" +
            "    JOIN softskills e on a.softskill_id = e.\"id\"\n" +
            "    WHERE a.delete_flag = :deleteFlag AND a.created_by = :userId\n" +
            "    ORDER BY a.id, a.status, a.created_date", nativeQuery = true)
    List<RecommentTaskMapping> getRecommendTaskForUser(@Param("deleteFlag") boolean deleteFlag, @Param("userId") Long userId);

    @Query(value = "SELECT COUNT(*) as number\n" +
            "FROM recomment_task a \n" +
            "WHERE a.delete_flag = :deleteFlag AND a.created_by = :userId", nativeQuery = true)
    Optional<Integer> countNumber(@Param("deleteFlag") boolean deleteFlag, @Param("userId") Long userId);

    @Query(value = "SELECT\n" +
            "        * \n" +
            "    from\n" +
            "        recomment_task \n" +
            "    WHERE\n" +
            "        delete_flag = FALSE \n" +
            "        and created_by =:id\n" +
            "    ORDER BY\n" +
            "        created_date DESC LIMIT 1",nativeQuery = true)
    Optional<RecommendTask> getRecommendTaskByUserId(Long id);

    @Query(value = "SELECT a.id, a.content as taskContent, d.name, u1.name as createdName, d.name as softSkillName, b.name as taskType, a.status, a.created_date as createdDate, \n" +
            "            CASE \n" +
            "            WHEN a.created_date = a.updated_date THEN\n" +
            "            ''\n" +
            "            ELSE\n" +
            "            u2.name\n" +
            "            END as updateName, a.image_data as taskImage, a.guideline, a.guideline_image as guidelineImage, u1.mail\n" +
            "            \n" +
            "            FROM recomment_task a \n" +
            "            JOIN recomment_task_type b on a.type_id = b.id\n" +
            "            JOIN softskills d on a.softskill_id = d.id\n" +
            "            JOIN users u1 on a.created_by = u1.id\n" +
            "            JOIN users u2 ON a.updated_by = u2.id\n" +
            "            WHERE a.delete_flag = FALSE AND a.delete_flag = FALSE AND b.delete_flag = FALSE AND d.delete_flag = FALSE\n" +
            "            ORDER BY a.status ASC, a.created_date DESC", nativeQuery = true)
    List<RecommendTaskInfoMapping> getRecommendTaskInfo();
}
