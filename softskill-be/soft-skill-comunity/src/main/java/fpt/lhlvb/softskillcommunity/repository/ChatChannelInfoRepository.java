package fpt.lhlvb.softskillcommunity.repository;

import fpt.lhlvb.softskillcommunity.entity.ChatChannelInfo;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.FriendMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatChannelInfoRepository extends JpaRepository<ChatChannelInfo, Long> {
    Optional<ChatChannelInfo> findByChatChannelIdAndDeleteFlagAndUserId(Long chatId, Boolean deleteFlag, Long userId);

    @Query(value = "SELECT u.id, u.name, u.nickname, u.picture\n" +
            "FROM friend_relation a JOIN users u ON a.user_recived_id = u.id\n" +
            "WHERE a.status = 1 AND a.delete_flag = false and a.user_sent_id = :id\n" +
            "AND u.\"id\" NOT IN (SELECT b.user_id\n" +
            "FROM chat_channel a \n" +
            "JOIN chat_channel_info b on a.\"id\" = b.chat_channel_id\n" +
            "WHERE a.delete_flag = FALSE and b.delete_flag = FALSE AND a.\"id\" = :roomId)\n" +
            "UNION\n" +
            "SELECT u.id, u.name, u.nickname, u.picture\n" +
            "FROM friend_relation a JOIN users u ON a.user_sent_id = u.id\n" +
            "WHERE a.status = 1 AND a.delete_flag = false and a.user_recived_id = :id\n" +
            "AND u.\"id\" NOT IN (SELECT b.user_id\n" +
            "FROM chat_channel a \n" +
            "JOIN chat_channel_info b on a.\"id\" = b.chat_channel_id\n" +
            "WHERE a.delete_flag = FALSE and b.delete_flag = FALSE AND a.\"id\" = :roomId)", nativeQuery = true)
    List<FriendMapping> getFriendToAdd(@Param("id") Long id, @Param("roomId") Long room);

    @Query(value = "SELECT u.id, u.name, u.nickname, u.picture\n" +
            "from users u\n" +
            "join chat_channel_info c on u.\"id\" = c.user_id and u.delete_flag = false and c.delete_flag = false\n" +
            "where c.user_id <> :id and c.chat_channel_id = :roomId", nativeQuery = true)
    List<FriendMapping> getAllMemberInRoom(@Param("id") Long id, @Param("roomId") Long roomId);

    Optional<ChatChannelInfo> findByChatChannelIdAndUserIdAndDeleteFlag(Long roomId, Long userID, Boolean deleteFlag);
}
