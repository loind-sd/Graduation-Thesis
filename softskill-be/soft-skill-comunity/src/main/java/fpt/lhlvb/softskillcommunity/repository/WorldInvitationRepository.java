package fpt.lhlvb.softskillcommunity.repository;

import fpt.lhlvb.softskillcommunity.entity.WorldInvitation;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.WorldInvitationMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorldInvitationRepository extends JpaRepository<WorldInvitation, Long> {
    @Query(value = "WITH t AS( SELECT\n" +
            "        a.id as worldInvitationId,\n" +
            "        a.room_id as roomId,\n" +
            "        a.created_by as createdBy,\n" +
            "        u.mail as createdMail,\n" +
            "        u.\"name\" as createdName,\n" +
            "        u.nickname as createdNickName,\n" +
            "        a.created_date as createdTime,\n" +
            "       CASE \n" +
            "           WHEN u.id IN (SELECT f.user_recived_id FROM users s JOIN friend_relation f on s.id = f.user_recived_id WHERE f.delete_flag = :deleteFlag AND f.user_sent_id = :userId\n" +
            "       UNION \n" +
            "           SELECT f.user_sent_id FROM users s JOIN friend_relation f on s.id = f.user_sent_id WHERE f.delete_flag = :deleteFlag AND f.user_recived_id = :userId) THEN 'isFriend'\n" +
            "       ELSE 'nothing' END status\n" +
            "    FROM\n" +
            "        world_invitation a \n" +
            "    JOIN\n" +
            "        users u \n" +
            "            ON a.created_by = u.\"id\" \n" +
            "    WHERE\n" +
            "        a.delete_flag = :deleteFlag AND u.delete_flag = :deleteFlag\n AND a.channel_id = :channelId AND a.id < :msgId" +
            "    ORDER BY\n" +
            "        a.created_date DESC LIMIT :limit OFFSET :offset)  " +
            "   SELECT\n" +
            "        t.worldInvitationId,\n" +
            "        t.roomId,\n" +
            "        t.createdBy,\n" +
            "        t.createdName,\n" +
            "        t.createdMail,\n" +
            "        t.createdNickName,\n" +
            "        t.createdTime,\n" +
            "t.status\n" +
            "    FROM\n" +
            "        t \n" +
            "    ORDER BY\n" +
            "        t.createdTime", nativeQuery = true)
    List<WorldInvitationMapping> getWithLimit(@Param("limit") int limit, @Param("deleteFlag") boolean deleteFlag, @Param("userId") Long userId, @Param("msgId") Long msgId, @Param("offset") int offset, @Param("channelId") Long channelId);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM world_invitation WHERE created_date BETWEEN (CURRENT_DATE) AND (CURRENT_DATE + 1) AND delete_flag = :deleteFlag AND created_by = :userId AND room_id = :roomId)", nativeQuery = true)
    Optional<Boolean> checkExistInvitation(@Param("deleteFlag") boolean deleteFlag, @Param("userId") Long userId, @Param("roomId") Long roomId);

    Optional<WorldInvitation> findByIdAndDeleteFlag(Long id, boolean deleteFlag);
}
