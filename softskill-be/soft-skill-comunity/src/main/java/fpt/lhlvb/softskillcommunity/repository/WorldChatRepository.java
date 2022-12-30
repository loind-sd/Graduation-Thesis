package fpt.lhlvb.softskillcommunity.repository;

import fpt.lhlvb.softskillcommunity.entity.WorldChat;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.WorldChatMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorldChatRepository extends JpaRepository<WorldChat, Long> {
    @Query(value = "WITH t AS( SELECT\n" +
            "        a.id as worldChatId,\n" +
            "        a.content as worldChatContent,\n" +
            "        a.created_by as createdBy,\n" +
            "        u.mail as createdMail,\n" +
            "        u.\"name\" as createdName,\n" +
            "        u.nickname as createdNickName,\n" +
            "        u.picture as createdPicture,\n" +
            "        a.created_date as createdTime,\n" +
            "       CASE \n" +
            "           WHEN u.id IN (SELECT f.user_recived_id FROM users s JOIN friend_relation f on s.id = f.user_recived_id WHERE f.delete_flag = :deleteFlag AND f.user_sent_id = :userId\n" +
            "       UNION \n" +
            "           SELECT f.user_sent_id FROM users s JOIN friend_relation f on s.id = f.user_sent_id WHERE f.delete_flag = :deleteFlag AND f.user_recived_id = :userId) THEN 'isFriend'\n" +
            "       ELSE 'nothing' END status,\n" +
            " CASE \n" +
            "WHEN to_char(a.created_date, 'YYYY-MM-dd HH24:MI:SS') = to_char(a.updated_date, 'YYYY-MM-dd HH24:MI:SS') THEN\n" +
            "''\n" +
            "ELSE\n" +
            "' (đã chỉnh sửa)'\n" +
            "END as isFix\n" +
            "    FROM\n" +
            "        world_chat a \n" +
            "    JOIN\n" +
            "        users u \n" +
            "            ON a.created_by = u.\"id\" \n" +
            "    WHERE\n" +
            "        a.delete_flag = :deleteFlag AND u.delete_flag = :deleteFlag\n AND a.channel_id = :channelId AND a.id < :msgId" +
            "    ORDER BY\n" +
            "        a.created_date DESC LIMIT :limit OFFSET :offset)  " +
            "   SELECT\n" +
            "        t.worldChatId,\n" +
            "        t.worldChatContent,\n" +
            "        t.createdBy,\n" +
            "        t.createdName,\n" +
            "        t.createdMail,\n" +
            "        t.createdNickName,\n" +
            "        t.createdPicture,\n" +
            "        t.createdTime,\n" +
            "t.status," +
            " t.isFix\n" +
            "    FROM\n" +
            "        t \n" +
            "    ORDER BY\n" +
            "        t.createdTime", nativeQuery = true)
    List<WorldChatMapping> getWithLimit(@Param("limit") int limit, @Param("deleteFlag") boolean deleteFlag, @Param("userId") Long userId, @Param("msgId") Long msgId, @Param("offset") int offset, @Param("channelId") Long channelId);

    Optional<WorldChat> findByIdAndDeleteFlag(Long id, boolean deleteFlag);

    @Query(value = "with t as (\n" +
            "select id, trim(both ' ' from split_part(content, '|', 2)) as roomId from world_chat where channel_id = 2 and created_date BETWEEN (CURRENT_DATE) AND (CURRENT_DATE + 1) AND delete_flag = false AND created_by = :userId)\n" +
            "\n" +
            "SELECT EXISTS(SELECT 1 FROM world_chat a join t on a.id = t.id  WHERE created_date BETWEEN (CURRENT_DATE) AND (CURRENT_DATE + 1) AND delete_flag = false AND created_by = :userId and t.roomId = CAST(:roomId as varchar))", nativeQuery = true)
    Optional<Boolean> checkExistInvitation(@Param("userId") Long userId, @Param("roomId") Long roomId);

}
