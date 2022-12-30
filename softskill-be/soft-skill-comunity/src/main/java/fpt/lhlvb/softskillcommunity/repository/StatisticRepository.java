package fpt.lhlvb.softskillcommunity.repository;

import fpt.lhlvb.softskillcommunity.entity.Statistic;
import fpt.lhlvb.softskillcommunity.model.response.admin.mapping.CountMapping;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long> {

    @Query(value = "SELECT\n" +
            "s.*\n" +
            "FROM\n" +
            "statistic s\n" +
            "WHERE\n" +
            "s.user_id = :userId\n" +
            "AND CAST(s.created_date AS DATE) = :createdAt\n" +
            "AND s.delete_flag = FALSE", nativeQuery = true)
    Statistic getByUserIdAndCreatedDate(Long userId, Date createdAt);

    @Query(value = "SELECT\n" +
            "SUM(s.task_completed_time) AS totalTimeCompleted,\n" +
            "SUM(s.count_task_doing) AS totalTaskDoing,\n" +
            "SUM(s.count_task_completed) AS totalTaskCompleted\n" +
            "FROM\n" +
            "statistic s\n" +
            "WHERE\n" +
            "s.user_id = :userId\n" +
            "AND CAST(s.created_date AS DATE) BETWEEN :fromDate AND :toDate\n" +
            "AND s.delete_flag = FALSE\n", nativeQuery = true)
    StatisticTaskMapping getStatisticTaskForUser(Long userId, Date fromDate, Date toDate);

    @Query(value = "SELECT\n" +
            "rt.softskill_id AS softSkillId,\n" +
            "COUNT(rt.status_id = 3) AS countTaskCompleted\n" +
            "FROM\n" +
            "rooms_task rt\n" +
            "WHERE\n" +
            "rt.delete_flag = FALSE\n" +
            "GROUP BY\n" +
            "rt.softskill_id", nativeQuery = true)
    List<StatisticSoftSkillMapping> getStatisticSoftSkillForUser();

    @Query(value = "SELECT\n" +
            "SUM(s.time_room)\n" +
            "FROM\n" +
            "statistic s\n" +
            "WHERE\n" +
            "s.user_id = :userId\n" +
            "AND s.delete_flag = FALSE", nativeQuery = true)
    Float countTimeRoomForUser(Long userId);

    @Query(value = "SELECT\n" +
            "SUM(s.task_completed_time) AS completedTime,\n" +
            "SUM(s.count_task_completed) AS completedTask\n" +
            "FROM\n" +
            "statistic s\n" +
            "WHERE\n" +
            "s.user_id = :userId\n" +
            "AND s.delete_flag = FALSE\n" +
            "GROUP BY\n" +
            "s.user_id", nativeQuery = true)
    StatisticRankUserMapping getStatisticRankUser(Long userId);

    @Query(value = "SELECT\n" +
            "            SUM(s.task_completed_time) AS completedTime,\n" +
            "            SUM(s.count_task_completed) AS completedTask\n" +
            "            FROM\n" +
            "            statistic s\n" +
            "            WHERE s.delete_flag = FALSE\n" +
            "            GROUP BY\n" +
            "            s.user_id", nativeQuery = true)
    List<StatisticRankUserMapping> getStatisticRankForAdmin();

    @Query(value = "WITH t as (SELECT COUNT(*) as number FROM \n" +
            "            users\n" +
            "            where delete_flag = FALSE), \n" +
            "            r as (SELECT COUNT(*) as before FROM \n" +
            "            users\n" +
            "            where delete_flag = FALSE AND created_date <= :date)\n" +
            "            \n" +
            "            SELECT t.number,\n" +
            "CASE \n" +
            "WHEN t.number - r.before > 0 THEN\n" +
            "'up'\n" +
            "WHEN t.number - r.before = 0 THEN\n" +
            "'normal'\n" +
            "ELSE\n" +
            "'down'\n" +
            "END as isDevelop\n" +
            "            from t,r", nativeQuery = true)
    Optional<CountMapping> countNumberUsers(@Param("date") java.sql.Date date);

    @Query(value = "WITH t as (SELECT COUNT(*) as number FROM \n" +
            "            rooms), \n" +
            "            r as (SELECT COUNT(*) as before FROM \n" +
            "            rooms\n" +
            "            where created_date <= :date)\n" +
            "            \n" +
            "            SELECT t.number,\n" +
            "CASE \n" +
            "WHEN t.number - r.before > 0 THEN\n" +
            "'up'\n" +
            "WHEN t.number - r.before = 0 THEN\n" +
            "'normal'\n" +
            "ELSE\n" +
            "'down'\n" +
            "END as isDevelop\n" +
            "            from t,r", nativeQuery = true)
    Optional<CountMapping> countTotalRooms(@Param("date") java.sql.Date date);

    @Query(value = "WITH t as(\n" +
            "SELECT\n" +
            "        date,\n" +
            "        number             \n" +
            "    FROM\n" +
            "        statistic_admin             \n" +
            "    WHERE\n" +
            "        delete_flag = FALSE \n" +
            "        AND \"date\" <= :date \n" +
            "\tORDER BY date DESC\n" +
            "\tLIMIT 7)\n" +
            "SELECT * from t\n" +
            "ORDER BY t.date ASC", nativeQuery = true)
    List<CountNumberJoinMapping> countNumberJoin(@Param("date") java.sql.Date date);

    @Query(value = "SELECT COUNT(*) as numberActiveRoom\n" +
            "FROM rooms a \n" +
            "WHERE a.created_date BETWEEN :fromDate AND :endDate AND a.\"id\" NOT IN (SELECT a.\"id\"\n" +
            "FROM rooms a \n" +
            "join rooms_task b on a.\"id\" = b.room_id\n" +
            "where b.start_time IS NOT NULL)", nativeQuery = true)
    Optional<Integer> countNumberActiveRoom(@Param("fromDate") Date fromDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT COUNT(*) as numberBookingRoom\n" +
            "FROM rooms a \n" +
            "join rooms_task b on a.\"id\" = b.room_id\n" +
            "where a.created_date BETWEEN :fromDate AND :endDate AND b.start_time IS NOT NULL", nativeQuery = true)
    Optional<Integer> countNumberBookingRoom(@Param("fromDate") Date fromDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT\n" +
            "        a.\"name\" as softSkillName,\n" +
            "        COUNT(*) AS countTaskCompleted \n" +
            "    FROM\n" +
            "        softskills a  \n" +
            "    LEFT JOIN\n" +
            "        rooms_task rt \n" +
            "            on a.\"id\" = rt.softskill_id \n" +
            "            AND rt.status_id = 3\n" +
            "    WHERE\n" +
            "        rt.delete_flag = FALSE \n" +
            "    GROUP BY\n" +
            "        a.id,\n" +
            "        a.\"name\"", nativeQuery = true)
    List<CountBySoftSkillMapping> countBySoftSkill();
}
