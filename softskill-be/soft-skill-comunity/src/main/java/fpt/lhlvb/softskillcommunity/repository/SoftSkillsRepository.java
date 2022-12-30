package fpt.lhlvb.softskillcommunity.repository;

import fpt.lhlvb.softskillcommunity.entity.SoftSkills;
import fpt.lhlvb.softskillcommunity.model.response.admin.mapping.SoftSkillInfoMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SoftSkillsRepository extends JpaRepository<SoftSkills, Long> {

    Optional<SoftSkills> findByIdAndDeleteFlag(Long id, Boolean delete_flg);

    @Query(value = "Select * from softskills where delete_flag =:deleteFlag  ORDER BY id",nativeQuery = true)
    List<SoftSkills> findByDeleteFlag(Boolean deleteFlag);

    @Query(value = "WITH T AS ( SELECT softskill_id, COUNT ( * ) AS nummberRoom FROM rooms WHERE delete_flag = FALSE GROUP BY softskill_id ),\n" +
            "r AS (\n" +
            "SELECT\n" +
            "rt.softskill_id AS softSkillId,\n" +
            "COUNT ( rt.status_id = 3 ) AS countTaskCompleted \n" +
            "FROM\n" +
            "rooms_task rt \n" +
            "WHERE\n" +
            "rt.delete_flag = FALSE \n" +
            "GROUP BY\n" +
            "rt.softskill_id \n" +
            ") SELECT A\n" +
            ".ID,\n" +
            "A.NAME AS softSkillName,\n" +
            "CASE\n" +
            "\n" +
            "WHEN T.nummberRoom IS NULL THEN\n" +
            "0 ELSE T.nummberRoom \n" +
            "END AS numberRoom,\n" +
            "CASE\n" +
            "\n" +
            "WHEN r.countTaskCompleted IS NULL THEN\n" +
            "0 ELSE r.countTaskCompleted \n" +
            "END AS countTaskCompleted,\n" +
            "CASE\n" +
            "\n" +
            "WHEN A.delete_flag = false THEN\n" +
            "'Hoạt động' ELSE'Dừng' \n" +
            "END AS status,\n" +
            "A.created_date AS createdDate,\n" +
            "b.NAME AS createdName \n" +
            "FROM\n" +
            "softskills\n" +
            "A JOIN users b ON A.created_by = b.\n" +
            "ID LEFT JOIN T ON T.softskill_id = A.\n" +
            "ID LEFT JOIN r ON r.softSkillId = A.ID \n" +
            "WHERE\n" +
            "b.delete_flag = FALSE \n" +
            "ORDER BY\n" +
            "A.ID", nativeQuery = true)
    List<SoftSkillInfoMapping> getInfo();
}
