package fpt.lhlvb.softskillcommunity.repository;

import fpt.lhlvb.softskillcommunity.entity.UserRoomInfo;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.FriendMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoomInfoRepository extends JpaRepository<UserRoomInfo, Long> {
    @Query(value = "SELECT * FROM \"user_room_info\"\n" +
            "WHERE user_id = :id AND delete_flag = :deleteFlag AND is_joined = :isJoined\n" +
            "ORDER BY updated_date DESC\n" +
            "LIMIT :limit", nativeQuery = true)
    Optional<UserRoomInfo> getRecentRoom(@Param("id") Long id, @Param("deleteFlag") Boolean deleteFlag, @Param("isJoined") Boolean isJoined, @Param("limit") int limit);

    Optional<UserRoomInfo> findByRoomIdAndDeleteFlagAndJoinStatusAndUserId(Long roomId, Boolean deleteFlag, Boolean joinStatus,Long userId);

    List<UserRoomInfo> findByRoomIdAndJoinStatusAndIsOldMemberAndDeleteFlag(Long roomId, Boolean joinStatus, Boolean isOldMember, Boolean deleteFlag);

    @Query(value = "SELECT u.\"id\", u.\"name\", u.nickname, u.picture\n," +
            "CASE \n" +
            "WHEN u.id IN (SELECT f.user_recived_id FROM users t JOIN friend_relation f on t.id = f.user_recived_id WHERE f.delete_flag = :deleteFlag AND f.status = 0 AND f.user_sent_id = :userId) THEN 'isSent'\n" +
            "WHEN u.id IN (SELECT f.user_sent_id FROM users t JOIN friend_relation f on t.id = f.user_sent_id WHERE f.delete_flag = :deleteFlag AND f.status = 0 AND f.user_recived_id = :userId) THEN 'isReceived'\n" +
            "WHEN u.id IN (SELECT f.user_recived_id FROM users t JOIN friend_relation f on t.id = f.user_recived_id WHERE f.delete_flag = :deleteFlag AND f.status = 1 AND f.user_sent_id = :userId\n" +
            "UNION \n" +
            "SELECT f.user_sent_id FROM users t JOIN friend_relation f on t.id = f.user_sent_id WHERE f.delete_flag = :deleteFlag AND f.status = 1 AND f.user_recived_id = :userId) THEN 'isFriend'\n" +
            "ELSE 'nothing'\n" +
            "END status\n" +
            "FROM user_room_info a JOIN users u ON a.user_id = u.\"id\"\n" +
            "WHERE a.delete_flag = :deleteFlag AND a.is_joined = :isJoined AND a.room_id = :id AND a.user_id <> :userId", nativeQuery = true)
    List<FriendMapping> getUsersInRoom(@Param("id") Long id, @Param("deleteFlag") Boolean deleteFlag, @Param("isJoined") Boolean isJoined, @Param("userId") Long userId);

    Optional<UserRoomInfo> findByUserIdAndRoomIdAndDeleteFlag(Long userId, Long roomId, boolean deleteFlag);

    Optional<UserRoomInfo> findByUserIdAndRoomIdAndJoinStatusAndDeleteFlag(Long userId, Long roomId, Boolean joinStatus, Boolean deleteFlag);

    @Query(value = "SELECT\n" +
            "\turi.user_id,\n" +
            "\turi.join_status\n" +
            "FROM\n" +
            "\trooms r\n" +
            "JOIN user_room_info uri ON\n" +
            "\turi.room_id = r.id\n" +
            "\tAND uri.join_status = TRUE\n" +
            "\tAND uri.delete_flag = FALSE\n" +
            "WHERE\n" +
            "\tr.id = :roomId\n" +
            "\tAND r.is_lock = FALSE\n" +
            "\tAND r.delete_flag = FALSE\n", nativeQuery = true)
    List<UserRoomInfo> getUsersOnlineInRoomByRoomId(Long roomId);

    List<UserRoomInfo> findByRoomIdAndDeleteFlagAndJoinStatus(Long roomId, Boolean deleteFlagFalse, Boolean joinStatusTrue);
}
