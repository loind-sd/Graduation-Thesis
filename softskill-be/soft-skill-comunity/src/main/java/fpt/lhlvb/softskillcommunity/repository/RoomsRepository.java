package fpt.lhlvb.softskillcommunity.repository;

import fpt.lhlvb.softskillcommunity.entity.Rooms;
import fpt.lhlvb.softskillcommunity.model.response.admin.mapping.AdminRoomsMapping;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.RoomDetailMapping;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.RoomShortMapping;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.RoomsMapping;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.UserRoomDetailMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomsRepository extends JpaRepository<Rooms, Long> {

    @Query(value = "select r.id as roomId, r.room_code as roomCode, r.name as roomName, r.description, r.password,\n" +
            "s.id as softSkillId, s.name as softSkillName, r.room_size as roomSize, r.created_by as createById, u.name as createByName,r.is_lock isLock\n" +
            "from rooms r\n" +
            "join softskills s on s.id  = r.softskill_id and s.delete_flag = false \n" +
            "join users u on u.id  = r.created_by and u.delete_flag = false \n" +
            "where r.id = :roomId and r.delete_flag = false ", nativeQuery = true)
    RoomDetailMapping getRoomDetailsByRoomId(Long roomId);

    Optional<Rooms> findByNameAndDeleteFlag(String name, Boolean deleteFlag);

    @Query(value = "SELECT\n" +
            "\tu.id AS userId ,\n" +
            "\tu.name AS userName,\n" +
            "\tu.nickname AS nickName\n," +
            "\tur.is_old_member as isOldMember\n" +
            "FROM\n" +
            "user_room_info ur\n" +
            "JOIN rooms r ON\n" +
            "r.id = ur.room_id\n" +
            "AND r.delete_flag = FALSE\n" +
            "JOIN users u ON\n" +
            "u.id = ur.user_id\n" +
            "AND u.delete_flag = FALSE\n" +
            "WHERE\n" +
            "r.id = :roomId\n" +
            "AND ur.join_status = TRUE\n" +
            "AND ur.delete_flag = FALSE", nativeQuery = true)
    List<UserRoomDetailMapping> getUserRoomDetailByRoomId(Long roomId);

    /**
     * -- phòng người khác tạo trục tiếp : softskill, currentUser
     * -- mình tạo trực tiếp: softskill, createBy
     * -- mình đã từng vào phòng: softskill, currentUser, userId
     */

    @Query(value = "select \n" +
            "r.id AS roomId,\n" +
            "        count(uri.join_status) AS countUserOnline,\n" +
            "        r.room_code AS roomCode,\n" +
            "        r.name AS roomName,\n" +
            "        r.description,\n" +
            "        r.softskill_id AS softSkillId,\n" +
            "        r.room_size AS roomSize,\n" +
            "        r.is_lock AS isLock,\n" +
            "        s.name AS softSkillName,\n" +
            "        r.created_by AS createById,\n" +
            "        r.created_date AS startTime,\n" +
            "t.id as TaskId,\n" +
            "CASE    \n" +
            "            WHEN rt.task_id IS NULL THEN 'Chưa xác nhận nhiệm vụ'   \n" +
            "            ELSE t.name  \n" +
            "        END AS taskName \n" +
            "from rooms r\n" +
            "left join rooms_task rt on r.id = rt.room_id AND rt.status_id = 2\n" +
            "left join tasks t on rt.task_id = t.id\n" +
            "join softskills s on r.softskill_id = s.id\n" +
            "LEFT JOIN\n" +
            "        user_room_info uri \n" +
            "            ON  uri.room_id = r.id  \n" +
            "            AND uri.delete_flag = FALSE  \n" +
            "            AND uri.is_joined = TRUE  \n" +
            "            AND uri.join_status = TRUE \n" +
            "where (rt.start_time IS NULL OR rt.start_time <= NOW() OR rt.id IS NULL)\n" +
            "AND\n" +
            "        (\n" +
            "            :softSkillId IS NULL   \n" +
            "            OR r.softskill_id = :softSkillId\n" +
            "        )  \n" +
            "        AND (\n" +
            "            :currentUser IS NULL   \n" +
            "            OR r.created_by <> :currentUser\n" +
            "        )  \n" +
            "        AND (\n" +
            "            :createdBy IS NULL   \n" +
            "            OR r.created_by = :createdBy\n" +
            "        )  \n" +
            "        AND (\n" +
            "            :userJoined IS NULL   \n" +
            "            OR uri.user_id = :userJoined\n" +
            "        ) \n" +
            "        AND r.delete_flag = FALSE \n" +
            "    GROUP BY\n" +
            "        r.id,\n" +
            "        t.id,\n" +
            "        rt.task_id,\n" +
            "        s.name,\n" +
            "        r.room_code,\n" +
            "        r.id,\n" +
            "        r.room_code,\n" +
            "        r.name,\n" +
            "        r.description,\n" +
            "        r.softskill_id,\n" +
            "        r.id,\n" +
            "        r.room_code,\n" +
            "        r.name,\n" +
            "        r.description,\n" +
            "        r.softskill_id,\n" +
            "        r.room_size,\n" +
            "        r.is_lock,\n" +
            "        r.created_by,\n" +
            "        r.created_date", nativeQuery = true)
    List<RoomsMapping> getRoomsActive(Long softSkillId, Long currentUser, Long createdBy, Long userJoined);

    /**
     * -- người khác tạo đặt lịch: softSkill, currentUser
     * -- mình tạo đặt lịch: softSkill, createBy
     * -- mình đã đăng ký tham gia: softSkill, userJoined, currentUser
     */

    @Query(value = "WITH task_recent AS (WITH t AS (\n" +
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
            "\tt.room_id,\n" +
            "\trt2.task_id,\n" +
            "\tt2.name AS taskName\n" +
            "FROM\n" +
            "\tt\n" +
            "JOIN rooms_task rt2 ON\n" +
            "\tt.room_id = rt2.room_id\n" +
            "JOIN tasks t2 ON\n" +
            "\tt2.id = rt2.task_id\n" +
            "\tAND t2.delete_flag = FALSE\n" +
            "WHERE\n" +
            "\trt2.created_date = t.created_date\n" +
            "\tAND rt2.start_time IS NOT NULL),\n" +
            "countRegister_tmp AS (\n" +
            "SELECT\n" +
            "\trt1.room_task_id AS rtId,\n" +
            "\tcount(rt1.id) AS countRegister\n" +
            "FROM\n" +
            "\troom_task_register_info rt1\n" +
            "GROUP BY\n" +
            "\trt1.room_task_id)\n" +
            "SELECT\n" +
            "\tr.id AS roomId,\n" +
            "\tcountRegister_tmp.countRegister AS countUserRegister,\n" +
            "\tr.room_code AS roomCode,\n" +
            "\tr.name AS roomName,\n" +
            "\tr.description,\n" +
            "\tr.softskill_id AS softSkillId,\n" +
            "\ts.name AS softSkillName,\n" +
            "\tr.room_size AS roomSize,\n" +
            "\tr.is_lock AS isLock,\n" +
            "\tr.created_by AS createById,\n" +
            "\trt3.start_time AS startTime,\n" +
            "\ttask_recent.task_id AS TaskId,\n" +
            "\ttask_recent.taskName\n" +
            "FROM\n" +
            "\trooms r\n" +
            "JOIN task_recent ON\n" +
            "\ttask_recent.room_id = r.id\n" +
            "JOIN rooms_task rt3 ON\n" +
            "\trt3.room_id = r.id\n" +
            "\tAND rt3.delete_flag = FALSE\n" +
            "JOIN countRegister_tmp ON\n" +
            "\tcountRegister_tmp.rtId = rt3.id\n" +
            "JOIN room_task_register_info rtri ON\n" +
            "\trtri.room_task_id = rt3.id\n" +
            "\tAND rtri.delete_flag = FALSE\n" +
            "JOIN softskills s ON\n" +
            "\ts.id = r.softskill_id\n" +
            "\tAND s.delete_flag = FALSE\n" +
            "WHERE\n" +
            "\t(:softSkillId IS NULL\n" +
            "\t\tOR r.softskill_id = :softSkillId)\n" +
            "\tAND (:currentUser IS NULL\n" +
            "\t\tOR r.created_by <> :currentUser)\n" +
            "\tAND (:createdBy IS NULL\n" +
            "\t\tOR r.created_by = :createdBy)\n" +
            "\tAND (:userJoined IS NULL\n" +
            "\t\tOR rtri.created_by = :userJoined)\n" +
            "\tAND r.delete_flag = FALSE and rt3.start_time >= now()\n" +
            "GROUP BY\n" +
            "\tr.id,\n" +
            "\ttask_recent.task_id,\n" +
            "\ttask_recent.taskName,\n" +
            "\tcountRegister_tmp.countRegister,\n" +
            "\trt3.start_time,\n" +
            "\tr.id,\n" +
            "\ts.name,\n" +
            "\tcountRegister_tmp.countRegister,\n" +
            "\tr.room_code,\n" +
            "\tr.name,\n" +
            "\tr.description,\n" +
            "\tr.softskill_id,\n" +
            "\tr.room_size,\n" +
            "\tr.is_lock,\n" +
            "\tr.created_by,\n" +
            "\trt3.start_time", nativeQuery = true)
    List<RoomsMapping> getRoomsBooking(Long softSkillId, Long currentUser, Long createdBy, Long userJoined);

    @Query(value = "WITH task_recent AS (WITH t AS ( SELECT\n" +
            "        rt.room_id,\n" +
            "        MAX(rt.created_date) AS created_date \n" +
            "    FROM\n" +
            "        rooms_task rt \n" +
            "    JOIN\n" +
            "        tasks t \n" +
            "            ON  t.id = rt.task_id \n" +
            "    WHERE\n" +
            "        rt.delete_flag = FALSE \n" +
            "    GROUP BY\n" +
            "        rt.room_id) SELECT\n" +
            "        t.room_id,\n" +
            "        rt2.task_id,\n" +
            "        t2.name AS taskName \n" +
            "    FROM\n" +
            "        t \n" +
            "    JOIN\n" +
            "        rooms_task rt2 \n" +
            "            ON  t.room_id = rt2.room_id \n" +
            "    JOIN\n" +
            "        tasks t2 \n" +
            "            ON  t2.id = rt2.task_id  \n" +
            "            AND t2.delete_flag = FALSE \n" +
            "    WHERE\n" +
            "        rt2.created_date = t.created_date  \n" +
            "        AND rt2.start_time IS NULL)  SELECT\n" +
            "        r.id AS roomId,\n" +
            "        count(uri.join_status) AS countUserOnline,\n" +
            "        r.room_code AS roomCode,\n" +
            "        r.name AS roomName,\n" +
            "        r.description,\n" +
            "        r.softskill_id AS softSkillId,\n" +
            "        r.room_size AS roomSize,\n" +
            "        r.is_lock AS isLock,\n" +
            "        r.created_by AS createById,\n" +
            "        r.created_date AS startTime,\n" +
            "        task_recent.task_id as TaskId,\n" +
            "        CASE    \n" +
            "            WHEN task_recent.taskName IS NULL THEN 'Chưa xác nhận nhiệm vụ'   \n" +
            "            ELSE task_recent.taskName  \n" +
            "        END AS taskName \n" +
            "    FROM\n" +
            "        rooms r \n" +
            "    LEFT JOIN\n" +
            "        task_recent \n" +
            "            ON  task_recent.room_id = r.id \n" +
            "    LEFT JOIN\n" +
            "        user_room_info uri \n" +
            "            ON  uri.room_id = r.id  \n" +
            "            AND uri.delete_flag = FALSE  \n" +
            "            AND uri.is_joined = TRUE  \n" +
            "            AND uri.join_status = TRUE \n" +
            "    WHERE\n" +
            "       r.id = :roomId\n" +
            "        AND r.delete_flag = FALSE \n" +
            "    GROUP BY\n" +
            "        r.id,\n" +
            "        task_recent.task_id,\n" +
            "        task_recent.taskName,\n" +
            "        r.room_code,\n" +
            "        r.id,\n" +
            "        r.room_code,\n" +
            "        r.name,\n" +
            "        r.description,\n" +
            "        r.softskill_id,\n" +
            "        r.id,\n" +
            "        r.room_code,\n" +
            "        r.name,\n" +
            "        r.description,\n" +
            "        r.softskill_id,\n" +
            "        r.room_size,\n" +
            "        r.is_lock,\n" +
            "        r.created_by,\n" +
            "        r.created_date", nativeQuery = true)
    RoomsMapping getRoomsActiveForChat(Long roomId);

    @Query(value = "WITH task_recent AS (WITH t AS (\n" +
            "SELECT\n" +
            "rt.room_id,\n" +
            "MAX(rt.created_date) AS created_date\n" +
            "FROM\n" +
            "rooms_task rt\n" +
            "JOIN tasks t ON\n" +
            "t.id = rt.task_id\n" +
            "WHERE\n" +
            "rt.delete_flag = FALSE\n" +
            "GROUP BY\n" +
            "rt.room_id)\n" +
            "SELECT\n" +
            "t.room_id,\n" +
            "rt2.task_id,\n" +
            "t2.name AS taskName\n" +
            "FROM\n" +
            "t\n" +
            "JOIN rooms_task rt2 ON\n" +
            "t.room_id = rt2.room_id\n" +
            "JOIN tasks t2 ON\n" +
            "t2.id = rt2.task_id\n" +
            "AND t2.delete_flag = FALSE\n" +
            "WHERE\n" +
            "rt2.created_date = t.created_date\n" +
            "AND rt2.start_time IS NULL) \n" +
            "SELECT\n" +
            "r.id AS roomId,\n" +
            "count(uri.join_status) AS countUserOnline,\n" +
            "r.room_code AS roomCode,\n" +
            "r.name AS roomName,\n" +
            "r.description,\n" +
            "r.softskill_id AS softSkillId,\n" +
            "r.room_size AS roomSize,\n" +
            "r.is_lock AS isLock,\n" +
            "r.created_by AS createById,\n" +
            "r.created_date AS startTime,\n" +
            "task_recent.task_id as TaskId,\n" +
            "CASE \n" +
            "WHEN task_recent.taskName IS NULL THEN 'Chưa xác nhận nhiệm vụ'\n" +
            "ELSE task_recent.taskName\n" +
            "END AS taskName\n" +
            "FROM\n" +
            "rooms r\n" +
            "JOIN softskills s ON\n" +
            "s.id = r.softskill_id\n" +
            "AND s.delete_flag = FALSE\n" +
            "JOIN task_recent ON\n" +
            "task_recent.room_id = r.id\n" +
            "JOIN user_room_info uri ON\n" +
            "uri.room_id = r.id\n" +
            "AND uri.delete_flag = FALSE\n" +
            "AND uri.is_joined = TRUE\n" +
            "AND uri.join_status = TRUE\n" +
            "WHERE" +
            " lower(s.name) LIKE %:textSearch%\n" +
            "OR lower(r.name) LIKE %:textSearch%\n" +
            "AND (r.room_size >= :fromSize AND r.room_size <= :toSize)\n" +
            "AND r.delete_flag = FALSE\n" +
            "GROUP BY\n" +
            "r.id,\n" +
            "task_recent.task_id,\n" +
            "task_recent.taskName, r.name, r.description,r.softskill_id,r.id, r.room_code, r.name, r.description, r.softskill_id, r.room_size,r.is_lock,r.created_by, r.created_date", nativeQuery = true)
    List<RoomsMapping> searchAndFilterForActiveRooom(String textSearch, Integer fromSize, Integer toSize);

    @Query(value = "WITH task_recent AS (WITH t AS (\n" +
            "SELECT\n" +
            "rt.room_id,\n" +
            "MAX(rt.created_date) AS created_date\n" +
            "FROM\n" +
            "rooms_task rt\n" +
            "JOIN tasks t ON\n" +
            "t.id = rt.task_id\n" +
            "WHERE\n" +
            "rt.delete_flag = FALSE\n" +
            "GROUP BY\n" +
            "rt.room_id)\n" +
            "SELECT\n" +
            "t.room_id,\n" +
            "rt2.task_id,\n" +
            "t2.name AS taskName\n" +
            "FROM\n" +
            "t\n" +
            "JOIN rooms_task rt2 ON\n" +
            "t.room_id = rt2.room_id\n" +
            "JOIN tasks t2 ON\n" +
            "t2.id = rt2.task_id\n" +
            "AND t2.delete_flag = FALSE\n" +
            "WHERE\n" +
            "rt2.created_date = t.created_date\n" +
            "AND rt2.start_time IS NOT NULL), \n" +
            "countRegister_tmp AS (\n" +
            "SELECT\n" +
            "rt1.room_task_id AS rtId,\n" +
            "count(rt1.id) AS countRegister\n" +
            "FROM\n" +
            "room_task_register_info rt1\n" +
            "GROUP BY\n" +
            "rt1.room_task_id)\n" +
            "SELECT\n" +
            "r.id AS roomId,\n" +
            "countRegister_tmp.countRegister AS countUserRegister,\n" +
            "r.room_code AS roomCode,\n" +
            "r.name AS roomName,\n" +
            "r.description,\n" +
            "r.softskill_id AS softSkillId,\n" +
            "r.room_size AS roomSize,\n" +
            "r.is_lock AS isLock,\n" +
            "r.created_by AS createById,\n" +
            "rt3.start_time AS startTime,\n" +
            "task_recent.task_id as TaskId,\n" +
            "task_recent.taskName\n" +
            "FROM\n" +
            "rooms r\n" +
            "JOIN softskills s ON\n" +
            "s.id = r.softskill_id\n" +
            "AND s.delete_flag = FALSE\n" +
            "JOIN task_recent ON\n" +
            "task_recent.room_id = r.id\n" +
            "JOIN rooms_task rt3 ON\n" +
            "rt3.room_id = r.id\n" +
            "AND rt3.delete_flag = FALSE\n" +
            "JOIN countRegister_tmp ON\n" +
            "countRegister_tmp.rtId = rt3.id\n" +
            "JOIN room_task_register_info rtri ON\n" +
            "rtri.room_task_id = rt3.id\n" +
            "AND rtri.delete_flag = FALSE\n" +
            "WHERE\n" +
            "(lower(s.name) LIKE %:textSearch%\n" +
            "OR lower(r.name) LIKE %:textSearch%\n" +
            "OR lower(task_recent.taskName) LIKE %:textSearch%)\n" +
            "AND (r.room_size >= :fromSize\n" +
            "AND r.room_size <= :toSize)\n" +
            "AND rt3.start_time >= :fromTime\n" +
            "AND rt3.start_time <= :toTime\n"+
            "AND r.delete_flag = FALSE\n" +
            "GROUP BY\n" +
            "r.id,\n" +
            "task_recent.task_id,\n" +
            "task_recent.taskName,\n" +
            "countRegister_tmp.countRegister,\n" +
            "rt3.start_time", nativeQuery = true)
    List<RoomsMapping> searchAndFilterForBookingRooom(String textSearch,
                                                      Integer fromSize, Integer toSize,
                                                      Timestamp fromTime, Timestamp toTime);

    @Query(value = "select r.*\n" +
            "from rooms r\n" +
            "join softskills s on s.id = r.softskill_id and s.delete_flag = false\n" +
            "where r.softskill_id = :softSkillId and r.id = :roomId\n" +
            "and r.is_lock = FALSE AND r.delete_flag = false ", nativeQuery = true)
    Optional<Rooms> findByIdAndSoftSkillAndDeleteFlag(Long softSkillId, Long roomId);

    Optional<Rooms> findByIdAndDeleteFlag(Long id, Boolean deleteFlag);

    @Query(value = "WITH task_recent AS (WITH t AS ( SELECT\n" +
            "        rt.room_id,\n" +
            "        MAX(rt.created_date) AS created_date \n" +
            "    FROM\n" +
            "        rooms_task rt \n" +
            "    JOIN\n" +
            "        tasks t \n" +
            "            ON t.id = rt.task_id \n" +
            "    WHERE\n" +
            "        rt.delete_flag = FALSE \n" +
            "    GROUP BY\n" +
            "        rt.room_id) SELECT\n" +
            "        t.room_id,\n" +
            "        rt2.task_id,\n" +
            "        t2.name AS taskName,\n" +
            "        rt2.start_time \n" +
            "    FROM\n" +
            "        t \n" +
            "    JOIN\n" +
            "        rooms_task rt2 \n" +
            "            ON t.room_id = rt2.room_id \n" +
            "    JOIN\n" +
            "        tasks t2 \n" +
            "            ON t2.id = rt2.task_id \n" +
            "            AND t2.delete_flag = FALSE \n" +
            "    WHERE\n" +
            "        rt2.created_date = t.created_date )  SELECT\n" +
            "        ROW_NUMBER() OVER () AS numRow,\n" +
            "        r.id AS roomId,\n" +
            "        r.name AS roomName,\n" +
            "        s.\"name\" AS softSkill,\n" +
            "        tc.task_id AS taskId,\n" +
            "        CASE \n" +
            "           WHEN tc.task_id IS NULL THEN 'Chưa xác nhận nhiệm vụ'\n" +
            "           ELSE tc.taskName\n" +
            "        END AS taskName,\n" +
            "        CAST(tc.start_time AS TIMESTAMP) AS startTime,\n" +
            "        count(uri.id) AS userOnline,\n" +
            "        r.room_size AS roomSize,\n" +
            "        r.is_lock AS isLock,\n" +
            "        r.description \n" +
            "    FROM\n" +
            "        rooms r \n" +
            "    LEFT JOIN\n" +
            "        task_recent tc \n" +
            "            ON tc.room_id = r.id \n" +
            "    JOIN\n" +
            "        softskills s \n" +
            "            ON s.id = r.softskill_id \n" +
            "            AND s.delete_flag = FALSE \n" +
            "    LEFT JOIN\n" +
            "        user_room_info uri \n" +
            "            ON  uri.room_id = r.id \n" +
            "    WHERE\n" +
            "        r.delete_flag = :deleteFlag \n" +
            "    GROUP BY\n" +
            "        r.id,\n" +
            "        r.name,\n" +
            "        s.name,\n" +
            "        tc.task_id,\n" +
            "        tc.taskName,\n" +
            "        tc.start_time,\n" +
            "        r.room_size,\n" +
            "        r.is_lock,\n" +
            "        r.description ", nativeQuery = true)
    List<AdminRoomsMapping> getRoomsByDeleteFlag(Boolean deleteFlag);

    List<Rooms> findAllByDeleteFlag(Boolean deleteFlag);

    @Query(value = "SELECT\n" +
            "r.id AS roomId,\n" +
            "count(uri.join_status) AS countUserOnline,\n" +
            "r.room_code AS roomCode,\n" +
            "r.name AS roomName,\n" +
            "r.room_size AS roomSize,\n" +
            "r.is_lock AS isLock, \n" +
            "rt.task_id AS taskId\n" +
            "FROM\n" +
            "rooms r\n" +
            "JOIN rooms_task rt ON\n" +
            "rt.room_id = r.id\n" +
            "AND rt.delete_flag = FALSE\n" +
            "LEFT JOIN user_room_info uri ON\n" +
            "uri.room_id = r.id\n" +
            "AND uri.delete_flag = FALSE\n" +
            "AND uri.is_joined = TRUE\n" +
            "AND uri.join_status = TRUE\n" +
            "WHERE\n" +
            "r.delete_flag = FALSE\n" +
            "GROUP BY\n" +
            "r.id, rt.task_id,r.room_code,r.name,r.room_size,r.is_lock,rt.task_id", nativeQuery = true)
    List<RoomShortMapping> getRoomsShortMapping();

    @Query(value = "SELECT\n" +
            "            r.id AS roomId,\n" +
            "            count(uri.join_status) AS countUserOnline,\n" +
            "            r.room_code AS roomCode,\n" +
            "            r.name AS roomName,\n" +
            "            r.description,\n" +
            "            rt.softskill_id as softSkillId,\n" +
            "            s.\"name\" as softSkillName,\n" +
            "            r.created_by as createdById,\n" +
            "            r.room_size AS roomSize,\n" +
            "            r.is_lock AS isLock, \n" +
            "            rt.task_id AS taskId\n" +
            "            FROM\n" +
            "            rooms r\n" +
            "            JOIN rooms_task rt ON\n" +
            "            rt.room_id = r.id\n" +
            "            AND rt.delete_flag = FALSE\n" +
            "            LEFT JOIN user_room_info uri ON\n" +
            "            uri.room_id = r.id\n" +
            "            AND uri.delete_flag = FALSE\n" +
            "            AND uri.is_joined = TRUE\n" +
            "            AND uri.join_status = true\n" +
            "            join softskills s on rt.softskill_id = s.id \n" +
            "            WHERE\n" +
            "            r.delete_flag = false and\n" +
            "            rt.status_id = 2\n" +
            "            and rt.task_id = :taskId\n" +
            "            and r.created_by <> :userId\n" +
            "            GROUP BY\n" +
            "            r.id, rt.task_id,r.room_code,r.name,r.description,\n" +
            "            rt.softskill_id,\n" +
            "            s.\"name\",\n" +
            "            r.created_by,r.room_size,r.is_lock,rt.task_id\n" +
            "            limit 3", nativeQuery = true)
    List<RoomsMapping> getTop3DoingRoom(Long taskId, Long userId);

    @Query(value = "with w as(\n" +
            "select r.id AS roomId,\n" +
            "        count(uri.join_status) AS countUserOnline\n" +
            "from rooms r \n" +
            "left join user_room_info uri on r.id = uri.room_id \n" +
            "where r.delete_flag = false and uri.delete_flag = false and uri.is_joined = true and uri.join_status = true\n" +
            "group by r.id\n" +
            ")\n" +
            "select \n" +
            "\t\tr.id AS roomId,\n" +
            "        CASE   \n" +
            "            WHEN w.countUserOnline IS NULL THEN 0\n" +
            "            ELSE w.countUserOnline\n" +
            "        END AS countUserOnline,\n" +
            "        r.room_code AS roomCode,\n" +
            "        r.name AS roomName,\n" +
            "        r.description,\n" +
            "        r.softskill_id AS softSkillId,\n" +
            "        s.name AS softSkillName,\n" +
            "        r.room_size AS roomSize,\n" +
            "        r.is_lock AS isLock,\n" +
            "        r.created_by AS createById,\n" +
            "        r.created_date AS startTime,\n" +
            "        rt.task_id AS TaskId,\n" +
            "        CASE   \n" +
            "            WHEN rt.task_id IS NULL THEN 'Chưa xác nhận nhiệm vụ'   \n" +
            "            ELSE t.\"name\" \n" +
            "        END AS taskName \n" +
            "from rooms r \n" +
            "left join rooms_task rt on r.id = rt.room_id \n" +
            "left join user_room_info uri on r.id = uri.room_id \n" +
            "left join w on r.id = w.roomId\n" +
            "join softskills s on rt.softskill_id = s.id \n" +
            "join tasks t on rt.task_id = t.id\n" +
            "where r.delete_flag = false \n" +
            "and r.created_by <> :userId\n" +
            "and uri.user_id = :userId\n" +
            "and uri.is_joined = true\n" +
            "AND (rt.start_time is null or rt.start_time <= now())\n" +
            "GROUP BY\n" +
            "        r.id,\n" +
            "        w.countUserOnline,\n" +
            "        rt.task_id,\n" +
            "        t.name,\n" +
            "        r.room_code,\n" +
            "        r.id,\n" +
            "        r.room_code,\n" +
            "        r.name,\n" +
            "        r.description,\n" +
            "        r.softskill_id,\n" +
            "        s.name,\n" +
            "        r.id,\n" +
            "        r.room_code,\n" +
            "        r.name,\n" +
            "        r.description,\n" +
            "        r.softskill_id,\n" +
            "        r.room_size,\n" +
            "        r.is_lock,\n" +
            "        r.created_by,\n" +
            "        r.created_date", nativeQuery = true)
    List<RoomsMapping> getActiveRoomIsJoined(Long userId);

    @Query(value = "WITH \n" +
            "            countRegister_tmp AS (\n" +
            "            SELECT\n" +
            "            rt1.room_task_id AS rtId,\n" +
            "            count(rt1.id) AS countRegister\n" +
            "            FROM\n" +
            "            room_task_register_info rt1\n" +
            "            GROUP BY\n" +
            "            rt1.room_task_id)\n" +
            "            select distinct \n" +
            "            r.id AS roomId,\n" +
            "            countRegister_tmp.countRegister AS countUserRegister,\n" +
            "            r.room_code AS roomCode,\n" +
            "            r.name AS roomName,\n" +
            "            r.description,\n" +
            "            r.softskill_id AS softSkillId,\n" +
            "            r.room_size AS roomSize,\n" +
            "            r.is_lock AS isLock,\n" +
            "            r.created_by AS createById,\n" +
            "            rt3.start_time AS startTime,\n" +
            "            t.id as TaskId,\n" +
            "            t.name as taskName\n" +
            "            FROM\n" +
            "            rooms r\n" +
            "            JOIN rooms_task rt3 ON\n" +
            "            rt3.room_id = r.id\n" +
            "            join tasks t on t.id = rt3.task_id \n" +
            "            AND rt3.delete_flag = FALSE\n" +
            "            JOIN countRegister_tmp ON\n" +
            "            countRegister_tmp.rtId = rt3.id\n" +
            "            JOIN room_task_register_info rtri ON\n" +
            "            rtri.room_task_id = rt3.id\n" +
            "            AND rtri.delete_flag = false \n" +
            "            where r.delete_flag = false\n" +
            "            and rt3.start_time > NOW()\n" +
            "            and r.id not in (\n" +
            "            select rt.room_id  from rooms r\n" +
            "            join rooms_task rt on r.id = rt.room_id\n" +
            "            join room_task_register_info rtri on rtri.room_task_id = rt.id\n" +
            "            where rtri.created_by = :currentUser)\n" +
            "            GROUP BY\n" +
            "            r.id,\n" +
            "            t.id,\n" +
            "            t.name,\n" +
            "            countRegister_tmp.countRegister,\n" +
            "            rt3.start_time,r.id, countRegister_tmp.countRegister, r.room_code, r.name, r.description, r.softskill_id, r.room_size, r.is_lock, r.created_by, rt3.start_time", nativeQuery = true)
    List<RoomsMapping> getRoomsBookingCommunity(Long currentUser);
}
